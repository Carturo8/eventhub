package com.carturo.eventhub.infrastructure.adapters.out.jpa.entity;

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

    @Column(nullable = true, length = 500)
    private String description;

    @Column(nullable = true, length = 100)
    private String category;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private VenueEntity venue;
}