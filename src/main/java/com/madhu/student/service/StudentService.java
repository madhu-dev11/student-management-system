package com.madhu.student.service;

import com.madhu.student.model.Student;
import com.madhu.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Save a new student
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    // Get student by ID
    public Student getStudentById(Long id) {
        Optional<Student> optional = studentRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Student not found with ID: " + id);
        }
    }

    // Update student
    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    // Delete student by ID
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    // Search students by name
    public List<Student> searchStudents(String keyword) {
        return studentRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        keyword, keyword);
    }
}
