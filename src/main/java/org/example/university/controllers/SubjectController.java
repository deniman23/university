package org.example.university.controllers;

import org.example.university.api.SubjectService;
import org.example.university.dto.SubjectDto;
import org.example.university.filter.SubjectFilter;
import org.example.university.request.SubjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectRequest subjectRequest) {
        SubjectDto subjectDto = subjectService.create(subjectRequest);
        return new ResponseEntity<>(subjectDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SubjectDto> edit(@RequestBody SubjectRequest teacherRequest) {
        SubjectDto updated = subjectService.edit(teacherRequest);
        return ResponseEntity.ok().body(updated);
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> terminate(@PathVariable UUID id) {
//        subjectService.terminate(id);
//        return ResponseEntity.ok().body("Employee terminated");
//    }
//
    @GetMapping("/{id}")
    public SubjectDto output(@PathVariable UUID id) {
        return subjectService.findById(id);
    }

    @GetMapping
    public List<SubjectDto> filter(SubjectFilter subjectFilter, Pageable pageable) {
        return subjectService.findAll(subjectFilter, pageable).getContent();
    }
}
