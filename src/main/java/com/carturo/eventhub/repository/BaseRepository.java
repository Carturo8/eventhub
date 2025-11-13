package com.carturo.eventhub.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    T save(T entity);
    List<T> findAll();
    Optional<T> findById(Long id);
    void delete(Long id);
}