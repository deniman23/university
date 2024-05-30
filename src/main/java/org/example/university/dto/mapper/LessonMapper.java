package org.example.university.dto.mapper;

import org.example.university.dao.model.Lesson;
import org.example.university.dto.LessonDto;

public class LessonMapper {
    public static LessonDto entityToDto(Lesson lesson) {
        LessonDto dto = new LessonDto();
        dto.setId(lesson.getId());
        dto.setName(lesson.getName());
        dto.setCreationDate(lesson.getCreationDate());
        return dto;
    }

}