package com.example.DvisWebShop.utils;

import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.models.Order;
import com.example.DvisWebShop.models.Product;
import com.example.DvisWebShop.models.User;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.stream.Collectors;

public class ResponseBuilder {

    @NotNull
    public static UserResponse buildUserResponse(@NotNull User user) {
        return new UserResponse()
                    .setUserId(user.getUserId())
                    .setLogin(user.getLogin())
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setAge(user.getAge())
                    .setOrdersId(user.getOrders() != null ? user.getOrders().stream()
                            .map(Order::getOrderId)
                            .collect(Collectors.toList()) : Collections.emptyList());
    }

    @NotNull
    public static OrderResponse buildOrderResponse(@NotNull Order order) {
        return new OrderResponse()
                .setOrderId(order.getOrderId())
                .setPrice(order.getPrice())
                .setDate(order.getDate())
                .setUserId(order.getUser().getUserId())
                .setProductsId(order.getProducts().stream()
                        .map(Product::getProductId)
                        .collect(Collectors.toList()));
    }

    @NotNull
    public static ProductResponse buildProductResponse(@NotNull Product product) {
        return new ProductResponse()
                    .setProductId(product.getProductId())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .setCompany(product.getCompany())
                    .setOrdersId(product.getOrders() != null ? product.getOrders().stream()
                            .map(Order::getOrderId)
                            .collect(Collectors.toList()) : Collections.emptyList());
    }
}
