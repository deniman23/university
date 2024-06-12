package org.example.university.controllers;

import org.example.university.service.GroupService;
import org.example.university.dto.GroupDto;
import org.example.university.filter.GroupFilter;
import org.example.university.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<GroupDto> create(@RequestBody GroupRequest groupRequest, @RequestParam(required = false) List<String> includes) {
        GroupDto created = groupService.create(groupRequest, includes);
        return ResponseEntity.ok().body(created);
    }

    @PutMapping
    public ResponseEntity<GroupDto> edit(@RequestBody GroupRequest groupRequest, @RequestParam(required = false) List<String> includes) {
        GroupDto updated = groupService.edit(groupRequest, includes);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        groupService.delete(id);
        return ResponseEntity.ok().body("Group deleted");
    }

    @GetMapping("/{id}")
    public GroupDto output(@PathVariable UUID id, @RequestParam(required = false) List<String> includes) {
        return groupService.findById(id, includes);
    }

    @GetMapping
    public Page<GroupDto> filter(GroupFilter groupFilter, Pageable pageable, @RequestParam(required = false) List<String> includes) {
        return groupService.findAll(groupFilter, pageable, includes);
    }
}