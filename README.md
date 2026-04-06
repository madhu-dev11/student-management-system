
# Student Management System

A full stack web application I built to manage student records. This project helped me understand how to connect a Java Spring Boot backend with a React frontend and MySQL database.

---

## Why I Built This

During my training at JSpiders, I wanted to build something practical that covers all the concepts I learned — REST APIs, Hibernate, React, and MySQL. A student management system felt like a real-world use case that covers all of these.

---

## What This App Can Do

- Add a new student with name, email, department and phone number
- View the list of all students
- Update student details
- Delete a student record
- Each student gets a unique ID automatically

---

## Technologies I Used

**Backend**
- Java 17
- Spring Boot 3
- Hibernate (JPA) for database operations
- Maven for project build
- REST API

**Frontend**
- React.js
- Axios for API calls
- CSS for styling

**Database**
- MySQL

---

## How the Project is Organized

```
student-management-system/
│
├── backend/
│   └── src/main/java/com/madhu/sms/
│       ├── StudentManagementApp.java
│       ├── model/
│       │   └── Student.java
│       ├── repository/
│       │   └── StudentRepository.java
│       ├── service/
│       │   ├── StudentService.java
│       │   └── StudentServiceImpl.java
│       ├── controller/
│       │   └── StudentController.java
│       └── exception/
│           └── ResourceNotFoundException.java
│
└── frontend/
    └── src/
        ├── App.js
        ├── components/
        │   ├── StudentList.js
        │   ├── AddStudent.js
        │   └── EditStudent.js
        └── services/
            └── StudentService.js
```

---

## Backend Code

### Student.java

This is the main entity class. It maps to the `students` table in MySQL.

```java
package com.madhu.sms.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String department;
    private String phone;
}
```

### StudentRepository.java

I used Spring Data JPA here so I don't have to write SQL queries manually. Spring handles it automatically.

```java
package com.madhu.sms.repository;

import com.madhu.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
}
```

### StudentService.java (Interface)

I created a separate interface for the service layer to follow good coding practices.

```java
package com.madhu.sms.service;

import com.madhu.sms.model.Student;
import java.util.List;

public interface StudentService {
    Student saveStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
}
```

### StudentServiceImpl.java

This is the actual implementation of the service. All the business logic lives here.

```java
package com.madhu.sms.service;

import com.madhu.sms.exception.ResourceNotFoundException;
import com.madhu.sms.model.Student;
import com.madhu.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setDepartment(studentDetails.getDepartment());
        student.setPhone(studentDetails.getPhone());
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}
```

### StudentController.java

This is where I defined all the REST API endpoints. Each method handles a different HTTP request.

```java
package com.madhu.sms.controller;

import com.madhu.sms.model.Student;
import com.madhu.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:3456")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(
            studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully.");
    }
}
```

### ResourceNotFoundException.java

I created this custom exception so that when a student ID is not found, it returns a proper 404 error instead of a generic error.

```java
package com.madhu.sms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

### application.properties

Database connection settings. Make sure to create the `studentdb` database in MySQL first.

```properties
spring.datasource.url=jdbc:mysql://localhost:3456/studentdb
spring.datasource.username=root
spring.datasource.password=Madhu@2003

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

server.port=8080
```

---

## Database Setup

```sql
CREATE DATABASE studentdb;
USE studentdb;

-- Hibernate creates the table automatically
-- But here is the sample data to test

INSERT INTO students (first_name, last_name, email, department, phone, age)
VALUES
  ('Arun Kumar', 'K', 'arun123@gmail.com', 'Computer Science', '9876543210', 22),
  ('Gagan', 'Jadav', 'jadav@gmail.com', 'Electronics', '9845632171', 23),
  ('Guru', 'Prasad', 'guru@gmail.com', 'Information Science', '7975487876', 22),
  ('Buvan', 'B', 'buvan24352@gmail.com', 'Civil', '7845786987', 20),
  ('Vanjan', 'Kumar', 'vanjankumar@gmail.com', 'Mechanical', '8754215698', 25),
  ('Pratham', 'S', 'prathams@gmail.com', 'Electronics', '8754624587', 23);
```

---

## API Endpoints

| Method | URL | What it does |
|--------|-----|--------------|
| POST | `/api/students` | Add new student |
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get one student by ID |
| PUT | `/api/students/{id}` | Update student details |
| DELETE | `/api/students/{id}` | Delete a student |

---

## Frontend – React.js

### StudentService.js

This file handles all API calls from React to the Spring Boot backend.

```javascript
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/students';

const StudentService = {
    getAllStudents: () => axios.get(BASE_URL),
    getStudentById: (id) => axios.get(`${BASE_URL}/${id}`),
    createStudent: (student) => axios.post(BASE_URL, student),
    updateStudent: (id, student) => axios.put(`${BASE_URL}/${id}`, student),
    deleteStudent: (id) => axios.delete(`${BASE_URL}/${id}`)
};

export default StudentService;
```

---

## How to Run This Project

**Step 1 — Setup Database**

```sql
CREATE DATABASE studentdb;
```

**Step 2 — Run Backend**

```bash
cd backend
mvn spring-boot:run
```

Backend will start at: `http://localhost:8080`

**Step 3 — Run Frontend**

```bash
cd frontend
npm install
npm start
```

Frontend will open at: `http://localhost:3456`

> To run React on port 3456, add this to your `frontend/.env` file:
> ```
> PORT=3456
> ```

---

## What I Learned From This Project

- How to build REST APIs using Spring Boot
- How Hibernate automatically creates and manages database tables
- How to connect React frontend to a Java backend using Axios
- How to handle errors properly using custom exceptions
- How the MVC (Model - View - Controller) pattern works in real projects

---

## About Me

**Madhu A L**  
Java Full Stack Developer Trainee  
📧 madhulokesh1117@gmail.com  
🔗 [linkedin.com/in/madhual](https://linkedin.com/in/madhual)
