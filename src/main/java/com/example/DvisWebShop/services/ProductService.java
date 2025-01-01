package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateProductRequest;
import com.example.DvisWebShop.DTO.responses.ProductResponse;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ProductService {

    @NotNull
    List<ProductResponse> getAllProducts();

    @NotNull
    ProductResponse getProductById(@NotNull Integer id);

    @NotNull
    List<ProductResponse> getProductsByOrderId(@NotNull Integer orderId);

    @NotNull
    ProductResponse createProduct(@NotNull CreateProductRequest createProductRequest);

    @NotNull
    ProductResponse updateProduct(@NotNull Integer id, @NotNull CreateProductRequest createProductRequest);

    @NotNull
    boolean deleteProduct(@NotNull Integer id);
}
