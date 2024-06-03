package org.example.university.service;

import org.example.university.dao.model.Subject;
import org.example.university.dao.service.SubjectServiceDao;
import org.example.university.dto.SubjectDto;
import org.example.university.dto.mapper.SubjectMapper;
import org.example.university.error.ResourceNotFoundException;
import org.example.university.filter.SubjectFilter;
import org.example.university.request.SubjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubjectService {

    private final SubjectServiceDao subjectServiceDao;

    @Autowired
    public SubjectService(SubjectServiceDao subjectServiceDao) {
        this.subjectServiceDao = subjectServiceDao;
    }

    // Поиск по ID
    public SubjectDto findById(UUID id) {
        Subject subject = subjectServiceDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + id));
        return SubjectMapper.entityToDto(subject);
    }

    public Page<SubjectDto> findAll(SubjectFilter filter, Pageable pageable) {
        Page<Subject> subjectPage = subjectServiceDao.findByFilter(filter, pageable);
        return subjectPage.map(SubjectMapper::entityToDto);
    }

    public SubjectDto create(SubjectRequest subjectRequest){
        Subject subject = new Subject();
        subject.setTitle(subjectRequest.getTitle());
        subject = subjectServiceDao.save(subject);
        return SubjectMapper.entityToDto(subject);
    }

    // Изменение сотрудника
    public SubjectDto edit(SubjectRequest subjectRequest) {
        Subject subject = subjectServiceDao.findById(subjectRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID " + subjectRequest.getId()));
        subject.setTitle(subjectRequest.getTitle());
        subject = subjectServiceDao.save(subject);
        return SubjectMapper.entityToDto(subject);
    }

    // Удаление должности по ID
    public void delete(UUID id) {
        subjectServiceDao.delete(id);
    }
}
