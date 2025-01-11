package com.example.DvisWebShop.DTO.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class CreateProductRequest {
    @Schema(example = "chocolate")
    private String name;

    @Schema(example = "23.23")
    private BigDecimal price;

    @Schema(example = "Choco-Choco")
    private String company;

    @Schema(example = "[1, 2, 3]")
    private List<Integer> ordersId;
}
