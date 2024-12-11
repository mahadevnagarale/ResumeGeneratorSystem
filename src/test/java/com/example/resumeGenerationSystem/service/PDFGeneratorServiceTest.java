package com.example.resumeGenerationSystem.service;

import com.example.resumeGenerationSystem.entity.Resume;
import com.example.resumeGenerationSystem.entity.Skill;
import com.example.resumeGenerationSystem.entity.WorkExperience;
import com.example.resumeGenerationSystem.entity.Education;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class PDFGeneratorServiceTest {

    private PDFGeneratorService pdfGeneratorService;
    private Resume resume;

    @BeforeEach
    void setUp() {
        pdfGeneratorService = new PDFGeneratorService();

        // Initialize a sample resume object
        resume = new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer");
        resume.setFullName("John Doe");
        resume.setEmail("john.doe@example.com");
        resume.setPhoneNumber("1234567890");
        resume.setSummary("Experienced software developer");

        // Add skills
        Skill skill1 = new Skill();
        skill1.setSkillName("Java");
        Skill skill2 = new Skill();
        skill2.setSkillName("Spring Boot");
        resume.setSkills(Arrays.asList(skill1, skill2));

        // Add work experience
        WorkExperience experience = new WorkExperience();
        experience.setJobTitle("Software Engineer");
        experience.setCompany("TechCorp");
        experience.setDuration("Jan 2020 - Dec 2022");
        resume.setExperiences(Arrays.asList(experience));

        // Add education
        Education education = new Education();
        education.setDegree("B.Tech in Computer Science");
        education.setInstitution("XYZ University");
        education.setGraduationYear("2019");
        resume.setEducation(Arrays.asList(education));
    }

    @Test
    void testGenerateResumePDF() throws Exception {
        // Generate the PDF as a byte array
        byte[] pdfBytes = pdfGeneratorService.generateResumePDF(resume);

        // Ensure the PDF content is not null or empty
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }

}
