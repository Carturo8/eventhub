package com.carturo.eventhub.infrastructure.adapters.in.web.dto.request;

import com.carturo.eventhub.infrastructure.adapters.in.web.validation.ValidationGroups;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenueRequest {

    @NotBlank(message = "{validation.venue.name.notBlank}", groups = {ValidationGroups.Create.class})
    @Size(max = 255, message = "{validation.venue.name.size}", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String name;

    @NotBlank(message = "{validation.venue.city.notBlank}", groups = {ValidationGroups.Create.class})
    @Size(max = 255, message = "{validation.venue.city.size}", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String city;

    @NotNull(message = "{validation.venue.capacity.notNull}", groups = {ValidationGroups.Create.class})
    @Min(value = 1, message = "{validation.venue.capacity.min}", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private Integer capacity;
}