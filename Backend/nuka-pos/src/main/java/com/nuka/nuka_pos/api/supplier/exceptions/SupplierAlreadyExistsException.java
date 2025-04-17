package com.nuka.nuka_pos.api.supplier.exceptions;

public class SupplierAlreadyExistsException extends RuntimeException {
    public SupplierAlreadyExistsException(String message) {
        super(message);
    }
}
