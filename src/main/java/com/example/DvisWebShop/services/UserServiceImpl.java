package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateUserRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.models.Order;
import com.example.DvisWebShop.models.Product;
import com.example.DvisWebShop.models.User;
import com.example.DvisWebShop.repositories.OrderRepository;
import com.example.DvisWebShop.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::buildUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public UserResponse getUserById(@NotNull Integer id) {
        return userRepository.findById(id).map(this::buildUserResponse).orElseThrow(
                () -> new EntityNotFoundException("USER with id = '" + id + "' does not exist"));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrdersById(@NotNull Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("USER with id = '" + id + "' does not exist"));
        return user.getOrders().stream()
                .map(order -> new OrderResponse()
                        .setOrderId(order.getOrderId())
                        .setPrice(order.getPrice())
                        .setDate(order.getDate())
                        .setUserId(order.getUser ().getUserId())
                        .setProductsId(order.getProducts().stream()
                                .map(Product::getProductId)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional
    public UserResponse createUser(@NotNull CreateUserRequest createUserRequest) {
        User user = buildUserRequest(createUserRequest);
        return buildUserResponse(userRepository.save(user));
    }

    @Override
    @NotNull
    @Transactional
    public UserResponse updateUser(@NotNull Integer id, @NotNull CreateUserRequest createUserRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("USER with id = '" + id + "' does not exist"));
        ofNullable(createUserRequest.getLogin()).map(user::setLogin);
        ofNullable(createUserRequest.getFirstName()).map(user::setFirstName);
        ofNullable(createUserRequest.getLastName()).map(user::setLastName);
        ofNullable(createUserRequest.getAge()).map(user::setAge);
        return buildUserResponse(userRepository.save(user));
    }

    @Override
    @NotNull
    @Transactional
    public boolean deleteUser(@NotNull Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("USER with id = '" + id + "' does not exist"));
    }

    private UserResponse buildUserResponse(@NotNull User user) {
        return new UserResponse()
                .setUserId(user.getUserId())
                .setLogin(user.getLogin())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge())
                .setOrdersId(user.getOrders().stream()
                        .map(Order::getOrderId).collect(Collectors.toList()));
    }

    private User buildUserRequest(@NotNull CreateUserRequest request) {
        return new User()
                .setUserId(request.getUserId())
                .setLogin(request.getLogin())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setAge(request.getAge())
                .setOrders(request.getOrdersId().stream()
                        .map(orderId -> orderRepository.findById(orderId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "ORDER with id = '" + orderId + "' does not exist")))
                        .collect(Collectors.toList()));
    }
}