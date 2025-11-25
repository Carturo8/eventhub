package com.carturo.eventhub.infrastructure.adapters.out.jpa;

import com.carturo.eventhub.domain.model.Event;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.EventEntity;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.mapper.EventEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class EventJpaAdapter implements EventRepositoryPort {

    private final EventJpaRepository repository;
    private final EventEntityMapper mapper;

    @Override
    public Event save(Event event) {
        EventEntity entity = mapper.toEntity(event);
        EventEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public PageResult<Event> findAll(PageRequest pageRequest) {

        Pageable pageable = Pageable.ofSize(pageRequest.size())
                .withPage(pageRequest.page());

        var page = repository.findAll(pageable);

        return new PageResult<>(
                page.getContent().stream().map(mapper::toDomain).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public PageResult<Event> search(String city, String category, LocalDate startDate, PageRequest pageRequest) {

        Pageable pageable = Pageable.ofSize(pageRequest.size())
                .withPage(pageRequest.page());

        var page = repository.findAll(
                EventSpecifications.withFilters(city, category, startDate),
                pageable
        );

        return new PageResult<>(
                page.getContent().stream().map(mapper::toDomain).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}