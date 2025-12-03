package com.carturo.eventhub.infrastructure.adapters.in.web.exception;

public record ValidationError(String field, String message) {
}