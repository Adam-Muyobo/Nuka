package com.nuka.nuka_pos.api.organization.exceptions;

/**
 * Exception thrown when an Organization is not found.
 */
public class OrganizationNotFoundException extends RuntimeException {

    public OrganizationNotFoundException(String message) {
        super(message);
    }
}
