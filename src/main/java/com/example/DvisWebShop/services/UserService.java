package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateUserRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {

    @NotNull
    List<UserResponse> getAllUsers();

    @NotNull
    UserResponse getUserById(@NotNull Integer id);

    @NotNull
    List<OrderResponse> getUserOrdersById(@NotNull Integer id);

    @NotNull
    UserResponse createUser(@NotNull CreateUserRequest createUserRequest);

    @NotNull
    UserResponse updateUser(@NotNull Integer id, @NotNull CreateUserRequest createUserRequest);

    @NotNull
    boolean deleteUser(@NotNull Integer id);
}
