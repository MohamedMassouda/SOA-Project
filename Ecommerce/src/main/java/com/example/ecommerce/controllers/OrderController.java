package com.example.ecommerce.controllers;

import com.example.ecommerce.dto.orders.CheckoutRequest;
import com.example.ecommerce.dto.orders.OrderDto;
import com.example.ecommerce.models.User;
import com.example.ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout(
            @AuthenticationPrincipal User user,
            @RequestBody CheckoutRequest request
    ) {
        return ResponseEntity.ok(orderService.checkout(user.getClient().getId(), request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        OrderDto order = orderService.findById(user.getClient().getId(), id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }


    @GetMapping
    public ResponseEntity<List<OrderDto>> getClientOrders(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                orderService.findOrdersByClientId(user.getClient().getId())
        );
    }
}
