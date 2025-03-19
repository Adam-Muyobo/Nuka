package com.nuka.nuka_pos.api.transaction;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nuka.nuka_pos.api.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore  // Prevents serialization issues
    private User user;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private BigDecimal taxAmount;

    @Column(nullable = false)
    private LocalDate transactionDate;
}


