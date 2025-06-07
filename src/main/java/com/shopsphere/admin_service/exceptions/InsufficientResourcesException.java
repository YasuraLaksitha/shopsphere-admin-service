package com.shopsphere.admin_service.exceptions;

public class InsufficientResourcesException extends RuntimeException {
    public InsufficientResourcesException(String message) {
        super(message);
    }
}
