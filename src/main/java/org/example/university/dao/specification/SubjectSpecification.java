package org.example.university.dao.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.example.university.dao.model.Subject;
import org.example.university.filter.SubjectFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SubjectSpecification {
    public Specification<Subject> searchFilter(SubjectFilter subjectFilter) {
        return (root, query, cb) -> {
            if (subjectFilter.getIncludes() != null && !subjectFilter.getIncludes().isEmpty()) {
                for (String include : subjectFilter.getIncludes()) {
                    fetch(root, include);
                }
            }
            return Specification.where(attributeContains("title", subjectFilter.getSearch()))
                    .toPredicate(root, query, cb);
        };
    }

    protected void fetch(Root<Subject> root, String attribute) {
        if (!isJoined(root, attribute)) {
            root.fetch(attribute, JoinType.LEFT);
        }
    }

    protected boolean isJoined(Root<Subject> root, String attribute) {
        return root.getFetches().stream().anyMatch(f -> f.getAttribute().getName().equals(attribute));
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