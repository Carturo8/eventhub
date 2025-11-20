package com.carturo.eventhub.repository.jpa;

import com.carturo.eventhub.entity.EventEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventSpecifications {

    public static Specification<EventEntity> withFilters(String city, String category, LocalDate startDate) {
        return (root, query, cb) -> {

            var predicates = new ArrayList<Predicate>();

            if (city != null && !city.isBlank()) {
                var venueJoin = root.join("venue");
                predicates.add(cb.like(cb.lower(venueJoin.get("city")), "%" + city.toLowerCase() + "%"));
            }

            if (category != null && !category.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("category")), "%" + category.toLowerCase() + "%"));
            }

            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), startDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}