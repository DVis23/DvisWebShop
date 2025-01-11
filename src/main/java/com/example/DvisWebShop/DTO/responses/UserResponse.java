package com.example.DvisWebShop.DTO.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserResponse {
    @Schema(example = "1")
    private Integer userId;

    @Schema(example = "admin23")
    private String login;

    @Schema(example = "Ansgar")
    private String firstName;

    @Schema(example = "Sauron")
    private String lastName;

    @Schema(example = "23")
    private Integer age;

    @Schema(example = "[]")
    private List<Integer> ordersId;
}