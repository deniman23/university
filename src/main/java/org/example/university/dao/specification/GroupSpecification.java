package org.example.university.dao.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.example.university.dao.model.Group;
import org.example.university.dao.model.Student;
import org.example.university.dao.model.Teacher;
import org.example.university.filter.GroupFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupSpecification {
    public Specification<Group> searchFilter(GroupFilter groupFilter, List<String> includes) {
        return (root, query, cb) -> {
            if (includes != null && !includes.isEmpty()) {
                for (String include : includes) {
                    fetch(root, include);
                }
            }
            return Specification.where(titleContains(groupFilter.getSearch()))
                    .and(equals("course", groupFilter.getCourse()))
                    .and(monitorNameContains(groupFilter.getMonitorName()))
                    .and(curatorNameContains(groupFilter.getCuratorName()))
                    .toPredicate(root, query, cb);
        };
    }



    protected void fetch(Root<Group> root, String attribute) {
        if (!isJoined(root, attribute)) {
            root.fetch(attribute, JoinType.LEFT);
        }
    }

    protected boolean isJoined(Root<Group> root, String attribute) {
        return root.getFetches().stream().anyMatch(f -> f.getAttribute().getName().equals(attribute));
    }

    protected Specification<Group> monitorNameContains(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            Join<Group, Student> monitor = root.join("monitor");
            return cb.or(
                    cb.like(
                            cb.lower(monitor.get("firstName")),
                            "%" + value.toLowerCase() + "%"
                    ),
                    cb.like(
                            cb.lower(monitor.get("middleName")),
                            "%" + value.toLowerCase() + "%"
                    ),
                    cb.like(
                            cb.lower(monitor.get("lastName")),
                            "%" + value.toLowerCase() + "%"
                    )
            );
        };
    }

    protected Specification<Group> curatorNameContains(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            Join<Group, Teacher> curator = root.join("curator");
            return cb.or(
                    cb.like(
                            cb.lower(curator.get("firstName")),
                            "%" + value.toLowerCase() + "%"
                    ),
                    cb.like(
                            cb.lower(curator.get("middleName")),
                            "%" + value.toLowerCase() + "%"
                    ),
                    cb.like(
                            cb.lower(curator.get("lastName")),
                            "%" + value.toLowerCase() + "%"
                    )
            );
        };
    }

    protected Specification<Group> titleContains(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.like(
                    cb.lower(root.get("title")),
                    "%" + value.toLowerCase() + "%"
            );
        };
    }

    protected Specification<Group> equals(String attribute, Integer value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
        };
    }
}