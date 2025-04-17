package com.nuka.nuka_pos.api.smart_invoice.exceptions;

public class SmartInvoiceRecordNotFoundException extends RuntimeException {
    public SmartInvoiceRecordNotFoundException(String message) {
        super(message);
    }
}
