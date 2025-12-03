package com.carturo.eventhub.domain.model.pagination;

public record PageRequest(int page, int size) {

    public PageRequest {
        if (page < 0) {
            throw new IllegalArgumentException("page must be >= 0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }
    }
}