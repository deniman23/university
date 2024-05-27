package org.example.university.dto.mapper;

import org.example.university.dao.model.Subject;
import org.example.university.dto.SubjectDto;

public class SubjectMapper {
    public static SubjectDto entityToDto(Subject subject){
        if (subject==null){
            return null;
        }
        SubjectDto dto = new SubjectDto();
        dto.setTitle(subject.getTitle());
        dto.setId(subject.getId());
        return dto;
    }
}
