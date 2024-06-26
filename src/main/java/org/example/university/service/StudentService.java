package org.example.university.service;

import org.example.university.dao.model.Student;
import org.example.university.dao.service.GroupServiceDao;
import org.example.university.dao.service.StudentServiceDao;
import org.example.university.dto.StudentDto;
import org.example.university.dto.mapper.StudentMapper;
import org.example.university.error.ResourceNotFoundException;
import org.example.university.filter.StudentFilter;
import org.example.university.request.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentServiceDao studentServiceDao;
    private final GroupServiceDao groupServiceDao;

    @Autowired
    public StudentService(StudentServiceDao studentServiceDao, GroupServiceDao groupServiceDao) {
        this.studentServiceDao = studentServiceDao;
        this.groupServiceDao = groupServiceDao;
    }

    @Transactional(readOnly = true)
    public StudentDto findById(UUID id, List<String> includes) {
        Student student = studentServiceDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return StudentMapper.entityToDto(student, includes);
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> findAll(StudentFilter filter, Pageable pageable, List<String> includes) {
        Page<Student> studentPage = studentServiceDao.findByFilter(filter, pageable, includes);
        return studentPage.map(student -> StudentMapper.entityToDto(student, includes));
    }

    @Transactional(readOnly = true)
    public StudentDto create(StudentRequest studentRequest, List<String> includes) {
        Student student = new Student();
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setMiddleName(studentRequest.getMiddleName());
        student.setGender(studentRequest.getGender());

        if (studentRequest.getGroupId() != null) {
            student.setGroup(groupServiceDao.findById(studentRequest.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group not found with ID: " + studentRequest.getGroupId())));
        }

        student = studentServiceDao.save(student);
        return StudentMapper.entityToDto(student, includes);
    }

    @Transactional(readOnly = true)
    public StudentDto edit(StudentRequest studentRequest, List<String> includes) {
        Student student = studentServiceDao.findById(studentRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentRequest.getId()));
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setMiddleName(studentRequest.getMiddleName());
        student.setGender(studentRequest.getGender());

        if (studentRequest.getGroupId() != null) {
            student.setGroup(groupServiceDao.findById(studentRequest.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group not found with ID: " + studentRequest.getGroupId())));
        }

        student = studentServiceDao.save(student);
        return StudentMapper.entityToDto(student, includes);
    }

    @Transactional(readOnly = true)
    public void delete(UUID id) {
        studentServiceDao.delete(id);
    }
}