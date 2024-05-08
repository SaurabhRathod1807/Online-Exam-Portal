package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan()
@ComponentScan()
public class SpringOnlineExamProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOnlineExamProjectApplication.class, args);
	}

}
