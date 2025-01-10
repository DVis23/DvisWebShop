package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateUserRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.exception.ResourceNotFoundException;
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
import java.util.List;

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
                id -> findEntityById(orderRepository.findById(id), "ORDER", id));
        return ResponseBuilder.buildUserResponse(userRepository.save(user));
    }

    @Override
    @NotNull
    @Transactional
    public UserResponse updateUser(@NotNull Integer id, @NotNull CreateUserRequest createUserRequest) {
        User user = findEntityById(userRepository.findById(id), "USER", id);
        ofNullable(createUserRequest.getLogin()).ifPresent(user::setLogin);
        ofNullable(createUserRequest.getFirstName()).ifPresent(user::setFirstName);
        ofNullable(createUserRequest.getLastName()).ifPresent(user::setLastName);
        ofNullable(createUserRequest.getAge()).ifPresent(user::setAge);
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
                .orElseThrow(() -> new ResourceNotFoundException("USER", "id", id));
    }
}