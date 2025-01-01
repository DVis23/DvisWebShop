package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateProductRequest;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    @Override
    public List<ProductResponse> getAllProducts() {
        return List.of();
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        return null;
    }

    @Override
    public List<ProductResponse> getProductsByOrderId(Integer orderId) {
        return List.of();
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(Integer id, CreateProductRequest createProductRequest) {
        return null;
    }

    @Override
    public boolean deleteProduct(Integer id) {
        return false;
    }
}
