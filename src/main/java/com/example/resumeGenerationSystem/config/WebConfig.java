package com.example.resumeGenerationSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Applies CORS to all endpoints
                .allowedOrigins("http://localhost:3000")  // Specify allowed origins (use "*" for all, but restrict in production)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Specify allowed HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true)  // Allow cookies if needed
                .maxAge(3600);  // Cache the preflight response for 1 hour
    }
}
