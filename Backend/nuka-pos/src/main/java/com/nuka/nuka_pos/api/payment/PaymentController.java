package com.nuka.nuka_pos.api.payment;

import com.nuka.nuka_pos.api.payment.enums.PaymentMethod;
import com.nuka.nuka_pos.api.payment.enums.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Create Payment
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestParam Long transactionId,
            @RequestParam PaymentMethod paymentMethod
    ) {
        Payment payment = paymentService.createPayment(transactionId, paymentMethod);
        return new ResponseEntity<>(toResponse(payment), HttpStatus.CREATED);
    }

    // Update Payment Status
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status
    ) {
        Payment payment = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(toResponse(payment));
    }

    // Delete Payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    // Get Payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        return ResponseEntity.ok(toResponse(payment));
    }

    // Get All Payments
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> responses = paymentService.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // Get Payment by Transaction ID
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentResponse> getPaymentByTransactionId(@PathVariable Long transactionId) {
        Payment payment = paymentService.findByTransactionId(transactionId);
        return ResponseEntity.ok(toResponse(payment));
    }

    // Convert to DTO
    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .transactionId(payment.getTransaction().getId())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .build();
    }
}

