package com.zemasterkrom.videogameslibraryapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class VideoGameLibraryService {

	public static void main(String[] args) {
		SpringApplication.run(VideoGameLibraryService.class, args);
	}
}
