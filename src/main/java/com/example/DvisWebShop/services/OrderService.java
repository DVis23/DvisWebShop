package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateOrderRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface OrderService {

    @NotNull
    List<OrderResponse> getAllOrders();

    @NotNull
    OrderResponse getOrderById(@NotNull Integer id);

    @NotNull
    UserResponse getOrderUserById(@NotNull Integer id);

    @NotNull
    List<ProductResponse> getOrderProductsById(@NotNull Integer id);

    @NotNull
    OrderResponse createOrder(@NotNull CreateOrderRequest createOrderRequest);

    @NotNull
    OrderResponse updateOrder(@NotNull Integer id, @NotNull CreateOrderRequest createOrderRequest);

    @NotNull
    boolean deleteOrder(@NotNull Integer id);
}
