package com.carturo.eventhub.infrastructure.adapters.in.web.validation;

import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.EventRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<DateRange, EventRequest> {

    @Override
    public void initialize(DateRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(EventRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        if (startDate == null || endDate == null) {
            return true;
        }

        return !startDate.isAfter(endDate);
    }
}