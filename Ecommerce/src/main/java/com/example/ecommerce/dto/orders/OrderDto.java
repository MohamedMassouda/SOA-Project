package com.example.ecommerce.dto.orders;

import com.example.ecommerce.models.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class OrderDto {
    private Long id;
    @JsonProperty("client_id")
    private Long clientId;
    @JsonProperty("order_date")
    private LocalDateTime orderDate;
    @JsonProperty("address")
    private String shippingAddress;
    @JsonProperty("total_amount")
    private double totalAmount;
    private OrderStatus status;
    private List<OrderItemDto> items;
}
