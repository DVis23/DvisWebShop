package com.example.DvisWebShop.DTO.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CreateUserRequest {
    @Schema(example = "admin23")
    private String login;

    @Schema(example = "Ansgar")
    private String firstName;

    @Schema(example = "Sauron")
    private String lastName;

    @Schema(example = "23")
    private Integer age;
}
