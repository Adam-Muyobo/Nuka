package com.nuka.nuka_pos.api.sale_item.exceptions;

public class SaleItemNotFoundException extends RuntimeException {
    public SaleItemNotFoundException(String message) {
        super(message);
    }
}
