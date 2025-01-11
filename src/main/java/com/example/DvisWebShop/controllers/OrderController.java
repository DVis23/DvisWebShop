package com.example.DvisWebShop.controllers;

import com.example.DvisWebShop.DTO.requests.CreateOrderRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get all orders")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderService.isOwner(#id, authentication.principal.userId))")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer id) {
        OrderResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Get user by order ID")
    @GetMapping("/{id}/users")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderService.isOwner(#id, authentication.principal.userId))")
    public ResponseEntity<UserResponse> getOrderUserById(@PathVariable Integer id) {
        UserResponse user = orderService.getOrderUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get products by order ID")
    @GetMapping("/{id}/products")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderService.isOwner(#id, authentication.principal.userId))")
    public ResponseEntity<List<ProductResponse>> getOrderProductsById(@PathVariable Integer id) {
        List<ProductResponse> products = orderService.getOrderProductsById(id);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Create a new order")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #createOrderRequest.userId == authentication.principal.userId)")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        OrderResponse order = orderService.createOrder(createOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @Operation(summary = "Update an existing order")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable Integer id,
            @RequestBody CreateOrderRequest createOrderRequest) {
        OrderResponse order = orderService.updateOrder(id, createOrderRequest);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Delete a order")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        boolean isDeleted = orderService.deleteOrder(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
