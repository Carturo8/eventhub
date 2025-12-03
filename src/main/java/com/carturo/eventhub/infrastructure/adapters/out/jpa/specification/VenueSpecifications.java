package com.carturo.eventhub.infrastructure.adapters.out.jpa.specification;

import com.carturo.eventhub.domain.model.venue.VenueFilter;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;

public class VenueSpecifications {

    public static Specification<VenueEntity> withFilters(VenueFilter filter) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (filter.getCity() != null && !filter.getCity().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("city")),
                        "%" + filter.getCity().toLowerCase() + "%"
                ));
            }

            if (filter.getMinCapacity() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("capacity"), filter.getMinCapacity()));
            }

            if (filter.getMaxCapacity() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("capacity"), filter.getMaxCapacity()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}