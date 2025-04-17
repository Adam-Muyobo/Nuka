package com.nuka.nuka_pos.api.service;

import com.nuka.nuka_pos.api.category.Category;
import com.nuka.nuka_pos.api.organization.Organization;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Service entity to represent services provided by the organization.
 */
@Entity
@Getter
@Setter
@Table(name = "services")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Using Long as the primary key for consistency

    @Column(nullable = false)
    private String name;

    private String description;  // Optional description of the service

    @Column(nullable = false)
    private BigDecimal price;  // Service price

    @Column(nullable = false)
    private Boolean isActive = true;  // Whether the service is active or discontinued

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;  // Organization to which the service belongs

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;  // Category of the service
}
