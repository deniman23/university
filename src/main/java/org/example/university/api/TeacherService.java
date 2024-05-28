package org.example.university.api;

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


import java.util.UUID;

@Service
public class TeacherService {

    private final TeacherServiceDao teacherServiceDao;
    private final SubjectServiceDao subjectServiceDao;

    @Autowired
    public TeacherService(TeacherServiceDao teacherServiceDao, SubjectServiceDao subjectServiceDao) {
        this.teacherServiceDao = teacherServiceDao;
        this.subjectServiceDao = subjectServiceDao;
    }

    // Поиск по ID
    public TeacherDto findById(UUID id) {
        Teacher teacher = teacherServiceDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));
        Hibernate.initialize(teacher.getTeacherLinks());
        for (TeacherSubject teacherSubject : teacher.getTeacherLinks()) {
            Hibernate.initialize(teacherSubject.getSubject());
        }
        return TeacherMapper.entityToDto(teacher);
    }

    public Page<TeacherDto> findAll(TeacherFilter filter, Pageable pageable) {
        Page<Teacher> teacherPage = teacherServiceDao.findByFilter(filter, pageable);
        return teacherPage.map(TeacherMapper::entityToDto);
    }


    public TeacherDto create(TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherRequest.getFirstName());
        teacher.setLastName(teacherRequest.getLastName());
        teacher.setMiddleName(teacherRequest.getMiddleName());
        teacher.setGender(teacherRequest.getGender());

        for (UUID subjectId : teacherRequest.getSubjectIds()) {
            Subject subject = subjectServiceDao.findById(subjectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
            TeacherSubject teacherSubject = new TeacherSubject();
            teacherSubject.setTeacher(teacher);
            teacherSubject.setSubject(subject);
            teacher.getTeacherLinks().add(teacherSubject);
        }

        teacher = teacherServiceDao.save(teacher);
        return TeacherMapper.entityToDto(teacher);
    }


    public TeacherDto edit(TeacherRequest teacherRequest) {
        Teacher teacher = teacherServiceDao.findById(teacherRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacherRequest.getId()));
        teacher.setFirstName(teacherRequest.getFirstName());
        teacher.setLastName(teacherRequest.getLastName());
        teacher.setMiddleName(teacherRequest.getMiddleName());
        teacher.setGender(teacherRequest.getGender());

        teacher.getTeacherLinks().clear();
        for (UUID subjectId : teacherRequest.getSubjectIds()) {
            Subject subject = subjectServiceDao.findById(subjectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
            TeacherSubject teacherSubject = new TeacherSubject();
            teacherSubject.setTeacher(teacher);
            teacherSubject.setSubject(subject);
            teacher.getTeacherLinks().add(teacherSubject);
        }

        teacher = teacherServiceDao.save(teacher);
        return TeacherMapper.entityToDto(teacher);
    }


    public void terminate(UUID id) {
        Teacher teacher = teacherServiceDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));
//        teacher.setTerminated(true);
        teacherServiceDao.save(teacher);
    }

}