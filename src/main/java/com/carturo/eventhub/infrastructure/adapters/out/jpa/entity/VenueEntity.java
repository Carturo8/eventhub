package com.carturo.eventhub.infrastructure.adapters.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "venues",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_venue_name", columnNames = "name")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String city;

    @Column(nullable = false)
    private Integer capacity;
}