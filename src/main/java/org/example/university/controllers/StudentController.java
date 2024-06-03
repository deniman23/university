package org.example.university.controllers;

import org.example.university.dto.StudentDto;
import org.example.university.filter.StudentFilter;
import org.example.university.request.StudentRequest;
import org.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/{id}")
    public StudentDto findById(@PathVariable UUID id, List<String> includes) {
        return studentService.findById(id, includes);
    }

    @GetMapping
    public Page<StudentDto> findAll(StudentFilter filter, Pageable pageable, @RequestParam(required = false) List<String> includes) {
        return studentService.findAll(filter, pageable, includes);
    }

    @PostMapping
    public StudentDto create(@RequestBody StudentRequest studentRequest, @RequestParam(required = false) List<String> includes) {
        return studentService.create(studentRequest, includes);
    }

    @PutMapping
    public StudentDto edit(@RequestBody StudentRequest studentRequest, @RequestParam(required = false) List<String> includes) {
        return studentService.edit(studentRequest, includes);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        studentService.delete(id);
    }
}