package com.nuka.nuka_pos.api.payment;

import com.nuka.nuka_pos.api.payment.enums.PaymentMethod;
import com.nuka.nuka_pos.api.payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find payments by status
    List<Payment> findByStatus(PaymentStatus status);

    // Find payments by payment method
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    // Find payments by saleId
    List<Payment> findBySaleId(Long saleId);
}