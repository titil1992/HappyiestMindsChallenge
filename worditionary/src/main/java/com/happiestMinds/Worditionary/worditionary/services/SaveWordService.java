package com.happiestMinds.Worditionary.worditionary.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
            String strValue = lineParts[1];
            if(strValue.matches("<.*>"))
            	wordsList.add(strValue);
		}
		
		File f = ResourceUtils.getFile("dictionary.json");
		String content = new String(Files.readAllBytes(f.toPath()));
		JsonParser parser = JsonParserFactory.getJsonParser();
		Map<String, Object> map = parser.parseMap(content);
		for(String st:wordsList) {
			if(!map.containsKey(st))
				map.put(st, new Integer(0));
		}
		FileWriter filew = new FileWriter(f);
		ObjectMapper mapper = new ObjectMapper();
		filew.write(mapper.writeValueAsString(map));
		return "";
	}
}
