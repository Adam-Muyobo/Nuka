package com.nuka.nuka_pos.api.payment;

import com.nuka.nuka_pos.api.payment.enums.PaymentMethod;
import com.nuka.nuka_pos.api.payment.enums.PaymentStatus;
import com.nuka.nuka_pos.api.transaction.Transaction;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false, unique = true)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
}
