package org.example.university.dao.service;

import org.example.university.dao.model.Teacher;
import org.example.university.dao.repository.TeacherRepository;
import org.example.university.dao.specification.TeacherSpecification;
import org.example.university.filter.TeacherFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherServiceDao {

    private final TeacherRepository teacherRepository;
    private final TeacherSpecification teacherSpecification;

    @Autowired
    public TeacherServiceDao(TeacherRepository teacherRepository, TeacherSpecification teacherSpecification) {
        this.teacherRepository = teacherRepository;
        this.teacherSpecification = teacherSpecification;
    }

    public Page<Teacher> findByFilter(TeacherFilter filter, Pageable pageable, List<String> includes) {
        Specification<Teacher> spec = teacherSpecification.searchFilter(filter, includes);
        return teacherRepository.findAll(spec, pageable);
    }

    public Optional<Teacher> findById(UUID id) {
        return teacherRepository.findById(id);
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void delete(UUID id) {
        teacherRepository.deleteById(id);
    }
}

