package org.example.university.controllers;

import org.example.university.service.LessonService;
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
    public ResponseEntity<LessonDto> create(@RequestBody LessonRequest lessonRequest, @RequestParam(required = false)List<String> includes) {
        LessonDto created = lessonService.create(lessonRequest, includes);
        return ResponseEntity.ok().body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDto> edit(@RequestBody LessonRequest lessonRequest, @PathVariable UUID id, @RequestParam(required = false)List<String> includes) {
        LessonDto updated = lessonService.edit(lessonRequest, id, includes);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        lessonService.delete(id);
        return ResponseEntity.ok().body("Lesson deleted");
    }

    @GetMapping("/{id}")
    public LessonDto output(@PathVariable UUID id, @RequestParam(required = false)List<String> includes) {
        return lessonService.findById(id, includes);
    }

    @GetMapping
    public List<LessonDto> filter(LessonFilter lessonFilter, Pageable pageable, @RequestParam(required = false) List<String> includes) {
        return lessonService.findAll(lessonFilter, pageable, includes).getContent();
    }
}