package com.example.ecommerce.errors;

public class NegativeIntegerException extends Exception {
    public NegativeIntegerException() {
        super("Negative integer is not allowed");
    }

    public NegativeIntegerException(String message) {
        super(message);
    }
}
