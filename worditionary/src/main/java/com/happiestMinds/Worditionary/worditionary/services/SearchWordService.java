package com.happiestMinds.Worditionary.worditionary.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchWordService {
	public boolean searchWord(String word) throws IOException {
		word = word.trim();
		File f = ResourceUtils.getFile("dictionary.json");
		String content = new String(Files.readAllBytes(f.toPath()));
		JsonParser parser = JsonParserFactory.getJsonParser();
		Map<String, Object> map = parser.parseMap(content);
		
		ArrayList<String> strList = new ArrayList<String>();
		ArrayList<Integer> occurList = new ArrayList<Integer>();
		for(Map.Entry<String, Object> entr:map.entrySet()) {
			strList.add(entr.getKey());
			occurList.add((Integer)entr.getValue());
		}
		//search
		for(int i=0; i<strList.size(); i++) {
			if(strList.get(i).equalsIgnoreCase(word)) {
				//change occurance value
				int oldval = occurList.get(i);
				int newVal = oldval + 1;
				occurList.set(i, newVal);
				//write to json
				map.put(strList.get(i), newVal);
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(f, map);
				//sort the data in the relevant new order
				sortArr(occurList, 0, strList.size()-1, strList);
				return true;
			}
			
		}
		return false;
	}
	public void sortArr(ArrayList<Integer> arr, int l, int r, ArrayList<String> strArr) 
    { 
        if (l < r) 
        { 
            int m = (l+r)/2; 
  
            sortArr(arr, l, m, strArr); 
            sortArr(arr , m+1, r, strArr); 
  
            merge(arr, l, m, r, strArr); 
        } 
    } 
	public void merge(ArrayList<Integer> numArr, int l, int m, int r, ArrayList<String> strArr) {
		int n1 = m - l + 1; 
        int n2 = r - m; 
        
        int L[] = new int [n1]; 
        int R[] = new int [n2]; 
        String strL[] = new String [n1];
        String strR[] = new String [n2];
        
        for (int i=0; i<n1; ++i) {
        	L[i] = numArr.get(l+i); 
        	strL[i] = strArr.get(l+i);
        } 
        for (int j=0; j<n2; ++j) {
        	R[j] = numArr.get(m + 1+ j);
        	strR[j] = strArr.get(m + 1 + j);
        }
            
        int i = 0, j = 0;   
        int k = l; 
        while (i < n1 && j < n2) 
        { 
            if (L[i] >= R[j]) 
            { 
                numArr.set(k, L[i]); 
                strArr.set(k, strL[i]);
                i++; 
            } 
            else
            { 
                numArr.set(k, R[j]); 
                strArr.set(k, strR[j]);
                j++; 
            } 
            k++; 
        } 
  
        while (i < n1) 
        { 
            numArr.set(k, L[i]); 
            strArr.set(k, strL[i]);
            i++; 
            k++; 
        } 
  
        while (j < n2) 
        { 
            numArr.set(k, R[j]); 
            strArr.set(k, strR[j]);
            j++; 
            k++; 
        } 
	}
	
	public HashMap<String, String> searchWords(ArrayList<String> words) throws IOException{
		HashMap<String, String> result = new HashMap<>();
		for(String str:words) {
			str = str.trim();
			result.put(str, searchWord(str)?"Word found":"Word not found");
		}
		return result;
	}
}
