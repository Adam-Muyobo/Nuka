package com.nuka.nuka_pos.api.sale.exceptions;

public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException(String message) {
        super(message);
    }
}
