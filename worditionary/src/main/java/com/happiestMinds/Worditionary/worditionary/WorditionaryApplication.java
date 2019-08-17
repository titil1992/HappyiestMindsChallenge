package com.happiestMinds.Worditionary.worditionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.happiestMinds.Worditionary.worditionary.util.FileStorageProperty;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperty.class
})
public class WorditionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorditionaryApplication.class, args);
	}

}
