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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public UserResponse getUserById(@NotNull Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToUserResponse).orElse(null);
    }

    @Override
    @NotNull
    @Transactional
    public UserResponse createUser(@NotNull CreateUserRequest createUserRequest) {
        User user = new User();
        user.setLogin(createUserRequest.getLogin());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setAge(createUserRequest.getAge());
        user = userRepository.save(user);
        return convertToUserResponse(user);
    }

    @Override
    @NotNull
    @Transactional
    public UserResponse updateUser(@NotNull Integer id, @NotNull CreateUserRequest createUserRequest) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User user = existingUserOpt.get();
            user.setLogin(createUserRequest.getLogin());
            user.setFirstName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
            user.setAge(createUserRequest.getAge());
            user = userRepository.save(user);
            return convertToUserResponse(user);
        } else {
            return null;
        }
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

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse()
                .setUserId(user.getUserId())
                .setLogin(user.getLogin())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge());
    }
}