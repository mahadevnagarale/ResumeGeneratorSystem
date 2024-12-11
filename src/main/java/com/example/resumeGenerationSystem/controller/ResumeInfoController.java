package com.example.resumeGenerationSystem.controller;

import com.example.resumeGenerationSystem.entity.Resume;
import com.example.resumeGenerationSystem.service.ResumeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class ResumeInfoController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private SpringTemplateEngine templateEngine;  // Thymeleaf template engine

    // Endpoint to show resume details
    @GetMapping("/api/resumes/{id}/info")
    public String getResumeInfo(@PathVariable("id") Long id, Model model) {
        // Fetch the resume by its ID
        Resume resume = resumeService.getResumeById(id);

        // Add resume to the model for Thymeleaf to render
        model.addAttribute("resume", resume);

        // Return the name of the Thymeleaf template (resumeInfo.html)
        return "resumeInfo";
    }

    // New endpoint to generate PDF
    @GetMapping("/api/resumes/{id}/pdf")
    public void generatePdf(@PathVariable Long id, HttpServletResponse response) throws IOException {
        // Get resume from the database
        Resume resume = resumeService.getResumeById(id);
        if (resume == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // Return 404 if resume is not found
            return;
        }

        // Prepare Thymeleaf context with dynamic data
        Context context = new Context();
        context.setVariable("resume", resume);

        // Use Thymeleaf template for the PDF (you can change templateName as needed)
        String htmlContent = templateEngine.process("resume-template1", context); // Use your actual template name

        // Convert the HTML content to PDF
        ByteArrayOutputStream pdfOutputStream = convertHtmlToPdf(htmlContent);

        // Send the PDF as a response
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=resume.pdf");
        response.getOutputStream().write(pdfOutputStream.toByteArray());
    }

    // Function to convert HTML to PDF (using Flying Saucer or iText)
    private ByteArrayOutputStream convertHtmlToPdf(String html) throws IOException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        renderer.layout();
        renderer.createPDF(baos);
        return baos;
    }
}
