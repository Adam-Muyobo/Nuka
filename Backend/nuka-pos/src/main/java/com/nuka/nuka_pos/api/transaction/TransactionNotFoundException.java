package com.nuka.nuka_pos.api.transaction;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id) {
        super("Transaction with ID " + id + " not found.");
    }
}
