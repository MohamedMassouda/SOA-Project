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
public class AddToCartRequest {
    @JsonProperty("product_id")
    private Long productId;
    private int quantity;
}
