package com.example.resumeGenerationSystem.controller;

import com.example.resumeGenerationSystem.entity.Education;
import com.example.resumeGenerationSystem.entity.Skill;
import com.example.resumeGenerationSystem.entity.WorkExperience;
import com.example.resumeGenerationSystem.entity.Resume;
import com.example.resumeGenerationSystem.service.PDFGeneratorService;
import com.example.resumeGenerationSystem.service.ResumeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PDFControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ResumeService resumeService;

    @Mock
    private PDFGeneratorService pdfGeneratorService;

    @InjectMocks
    private PDFController pdfController;

    private Resume sampleResume;

    @BeforeEach
    void setUp() {
        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(pdfController).build();

        // Prepare a sample resume object
        sampleResume = new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer");
        sampleResume.setId(1L);
        sampleResume.setFullName("John Doe");
        sampleResume.setEmail("john.doe@example.com");
        sampleResume.setPhoneNumber("123-456-7890");
        sampleResume.setSummary("Experienced Software Developer.");

        // Add skills
        Skill skill1 = new Skill();
        skill1.setSkillName("Java");
        skill1.setResume(sampleResume);

        Skill skill2 = new Skill();
        skill2.setSkillName("Spring Boot");
        skill2.setResume(sampleResume);

        sampleResume.setSkills(Arrays.asList(skill1, skill2));

        // Add work experience
        WorkExperience workExperience = new WorkExperience();
        workExperience.setJobTitle("Software Engineer");
        workExperience.setCompany("Tech Corp");
        workExperience.setDuration("2 years");
        workExperience.setResume(sampleResume);

        sampleResume.setExperiences(Arrays.asList(workExperience));

        // Add education
        Education education = new Education();
        education.setDegree("Bachelor of Science in Computer Science");
        education.setInstitution("ABC University");
        education.setGraduationYear("2022");
        education.setResume(sampleResume);

        sampleResume.setEducation(Arrays.asList(education));
    }

    @Test
    void testExportResumeToPDF() throws Exception {
        // Mock the behavior of resumeService to return the sample resume
        when(resumeService.getResumeById(1L)).thenReturn(sampleResume);

        // Mock the behavior of PDFGeneratorService to return some byte data
        byte[] mockPdfBytes = new byte[]{1, 2, 3, 4, 5}; // Simulate a PDF byte array
        when(pdfGeneratorService.generateResumePDF(sampleResume)).thenReturn(mockPdfBytes);

        // Perform the GET request to export the resume
        mockMvc.perform(MockMvcRequestBuilders.get("/resumes/{id}/export", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume_1.pdf"))
                .andExpect(MockMvcResultMatchers.content().bytes(mockPdfBytes));  // Skip contentType check for now
    }
}
