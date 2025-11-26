package com.carturo.eventhub.infrastructure.adapters.out.jpa.entity;

import com.carturo.eventhub.domain.model.event.EventCategory;
import com.carturo.eventhub.domain.model.event.EventStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "events",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_event_name", columnNames = "name")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory category;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private VenueEntity venue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;
}