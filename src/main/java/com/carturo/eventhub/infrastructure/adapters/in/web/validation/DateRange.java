package com.carturo.eventhub.infrastructure.adapters.in.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that a class has a startDate and an endDate,
 * and that the startDate is not after the endDate.
 */
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRange {
    String message() default "{validation.event.dateRange}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}