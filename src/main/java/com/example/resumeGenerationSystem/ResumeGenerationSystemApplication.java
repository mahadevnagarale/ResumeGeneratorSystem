package com.example.resumeGenerationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan(basePackages = "com.example.resumeGenerationSystem")  // Ensure the base package is correct

public class ResumeGenerationSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResumeGenerationSystemApplication.class, args);
	}
}

