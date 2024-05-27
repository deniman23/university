package org.example.university.controllers;

import org.example.university.api.TeacherService;
import org.example.university.dto.TeacherDto;
import org.example.university.filter.TeacherFilter;
import org.example.university.request.TeacherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherRequest teacherRequest) {
        TeacherDto created = teacherService.create(teacherRequest);
        return ResponseEntity.ok().body(created);
    }

    @PutMapping
    public ResponseEntity<TeacherDto> edit(@RequestBody TeacherRequest teacherRequest) {
        TeacherDto updated = teacherService.edit(teacherRequest);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> terminate(@PathVariable UUID id) {
        teacherService.terminate(id);
        return ResponseEntity.ok().body("Employee terminated");
    }

    @GetMapping("/{id}")
    public TeacherDto output(@PathVariable UUID id) {
        return teacherService.findById(id);
    }

    @GetMapping
    public List<TeacherDto> filter(TeacherFilter teacherFilter, Pageable pageable) {
        return teacherService.findAll(teacherFilter, pageable).getContent();
    }
}
