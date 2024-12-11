# ResumeGeneratorSystem
1. Project Overview
The Resume Generation System is a backend-focused web application that allows users to create, store, and download resumes based on predefined templates. The system is developed using Spring Boot, with a MySQL database for data storage. It supports user authentication, dynamic resume generation, and PDF export functionalities.

2. Prerequisites
Ensure that you have the following installed on your machine:
Java 17 or later
Maven (for project build and dependency management)
MySQL (for database storage)
IDE: IntelliJ IDEA or any Java-compatible IDE
Postman or any API testing tool (for API testing)

3. Setup Instructions
3.1. Clone the Project
   
Clone the repository to your local machine:
git clone https://github.com/mahadevnagarale/ResumeGeneratorSystem.git
cd ResumeGeneratorSystem

3.2. Configure MySQL Database
Install MySQL if it's not already installed on your machine.
Create a Database for the project:

CREATE DATABASE resume_db;
Configure application.properties:   src/main/resources/application.properties and update the database connection settings:

spring.datasource.url=jdbc:mysql://localhost:3306/resume_db?useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true

3.3. Build and Run the Project
Build the project using Maven:
./mvnw clean package

Run the Spring Boot application:
./mvnw spring-boot:run

4. API Usage
4.1. User Authentication API
Signup User

URL: /api/auth/signup
Method: POST
Request Body:

{
  "username": "johnDoe",
  "password": "password123",
  "email": "john.doe@example.com",
  "role": "USER"
} 

Response:
Status: 201 CREATED
Response Body:

{
  "id": 1,
  "username": "johnDoe",
  "email": "john.doe@example.com",
  "role": "USER"
}
Login User

URL: /api/auth/login
Method: POST
Request Body:

{
  "username": "johnDoe",
  "password": "password123"
}
Response:
Status: 200 OK
Response Body:

{
  "message": "Login successful",
  "token": "your_jwt_token"
}
Logout User

URL: /api/auth/logout
Method: POST
Response:
Status: 200 OK
Response Body:

{
  "message": "Logout successful"
}

4.2. Resume Data API
Create New Resume

URL: /api/resumes
Method: POST
Request Body:

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "education": [
    {
      "degree": "Bachelors in Computer Science",
      "institution": "XYZ University",
      "year": "2022"
    }
  ],
  "skills": ["Java", "Spring Boot", "SQL"]
}
Response:
Status: 201 CREATED
Response Body:

{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "education": [...],
  "skills": [...]
}
Get Resume by ID

URL: /api/resumes/{id}
Method: GET
Response:
Status: 200 OK
Response Body:

{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "education": [...],
  "skills": [...]
}
Update Resume

URL: /api/resumes/{id}
Method: PUT
Request Body:

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "education": [...],
  "skills": ["Java", "Spring Boot", "SQL", "AWS"]
}
Response:
Status: 200 OK
Response Body:

{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "education": [...],
  "skills": ["Java", "Spring Boot", "SQL", "AWS"]
}
Delete Resume

URL: /api/resumes/{id}
Method: DELETE
Response:
Status: 200 OK
Response Body:

{
  "message": "Resume deleted successfully"
}

4.3. PDF Export API
Export Resume as PDF
URL: /api/resumes/{id}/export
Method: GET
Response:
Status: 200 OK
The response will be the PDF file containing the resume in the predefined template.


5. Project Structure

ResumeGenerationSystem
│
├── src/main/java/com/example/resumegenerationsystem
│   ├── config
│   │   ├── WebConfig.java
│   │   ├── SecurityConfig.java
│   ├── controller
│   │   ├── AuthController.java
│   │   ├── ResumeController.java
│   │   ├── PDFController.java
│   │   ├── UserController.java
│   │   ├── ResumeInfoController.java
│   ├── entity
│   │   ├── User.java
│   │   ├── Resume.java
│   │   ├── WorkExperience.java
│   │   ├── Skill.java
│   │   ├── PersonalDetails.java
│   │   ├── Role.java
│   │   ├── Education.java
│   ├── exception
│   │   └── ResourceNotFoundException.java
│   ├── repository
│   │   ├── ResumeRepository.java
│   │   ├── UserRepository.java
│   ├── service
│   │   ├── PDFGeneratorService.java
│   │   ├── ResumeService.java
│   │   ├── UserService.java
│   │   ├── UserDetailsServiceImpl.java
│   └── ResumeGenerationSystemApplication.java
│
├── src/main/resources/templates
│   └── resume-template.html
│
└── src/main/resources/application.properties

6. Testing
Unit and integration tests have been written for each feature using JUnit and MockMvc. The tests cover authentication, CRUD operations for resume data, and PDF generation.

Test cases include:
UserControllerTest: Tests user authentication, including signup, login, and logout functionalities.
ResumeControllerTest: Tests CRUD operations for resume data and the generation of PDFs.
PDFControllerTest: Verifies the PDF generation functionality for resumes.
ResumeServiceTest: Unit tests for the logic behind resume data handling and PDF export.
UserServiceTest: Unit tests for user management.
7. Conclusion
The Resume Generation System is fully functional, with a robust backend providing features like user authentication, resume CRUD operations, and dynamic PDF export. The project is set up with Spring Boot and MySQL, and the API documentation provided helps guide developers through using and testing the system's endpoints.

This documentation serves as a guide for setting up, using, and extending the system, with testing covering all major functionalities.






