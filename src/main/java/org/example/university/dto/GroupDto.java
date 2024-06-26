package org.example.university.dto;

import org.example.university.dao.model.Student;
import org.example.university.dao.model.Teacher;

import java.util.List;
import java.util.UUID;

public class GroupDto {

    private UUID id;
    private String title;
    private Integer course;
    private StudentDto monitor;
    private TeacherDto curator;
    private List<StudentDto> students;

    public GroupDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public StudentDto getMonitor() {
        return monitor;
    }

    public void setMonitor(StudentDto monitor) {
        this.monitor = monitor;
    }

    public TeacherDto getCurator() {
        return curator;
    }

    public void setCurator(TeacherDto curator) {
        this.curator = curator;
    }

    public List<StudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDto> students) {
        this.students = students;
    }
}
