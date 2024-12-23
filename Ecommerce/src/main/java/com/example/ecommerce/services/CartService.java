package com.example.ecommerce.services;

import com.example.ecommerce.dto.cart.AddToCartRequest;
import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.dto.cart.CartItemDto;
import com.example.ecommerce.dto.cart.UpdateCartItemQuantity;
import com.example.ecommerce.errors.ClientNotFoundException;
import com.example.ecommerce.errors.InsufficientStockException;
import com.example.ecommerce.errors.ProductNotFoundException;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repositories.CartRepository;
import com.example.ecommerce.repositories.ClientRepository;
import com.example.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public Cart findCartByClientId(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client not found")).getCart();
    }

    private CartDto mapCartToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setClientId(cart.getClient().getId());
        cartDto.setItems(cart.getItems().stream().map(this::mapCartItemToCartItemDto).toList());
        cartDto.setTotalPrice(calculateTotal(cart));
        return cartDto;
    }

    private CartItemDto mapCartItemToCartItemDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setProductId(cartItem.getProduct().getId());
        cartItemDto.setProductName(cartItem.getProduct().getName());
        cartItemDto.setProductPhoto(cartItem.getProduct().getPhoto());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setPrice(cartItem.getProduct().getPrice());
        cartItemDto.setSubtotal(calculateSubtotal(cartItem));
        return cartItemDto;
    }

    private Double calculateSubtotal(CartItem cartItem) {
        return cartItem.getProduct().getPrice() * cartItem.getQuantity();
    }

    @Transactional(readOnly = true)
    public CartDto getCart(Long clientId) {
        Cart cart = findCartByClientId(clientId);

        return mapCartToCartDto(cart);
    }

    @Transactional
    public CartDto addToCart(Long clientId, AddToCartRequest request) {
        Cart cart = findCartByClientId(clientId);
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Check the stock
        if (product.getQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock");
        }

        CartItem existingCartItem = findCartItemByProductId(cart, request.getProductId());

        if (existingCartItem != null) {
            if (existingCartItem.getQuantity() + request.getQuantity() > product.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock");
            }

            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }

        cartRepository.save(cart);
        return mapCartToCartDto(cart);
    }

    private Double calculateTotal(Cart cart) {
        return cart.getItems().stream().mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity()).sum();
    }

    private void removeItemFromCart(Cart cart, Long productId) {
        CartItem cartItem = findCartItemByProductId(cart, productId);
        cart.getItems().remove(cartItem);
    }

    @Transactional
    public CartDto removeFromCart(Long clientId, Long productId) {
        Cart cart = findCartByClientId(clientId);
        productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        removeItemFromCart(cart, productId);
        cart = cartRepository.save(cart);
        return mapCartToCartDto(cart);
    }

    @Transactional
    public CartDto clearCart(Long clientId) {
        Cart cart = findCartByClientId(clientId);

        cart.getItems().clear();
        cart = cartRepository.save(cart);
        return mapCartToCartDto(cart);
    }

    @Transactional
    public CartDto updateCartItem(Long clientId, Long productId, UpdateCartItemQuantity itemQuantity) {
        Cart cart = findCartByClientId(clientId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        int quantity = itemQuantity.quantity();

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than or equal to 0");
        }

        CartItem cartItem = findCartItemByProductId(cart, productId);

        if (cartItem == null) {
            throw new IllegalArgumentException("Item not found in cart");
        }

        if (quantity == 0) {
            removeItemFromCart(cart, productId);
        } else {
            if (quantity > product.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock");
            }

            cartItem.setQuantity(quantity);
        }

        cartRepository.save(cart);

        return mapCartToCartDto(cart);
    }

    private CartItem findCartItemByProductId(Cart cart, Long productId) {
        return cart.getItems().stream().filter(cartItem -> cartItem.getProduct().getId().equals(productId)).findFirst().orElse(null);
    }
}
