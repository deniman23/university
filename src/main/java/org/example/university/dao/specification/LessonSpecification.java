package org.example.university.dao.specification;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.example.university.dao.model.Lesson;
import org.example.university.error.ResourceNotFoundException;
import org.example.university.filter.LessonFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Component
public class LessonSpecification {
    public Specification<Lesson> searchFilter(LessonFilter lessonFilter, List<String> includes) {

        if (includes == null || includes.isEmpty()) {
            includes = Arrays.asList("id", "name", "creationDate");
        }

        List<String> finalIncludes = includes;
        return (root, query, cb) -> {
            for (String include : finalIncludes) {
                fetch(root, include);
            }
            return Specification.where(attributeContains("teacher", lessonFilter.getTeacherName()))
                    .and(attributeContains("subject", lessonFilter.getSubjectTitle()))
                    .and(attributeContains("group", lessonFilter.getGroupName()))
                    .and(date(lessonFilter.getStartDate(), lessonFilter.getEndDate()))
                    .toPredicate(root, query, cb);
        };
    }

    protected void fetch(Root<Lesson> root, String attribute) {
        switch (attribute) {
            case "subject":
                if (!isJoined(root, "subject")) {
                    root.fetch("subject", JoinType.LEFT);
                }
                break;
            case "teacher":
                if (!isJoined(root, "teacher")) {
                    root.fetch("teacher", JoinType.LEFT);
                }
                break;
            case "group":
                if (!isJoined(root, "group")) {
                    root.fetch("group", JoinType.LEFT);
                }
                break;
            case "id":
            case "name":
            case "creationDate":
                break;
            default:
                throw new ResourceNotFoundException("Unknown attribute: " + attribute + " for Lesson entity.");
        }
    }

    protected boolean isJoined(Root<Lesson> root, String attribute) {
        return root.getFetches().stream().anyMatch(f -> f.getAttribute().getName().equals(attribute));
    }

    protected Specification<Lesson> attributeContains(String attribute, String value) {
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

    protected Specification<Lesson> date(Instant startDate, Instant endDate) {
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
