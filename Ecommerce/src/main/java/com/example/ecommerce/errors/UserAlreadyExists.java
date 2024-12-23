package com.example.ecommerce.errors;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists() {
        super("User with this email or username already exists");
    }

    public UserAlreadyExists(String message) {
        super(message);
    }
}
