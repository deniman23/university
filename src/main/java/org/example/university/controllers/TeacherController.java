package org.example.university.controllers;

import org.example.university.service.TeacherService;
import org.example.university.dto.TeacherDto;
import org.example.university.filter.TeacherFilter;
import org.example.university.request.TeacherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/{id}")
    public TeacherDto findById(@PathVariable UUID id, @RequestParam(required = false) List<String> includes) {
        return teacherService.findById(id, includes);
    }

    @GetMapping
    public Page<TeacherDto> findAll(TeacherFilter filter, Pageable pageable, @RequestParam(required = false) List<String> includes) {
        return teacherService.findAll(filter, pageable, includes);
    }

    @PostMapping
    public TeacherDto create(@RequestBody TeacherRequest teacherRequest, @RequestParam(required = false) List<String> includes) {
        return teacherService.create(teacherRequest, includes);
    }

    @PutMapping
    public TeacherDto edit(@RequestBody TeacherRequest teacherRequest, @RequestParam(required = false) List<String> includes) {
        return teacherService.edit(teacherRequest, includes);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        teacherService.delete(id);
    }
}
