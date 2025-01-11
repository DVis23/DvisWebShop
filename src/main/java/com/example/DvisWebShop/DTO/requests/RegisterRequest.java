package com.example.DvisWebShop.DTO.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    @Schema(example = "admin23")
    private String login;

    @Schema(example = "P@$$w0rd")
    private String password;

    @Schema(example = "Ansgar")
    private String firstName;

    @Schema(example = "Sauron")
    private String lastName;

    @Schema(example = "23")
    private Integer age;

    @Schema(example = "[ROLE_ADMIN]")
    private Set<String> roles;
}
