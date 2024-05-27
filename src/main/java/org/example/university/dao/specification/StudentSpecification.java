package org.example.university.dao.specification;

import org.example.university.dao.model.Student;
import org.example.university.filter.StudentFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class StudentSpecification {
    public Specification<Student> searchFilter(StudentFilter studentFilter) {
        return (root, query, cb) -> Specification.where(attributeContains("lastName", studentFilter.getSearch()))
                .or(attributeContains("firstName", studentFilter.getSearch()))
                .or(attributeContains("middleName", studentFilter.getSearch()))
                .and(equals("gender", studentFilter.getGender()))
                .and(date(studentFilter.getStartDate(), studentFilter.getEndDate()))
                .toPredicate(root, query, cb);
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

    protected Specification<Student> equals(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
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
