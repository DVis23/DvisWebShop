package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateOrderRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.models.Order;
import com.example.DvisWebShop.models.Product;
import com.example.DvisWebShop.models.User;
import com.example.DvisWebShop.repositories.OrderRepository;
import com.example.DvisWebShop.repositories.ProductRepository;
import com.example.DvisWebShop.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::buildOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(@NotNull Integer id) {
        return orderRepository.findById(id).map(this::buildOrderResponse).orElseThrow(
                ()-> new EntityNotFoundException("ORDER with id = '" + id + "' does not exist"));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public UserResponse getOrderUserById(@NotNull Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("ORDER with id = '" + id + "' does not exist"));
        User user = order.getUser();
        return new UserResponse()
                .setUserId(user.getUserId())
                .setLogin(user.getLogin())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge())
                .setOrdersId(user.getOrders().stream()
                        .map(Order::getOrderId).collect(Collectors.toList()));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<ProductResponse> getOrderProductsById(@NotNull Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ORDER with id = '" + id + "' does not exist"));
        return order.getProducts().stream()
                .map(product -> new ProductResponse()
                        .setProductId(product.getProductId())
                        .setName(product.getName())
                        .setPrice(product.getPrice())
                        .setCompany(product.getCompany())
                        .setOrdersId(product.getOrders().stream()
                                .map(Order::getOrderId)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUserId(@NotNull Integer userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("USER with id = '" + userId + "' does not exist")
        );
        return orderRepository.findByUserId(userId).stream()
                .map(this::buildOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional
    public OrderResponse createOrder(@NotNull CreateOrderRequest createOrderRequest) {
        Order order = buildOrderRequest(createOrderRequest);
        return buildOrderResponse(orderRepository.save(order));
    }

    @Override
    @NotNull
    @Transactional
    public OrderResponse updateOrder(@NotNull Integer id, @NotNull CreateOrderRequest createOrderRequest) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ORDER with id = '" + id + "' does not exist"));
        ofNullable(createOrderRequest.getPrice()).map(order::setPrice);
        ofNullable(createOrderRequest.getDate()).map(order::setDate);
        return buildOrderResponse(orderRepository.save(order));
    }

    @Override
    @NotNull
    @Transactional
    public boolean deleteOrder(@NotNull Integer id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("ORDER with id = '" + id + "' does not exist"));
    }

    private OrderResponse buildOrderResponse(@NotNull Order order) {
        return new OrderResponse()
                .setOrderId(order.getOrderId())
                .setPrice(order.getPrice())
                .setDate(order.getDate())
                .setUserId(order.getUser().getUserId())
                .setProductsId(order.getProducts().stream()
                        .map(Product::getProductId).collect(Collectors.toList()));
    }

    private Order buildOrderRequest(@NotNull CreateOrderRequest request) {
        return new Order()
                .setOrderId(request.getOrderId())
                .setPrice(request.getPrice())
                .setDate(request.getDate())
                .setUser(userRepository.findById(request.getUserId()).orElseThrow(
                        () -> new EntityNotFoundException(
                                "USER with id = '" + request.getUserId() + "' does not exist")))
                .setProducts(request.getProductsId().stream()
                        .map(productId -> productRepository.findById(productId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "PRODUCT with id = '" + productId + "' does not exist")))
                        .collect(Collectors.toList()));
    }
}
