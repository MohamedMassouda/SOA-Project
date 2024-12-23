package com.example.ecommerce.errors;

public class EmptyCartException extends IllegalStateException {
    public EmptyCartException(String message) {
        super(message);
    }
}
