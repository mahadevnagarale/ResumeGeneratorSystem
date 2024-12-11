package com.example.resumeGenerationSystem.service;

import com.example.resumeGenerationSystem.entity.*;
import com.example.resumeGenerationSystem.repository.ResumeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ResumeServiceTest {

    @InjectMocks
    private ResumeService resumeService;

    @Mock
    private ResumeRepository resumeRepository;

    private Resume resume;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample Resume object
        resume = new Resume("John Doe", "johndoe@example.com", "1234567890", "Software Developer");
        resume.setId(1L);
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

        // Add work experiences
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
    void testCreateResume() {
        when(resumeRepository.save(any(Resume.class))).thenReturn(resume);

        Resume createdResume = resumeService.createResume(resume);

        assertNotNull(createdResume);
        assertEquals("John Doe", createdResume.getFullName());
        verify(resumeRepository, times(1)).save(resume);
    }

    @Test
    void testGetResumeById() {
        when(resumeRepository.findById(1L)).thenReturn(Optional.of(resume));

        Resume foundResume = resumeService.getResumeById(1L);

        assertNotNull(foundResume);
        assertEquals("John Doe", foundResume.getFullName());
        verify(resumeRepository, times(1)).findById(1L);
    }


    @Test
    void testDeleteResume() {
        when(resumeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(resumeRepository).deleteById(1L);

        boolean isDeleted = resumeService.deleteResume(1L);

        assertTrue(isDeleted);
        verify(resumeRepository, times(1)).existsById(1L);
        verify(resumeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteResume_NotFound() {
        when(resumeRepository.existsById(2L)).thenReturn(false);

        boolean isDeleted = resumeService.deleteResume(2L);

        assertFalse(isDeleted);
        verify(resumeRepository, times(1)).existsById(2L);
        verify(resumeRepository, never()).deleteById(2L);
    }




}
