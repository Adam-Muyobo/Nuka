package com.nuka.nuka_pos.api.tax_record;

public class TaxRecordNotFoundException extends RuntimeException {
    public TaxRecordNotFoundException(Long id) {
        super("Tax Record with ID " + id + " not found.");
    }
}
