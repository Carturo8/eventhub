package com.carturo.eventhub.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private List<String> errors;
    private String path;

    public ApiError(String message, List<String> errors, String path) {
        this.message = message;
        this.errors = errors;
        this.path = path;
    }
}