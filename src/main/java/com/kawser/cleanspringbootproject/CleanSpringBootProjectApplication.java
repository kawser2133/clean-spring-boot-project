package com.kawser.cleanspringbootproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@EnableCaching
@SpringBootApplication
public class CleanSpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleanSpringBootProjectApplication.class, args);
		log.info("Application started successfully!");
	}

}
