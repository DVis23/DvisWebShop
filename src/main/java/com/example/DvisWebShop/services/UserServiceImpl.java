package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateUserRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.models.User;
import com.example.DvisWebShop.repositories.OrderRepository;
import com.example.DvisWebShop.repositories.UserRepository;

import com.example.DvisWebShop.utils.EntityBuilder;
import com.example.DvisWebShop.utils.ResponseBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServices implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return buildResponseList(userRepository.findAll(), ResponseBuilder::buildUserResponse);
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public UserResponse getUserById(@NotNull Integer id) {
        return ResponseBuilder.buildUserResponse(findEntityById(userRepository.findById(id), "USER", id));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrdersById(@NotNull Integer id) {
        User user = findEntityById(userRepository.findById(id), "USER", id);
        return buildResponseList(user.getOrders(), ResponseBuilder::buildOrderResponse);
    }

    @Override
    @NotNull
    @Transactional
    public UserResponse createUser(@NotNull CreateUserRequest createUserRequest) {
        User user = EntityBuilder.buildUserRequest(createUserRequest,
                id -> findEntityById(orderRepository.findById(id), "OLDER", id));
        return ResponseBuilder.buildUserResponse(userRepository.save(user));
    }

    @Override
    @NotNull
    @Transactional
    public UserResponse updateUser(@NotNull Integer id, @NotNull CreateUserRequest createUserRequest) {
        User user = findEntityById(userRepository.findById(id), "USER", id);
        ofNullable(createUserRequest.getLogin()).map(user::setLogin);
        ofNullable(createUserRequest.getFirstName()).map(user::setFirstName);
        ofNullable(createUserRequest.getLastName()).map(user::setLastName);
        ofNullable(createUserRequest.getAge()).map(user::setAge);
        return ResponseBuilder.buildUserResponse(userRepository.save(user));
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
}