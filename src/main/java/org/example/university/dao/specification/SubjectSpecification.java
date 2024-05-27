package org.example.university.dao.specification;

import org.example.university.dao.model.Subject;
import org.example.university.filter.SubjectFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SubjectSpecification {
    public Specification<Subject> searchFilter(SubjectFilter subjectFilter) {
        return (root, query, cb) -> Specification.where(attributeContains("title", subjectFilter.getSearch()))
                .toPredicate(root, query, cb);
    }
    protected Specification<Subject> attributeContains(String attribute, String value) {
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
}
