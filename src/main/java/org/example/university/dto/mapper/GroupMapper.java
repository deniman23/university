package org.example.university.dto.mapper;

import org.example.university.dao.model.Group;
import org.example.university.dto.GroupDto;
import org.example.university.dto.StudentDto;

import java.util.List;

public class GroupMapper {
    public static GroupDto entityToDto(Group group, List<String> includes) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setTitle(group.getTitle());
        groupDto.setCourse(group.getCourse());

        if (includes != null) {
            if (includes.contains("monitor")) {
                groupDto.setMonitor(StudentMapper.entityToDtoWithoutGroup(group.getMonitor()));
            }
            if (includes.contains("curator")) {
                groupDto.setCurator(TeacherMapper.entityToDto(group.getCurator(), includes));
            }
            if (includes.contains("students")) {
                List<StudentDto> studentDtos = group.getStudents().stream()
                        .map(StudentMapper::entityToDtoWithoutGroup)
                        .toList();
                groupDto.setStudents(studentDtos);
            }
        }

        return groupDto;
    }
}