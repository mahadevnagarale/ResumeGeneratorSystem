package com.example.resumeGenerationSystem.repository;

import com.example.resumeGenerationSystem.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
}

