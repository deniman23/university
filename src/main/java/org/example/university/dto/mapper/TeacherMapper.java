package org.example.university.dto.mapper;

import org.example.university.dao.model.Teacher;
import org.example.university.dto.TeacherDto;

import java.util.List;

public class TeacherMapper {
    public static TeacherDto entityToDto(Teacher teacher, List<String> includes) {
        TeacherDto teacherDto = new TeacherDto();
        if (teacher != null) {
            teacherDto.setId(teacher.getId());
            teacherDto.setFirstName(teacher.getFirstName());
            teacherDto.setLastName(teacher.getLastName());
            teacherDto.setMiddleName(teacher.getMiddleName());
            teacherDto.setGender(teacher.getGender());
        }
        return teacherDto;
    }
}