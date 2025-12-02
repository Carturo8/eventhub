package com.carturo.eventhub.infrastructure.adapters.in.web.validation;

/**
 * A container for validation group marker interfaces.
 * - Create: Used for validation during resource creation.
 * - Update: Used for validation during resource update.
 */
public interface ValidationGroups {
    interface Create {}
    interface Update {}
}