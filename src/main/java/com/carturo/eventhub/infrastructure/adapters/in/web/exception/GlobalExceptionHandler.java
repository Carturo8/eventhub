package com.carturo.eventhub.infrastructure.adapters.in.web.exception;

import com.carturo.eventhub.application.exception.DuplicateResourceException;
import com.carturo.eventhub.application.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String TIMESTAMP = "timestamp";
    private static final String TRACE_ID = "traceId";
    private static final String SPAN_ID = "spanId";

    private final CurrentTraceContext currentTraceContext;

    public GlobalExceptionHandler(CurrentTraceContext currentTraceContext) {
        this.currentTraceContext = currentTraceContext;
    }

    private void addTracingInfoToProblemDetail(ProblemDetail problemDetail) {
        TraceContext traceContext = currentTraceContext.context();
        if (traceContext != null) {
            problemDetail.setProperty(TRACE_ID, traceContext.traceId());
            problemDetail.setProperty(SPAN_ID, traceContext.spanId());
        } else {
            problemDetail.setProperty(TRACE_ID, "N/A");
            problemDetail.setProperty(SPAN_ID, "N/A");
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed");
        problemDetail.setDetail("One or more fields have validation errors.");
        problemDetail.setType(URI.create("https://example.com/docs/errors/validation"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        addTracingInfoToProblemDetail(problemDetail);

        List<ValidationError> errors = ex.getBindingResult().getAllErrors().stream()
                .map(err -> {
                    if (err instanceof FieldError fieldError) {
                        return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    return new ValidationError(err.getObjectName(), err.getDefaultMessage());
                })
                .collect(Collectors.toList());

        problemDetail.setProperty("errors", errors);
        log.warn("Validation failed for request {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Invalid Argument");
        problemDetail.setType(URI.create("https://example.com/docs/errors/invalid-argument"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        addTracingInfoToProblemDetail(problemDetail);
        log.warn("Invalid argument for request {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return problemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://example.com/docs/errors/not-found"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        addTracingInfoToProblemDetail(problemDetail);
        log.warn("Resource not found for request {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return problemDetail;
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ProblemDetail handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Duplicate Resource");
        problemDetail.setType(URI.create("https://example.com/docs/errors/duplicate-resource"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        addTracingInfoToProblemDetail(problemDetail);
        log.warn("Duplicate resource for request {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        String detail = "A database integrity constraint was violated. This may be due to a duplicate entry or a foreign key constraint failure.";
        if (ex.getMostSpecificCause() != null) {
            detail = ex.getMostSpecificCause().getMessage();
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, detail);
        problemDetail.setTitle("Data Integrity Violation");
        problemDetail.setType(URI.create("https://example.com/docs/errors/data-integrity"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        addTracingInfoToProblemDetail(problemDetail);
        log.error("Data integrity violation for request {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected internal server error occurred.");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://example.com/docs/errors/internal-error"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        addTracingInfoToProblemDetail(problemDetail);
        log.error("Unhandled exception for request {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return problemDetail;
    }
}