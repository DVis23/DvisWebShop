package com.example.DvisWebShop.DTO.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderResponse {
    @Schema(example = "1")
    private Integer orderId;

    @Schema(example = "23.23")
    private BigDecimal price;

    @Schema(example = "2023-12-23T23:23:23")
    private LocalDateTime date;

    @Schema(example = "2")
    private Integer userId;

    @Schema(example = "[2, 3]")
    private List<Integer> productsId;
}
