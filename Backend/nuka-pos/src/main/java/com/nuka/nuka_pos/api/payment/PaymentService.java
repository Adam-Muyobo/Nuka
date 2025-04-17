package com.nuka.nuka_pos.api.payment;

import com.nuka.nuka_pos.api.payment.enums.PaymentMethod;
import com.nuka.nuka_pos.api.payment.enums.PaymentStatus;
import com.nuka.nuka_pos.api.payment.exceptions.PaymentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        return mapToResponse(payment);
    }

    public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByPaymentMethod(PaymentMethod method) {
        return paymentRepository.findByPaymentMethod(method)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsBySaleId(Long saleId) {
        return paymentRepository.findBySaleId(saleId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void createPayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public void updatePayment(Long id, Payment updatedData) {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

        if (updatedData.getAmount() != null) existing.setAmount(updatedData.getAmount());
        if (updatedData.getPaymentDate() != null) existing.setPaymentDate(updatedData.getPaymentDate());
        if (updatedData.getPaymentMethod() != null) existing.setPaymentMethod(updatedData.getPaymentMethod());
        if (updatedData.getStatus() != null) existing.setStatus(updatedData.getStatus());
        if (updatedData.getTransactionReference() != null) existing.setTransactionReference(updatedData.getTransactionReference());
        if (updatedData.getSale() != null) existing.setSale(updatedData.getSale());

        paymentRepository.save(existing);
    }

    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getSale().getId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod().name(),
                payment.getStatus().name(),
                payment.getTransactionReference(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
