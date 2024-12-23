package com.example.ecommerce.controllers;

import com.example.ecommerce.dto.ErrorResponse;
import com.example.ecommerce.errors.CartNotFoundException;
import com.example.ecommerce.errors.ClientNotFoundException;
import com.example.ecommerce.errors.InsufficientStockException;
import com.example.ecommerce.errors.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(
            {CartNotFoundException.class, ProductNotFoundException.class, ClientNotFoundException.class}
    )
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(
            InsufficientStockException ex
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
