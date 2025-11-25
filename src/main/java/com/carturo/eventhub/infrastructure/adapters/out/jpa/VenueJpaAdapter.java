package com.carturo.eventhub.infrastructure.adapters.out.jpa;

import com.carturo.eventhub.domain.model.Venue;
import com.carturo.eventhub.domain.model.pagination.PageRequest;
import com.carturo.eventhub.domain.model.pagination.PageResult;
import com.carturo.eventhub.domain.ports.out.VenueRepositoryPort;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.VenueEntity;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.mapper.VenueEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class VenueJpaAdapter implements VenueRepositoryPort {

    private final VenueJpaRepository repository;
    private final VenueEntityMapper mapper;

    @Override
    public Venue save(Venue venue) {
        VenueEntity entity = mapper.toEntity(venue);
        VenueEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public PageResult<Venue> findAll(PageRequest pageRequest) {

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
    public boolean existsByNameIgnoreCase(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}