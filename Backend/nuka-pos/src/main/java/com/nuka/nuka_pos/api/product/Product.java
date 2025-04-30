package com.nuka.nuka_pos.api.product;

import com.nuka.nuka_pos.api.category.Category;
import com.nuka.nuka_pos.api.organization.Organization;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(length = 100)
    private String barcode;

    @Column(nullable = false)
    private Boolean isActive = true;
}
