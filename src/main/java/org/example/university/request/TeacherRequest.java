package org.example.university.request;

import org.example.university.dao.model.Gender;

import java.util.List;
import java.util.UUID;

public class TeacherRequest {
    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Gender gender;
    private List<UUID> subjectIds;

    public TeacherRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<UUID> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<UUID> subjectIds) {
        this.subjectIds = subjectIds;
    }
}
