package org.example.university.dto;

import java.util.UUID;

public class SubjectDto {

    private UUID id;
    private String title;

    public SubjectDto() {
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
}
