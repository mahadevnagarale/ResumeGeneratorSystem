package com.example.resumeGenerationSystem.entity;
public enum Role {
    USER, ADMIN;

    public static boolean isValid(String role) {
        try {
            Role.valueOf(role);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}


