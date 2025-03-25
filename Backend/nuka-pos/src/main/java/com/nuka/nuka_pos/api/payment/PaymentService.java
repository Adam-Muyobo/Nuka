package com.nuka.nuka_pos.api.payment;

import com.nuka.nuka_pos.api.payment.enums.PaymentMethod;
import com.nuka.nuka_pos.api.payment.enums.PaymentStatus;
import com.nuka.nuka_pos.api.transaction.Transaction;
import com.nuka.nuka_pos.api.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionService transactionService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, TransactionService transactionService) {
        this.paymentRepository = paymentRepository;
        this.transactionService = transactionService;
    }

    // Add Payment
    @Transactional
    public Payment createPayment(Long transactionId, PaymentMethod paymentMethod) {
        Transaction transaction = transactionService.findById(transactionId);

        Payment payment = Payment.builder()
                .transaction(transaction)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PENDING) // Default status
                .build();

        return paymentRepository.save(payment);
    }

    // Update Payment Status
    @Transactional
    public Payment updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    // Delete Payment
    @Transactional
    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        paymentRepository.delete(payment);
    }

    // Get Payment by ID
    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    // Get All Payments
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    // Get Payments by Transaction ID
    public Payment findByTransactionId(Long transactionId) {
        return paymentRepository.findByTransaction_Id(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found for transaction"));
    }
}


