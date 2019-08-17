package com.happiestMinds.Worditionary.worditionary.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public class SearchWordService {
	public boolean searchWord(String word) {
		return true;
	}
	
	public HashMap<String, String> searchWords(ArrayList<String> words){
		return new HashMap<>();
	}
}
