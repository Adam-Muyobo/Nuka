package com.nuka.nuka_pos.api.taxtype;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TaxType represents the types of tax registrations available in the system.
 * Examples include VAT and NONE.
 */
@Entity
@Table(name = "tax_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // e.g., "VAT", "NONE"
}
