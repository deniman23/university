package org.example.university.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.util.*;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(generator = "uuid-generator")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @NotBlank
    private String title;

    @NotNull
    private Integer course;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id")
    private Student monitor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curator_id")
    private Teacher curator;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

    public Group() {
    }

    public UUID getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Student getMonitor() {
        return monitor;
    }

    public void setMonitor(Student monitor) {
        this.monitor = monitor;
    }

    public Teacher getCurator() {
        return curator;
    }

    public void setCurator(Teacher curator) {
        this.curator = curator;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
