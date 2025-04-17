package com.nuka.nuka_pos.api.taxtype.exceptions;

/**
 * Custom exception thrown when a TaxType is not found.
 */
public class TaxTypeNotFoundException extends RuntimeException {
    public TaxTypeNotFoundException(String message) {
        super(message);
    }
}
