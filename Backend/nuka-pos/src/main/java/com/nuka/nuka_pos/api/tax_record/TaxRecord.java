package com.nuka.nuka_pos.api.tax_record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nuka.nuka_pos.api.transaction.Transaction;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tax_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonIgnore
    private Transaction transaction;

    @Column(nullable = false)
    private BigDecimal vatAmount;

    @Column(nullable = false)
    private Boolean submittedToZRA;
}
