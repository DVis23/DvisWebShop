package com.example.DvisWebShop.controllers;

import com.example.DvisWebShop.DTO.requests.CreateUserRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all users")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == principal.userId)")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get orders by user ID")
    @GetMapping("/{id}/orders")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == principal.userId)")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Integer id) {
        List<OrderResponse> orders = userService.getUserOrdersById(id);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        UserResponse userResponse = userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @Operation(summary = "Update an existing user")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == principal.userId)")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Integer id,
            @RequestBody CreateUserRequest createUserRequest) {
        UserResponse updatedUser = userService.updateUser(id, createUserRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == principal.userId)")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
