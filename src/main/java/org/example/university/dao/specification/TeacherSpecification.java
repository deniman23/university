package org.example.university.dao.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.example.university.dao.model.Teacher;
import org.example.university.filter.TeacherFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class TeacherSpecification {
    public Specification<Teacher> searchFilter(TeacherFilter teacherFilter, List<String> includes) {
        return (root, query, cb) -> {
            if (includes != null && !includes.isEmpty()) {
                for (String include : includes) {
                    fetch(root, include);
                }
            }
            return Specification.where(attributeContains("lastName", teacherFilter.getSearch()))
                    .or(attributeContains("firstName", teacherFilter.getSearch()))
                    .or(attributeContains("middleName", teacherFilter.getSearch()))
                    .and(equals("gender", teacherFilter.getGender()))
                    .toPredicate(root, query, cb);
        };
    }

    protected void fetch(Root<Teacher> root, String attribute) {
        if (!isJoined(root, attribute)) {
            root.fetch(attribute, JoinType.LEFT);
        }
    }

    protected boolean isJoined(Root<Teacher> root, String attribute) {
        return root.getFetches().stream().anyMatch(f -> f.getAttribute().getName().equals(attribute));
    }

    protected Specification<Teacher> attributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.like(
                    cb.lower(root.get(attribute)),
                    "%" + value.toLowerCase() + "%"
            );
        };
    }

    protected Specification<Teacher> equals(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
        };
    }

    protected Specification<Teacher> date(Instant startDate, Instant endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) {
                return null;
            }

            if (startDate != null && endDate == null) {
                return cb.greaterThanOrEqualTo(root.get("creationDate"), startDate);
            }

            if (startDate == null) {
                return cb.lessThanOrEqualTo(root.get("creationDate"), endDate);
            }

            return cb.between(root.get("creationDate"), startDate, endDate);
        };
    }
}