package org.example.university.dao.specification;

import org.example.university.dao.model.Lesson;
import org.example.university.filter.LessonFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class LessonSpecification {
    public Specification<Lesson> searchFilter(LessonFilter lessonFilter) {
        return (root, query, cb) -> Specification.where(attributeContains("teacherName", lessonFilter.getTeacherName()))
                .and(attributeContains("subjectTitle", lessonFilter.getSubjectTitle()))
                .and(attributeContains("groupName", lessonFilter.getGroupName()))
                .and(date(lessonFilter.getStartDate(), lessonFilter.getEndDate()))
                .toPredicate(root, query, cb);
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
