package com.example.ecommerce.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class CartDto {
    private Long id;
    @JsonProperty("client_id")
    private Long clientId;
    private List<CartItemDto> items;
    @JsonProperty("total_price")
    private double totalPrice;
}
