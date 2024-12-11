package com.example.resumeGenerationSystem.controller;

import com.example.resumeGenerationSystem.entity.Resume;
import com.example.resumeGenerationSystem.repository.ResumeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  // Use RANDOM_PORT for integration testing
class ResumeControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;  // Autowired WebTestClient

    @Autowired
    private ResumeRepository resumeRepository;  // Autowired Resume Repository

    @Autowired
    private ObjectMapper objectMapper;  // Autowired ObjectMapper for JSON conversion

    @BeforeEach
    void setUp() {
        resumeRepository.deleteAll();  // Clears the database before each test
    }

    @Test
    void testCreateResume_whenResumeDoesNotExist_thenCreateResume() throws Exception {
        // Create a new Resume object for testing
        Resume resume = new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer");

        // Test the POST request to create a resume
        webTestClient.post()
                .uri("/api/resumes")
                .bodyValue(resume)
                .exchange()
                .expectStatus().isCreated()  // Expect HTTP status 201 Created
                .expectBody()
                .jsonPath("$.fullName").isEqualTo(resume.getFullName())  // Check if fullName matches
                .jsonPath("$.email").isEqualTo(resume.getEmail());  // Check if email matches
    }

    @Test
    void testGetResumeById_success() throws Exception {
        // Save a Resume object in the database for testing
        Resume savedResume = resumeRepository.save(new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer"));

        // Test the GET request to fetch the resume by ID
        webTestClient.get()
                .uri("/api/resumes/{id}", savedResume.getId())  // Use the saved ID in the URL
                .exchange()
                .expectStatus().isOk()  // Expect HTTP status 200 OK
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedResume.getId())  // Check if ID matches
                .jsonPath("$.email").isEqualTo(savedResume.getEmail());  // Check if email matches
    }

    @Test
    void testUpdateResume_whenResumeDoesNotExist_thenThrowException() throws Exception {
        // Create a new Resume object with updated information
        Resume resume = new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer");
        resume.setFullName("Updated Name");
        resume.setEmail("updated@example.com");
        resume.setPhoneNumber("9876543210");
        resume.setSummary("Updated Developer");

        // Test the PUT request to update a resume that doesn't exist
        webTestClient.put()
                .uri("/api/resumes/999")  // Assume ID 999 doesn't exist
                .bodyValue(resume)
                .exchange()
                .expectStatus().isNotFound();  // Expect HTTP status 404 Not Found
    }

    @Test
    void testUpdateResume_whenResumeExists_thenUpdateResume() throws Exception {
        // Create and save a Resume object
        Resume savedResume = new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer");
        resumeRepository.save(savedResume);

        // Update details for the resume
        savedResume.setFullName("John Doe Updated");
        savedResume.setSummary("Updated Developer");

        // Test the PUT request to update the resume using WebTestClient
        webTestClient.put()
                .uri("/api/resumes/{id}", savedResume.getId())  // Use the saved ID in the URL
                .contentType(MediaType.APPLICATION_JSON)  // Specify content type
                .bodyValue(savedResume)  // Send the updated resume as request body
                .exchange()
                .expectStatus().isOk()  // Expect HTTP status 200 OK
                .expectBody()
                .jsonPath("$.fullName").isEqualTo("John Doe Updated")  // Check if fullName is updated
                .jsonPath("$.summary").isEqualTo("Updated Developer");  // Check if summary is updated
    }


    @Test
    void testDeleteResume_whenResumeExists_thenDeleteResume() throws Exception {
        // Create and save a Resume object
        Resume savedResume = resumeRepository.save(new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer"));

        // Test the DELETE request to delete the resume
        webTestClient.delete()
                .uri("/api/resumes/{id}", savedResume.getId())  // Use the saved ID in the URL
                .exchange()
                .expectStatus().isNoContent();  // Expect HTTP status 204 No Content

        // Test GET request to ensure the resume was deleted
        webTestClient.get()
                .uri("/api/resumes/{id}", savedResume.getId())
                .exchange()
                .expectStatus().isNotFound();  // Expect HTTP status 404 Not Found
    }
}
