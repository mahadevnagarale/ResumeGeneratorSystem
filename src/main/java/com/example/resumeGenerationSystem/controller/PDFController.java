package com.example.resumeGenerationSystem.controller;

import com.example.resumeGenerationSystem.entity.Resume;
import com.example.resumeGenerationSystem.service.PDFGeneratorService;
import com.example.resumeGenerationSystem.service.ResumeService; // Assuming you have a service to fetch resumes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PDFController {

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/resumes/{id}/export")
    public ResponseEntity<byte[]> exportResumeToPDF(@PathVariable Long id) {
        // Fetch the resume by ID
        Resume resume = resumeService.getResumeById(id);
        if (resume == null) {
            return ResponseEntity.notFound().build();
        }

        // Generate PDF
        byte[] pdfBytes = pdfGeneratorService.generateResumePDF(resume);

        // Return PDF as response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)  // Ensure this line is present
                .body(pdfBytes);

    }
}
