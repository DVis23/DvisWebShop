package com.example.DvisWebShop.DTO.requests;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class CreateProductRequest {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private String company;
    private List<Integer> ordersId;
}
