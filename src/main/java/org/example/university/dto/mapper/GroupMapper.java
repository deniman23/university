package org.example.university.dto.mapper;

import org.example.university.dao.model.Group;
import org.example.university.dto.GroupDto;
import org.example.university.dto.StudentDto;

import java.util.List;

public class GroupMapper {
    public static GroupDto entityToDto(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setTitle(group.getTitle());
        groupDto.setCourse(group.getCourse());
        if (group.getMonitor() != null) {
            groupDto.setMonitor(StudentMapper.entityToDtoWithoutGroup(group.getMonitor()));
        }
        if (group.getCurator() != null) {
            groupDto.setCurator(TeacherMapper.entityToDto(group.getCurator()));
        }
        List<StudentDto> studentDtos = group.getStudents().stream()
                .map(StudentMapper::entityToDtoWithoutGroup)
                .toList();
        groupDto.setStudents(studentDtos);
        return groupDto;
    }

    public static GroupDto entityToDtoWithoutStudents(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setTitle(group.getTitle());
        groupDto.setCourse(group.getCourse());
        if (group.getMonitor() != null) {
            groupDto.setMonitor(StudentMapper.entityToDtoWithoutGroup(group.getMonitor()));
        }
        if (group.getCurator() != null) {
            groupDto.setCurator(TeacherMapper.entityToDto(group.getCurator()));
        }
        return groupDto;
    }
}