package com.example.DvisWebShop.utils;

import com.example.DvisWebShop.DTO.requests.CreateOrderRequest;
import com.example.DvisWebShop.DTO.requests.CreateProductRequest;
import com.example.DvisWebShop.DTO.requests.CreateUserRequest;
import com.example.DvisWebShop.models.Order;
import com.example.DvisWebShop.models.Product;
import com.example.DvisWebShop.models.User;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityBuilder {

    @NotNull
    public static User buildUserRequest(@NotNull CreateUserRequest request,
                                        @NotNull Function<Integer, Order> findEntityByIdOrder) {
        return new User()
                .setUserId(request.getUserId())
                .setLogin(request.getLogin())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setAge(request.getAge())
                .setOrders(request.getOrdersId() != null ? request.getOrdersId().stream()
                        .map(findEntityByIdOrder)
                        .collect(Collectors.toList()) : Collections.emptyList());
    }

    @NotNull
    public static Product buildProductRequest(@NotNull CreateProductRequest request) {
        return new Product()
                .setProductId(request.getProductId())
                .setName(request.getName())
                .setPrice(request.getPrice())
                .setCompany(request.getCompany());
    }

    @NotNull
    public static Order buildOrderRequest(@NotNull CreateOrderRequest request,
                                    @NotNull Function<Integer, User> findEntityByIdUser,
                                    @NotNull Function<Integer, Product> findEntityByIdProduct) {
        return new Order()
                .setOrderId(request.getOrderId())
                .setPrice(request.getPrice())
                .setDate(request.getDate())
                .setUser(findEntityByIdUser.apply(request.getUserId()))
                .setProducts(request.getProductsId().stream()
                        .map(findEntityByIdProduct)
                        .collect(Collectors.toList()));
    }
}
