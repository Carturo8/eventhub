package com.carturo.eventhub.infrastructure.adapters.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PageResponse<T>(
    List<T> content,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages,
    @JsonProperty("isLast")
    boolean isLast
) {}