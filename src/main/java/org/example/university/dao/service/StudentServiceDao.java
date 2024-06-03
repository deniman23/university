package org.example.university.dao.service;

import org.example.university.dao.model.Student;
import org.example.university.dao.repository.StudentRepository;
import org.example.university.dao.specification.StudentSpecification;
import org.example.university.filter.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceDao {
    private final StudentRepository studentRepository;
    private final StudentSpecification studentSpecification;

    @Autowired
    public StudentServiceDao(StudentRepository studentRepository, StudentSpecification studentSpecification) {
        this.studentRepository = studentRepository;
        this.studentSpecification = studentSpecification;
    }

    public Page<Student> findByFilter(StudentFilter filter, Pageable pageable, List<String> includes) {
        Specification<Student> spec = studentSpecification.searchFilter(filter, includes);
        return studentRepository.findAll(spec, pageable);
    }

    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
    }

    public List<Student> findAllById(List<UUID> ids) {
        return studentRepository.findAllById(ids);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void delete(UUID id) {
        studentRepository.deleteById(id);
    }
}