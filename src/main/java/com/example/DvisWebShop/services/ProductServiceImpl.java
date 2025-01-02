package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateProductRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import com.example.DvisWebShop.models.Order;
import com.example.DvisWebShop.models.Product;
import com.example.DvisWebShop.repositories.OrderRepository;
import com.example.DvisWebShop.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::buildProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public ProductResponse getProductById(@NotNull Integer id) {
        return productRepository.findById(id).map(this::buildProductResponse).orElseThrow(
                ()-> new EntityNotFoundException("PRODUCT with id = '" + id + "' does not exist"));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<OrderResponse> getProductOrdersById(@NotNull Integer id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("PRODUCT with id = '" + id + "' does not exist"));
        return product.getOrders().stream()
                .map(order -> new OrderResponse()
                        .setOrderId(order.getOrderId())
                        .setPrice(order.getPrice())
                        .setDate(order.getDate())
                        .setUserId(order.getUser().getUserId())
                        .setProductsId(order.getProducts().stream()
                                .map(Product::getProductId)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByOrderId(@NotNull Integer orderId) {
        productRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("ORDER with id = '" + orderId + "' does not exist")
        );
        return productRepository.findByOrderId(orderId).stream()
                .map(this::buildProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional
    public ProductResponse createProduct(@NotNull CreateProductRequest createProductRequest) {
        Product product = buildProductRequest(createProductRequest);
        return buildProductResponse(productRepository.save(product));
    }

    @Override
    @NotNull
    @Transactional
    public ProductResponse updateProduct(@NotNull Integer id, @NotNull CreateProductRequest createProductRequest) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("PRODUCT with id = '" + id + "' does not exist"));
        ofNullable(createProductRequest.getName()).map(product::setName);
        ofNullable(createProductRequest.getPrice()).map(product::setPrice);
        ofNullable(createProductRequest.getCompany()).map(product::setCompany);
        return buildProductResponse(productRepository.save(product));
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

    private ProductResponse buildProductResponse(@NotNull Product product) {
        return new ProductResponse()
                .setProductId(product.getProductId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setCompany(product.getCompany())
                .setOrdersId(product.getOrders().stream()
                        .map(Order::getOrderId).collect(Collectors.toList()));
    }

    private Product buildProductRequest(@NotNull CreateProductRequest request) {
        return new Product()
                .setProductId(request.getProductId())
                .setName(request.getName())
                .setPrice(request.getPrice())
                .setCompany(request.getCompany())
                .setOrders(request.getOrdersId().stream()
                        .map(orderId -> orderRepository.findById(orderId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "ORDER with id = '" + orderId + "' does not exist")))
                        .collect(Collectors.toList()));
    }
}
