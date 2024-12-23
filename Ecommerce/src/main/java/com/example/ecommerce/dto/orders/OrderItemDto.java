package com.example.ecommerce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class OrderItemDto {
    private Long id;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_photo")
    private String productPhoto;
    private int quantity;
    @JsonProperty("price_at_time")
    private double priceAtTime;
    private double subtotal;
}
