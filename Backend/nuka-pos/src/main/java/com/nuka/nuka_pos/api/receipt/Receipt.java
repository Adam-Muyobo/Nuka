package com.nuka.nuka_pos.api.receipt;

import com.nuka.nuka_pos.api.receipt.enums.ReceiptType;
import com.nuka.nuka_pos.api.sale.Sale;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "receipts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReceiptType type; // EMAIL or PRINT

    @Column(nullable = false)
    private LocalDateTime receiptDate;

    @Column(unique = true)
    private String receiptNumber; // Optional, but useful for printed receipts

    private String recipientEmail; // Optional, relevant for email receipts

    private Boolean delivered; // Optional, to track if sent/printed successfully

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.receiptDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
