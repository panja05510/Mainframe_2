package com.citizens.mainframe.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MFRequestHandler {
	
	@Autowired
	EbcdicToJson e2j;
	
	@Autowired
	ParseCopybook parseCopybook;
	
	public String readMessageBody(byte[] edcdic) {
		
		String copybook = "customerRequest.cpy";
		try {
			List<HashMap<String, String>> copybookAsListOfMap = parseCopybook.getCopybookAsListOfMap(copybook);
			List<Map<String, String>> copybookAsListOfHashmap = printAndConvertCopybook(copybookAsListOfMap);
			String messageBodyAsJsonString = e2j.convertToJSON(edcdic, copybookAsListOfHashmap);
			return messageBodyAsJsonString;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public List<Map<String, String>> printAndConvertCopybook(List<HashMap<String, String>> copybook) {
		List<Map<String, String>> copybookToListOfMap = new ArrayList<>();
		//System.out.println("----------------------------------------------------------------");
		for (HashMap<String, String> field : copybook) {
			Map<String, String> tempMap = new HashMap<>();

			//System.out.println("Field:");
			for (Map.Entry<String, String> entry : field.entrySet()) {
				tempMap.put(entry.getKey(), entry.getValue());
				//System.out.println(entry.getKey() + ": " + entry.getValue());
			}
			copybookToListOfMap.add(tempMap);
			//System.out.println();
		}
		//System.out.println("--------------------------------------------------------------------------------");
		return copybookToListOfMap;
	}
	
}
