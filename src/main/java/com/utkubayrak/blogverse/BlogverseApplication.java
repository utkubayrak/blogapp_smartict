package com.utkubayrak.blogverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.utkubayrak.blogverse.data.repository")

@SpringBootApplication
public class BlogverseApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogverseApplication.class, args);
	}

}
