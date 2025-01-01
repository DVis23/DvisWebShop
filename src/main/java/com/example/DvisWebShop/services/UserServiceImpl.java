package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateUserRequest;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.models.User;
import com.example.DvisWebShop.repositories.UserRepository;

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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
                () -> new NoSuchElementException("USER with id = '" + id + "' does not exist"));
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
                () -> new NoSuchElementException("USER with id = '" + id + "' does not exist"));
        ofNullable(createUserRequest.getLogin()).map(user::setLogin);
        ofNullable(createUserRequest.getFirstName()).map(user::setFirstName);
        ofNullable(createUserRequest.getLastName()).map(user::setLastName);
        Optional.of(createUserRequest.getAge()).map(user::setAge);
        return buildUserResponse(userRepository.save(user));
    }

    @Override
    @NotNull
    @Transactional
    public boolean deleteUser(@NotNull Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private UserResponse buildUserResponse(User user) {
        return new UserResponse()
                .setUserId(user.getUserId())
                .setLogin(user.getLogin())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge());
    }

    private User buildUserRequest(CreateUserRequest request) {
        return new User()
                .setUserId(request.getUserId())
                .setLogin(request.getLogin())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());
    }
}