package com.example.ecommerce.controllers;

import com.example.ecommerce.dto.cart.AddToCartRequest;
import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.dto.cart.UpdateCartItemQuantity;
import com.example.ecommerce.models.User;
import com.example.ecommerce.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getCart(user.getClient().getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<CartDto> addToCart(
            @AuthenticationPrincipal User user,
            @RequestBody AddToCartRequest request
    ) {
        return ResponseEntity.ok(cartService.addToCart(user.getClient().getId(), request));
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartDto> updateCartItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId,
            @RequestBody UpdateCartItemQuantity itemQuantity
            ) {
        return ResponseEntity.ok(cartService.updateCartItem(user.getClient().getId(), productId, itemQuantity));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartDto> removeFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(cartService.removeFromCart(user.getClient().getId(), productId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartDto> clearCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.clearCart(user.getClient().getId()));
    }
}
