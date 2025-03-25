package com.nuka.nuka_pos.api.transaction_item;

public class TransactionItemNotFoundException extends RuntimeException {
    public TransactionItemNotFoundException(Long id) {
        super("Transaction Item with ID " + id + " not found.");
    }
}

