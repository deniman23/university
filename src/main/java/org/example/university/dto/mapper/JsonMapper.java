package org.example.university.dto.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.university.dto.SubjectDto;
import org.example.university.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class JsonMapper {
    private final ObjectMapper objectMapper;

    @Autowired
    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public String convertTeacher(TeacherDto teacherDto) throws JsonProcessingException {
        if (teacherDto == null) {
            return "\"Not found\"";
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(teacherDto);
    }


    public String convertSubject(SubjectDto subjectDto) throws JsonProcessingException {
        if (subjectDto == null) {
            return "\"Not found\"";
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(subjectDto);
    }
}
