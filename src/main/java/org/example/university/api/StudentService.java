package org.example.university.api;

import org.example.university.dao.model.Student;
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

import java.util.UUID;

@Service
public class StudentService {

    private final StudentServiceDao studentServiceDao;


    @Autowired
    public StudentService(StudentServiceDao studentServiceDao) {
        this.studentServiceDao = studentServiceDao;
    }

    // Поиск по ID
    public StudentDto findById(UUID id) {
        Student student = studentServiceDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return StudentMapper.entityToDto(student);
    }

    public Page<StudentDto> findAll(StudentFilter filter, Pageable pageable) {
        Page<Student> studentPage = studentServiceDao.findByFilter(filter, pageable);
        return studentPage.map(StudentMapper::entityToDto);
    }

    public StudentDto create(StudentRequest studentRequest) {
        Student student = new Student();
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setMiddleName(studentRequest.getMiddleName());
        student.setGender(studentRequest.getGender());
        student = studentServiceDao.save(student);
        return StudentMapper.entityToDto(student);
    }

    public StudentDto edit(StudentRequest studentRequest) {
        Student student = studentServiceDao.findById(studentRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentRequest.getId()));
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setMiddleName(studentRequest.getMiddleName());
        student.setGender(studentRequest.getGender());

        student = studentServiceDao.save(student);
        return StudentMapper.entityToDto(student);
    }

    public void terminate(UUID id) {
        Student student = studentServiceDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
//        student.setTerminated(true);
        studentServiceDao.save(student);
    }
}
