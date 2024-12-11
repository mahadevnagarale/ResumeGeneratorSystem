package com.example.resumeGenerationSystem.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.example.resumeGenerationSystem.entity.Resume;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PDFGeneratorService {

    public byte[] generateResumePDF(Resume resume) {
        // Check if the resume is valid (ensure necessary fields are present)
        if (resume == null || resume.getFullName() == null || resume.getFullName().isEmpty() ||
                resume.getEmail() == null || resume.getEmail().isEmpty()) {
            throw new RuntimeException("Error generating PDF due to missing fields");
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(outputStream);
            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            // Add Resume Header
            document.add(new Paragraph("Resume").setBold().setFontSize(18));
            document.add(new Paragraph("Name: " + resume.getFullName()));
            document.add(new Paragraph("Email: " + resume.getEmail()));
            document.add(new Paragraph("Phone: " + resume.getPhoneNumber()));
            document.add(new Paragraph("Summary: " + resume.getSummary()).setMarginBottom(20));

            // Add Skills
            document.add(new Paragraph("Skills").setBold().setFontSize(14));
            Table skillsTable = new Table(1);
            resume.getSkills().forEach(skill -> skillsTable.addCell(skill.getSkillName()));
            document.add(skillsTable.setMarginBottom(20));

            // Add Work Experience
            document.add(new Paragraph("Work Experience").setBold().setFontSize(14));
            resume.getExperiences().forEach(experience -> {
                document.add(new Paragraph("Job Title: " + experience.getJobTitle()));
                document.add(new Paragraph("Company: " + experience.getCompany()));
                document.add(new Paragraph("Duration: " + experience.getDuration()).setMarginBottom(10));
            });

            // Add Education
            document.add(new Paragraph("Education").setBold().setFontSize(14));
            resume.getEducation().forEach(edu -> {
                document.add(new Paragraph("Degree: " + edu.getDegree()));
                document.add(new Paragraph("Institution: " + edu.getInstitution()));
                document.add(new Paragraph("Graduation Year: " + edu.getGraduationYear()).setMarginBottom(10));
            });

            // Close document
            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
