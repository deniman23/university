package org.example.university.controllers;

import org.example.university.api.GroupService;
import org.example.university.dto.GroupDto;
import org.example.university.filter.GroupFilter;
import org.example.university.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@RequestBody GroupRequest groupRequest) {
        GroupDto created = groupService.create(groupRequest);
        return ResponseEntity.ok().body(created);
    }

    @PutMapping
    public ResponseEntity<GroupDto> edit(@RequestBody GroupRequest groupRequest) {
        GroupDto updated = groupService.edit(groupRequest);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> terminate(@PathVariable UUID id) {
        groupService.terminate(id);
        return ResponseEntity.ok().body("Group terminated");
    }

    @GetMapping("/{id}")
    public GroupDto output(@PathVariable UUID id) {
        return groupService.findById(id);
    }

    @GetMapping
    public List<GroupDto> filter(GroupFilter groupFilter, Pageable pageable) {
        return groupService.findAll(groupFilter, pageable).getContent();
    }
}