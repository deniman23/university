package org.example.university.service;

import org.example.university.dao.model.Group;
import org.example.university.dao.service.GroupServiceDao;
import org.example.university.dao.service.StudentServiceDao;
import org.example.university.dao.service.TeacherServiceDao;
import org.example.university.dto.GroupDto;
import org.example.university.dto.mapper.GroupMapper;
import org.example.university.error.ResourceNotFoundException;
import org.example.university.filter.GroupFilter;
import org.example.university.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GroupService {
    private final GroupServiceDao groupServiceDao;
    private final StudentServiceDao studentServiceDao;
    private final TeacherServiceDao teacherServiceDao;

    @Autowired
    public GroupService(GroupServiceDao groupServiceDao, StudentServiceDao studentServiceDao, TeacherServiceDao teacherServiceDao) {
        this.groupServiceDao = groupServiceDao;
        this.studentServiceDao = studentServiceDao;
        this.teacherServiceDao = teacherServiceDao;
    }

    // Поиск по ID
    @Transactional(readOnly = true)
    public GroupDto findById(UUID id, List<String> includes) {
        Group group = groupServiceDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group not found with ID: " + id));
        return GroupMapper.entityToDto(group, includes);
    }
    @Transactional(readOnly = true)
    public Page<GroupDto> findAll(GroupFilter filter, Pageable pageable, List<String> includes) {
        Page<Group> groupPage = groupServiceDao.findByFilter(filter, pageable, includes);
        return groupPage.map(group -> GroupMapper.entityToDto(group, includes));
    }
    @Transactional(readOnly = true)
    public GroupDto create(GroupRequest groupRequest, List<String> includes) {
        Group group = new Group();
        group.setTitle(groupRequest.getTitle());
        group.setCourse(groupRequest.getCourse());
        group.setStudents(new ArrayList<>());

        if (groupRequest.getMonitorId() != null) {
            group.setMonitor(studentServiceDao.findById(groupRequest.getMonitorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + groupRequest.getMonitorId())));
        }

        if (groupRequest.getCuratorId() != null) {
            group.setCurator(teacherServiceDao.findById(groupRequest.getCuratorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + groupRequest.getCuratorId())));
        }

        if (groupRequest.getStudentIds() != null) {
            group.setStudents(studentServiceDao.findAllById(groupRequest.getStudentIds()));
        }

        group = groupServiceDao.save(group);
        return GroupMapper.entityToDto(group, includes);
    }
    @Transactional(readOnly = true)
    public GroupDto edit(GroupRequest groupRequest, List<String> includes) {
        Group group = groupServiceDao.findById(groupRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with ID: " + groupRequest.getId()));
        group.setTitle(groupRequest.getTitle());
        group.setCourse(groupRequest.getCourse());

        if (groupRequest.getMonitorId() != null) {
            group.setMonitor(studentServiceDao.findById(groupRequest.getMonitorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + groupRequest.getMonitorId())));
        }

        if (groupRequest.getCuratorId() != null) {
            group.setCurator(teacherServiceDao.findById(groupRequest.getCuratorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + groupRequest.getCuratorId())));
        }

        if (groupRequest.getStudentIds() != null) {
            group.setStudents(studentServiceDao.findAllById(groupRequest.getStudentIds()));
        }

        group = groupServiceDao.save(group);
        return GroupMapper.entityToDto(group, includes);
    }
    @Transactional(readOnly = true)
    public void delete(UUID id) {
        groupServiceDao.delete(id);
    }
}
