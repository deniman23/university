package org.example.university.dto.mapper;

import org.example.university.dao.model.Teacher;
import org.example.university.dto.SubjectDto;
import org.example.university.dto.TeacherDto;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherMapper {
    public static TeacherDto entityToDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setMiddleName(teacher.getMiddleName());
        teacherDto.setGender(teacher.getGender());
        teacherDto.setCreationDate(teacher.getCreationDate());


        List<SubjectDto> subjectDtos = teacher.getSubjects().stream()
                .map(SubjectMapper::entityToDto)
                .collect(Collectors.toList());
        teacherDto.setSubjects(subjectDtos);
        return teacherDto;
    }
}
