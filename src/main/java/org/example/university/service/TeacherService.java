package org.example.university.service;

import org.example.university.dao.model.Subject;
import org.example.university.dao.model.Teacher;
import org.example.university.dao.model.TeacherSubject;
import org.example.university.dao.service.SubjectServiceDao;
import org.example.university.dao.service.TeacherServiceDao;
import org.example.university.dto.TeacherDto;
import org.example.university.dto.mapper.TeacherMapper;
import org.example.university.error.ResourceNotFoundException;
import org.example.university.filter.TeacherFilter;
import org.example.university.request.TeacherRequest;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {

    private final TeacherServiceDao teacherServiceDao;

    @Autowired
    public TeacherService(TeacherServiceDao teacherServiceDao) {
        this.teacherServiceDao = teacherServiceDao;
    }

    public TeacherDto findById(UUID id, List<String> includes) {
        Teacher teacher = teacherServiceDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));
        return TeacherMapper.entityToDto(teacher, includes);
    }

    public Page<TeacherDto> findAll(TeacherFilter filter, Pageable pageable, List<String> includes) {
        Page<Teacher> teacherPage = teacherServiceDao.findByFilter(filter, pageable, includes);
        return teacherPage.map(teacher -> TeacherMapper.entityToDto(teacher, includes));
    }

    public TeacherDto create(TeacherRequest teacherRequest, List<String> includes) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherRequest.getFirstName());
        teacher.setLastName(teacherRequest.getLastName());
        teacher.setMiddleName(teacherRequest.getMiddleName());
        teacher.setGender(teacherRequest.getGender());

        teacher = teacherServiceDao.save(teacher);
        return TeacherMapper.entityToDto(teacher, includes);
    }

    public TeacherDto edit(TeacherRequest teacherRequest, List<String> includes) {
        Teacher teacher = teacherServiceDao.findById(teacherRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacherRequest.getId()));
        teacher.setFirstName(teacherRequest.getFirstName());
        teacher.setLastName(teacherRequest.getLastName());
        teacher.setMiddleName(teacherRequest.getMiddleName());
        teacher.setGender(teacherRequest.getGender());

        teacher = teacherServiceDao.save(teacher);
        return TeacherMapper.entityToDto(teacher, includes);
    }

    public void delete(UUID id) {
        teacherServiceDao.delete(id);
    }
}