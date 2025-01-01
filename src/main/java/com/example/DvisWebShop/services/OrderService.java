package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateOrderRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface OrderService {

    @NotNull
    List<OrderResponse> getAllOrders();

    @NotNull
    OrderResponse getOrderById(@NotNull Integer id);

    @NotNull
    List<OrderResponse> getOrdersByUserId(@NotNull Integer userId);

    @NotNull
    OrderResponse createOrder(@NotNull CreateOrderRequest createOrderRequest);

    @NotNull
    OrderResponse updateOrder(@NotNull Integer id, @NotNull CreateOrderRequest createOrderRequest);

    @NotNull
    boolean deleteOrder(@NotNull Integer id);
}
