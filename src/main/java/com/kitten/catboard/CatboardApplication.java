package com.kitten.catboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CatboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatboardApplication.class, args);
	}

}
