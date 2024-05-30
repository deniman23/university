package org.example.university.controllers;

import org.example.university.api.LessonService;
import org.example.university.dto.LessonDto;
import org.example.university.filter.LessonFilter;
import org.example.university.request.LessonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ResponseEntity<LessonDto> create(@RequestBody LessonRequest lessonRequest) {
        LessonDto created = lessonService.create(lessonRequest);
        return ResponseEntity.ok().body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDto> update(@RequestBody LessonRequest lessonRequest, @PathVariable UUID id) {
        LessonDto updated = lessonService.edit(lessonRequest, id);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        lessonService.delete(id);
        return ResponseEntity.ok().body("Lesson deleted");
    }

    @GetMapping("/{id}")
    public LessonDto findById(@PathVariable UUID id) {
        return lessonService.findById(id);
    }

    @GetMapping
    public List<LessonDto> findAll(LessonFilter lessonFilter, Pageable pageable) {
        return lessonService.findAll(lessonFilter, pageable).getContent();
    }
}
