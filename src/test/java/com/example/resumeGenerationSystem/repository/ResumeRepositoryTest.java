package com.example.resumeGenerationSystem.repository;


import com.example.resumeGenerationSystem.entity.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ResumeRepositoryTest {

    @Autowired
    private ResumeRepository resumeRepository;

    private Resume resume;

    @BeforeEach
    void setUp() {
        // Set up a sample resume object
        resume = new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer");
        resume.setFullName("John Doe");
        resume.setEmail("john.doe@example.com");
        resume.setPhoneNumber("1234567890");
        resume.setSummary("Experienced software developer");
    }

    @Test
    void testSaveResume() {
        // Save resume and verify it's saved
        Resume savedResume = resumeRepository.save(resume);
        assertNotNull(savedResume);
        assertNotNull(savedResume.getId());  // Assuming the ID is generated after saving
        assertEquals("John Doe", savedResume.getFullName());
    }

    @Test
    void testFindById() {
        // Save resume and find by ID
        Resume savedResume = resumeRepository.save(resume);
        Optional<Resume> foundResume = resumeRepository.findById(savedResume.getId());
        assertTrue(foundResume.isPresent());
        assertEquals(savedResume.getFullName(), foundResume.get().getFullName());
    }

    @Test
    void testFindAll() {
        // Save multiple resumes and check the list
        Resume resume2 = new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer");
        resume2.setFullName("Jane Doe");
        resume2.setEmail("jane.doe@example.com");
        resume2.setPhoneNumber("0987654321");
        resume2.setSummary("Software Engineer");

        resumeRepository.save(resume);
        resumeRepository.save(resume2);

        List<Resume> resumes = resumeRepository.findAll();
        assertNotNull(resumes);
        assertTrue(resumes.size() >= 2);
    }

    @Test
    void testDeleteById() {
        // Save and then delete the resume
        Resume savedResume = resumeRepository.save(resume);
        Long id = savedResume.getId();

        resumeRepository.deleteById(id);

        Optional<Resume> deletedResume = resumeRepository.findById(id);
        assertFalse(deletedResume.isPresent());
    }
}
