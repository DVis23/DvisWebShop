package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateOrderRequest;
import com.example.DvisWebShop.DTO.requests.CreateProductRequest;
import com.example.DvisWebShop.DTO.requests.CreateUserRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.models.Order;
import com.example.DvisWebShop.models.Product;
import com.example.DvisWebShop.models.User;
import com.example.DvisWebShop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

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
    public ProductResponse getProductById(Integer id) {
        return productRepository.findById(id).map(this::buildProductResponse).orElseThrow(
                ()-> new NoSuchElementException("PRODUCT with id = '" + id + "' does not exist"));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByOrderId(Integer orderId) {
        productRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException("ORDER with id = '" + orderId + "' does not exist")
        );
        return productRepository.findByOrderId(orderId).stream()
                .map(this::buildProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    @NotNull
    @Transactional()
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        Product product = buildProductRequest(createProductRequest);
        return buildProductResponse(productRepository.save(product));
    }

    @Override
    @NotNull
    @Transactional()
    public ProductResponse updateProduct(Integer id, CreateProductRequest createProductRequest) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("PRODUCT with id = '" + id + "' does not exist"));
        ofNullable(createProductRequest.getName()).map(product::setName);
        ofNullable(createProductRequest.getPrice()).map(product::setPrice);
        ofNullable(createProductRequest.getCompany()).map(product::setCompany);
        return buildProductResponse(productRepository.save(product));
    }

    @Override
    @NotNull
    @Transactional()
    public boolean deleteProduct(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProductResponse buildProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse
                .setProductId(product.getProductId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setCompany(product.getCompany());
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : product.getOrders()) {
            User user = order.getUser();
            orderResponses.add(new OrderResponse()
                    .setOrderId(order.getOrderId())
                    .setPrice(order.getPrice())
                    .setDate(order.getDate())
                    .setUser(new UserResponse()
                            .setUserId(user.getUserId())
                            .setLogin(user.getLogin())
                            .setFirstName(user.getFirstName())
                            .setLastName(user.getLastName())
                            .setAge(user.getAge())));
        }
        productResponse.setOrders(orderResponses);
        return productResponse;
    }

    private Product buildProductRequest(CreateProductRequest request) {
        Product product = new Product();
        product
                .setProductId(request.getProductId())
                .setName(request.getName())
                .setPrice(request.getPrice())
                .setCompany(request.getCompany());
        List<Order> orders = new ArrayList<>();
        for (CreateOrderRequest createOrderRequest : request.getOrders()) {
            CreateUserRequest createUserRequest = createOrderRequest.getUser();
            orders.add(new Order()
                    .setOrderId(createOrderRequest.getOrderId())
                    .setPrice(createOrderRequest.getPrice())
                    .setDate(createOrderRequest.getDate())
                    .setUser(new User()
                            .setUserId(createUserRequest.getUserId())
                            .setLogin(createUserRequest.getLogin())
                            .setFirstName(createUserRequest.getFirstName())
                            .setLastName(createUserRequest.getLastName())
                            .setAge(createUserRequest.getAge())));
        }
        product.setOrders(orders);
        return product;
    }
}
