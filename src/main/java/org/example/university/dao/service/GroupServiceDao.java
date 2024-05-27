package org.example.university.dao.service;

import org.example.university.dao.model.Group;
import org.example.university.dao.repository.GroupRepository;
import org.example.university.dao.repository.GroupRepository;
import org.example.university.dao.specification.GroupSpecification;
import org.example.university.filter.GroupFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupServiceDao {
    private final GroupRepository groupRepository;
    private final GroupSpecification groupSpecification;

    @Autowired
    public GroupServiceDao(GroupRepository groupRepository, GroupSpecification groupSpecification) {
        this.groupRepository = groupRepository;
        this.groupSpecification = groupSpecification;
    }

    public Page<Group> findByFilter(GroupFilter filter, Pageable pageable) {
        Specification<Group> spec = groupSpecification.searchFilter(filter);
        return groupRepository.findAll(spec, pageable);
    }



    public Optional<Group> findById(UUID id) {
        return groupRepository.findById(id);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public void delete(UUID id) {
        groupRepository.deleteById(id);
    }
}
