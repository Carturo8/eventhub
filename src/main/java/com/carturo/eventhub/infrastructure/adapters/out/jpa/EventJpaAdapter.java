package com.carturo.eventhub.infrastructure.adapters.out.jpa;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.domain.model.event.EventFilter;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.out.EventRepositoryPort;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.EventEntity;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.mapper.EventEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
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
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageResult<Event> findAll(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.page(),
                pageRequest.size()
        );

        Page<EventEntity> page = repository.findAll(pageable);

        return new PageResult<>(
                page.getContent().stream().map(mapper::toDomain).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public PageResult<Event> search(EventFilter filter, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.page(),
                pageRequest.size()
        );

        Page<EventEntity> page = repository.findAll(
                EventSpecifications.withFilters(filter),
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