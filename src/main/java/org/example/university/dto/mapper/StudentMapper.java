package org.example.university.dto.mapper;

import org.example.university.dao.model.Student;
import org.example.university.dto.StudentDto;

import java.util.List;

public class StudentMapper {
    public static StudentDto entityToDto(Student student, List<String> includes) {
        StudentDto studentDto = entityToDtoWithoutGroup(student);
        if (includes != null && includes.contains("group") && student.getGroup() != null) {
            studentDto.setGroup(GroupMapper.entityToDto(student.getGroup(), includes));
        }
        return studentDto;
    }

    public static StudentDto entityToDtoWithoutGroup(Student student) {
        StudentDto studentDto = new StudentDto();
        if (student != null) {
            studentDto.setId(student.getId());
            studentDto.setFirstName(student.getFirstName());
            studentDto.setLastName(student.getLastName());
            studentDto.setMiddleName(student.getMiddleName());
            studentDto.setGender(student.getGender());
        }
        return studentDto;
    }
}