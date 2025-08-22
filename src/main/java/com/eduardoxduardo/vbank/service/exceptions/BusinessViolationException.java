package com.eduardoxduardo.vbank.service.exceptions;

public class BusinessViolationException extends RuntimeException {
    public BusinessViolationException(String message) {
        super(message);
    }
}
