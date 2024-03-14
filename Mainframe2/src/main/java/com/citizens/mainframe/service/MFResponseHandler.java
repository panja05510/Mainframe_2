package com.citizens.mainframe.service;

import java.nio.charset.Charset;
import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MFResponseHandler {

	public HashMap<String, String> populateMap() {

		HashMap<String, String> cobolConstants = new HashMap<>();
		cobolConstants.put("ERROR_MESSAGE", "error occured while processing request");
		cobolConstants.put("SDSTMYM1-EXT-AMT", "3578");
		cobolConstants.put("SDSTMYM1-EXT-AMT-LIT", "ACAI");
		cobolConstants.put("ACAI-SERVICE-NAME", "ACCOUNT CLOSING BALANCE INQUIRE");
		cobolConstants.put("ACAI-SESSION-ID", "UNKNOWN");
		cobolConstants.put("ACAI-SESSION-ARCHIVE", "Y");
		return cobolConstants;
	}

	public List<String> hexFields() {
		return new ArrayList<String>();
	}

	public byte[] JsonToEbc(boolean err) {
		HashMap<String, String> cobolConstants = populateMap();
		List<String> hexFields = hexFields();
		String cobolFile = "";
			if(err) {
				cobolFile = "customerError.cpy";
				System.out.println("Error generated...");
			}
			else {
				System.out.println("Response Generating...");
				cobolFile = "customerResponse.cpy";
			}	

		JsonToEbcdic j2e = new JsonToEbcdic(cobolConstants, hexFields);
		try {
			byte[] content = j2e.request2mainframe(cobolFile);
			String asciiData = new String(content, Charset.forName("cp500"));
	        System.out.println("ascii data ----->" + asciiData + "/////");
			return content;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
