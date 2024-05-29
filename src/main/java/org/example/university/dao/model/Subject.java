package org.example.university.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(generator = "uuid-generator")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @NotBlank
    private String title;


    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherSubject> subjectLinks = new HashSet<>();

    public Subject() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return id;
    }


    public Set<TeacherSubject> getSubjectLinks() {
        return subjectLinks;
    }

    public void setSubjectLinks(Set<TeacherSubject> subjectLinks) {
        this.subjectLinks = subjectLinks;
    }
}