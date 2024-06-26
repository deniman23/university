package org.example.university.service;

import org.example.university.dao.model.Lesson;
import org.example.university.dao.service.*;
import org.example.university.dto.LessonDto;
import org.example.university.dto.mapper.GroupMapper;
import org.example.university.dto.mapper.LessonMapper;
import org.example.university.dto.mapper.SubjectMapper;
import org.example.university.dto.mapper.TeacherMapper;
import org.example.university.error.ResourceNotFoundException;
import org.example.university.filter.LessonFilter;
import org.example.university.request.LessonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LessonService {
    private final LessonServiceDao lessonServiceDao;
    private final SubjectServiceDao subjectServiceDao;
    private final TeacherServiceDao teacherServiceDao;
    private final GroupServiceDao groupServiceDao;

    @Autowired
    public LessonService(LessonServiceDao lessonServiceDao, SubjectServiceDao subjectServiceDao, TeacherServiceDao teacherServiceDao, GroupServiceDao groupServiceDao) {
        this.lessonServiceDao = lessonServiceDao;
        this.subjectServiceDao = subjectServiceDao;
        this.teacherServiceDao = teacherServiceDao;
        this.groupServiceDao = groupServiceDao;
    }

    // Поиск по ID
    @Transactional(readOnly = true)
    public LessonDto findById(UUID id, List<String> includes) {
        Lesson lesson = lessonServiceDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson not found with ID: " + id));
        return LessonMapper.entityToDto(lesson, includes);
    }

    // Поиск по фильтру с постраничным выводом
    @Transactional(readOnly = true)
    public Page<LessonDto> findAll(LessonFilter filter, Pageable pageable, List<String> includes) {
        Page<Lesson> lessonPage = lessonServiceDao.findByFilter(filter, pageable, includes);
        return lessonPage.map(lesson -> LessonMapper.entityToDto(lesson, includes));
    }

    // Создать
    @Transactional(readOnly = true)
    public LessonDto create(LessonRequest lessonRequest, List<String> includes) {
        Lesson lesson = new Lesson();
        lesson.setName(lessonRequest.getName());
        lesson.setCreationDate(lessonRequest.getCreationDate());

        if (lessonRequest.getGroupId() != null) {
            lesson.setGroup(groupServiceDao.findById(lessonRequest.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group not found with ID: " + lessonRequest.getGroupId())));
        }
        if (lessonRequest.getTeacherId() != null) {
            lesson.setTeacher(teacherServiceDao.findById(lessonRequest.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + lessonRequest.getTeacherId())));
        }
        if (lessonRequest.getSubjectId() != null) {
            lesson.setSubject(subjectServiceDao.findById(lessonRequest.getSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + lessonRequest.getSubjectId())));
        }

        lesson = lessonServiceDao.save(lesson);

        LessonDto lessonDto = LessonMapper.entityToDto(lesson, includes);

        lessonDto.setTeacher(TeacherMapper.entityToDto(lesson.getTeacher(), includes));
        lessonDto.setGroup(GroupMapper.entityToDto(lesson.getGroup(), includes));
        lessonDto.setSubject(SubjectMapper.entityToDto(lesson.getSubject()));

        return lessonDto;
    }

    // Изменить
    @Transactional(readOnly = true)
    public LessonDto edit(LessonRequest lessonRequest, UUID id, List<String> includes) {
        Lesson lesson = lessonServiceDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with ID: " + id));

        lesson.setName(lessonRequest.getName());
        lesson.setCreationDate(lessonRequest.getCreationDate());

        if (lessonRequest.getGroupId() != null) {
            lesson.setGroup(groupServiceDao.findById(lessonRequest.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group not found with ID: " + lessonRequest.getGroupId())));
        } else {
            lesson.setGroup(null);
        }

        if (lessonRequest.getTeacherId() != null) {
            lesson.setTeacher(teacherServiceDao.findById(lessonRequest.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + lessonRequest.getTeacherId())));
        } else {
            lesson.setTeacher(null);
        }

        if (lessonRequest.getSubjectId() != null) {
            lesson.setSubject(subjectServiceDao.findById(lessonRequest.getSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + lessonRequest.getSubjectId())));
        } else {
            lesson.setSubject(null);
        }

        lesson = lessonServiceDao.save(lesson);

        LessonDto lessonDto = LessonMapper.entityToDto(lesson, includes);

        lessonDto.setTeacher(TeacherMapper.entityToDto(lesson.getTeacher(), includes));
        lessonDto.setGroup(GroupMapper.entityToDto(lesson.getGroup(), includes));
        lessonDto.setSubject(SubjectMapper.entityToDto(lesson.getSubject()));

        return lessonDto;
    }

    // Удалить
    @Transactional(readOnly = true)
    public void delete(UUID id) {
        lessonServiceDao.delete(id);
    }
}
