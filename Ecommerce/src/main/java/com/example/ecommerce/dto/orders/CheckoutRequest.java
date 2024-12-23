package com.example.ecommerce.dto.orders;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CheckoutRequest {
    private String address;
}
