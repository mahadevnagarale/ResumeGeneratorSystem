package com.example.resumeGenerationSystem.controller;

import com.example.resumeGenerationSystem.service.ResumeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ResumeInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ResumeService resumeService;

    @InjectMocks
    private ResumeInfoController resumeInfoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        @Test
    void testGeneratePdfNotFound() throws Exception {
        when(resumeService.getResumeById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/resumes/1/pdf"))
                .andExpect(status().isNotFound());
    }
}
