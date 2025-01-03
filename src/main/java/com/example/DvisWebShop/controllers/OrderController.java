package com.example.DvisWebShop.controllers;

import com.example.DvisWebShop.DTO.requests.CreateOrderRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer id) {
        OrderResponse order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<UserResponse> getOrderUserById(@PathVariable Integer id) {
        UserResponse user = orderService.getOrderUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> getOrderProductsById(@PathVariable Integer id) {
        List<ProductResponse> products = orderService.getOrderProductsById(id);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        OrderResponse order = orderService.createOrder(createOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable Integer id,
            @RequestBody CreateOrderRequest createOrderRequest) {
        OrderResponse order = orderService.updateOrder(id, createOrderRequest);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        boolean isDeleted = orderService.deleteOrder(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
