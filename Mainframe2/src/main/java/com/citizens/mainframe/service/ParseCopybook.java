package com.citizens.mainframe.service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
import net.sf.JRecord.detailsBasic.IItemDetails;

@Component
public class ParseCopybook {
	
	public List<HashMap<String, String>> getCopybookAsListOfMap(String copybook) throws InterruptedException {
		ArrayList<HashMap<String,String>> intermediate_map = copybookToIntermediate(copybook);
		return intermediate_map;
	}
	
	public ArrayList<HashMap<String, String>> copybookToIntermediate(String copybookName)
	{
		System.out.println("parsing copybook " + copybookName);
		try {
			ClassPathResource resource = new ClassPathResource(copybookName);
			InputStream inputStream = resource.getInputStream();
			ICobolIOBuilder iob = JRecordInterface1.COBOL.newIOBuilder(inputStream,copybookName);
			RecordDetail record = iob.getLayout().getRecord(0);
			IItemDetails root = record.getCobolItems().get(0);
			return getIntermediateList(root,new ArrayList<>(), new HashMap<>());
		}
		catch(Exception e) {
			System.out.println("exception occur : ParseCopybook->copyBookToIntermediate() " + e);
			return null;  
		}
	}
	
	public ArrayList<HashMap<String, String>> getIntermediateList(IItemDetails items, ArrayList<HashMap<String, String>> fields, HashMap<String, Integer> allNames) throws InterruptedException {
	    if (items == null) {
	        return null;
	    }

	    for (IItemDetails i : items.getChildItems()) {
	        String fieldName = i.getFieldName();

	        if (i.isLeaf()) {
	            if (!i.isFieldRedefined()) {
	                if (i.isFieldRedefined()) {
	                    fieldName = i.getRedefinesFieldName();
	                }

	                if (!allNames.containsKey(fieldName)) {
	                    allNames.put(fieldName, 0);
	                } else {
	                    int occurrence = allNames.get(fieldName) + 1;
	                    allNames.put(fieldName, occurrence);
	                    fieldName = String.format("%s (%d)", fieldName, occurrence);
	                }

	                HashMap<String, String> fieldData = getRecordHashMap(i, fieldName);
	                fields.add(fieldData);
	            }
	        } else {
	            int occurs = i.getOccurs();
	            if (occurs < 0) {
	                occurs = 1;
	            }

	            for (int j = 0; j < occurs; j++) {
	                // Corrected recursive call: pass 'i' instead of 'items'
	                getIntermediateList(i, fields, allNames);
	            }
	        }
	    }
	    return fields;
	}
	
	/***********************************
	*get cobol variable metadata as hashmap
	*****************************************/
	public HashMap<String, String> getRecordHashMap(IItemDetails item, String fieldName){
		HashMap<String, String> hm = new HashMap();
		
		hm.put("name", fieldName);
		hm.put("storage_length", Integer.toString(item.getStorageLength()));
		hm.put("display_length", Integer.toString(item.getDisplayLength()));
		hm.put("type_id", Integer.toString(item.getType()));
		hm.put("pic", item.getPicture());
		
		return hm;
	}
	
}
