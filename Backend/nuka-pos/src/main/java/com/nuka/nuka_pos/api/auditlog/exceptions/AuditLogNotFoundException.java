package com.nuka.nuka_pos.api.auditlog.exceptions;

public class AuditLogNotFoundException extends RuntimeException {
    public AuditLogNotFoundException(String message) {
        super(message);
    }
}
