package com.nuka.nuka_pos.api.branch.exceptions;

public class BranchNotFoundException extends RuntimeException {
    public BranchNotFoundException(String message) {
        super(message);
    }
}
