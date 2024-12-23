package com.example.ecommerce.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class CartItemDto {
    private Long id;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_photo")
    private String productPhoto;
    private double price;
    private int quantity;
    private double subtotal;
}
