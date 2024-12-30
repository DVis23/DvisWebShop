package com.example.DvisWebShop.DTO.responses;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ProductResponse {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private String company;
}