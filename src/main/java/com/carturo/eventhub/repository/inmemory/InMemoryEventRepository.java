package com.carturo.eventhub.repository.inmemory;

import com.carturo.eventhub.entity.EventEntity;
import com.carturo.eventhub.repository.domain.EventRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Profile("inmemory")
public class InMemoryEventRepository implements EventRepository {

    private final Map<Long, EventEntity> storage = new LinkedHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public EventEntity save(EventEntity event) {
        if (event.getId() == null) {
            event.setId(idCounter.getAndIncrement());
        }
        storage.put(event.getId(), event);
        return event;
    }

    @Override
    public Page<EventEntity> findAll(Pageable pageable) {
        List<EventEntity> list = new ArrayList<>(storage.values());
        return toPage(list, pageable);
    }

    @Override
    public Optional<EventEntity> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return storage.values().stream()
                .anyMatch(e -> e.getName() != null && e.getName().equalsIgnoreCase(name));
    }

    @Override
    public Page<EventEntity> search(String city, String category, LocalDate startDate, Pageable pageable) {
        Stream<EventEntity> stream = storage.values().stream();
        if (city != null && !city.isBlank()) {
            stream = stream.filter(e -> e.getVenue() != null && e.getVenue().getCity() != null
                    && e.getVenue().getCity().toLowerCase().contains(city.toLowerCase()));
        }
        if (category != null && !category.isBlank()) {
            stream = stream.filter(e -> e.getCategory() != null && e.getCategory().toLowerCase().contains(category.toLowerCase()));
        }
        if (startDate != null) {
            stream = stream.filter(e -> e.getDate() != null && !e.getDate().isBefore(startDate));
        }
        List<EventEntity> filtered = stream.collect(Collectors.toList());
        return toPage(filtered, pageable);
    }

    private Page<EventEntity> toPage(List<EventEntity> list, Pageable pageable) {
        int total = list.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), total);
        List<EventEntity> content = start > end ? Collections.emptyList() : list.subList(start, end);
        return new PageImpl<>(content, pageable, total);
    }
}