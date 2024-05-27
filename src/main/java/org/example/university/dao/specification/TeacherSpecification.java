package org.example.university.dao.specification;

import org.example.university.dao.model.Teacher;
import org.example.university.filter.TeacherFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
public class TeacherSpecification {
    public Specification<Teacher> searchFilter(TeacherFilter teacherFilter) {
        return (root, query, cb) -> {
            return Specification.where(attributeContains("lastName", teacherFilter.getSearch()))
                    .or(attributeContains("firstName", teacherFilter.getSearch()))
                    .or(attributeContains("middleName", teacherFilter.getSearch()))
                    .and(equals("gender", teacherFilter.getGender()))
                    .and(date(teacherFilter.getStartDate(), teacherFilter.getEndDate()))
                    .toPredicate(root, query, cb);
        };
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