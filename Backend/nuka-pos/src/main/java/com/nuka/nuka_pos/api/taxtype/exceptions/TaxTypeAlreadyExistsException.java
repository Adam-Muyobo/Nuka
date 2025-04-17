package com.nuka.nuka_pos.api.taxtype.exceptions;

/**
 * Custom exception thrown when a TaxType already exists.
 */
public class TaxTypeAlreadyExistsException extends RuntimeException {
    public TaxTypeAlreadyExistsException(String message) {
        super(message);
    }
}
