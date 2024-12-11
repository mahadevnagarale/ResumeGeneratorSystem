package com.example.resumeGenerationSystem.service;

import com.example.resumeGenerationSystem.entity.Education;
import com.example.resumeGenerationSystem.entity.Resume;
import com.example.resumeGenerationSystem.entity.Skill;
import com.example.resumeGenerationSystem.entity.WorkExperience;
import com.example.resumeGenerationSystem.exception.ResourceNotFoundException;
import com.example.resumeGenerationSystem.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public Resume createResume(Resume resume) {
        // Set the resume reference for each skill, experience, and education
        if (resume.getSkills() != null) {
            for (Skill skill : resume.getSkills()) {
                skill.setResume(resume);  // Set the parent Resume
            }
        }

        if (resume.getExperiences() != null) {
            for (WorkExperience experience : resume.getExperiences()) {
                experience.setResume(resume);  // Set the parent Resume
            }
        }

        if (resume.getEducation() != null) {
            for (Education edu : resume.getEducation()) {
                edu.setResume(resume);  // Set the parent Resume
            }
        }

        // Save the entire Resume with its related entities (skills, experiences, education)
        return resumeRepository.save(resume);
    }

    public Resume updateResume(Long id, Resume resume) {
        // Fetch the existing resume
        Resume existingResume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        // Update the main resume fields
        existingResume.setFullName(resume.getFullName());
        existingResume.setEmail(resume.getEmail());
        existingResume.setPhoneNumber(resume.getPhoneNumber());
        existingResume.setSummary(resume.getSummary());

        // Update related entities (Skills, WorkExperience, Education)
        existingResume.setSkills(updateSkills(existingResume, resume.getSkills()));
        existingResume.setExperiences(updateExperiences(existingResume, resume.getExperiences()));
        existingResume.setEducation(updateEducation(existingResume, resume.getEducation()));

        // Save and return the updated resume
        return resumeRepository.save(existingResume);
    }

    private List<Skill> updateSkills(Resume existingResume, List<Skill> newSkills) {
        // Clear existing skills and update
        existingResume.getSkills().clear();
        for (Skill skill : newSkills) {
            skill.setResume(existingResume);  // Set the Resume reference
            existingResume.getSkills().add(skill);
        }
        return existingResume.getSkills();  // Return the updated list
    }

    private List<WorkExperience> updateExperiences(Resume existingResume, List<WorkExperience> newExperiences) {
        // Clear existing experiences and update
        existingResume.getExperiences().clear();
        for (WorkExperience experience : newExperiences) {
            experience.setResume(existingResume);  // Set the Resume reference
            existingResume.getExperiences().add(experience);
        }
        return existingResume.getExperiences();  // Return the updated list
    }

    private List<Education> updateEducation(Resume existingResume, List<Education> newEducation) {
        // Clear existing education and update
        existingResume.getEducation().clear();
        for (Education edu : newEducation) {
            edu.setResume(existingResume);  // Set the Resume reference
            existingResume.getEducation().add(edu);
        }
        return existingResume.getEducation();  // Return the updated list
    }

    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id).orElse(null); // Or handle this more gracefully
    }

    public boolean deleteResume(Long id) {
        if (resumeRepository.existsById(id)) {
            resumeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }
}
