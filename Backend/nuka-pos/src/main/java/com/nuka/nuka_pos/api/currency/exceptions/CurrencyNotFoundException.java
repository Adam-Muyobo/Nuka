package com.nuka.nuka_pos.api.currency.exceptions;

/**
 * CurrencyNotFoundException is thrown when a requested currency is not found in the system.
 */
public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
