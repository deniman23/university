package org.example.university.dao.service;

import org.example.university.dao.model.Subject;
import org.example.university.dao.repository.SubjectRepository;
import org.example.university.dao.specification.SubjectSpecification;
import org.example.university.error.ResourceNotFoundException;
import org.example.university.filter.SubjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectServiceDao {

    private final SubjectRepository subjectRepository;
    private final SubjectSpecification subjectSpecification;

    @Autowired
    public SubjectServiceDao(SubjectRepository subjectRepository, SubjectSpecification subjectSpecification) {
        this.subjectRepository = subjectRepository;
        this.subjectSpecification = subjectSpecification;
    }

    public Page<Subject> findByFilter(SubjectFilter filter, Pageable pageable) {
        Specification<Subject> spec = subjectSpecification.searchFilter(filter);
        return subjectRepository.findAll(spec, pageable);
    }



    public Optional<Subject> findById(UUID id) {
        return subjectRepository.findById(id);
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void delete(UUID id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID " + id));
        subjectRepository.delete(subject);
    }
}
