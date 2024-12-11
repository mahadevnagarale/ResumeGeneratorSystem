package com.example.resumeGenerationSystem.controller;


import com.example.resumeGenerationSystem.entity.Resume;
import com.example.resumeGenerationSystem.repository.ResumeRepository;
import com.example.resumeGenerationSystem.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;
    @Autowired
    private  ResumeRepository resumeRepository;

    @GetMapping("/test")
    public String testPage() {
        return "test";  // Ensure this is the correct name of the template (without .html)
    }



    @PostMapping
    public ResponseEntity<Resume> createResume(@RequestBody Resume resume) {
        Resume createdResume = resumeService.createResume(resume);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResume);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resume updateResume(@PathVariable Long id, @RequestBody Resume resume) {
        return resumeService.updateResume(id, resume);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResumeById(@PathVariable Long id) {
        Resume resume = resumeService.getResumeById(id);
        if (resume != null) {
            return ResponseEntity.ok(resume);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        if (resumeService.deleteResume(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes() {
        List<Resume> resumes = resumeService.getAllResumes();
        return ResponseEntity.ok(resumes);
    }

}
