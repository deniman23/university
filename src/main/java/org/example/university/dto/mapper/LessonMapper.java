package org.example.university.dto.mapper;

import org.example.university.dao.model.Lesson;
import org.example.university.dto.LessonDto;

public class LessonMapper {
    public static LessonDto entityToDto(Lesson lesson) {
        LessonDto dto = new LessonDto();
        dto.setId(lesson.getId());
        dto.setName(lesson.getName());
        dto.setCreationDate(lesson.getCreationDate());
        dto.setTeacherId(lesson.getTeacher().getId());
        dto.setGroupId(lesson.getGroup().getId());
        dto.setSubjectId(lesson.getSubject().getId());
        return dto;
    }

}