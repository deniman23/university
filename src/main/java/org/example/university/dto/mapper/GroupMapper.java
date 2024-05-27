package org.example.university.dto.mapper;

import org.example.university.dao.model.Group;
import org.example.university.dto.StudentDto;
import org.example.university.dto.GroupDto;

import java.util.List;

public class GroupMapper {
    public static GroupDto entityToDto(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setTitle(group.getTitle());
        groupDto.setCourse(group.getCourse());
        groupDto.setMonitor(group.getMonitor());
        groupDto.setCurator(group.getCurator());
        List<StudentDto> studentDtos = group.getStudents().stream()
                .map(StudentMapper::entityToDto)
                .toList();
        groupDto.setStudents(studentDtos);
        return groupDto;
    }
}
