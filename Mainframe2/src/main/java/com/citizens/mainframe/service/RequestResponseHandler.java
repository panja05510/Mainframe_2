package com.citizens.mainframe.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.google.gson.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.ibm.jakarta.jms.JMSMessage;

import jakarta.jms.BytesMessage;

@Service
public class RequestResponseHandler {
	@Autowired
	MFResponseHandler mfRequestHandler;

	@Autowired
	ObjectMapper objectMapper;
	
	private String accNumber = "";
	
	private final JmsTemplate jmsTemplate;
	public static final String EBCDIC_CHARSET = String.format("CP%s", "500");
	public static final String LATIN_1_CHARSET = "ISO-8859-1";
	@Value("${requestQueue}")
	private final String requestQueue;
	@Value("${responseQueue}")
	private final String responseQueue;
	private final ThreadPoolTaskExecutor taskExecutor;
	private  final String errAccount = "\"9999999999999\"";

	@Autowired
	private MFRequestHandler reader;

	public RequestResponseHandler(JmsTemplate jmsTemplate, ThreadPoolTaskExecutor taskExecutor) {
		this.jmsTemplate = jmsTemplate;
		this.taskExecutor = taskExecutor;
	}

	/***********************************************
	 * listen to the request queue if request message is not null, read the request
	 *************************************************/
	@JmsListener(destination = requestQueue)
	public void receiveMessage(JMSMessage receivedJMSMessage) {
		taskExecutor.execute(() -> {
//			System.out.println("thread id --> "+Thread.currentThread());;
			try {
				if (receivedJMSMessage != null) {
					BytesMessage receivedMessage = (BytesMessage) receivedJMSMessage;
					byte[] bytes = new byte[(int) receivedMessage.getBodyLength()];
					receivedMessage.readBytes(bytes);

					String messagebodyAsString = reader.readMessageBody(bytes);
					System.out.println("received message body is ----> " + messagebodyAsString);
					System.out.println("requested Service is : \"" + convertToJson(messagebodyAsString).get("SERVICE-NAME").getAsString() + "\"");
					accNumber = convertToJson(messagebodyAsString).get("ACC-NUMBER").toString();
					System.out.println("account number is : "+ accNumber);
				}

				// response
				replyAsync(receivedJMSMessage);
			} catch (Exception e) {
				System.out.println("exception occur : RequestResponseHandler->receiveMessage() " + e);
			}
		});
	}

	public void replyAsync(JMSMessage receivedMessage) {
		try {
//			Thread.sleep(1000);
			System.out.println("Processing...");
			System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getId());
			jmsTemplate.send(responseQueue, session -> {
				BytesMessage message = session.createBytesMessage();
				try {
					message.writeBytes(processEbcdic());
					System.out.println("response send successfully.");
				} catch (IOException e) {
					System.out.println("exception occur : RequestResponseHandler->replyAsync() " + e);
				}
				message.setJMSCorrelationID(receivedMessage.getJMSCorrelationID());
				return message;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] processEbcdic() throws IOException {
		byte[] jsonToEbc;
		if(accNumber.equals(errAccount)) {
			
			
			System.out.println("*****************************generate error response**********************");
			jsonToEbc = mfRequestHandler.JsonToEbc(true);
		}
		else {
			System.out.println("*****************************generate response**********************  " + accNumber);
			
			jsonToEbc = mfRequestHandler.JsonToEbc(false);
		}
		return jsonToEbc;
	}

	public JsonObject convertToJson(String jsonRequestObject) {
		// Parse the JSON string
		JsonObject jsonObject = JsonParser.parseString(jsonRequestObject).getAsJsonObject();
		return jsonObject;
	}

}
