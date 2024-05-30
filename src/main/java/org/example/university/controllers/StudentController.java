package org.example.university.controllers;

import org.example.university.api.StudentService;
import org.example.university.dto.StudentDto;
import org.example.university.filter.StudentFilter;
import org.example.university.request.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentRequest studentRequest) {
        StudentDto created = studentService.create(studentRequest);
        return ResponseEntity.ok().body(created);
    }

    @PutMapping
    public ResponseEntity<StudentDto> edit(@RequestBody StudentRequest studentRequest) {
        StudentDto updated = studentService.edit(studentRequest);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        studentService.delete(id);
        return ResponseEntity.ok().body("Student terminated");
    }

    @GetMapping("/{id}")
    public StudentDto output(@PathVariable UUID id) {
        return studentService.findById(id);
    }

    @GetMapping
    public List<StudentDto> filter(StudentFilter studentFilter, Pageable pageable) {
        return studentService.findAll(studentFilter, pageable).getContent();
    }
}
