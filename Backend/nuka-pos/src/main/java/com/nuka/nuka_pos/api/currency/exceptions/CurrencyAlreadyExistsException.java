package com.nuka.nuka_pos.api.currency.exceptions;

/**
 * CurrencyAlreadyExistsException is thrown when attempting to create a currency that already exists.
 */
public class CurrencyAlreadyExistsException extends RuntimeException {

    public CurrencyAlreadyExistsException(String message) {
        super(message);
    }
}
