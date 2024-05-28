package org.example.university.dao.service;

import org.example.university.dao.model.Lesson;
import org.example.university.dao.repository.LessonRepository;
import org.example.university.dao.specification.LessonSpecification;
import org.example.university.filter.LessonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceDao {
    private final LessonRepository lessonRepository;
    private final LessonSpecification lessonSpecification;

    @Autowired
    public LessonServiceDao(LessonRepository lessonRepository, LessonSpecification lessonSpecification) {
        this.lessonRepository = lessonRepository;
        this.lessonSpecification = lessonSpecification;
    }

    public Page<Lesson> findByFilter(LessonFilter filter, Pageable pageable) {
        Specification<Lesson> spec = lessonSpecification.searchFilter(filter);
        return lessonRepository.findAll(spec, pageable);
    }

    public Optional<Lesson> findById(UUID id) {
        return lessonRepository.findById(id);
    }

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public void delete(UUID id) {
        lessonRepository.deleteById(id);
    }
}
