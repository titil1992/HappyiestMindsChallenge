package com.happiestMinds.Worditionary.worditionary.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

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
		return "";
	}
}
