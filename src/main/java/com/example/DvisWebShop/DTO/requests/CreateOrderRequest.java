package com.example.DvisWebShop.DTO.requests;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class CreateOrderRequest {
    private Integer orderId;
    private BigDecimal price;
    private LocalDateTime date;
    private CreateUserRequest user;
}
