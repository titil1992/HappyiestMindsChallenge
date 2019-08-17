package com.happiestMinds.Worditionary.worditionary.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.happiestMinds.Worditionary.worditionary.exceptions.MyFileNotFoundException;
import com.happiestMinds.Worditionary.worditionary.services.FileStorageService;
import com.happiestMinds.Worditionary.worditionary.services.SaveWordService;
import com.happiestMinds.Worditionary.worditionary.services.SearchWordService;
import com.happiestMinds.Worditionary.worditionary.util.UploadFileResponse;

@RestController("/words")
public class WorditionaryController {

	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private SearchWordService searchWordService;
	
	@Autowired
	private SaveWordService saveWordService;
	
	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws MyFileNotFoundException, FileNotFoundException, IOException {
		String fileName = fileStorageService.storeFile(file);
		String msg = saveWordService.textFileToWords(fileStorageService.loadFileAsResource(fileName));

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
	}
	
	@GetMapping("/searchWord/{word}")
	public String searchWord(@PathVariable("word") String word) throws IOException {
		if(searchWordService.searchWord(word)) {
			return "Word Found";
		}
		return "Word not Found";
	}
	
	@GetMapping("/searchWords")
	public HashMap<String, String> searchWords(@RequestParam("words") ArrayList<String> words) throws IOException {
		HashMap<String, String> result = searchWordService.searchWords(words);
		return result;
	}
}
