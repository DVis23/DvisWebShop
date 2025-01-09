package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateProductRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import com.example.DvisWebShop.models.Product;
import com.example.DvisWebShop.repositories.OrderRepository;
import com.example.DvisWebShop.repositories.ProductRepository;
import com.example.DvisWebShop.utils.EntityBuilder;
import com.example.DvisWebShop.utils.ResponseBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends BaseServices implements ProductService{

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return buildResponseList(productRepository.findAll(), ResponseBuilder::buildProductResponse);
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public ProductResponse getProductById(@NotNull Integer id) {
        return ResponseBuilder.buildProductResponse(findEntityById(productRepository.findById(id), "PRODUCT", id));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<OrderResponse> getProductOrdersById(@NotNull Integer id) {
        Product product = findEntityById(productRepository.findById(id), "PRODUCT", id);
        return buildResponseList(new ArrayList<>(product.getOrders()), ResponseBuilder::buildOrderResponse);
    }

    @Override
    @NotNull
    @Transactional
    public ProductResponse createProduct(@NotNull CreateProductRequest createProductRequest) {
        Product product = EntityBuilder.buildProductRequest(createProductRequest,
                id -> findEntityById(orderRepository.findById(id), "ORDER", id));
        return ResponseBuilder.buildProductResponse(productRepository.save(product));
    }

    @Override
    @NotNull
    @Transactional
    public ProductResponse updateProduct(@NotNull Integer id, @NotNull CreateProductRequest createProductRequest) {
        Product product = findEntityById(productRepository.findById(id), "PRODUCT", id);
        ofNullable(createProductRequest.getName()).map(product::setName);
        ofNullable(createProductRequest.getPrice()).map(product::setPrice);
        ofNullable(createProductRequest.getCompany()).map(product::setCompany);
        return ResponseBuilder.buildProductResponse(productRepository.save(product));
    }

    @Override
    @NotNull
    @Transactional
    public boolean deleteProduct(@NotNull Integer id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT with id = '" + id + "' does not exist"));
    }
}
