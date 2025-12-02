package com.carturo.eventhub.infrastructure.adapters.in.web.dto.request;

import com.carturo.eventhub.domain.model.event.EventCategory;
import com.carturo.eventhub.infrastructure.adapters.in.web.validation.DateRange;
import com.carturo.eventhub.infrastructure.adapters.in.web.validation.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DateRange(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
public class EventRequest {

    @NotBlank(message = "{validation.event.name.notBlank}", groups = {ValidationGroups.Create.class})
    @Size(max = 255, message = "{validation.event.name.size}", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String name;

    @Size(max = 500, message = "{validation.event.description.size}", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String description;

    @NotNull(message = "{validation.event.category.notNull}", groups = {ValidationGroups.Create.class})
    private EventCategory category;

    @NotNull(message = "{validation.event.startDate.notNull}", groups = {ValidationGroups.Create.class})
    @FutureOrPresent(message = "{validation.event.startDate.futureOrPresent}", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "{validation.event.endDate.notNull}", groups = {ValidationGroups.Create.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "{validation.event.venueId.notNull}", groups = {ValidationGroups.Create.class})
    private Long venueId;
}