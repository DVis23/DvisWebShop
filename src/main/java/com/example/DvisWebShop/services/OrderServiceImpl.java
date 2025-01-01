package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateOrderRequest;
import com.example.DvisWebShop.DTO.requests.CreateUserRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.models.Order;
import com.example.DvisWebShop.models.User;
import com.example.DvisWebShop.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

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
    public OrderResponse getOrderById(Integer id) {
        return orderRepository.findById(id).map(this::buildOrderResponse).orElseThrow(
                ()-> new NoSuchElementException("ORDER with id = '" + id + "' does not exist"));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUserId(Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new NoSuchElementException("USER with id = '" + userId + "' does not exist");
        }
        return orders.stream()
                .map(this::buildOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        Order order = buildOrderRequest(createOrderRequest);
        return buildOrderResponse(orderRepository.save(order));
    }

    @Override
    @NotNull
    @Transactional
    public OrderResponse updateOrder(Integer id, CreateOrderRequest createOrderRequest) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Order with id = '" + id + "' does not exist"));
        ofNullable(createOrderRequest.getPrice()).map(order::setPrice);
        ofNullable(createOrderRequest.getDate()).map(order::setDate);

        CreateUserRequest createUserRequest = createOrderRequest.getUser();
        ofNullable(createUserRequest.getLogin()).map(order.getUser()::setLogin);
        ofNullable(createUserRequest.getFirstName()).map(order.getUser()::setFirstName);
        ofNullable(createUserRequest.getLastName()).map(order.getUser()::setLastName);
        Optional.of(createUserRequest.getAge()).map(order.getUser()::setAge);
        return buildOrderResponse(orderRepository.save(order));
    }

    @Override
    @NotNull
    @Transactional
    public boolean deleteOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private OrderResponse buildOrderResponse(Order order) {
        User user = order.getUser();
        return new OrderResponse()
                .setOrderId(order.getOrderId())
                .setPrice(order.getPrice())
                .setDate(order.getDate())
                .setUser(new UserResponse()
                        .setUserId(user.getUserId())
                        .setLogin(user.getLogin())
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setAge(user.getAge()));
    }

    private Order buildOrderRequest(CreateOrderRequest request) {
        CreateUserRequest createUserRequest = request.getUser();
        return new Order()
                .setOrderId(request.getOrderId())
                .setPrice(request.getPrice())
                .setDate(request.getDate())
                .setUser(new User()
                        .setUserId(createUserRequest.getUserId())
                        .setLogin(createUserRequest.getLogin())
                        .setFirstName(createUserRequest.getFirstName())
                        .setLastName(createUserRequest.getLastName())
                        .setAge(createUserRequest.getAge()));
    }
}
