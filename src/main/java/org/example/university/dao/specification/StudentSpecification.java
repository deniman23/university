package org.example.university.dao.specification;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.example.university.dao.model.Student;
import org.example.university.filter.StudentFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class StudentSpecification {
    public Specification<Student> searchFilter(StudentFilter studentFilter, List<String> includes) {
        return (root, query, cb) -> {
            if (includes != null && !includes.isEmpty()) {
                for (String include : includes) {
                    fetch(root, include);
                }
            }
            return Specification.where(attributeContains("lastName", studentFilter.getSearch()))
                    .or(attributeContains("firstName", studentFilter.getSearch()))
                    .or(attributeContains("middleName", studentFilter.getSearch()))
                    .and(equals(studentFilter.getGender()))
                    .and(date(studentFilter.getStartDate(), studentFilter.getEndDate()))
                    .toPredicate(root, query, cb);
        };
    }

    protected void fetch(Root<Student> root, String attribute) {
        if (!isJoined(root, attribute)) {
            root.fetch(attribute, JoinType.LEFT);
        }
    }

    protected boolean isJoined(Root<Student> root, String attribute) {
        return root.getFetches().stream().anyMatch(f -> f.getAttribute().getName().equals(attribute));
    }

    protected Specification<Student> attributeContains(String attribute, String value) {
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

    protected Specification<Student> equals(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get("gender"), value);
        };
    }

    protected Specification<Student> date(Instant startDate, Instant endDate) {
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
