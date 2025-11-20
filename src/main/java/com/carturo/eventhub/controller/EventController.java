package com.carturo.eventhub.controller;

import com.carturo.eventhub.docs.EventControllerDoc;
import com.carturo.eventhub.dto.request.EventRequest;
import com.carturo.eventhub.dto.response.EventResponse;
import com.carturo.eventhub.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController implements EventControllerDoc {

    private final EventService eventService;

    @Override
    @PostMapping
    public EventResponse createEvent(@RequestBody EventRequest request) {
        return eventService.create(request);
    }

    @Override
    @GetMapping
    public Page<EventResponse> getEvents(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        return eventService.search(city, category, startDate, pageable);
    }

    @Override
    @GetMapping("/{id}")
    public EventResponse getEventById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @Override
    @PutMapping("/{id}")
    public EventResponse updateEvent(@PathVariable Long id, @RequestBody EventRequest request) {
        return eventService.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.delete(id);
    }
}