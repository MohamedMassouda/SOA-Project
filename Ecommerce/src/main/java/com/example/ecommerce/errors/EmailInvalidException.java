package com.example.ecommerce.errors;

public class EmailInvalidException extends Exception {
    public EmailInvalidException() {
        super("Email is invalid");
    }

    public EmailInvalidException(String message) {
        super(message);
    }
}
