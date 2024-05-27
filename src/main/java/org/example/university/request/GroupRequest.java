package org.example.university.request;

import java.util.List;
import java.util.UUID;

public class GroupRequest {
    private UUID id;
    private String title;
    private Integer course;
    private UUID monitorId;
    private UUID curatorId;
    private List<UUID> studentIds;

    public GroupRequest() {
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

    public UUID getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(UUID monitorId) {
        this.monitorId = monitorId;
    }

    public UUID getCuratorId() {
        return curatorId;
    }

    public void setCuratorId(UUID curatorId) {
        this.curatorId = curatorId;
    }

    public List<UUID> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<UUID> studentIds) {
        this.studentIds = studentIds;
    }
}
