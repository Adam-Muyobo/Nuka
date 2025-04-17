package com.nuka.nuka_pos.api.sale.exceptions;

public class SaleAlreadyExistsException extends RuntimeException {
    public SaleAlreadyExistsException(String message) {
        super(message);
    }
}
