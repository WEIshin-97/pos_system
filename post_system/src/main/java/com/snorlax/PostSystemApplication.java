package com.snorlax;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.snorlax.repository.mybatis")
public class PostSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostSystemApplication.class, args);
	}

}
