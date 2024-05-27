package org.example.university.dto.mapper;

import org.example.university.dao.model.Student;
import org.example.university.dto.StudentDto;

public class StudentMapper {
    public static StudentDto entityToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setMiddleName(student.getMiddleName());
        studentDto.setGender(student.getGender());
        studentDto.setGroup(student.getGroup());

        return studentDto;
    }
}
