package com.example.ecommerce.services;

import com.example.ecommerce.dto.orders.CheckoutRequest;
import com.example.ecommerce.dto.orders.OrderDto;
import com.example.ecommerce.dto.orders.OrderItemDto;
import com.example.ecommerce.errors.EmptyCartException;
import com.example.ecommerce.errors.InsufficientStockException;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.OrderItem;
import com.example.ecommerce.repositories.ClientRepository;
import com.example.ecommerce.repositories.OrderRepository;
import com.example.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final ClientRepository clientRepository;

    public List<OrderDto> findOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId).stream().map(this::mapOrderToOrderDto).toList();
    }

    public OrderDto findById(Long clientId, Long orderId) {
        Optional<Order> order = orderRepository.findByClientIdAndOrderId(clientId, orderId);

        return order.map(this::mapOrderToOrderDto).orElse(null);
    }

    public OrderDto checkout(Long clientId, CheckoutRequest request) {
        Cart cart = cartService.findCartByClientId(clientId);

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cart is empty");
        }

        // Check the stock
        for (var item : cart.getItems()) {
            if (!productService.checkStock(item.getProduct().getId(), item.getQuantity())) {
                throw new InsufficientStockException("Insufficient stock for product with id: " + item.getProduct().getId());
            }
        }

        Order order = Order.createFromCart(cart, request.getAddress());
        order = orderRepository.save(order);

        cartService.clearCart(clientId);

        return mapOrderToOrderDto(order);
    }

    private OrderItemDto mapOrderItemToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        orderItemDto.setProductName(orderItem.getProduct().getName());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPriceAtTime(orderItem.getPriceAtTime());
        orderItemDto.setProductPhoto(orderItem.getProduct().getPhoto());
        orderItemDto.setSubtotal(orderItem.getProduct().getPrice() * orderItem.getQuantity());
        return orderItemDto;
    }

    private OrderDto mapOrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setItems(order.getItems().stream().map(this::mapOrderItemToOrderItemDto).toList());
        orderDto.setStatus(order.getStatus());
        orderDto.setShippingAddress(order.getShippingAddress());
        orderDto.setTotalAmount(order.getTotalAmount());
        return orderDto;
    }
}
