package com.happiestMinds.Worditionary.worditionary.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SaveWordService {
	
	public String textFileToWords(Resource res) throws FileNotFoundException, IOException {
		List<String> wordsList = new ArrayList<>();
		Scanner sc = new Scanner(res.getFile());
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
            String[] lineParts = line.split("\\s+");
            for(String strValue:lineParts) {
            	if(strValue.matches("^[a-zA-Z]+$"))
                	wordsList.add(strValue);
            } 
		}
		
		File f = ResourceUtils.getFile("dictionary.json");
		String content = new String(Files.readAllBytes(f.toPath()));
		JsonParser parser = JsonParserFactory.getJsonParser();
		Map<String, Object> map = new HashMap<String, Object>();
		if(!content.equals(null) && !content.equals("")) {
			map = parser.parseMap(content);
		}
		
		for(String st:wordsList) {
			if(!map.containsKey(st))
				map.put(st, new Integer(0));
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(f, map);
		return "";
	}
}
