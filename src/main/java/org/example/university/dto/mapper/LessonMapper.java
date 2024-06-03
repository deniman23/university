package org.example.university.dto.mapper;

import org.example.university.dao.model.Lesson;
import org.example.university.dto.LessonDto;

import java.util.List;

public class LessonMapper {
    public static LessonDto entityToDto(Lesson lesson, List<String> includes) {
        LessonDto dto = new LessonDto();
        dto.setId(lesson.getId());
        dto.setName(lesson.getName());
        dto.setCreationDate(lesson.getCreationDate());

        if (includes != null && includes.contains("teacher")) {
            dto.setTeacher(TeacherMapper.entityToDto(lesson.getTeacher(), includes));
        }
        if (includes != null && includes.contains("group")) {
            dto.setGroup(GroupMapper.entityToDto(lesson.getGroup(), includes));
        }
        if (includes != null && includes.contains("subject")) {
            dto.setSubject(SubjectMapper.entityToDto(lesson.getSubject()));
        }

        return dto;
    }
}