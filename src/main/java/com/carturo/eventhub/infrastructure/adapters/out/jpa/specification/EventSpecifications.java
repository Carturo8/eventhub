package com.carturo.eventhub.infrastructure.adapters.out.jpa.specification;

import com.carturo.eventhub.domain.model.event.EventFilter;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.JoinType;

import java.util.ArrayList;

public class EventSpecifications {

    public static Specification<EventEntity> withFilters(EventFilter filter) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (filter.getCity() != null && !filter.getCity().isBlank()) {
                var venueJoin = root.join("venue", JoinType.LEFT);
                predicates.add(cb.like(
                        cb.lower(venueJoin.get("city")),
                        "%" + filter.getCity().toLowerCase() + "%"
                ));
            }

            if (filter.getCategory() != null) {
                predicates.add(cb.equal(root.get("category"), filter.getCategory()));
            }

            if (filter.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("eventDate"), filter.getStartDate()));
            }

            if (filter.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("eventDate"), filter.getEndDate()));
            }

            if (filter.getVenueId() != null) {
                predicates.add(cb.equal(root.get("venue").get("id"), filter.getVenueId()));
            }

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}