package com.nuka.nuka_pos.api.receipt.exceptions;

public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(String message) {
        super(message);
    }
}
