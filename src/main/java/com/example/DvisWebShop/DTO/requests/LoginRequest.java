package com.example.DvisWebShop.DTO.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(example = "admin23")
    private String login;

    @Schema(example = "P@$$w0rd")
    private String password;
}
