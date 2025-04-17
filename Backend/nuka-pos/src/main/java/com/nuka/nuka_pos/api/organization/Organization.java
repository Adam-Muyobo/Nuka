package com.nuka.nuka_pos.api.organization;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing an Organization in the system.
 */
@Entity
@Table(name = "organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessType businessType;

    @Column(nullable = false)
    private Boolean smartInvoicingEnabled;
    @Column(nullable = false)
    private Boolean barcodeEnabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReceiptMode receiptMode;

    private String taxNumber;
    private String logoPath;
    private Boolean isVerified;

    private String verificationCode;

    private LocalDateTime createdAt;

    // Enum for BusinessType
    public enum BusinessType {
        RETAIL, SERVICE, WHOLESALE, MIXED
    }

    // Enum for ReceiptMode
    public enum ReceiptMode {
        EMAIL, PRINT, BOTH
    }
}
