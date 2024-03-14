package com.citizens.mainframe.service;



import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class EbcdicToJson {
	
	public static final String LATIN_1_CHARSET = "ISO-8859-1";
	public static final String EBCDIC_CHARSET = String.format("CP500");
	
	
	
    // Method to convert EBCDIC byte array to JSON
    public String convertToJSON(byte[] ebcdicData, List<Map<String, String>> copybook) {
//    	System.out.println("length os message is : "+ ebcdicData.length);
        String asciiData = new String(ebcdicData, Charset.forName("IBM500"));
//        System.out.println("converted stirng is : " + asciiData);
        StringBuilder jsonBuilder = new StringBuilder("{");

        
        int startIndex = 0;
        for (Map<String, String> field : copybook) {
            String fieldName = field.get("name");
            int fieldLength = Integer.parseInt(field.get("display_length"));
            String fieldValue = asciiData.substring(startIndex, startIndex + fieldLength).trim();
            
            // Add field to JSON
            jsonBuilder.append("\"").append(fieldName).append("\": \"").append(fieldValue).append("\", ");

            startIndex += fieldLength;
        }

        // Remove trailing comma and space
        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 2);
        }

        // Close JSON object
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}
