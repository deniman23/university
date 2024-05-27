package org.example.university.dao.specification;

import org.example.university.dao.model.Group;
import org.example.university.filter.GroupFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class GroupSpecification {
    public Specification<Group> searchFilter(GroupFilter groupFilter) {
        return (root, query, cb) -> Specification.where(attributeContains("title", groupFilter.getSearch()))
                .and(equals("course", groupFilter.getCourse()))
                .and(attributeContains("monitorName", groupFilter.getMonitorName()))
                .and(attributeContains("curatorName", groupFilter.getCuratorName()))
                .toPredicate(root, query, cb);
    }

    protected Specification<Group> attributeContains(String attribute, String value) {
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

    protected Specification<Group> equals(String attribute, Integer value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
        };
    }
}