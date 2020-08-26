package com.tms.api.users.util.exception;

public class PasswordDoesNotMatchException extends RuntimeException {
    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}
