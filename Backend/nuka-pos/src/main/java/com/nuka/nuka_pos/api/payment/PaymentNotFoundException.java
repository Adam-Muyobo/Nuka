package com.nuka.nuka_pos.api.payment;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(Long id) {
        super("Payment with ID " + id + " not found.");
    }
}
