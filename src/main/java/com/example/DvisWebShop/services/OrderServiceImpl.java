package com.example.DvisWebShop.services;

import com.example.DvisWebShop.DTO.requests.CreateOrderRequest;
import com.example.DvisWebShop.DTO.responses.OrderResponse;
import com.example.DvisWebShop.DTO.responses.ProductResponse;
import com.example.DvisWebShop.DTO.responses.UserResponse;
import com.example.DvisWebShop.models.Order;
import com.example.DvisWebShop.repositories.OrderRepository;
import com.example.DvisWebShop.repositories.ProductRepository;
import com.example.DvisWebShop.repositories.UserRepository;
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
public class OrderServiceImpl extends BaseServices implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return buildResponseList(orderRepository.findAll(), ResponseBuilder::buildOrderResponse);
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(@NotNull Integer id) {
        return ResponseBuilder.buildOrderResponse(findEntityById(orderRepository.findById(id), "ORDER", id));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public UserResponse getOrderUserById(@NotNull Integer id) {
        Order order = findEntityById(orderRepository.findById(id), "ORDER", id);
        return ResponseBuilder.buildUserResponse(order.getUser());
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public List<ProductResponse> getOrderProductsById(@NotNull Integer id) {
        Order order = findEntityById(orderRepository.findById(id), "ORDER", id);
        return buildResponseList(new ArrayList<>(order.getProducts()), ResponseBuilder::buildProductResponse);
    }

    @Override
    @NotNull
    @Transactional
    public OrderResponse createOrder(@NotNull CreateOrderRequest createOrderRequest) {
        Order order = EntityBuilder.buildOrderRequest(createOrderRequest,
                id -> findEntityById(userRepository.findById(id), "USER", id),
                id -> findEntityById(productRepository.findById(id), "PRODUCT", id));
        return ResponseBuilder.buildOrderResponse(orderRepository.save(order));
    }

    @Override
    @NotNull
    @Transactional
    public OrderResponse updateOrder(@NotNull Integer id, @NotNull CreateOrderRequest createOrderRequest) {
        Order order = findEntityById(orderRepository.findById(id), "ORDER", id);
        ofNullable(createOrderRequest.getPrice()).map(order::setPrice);
        ofNullable(createOrderRequest.getDate()).map(order::setDate);
        return ResponseBuilder.buildOrderResponse(orderRepository.save(order));
    }

    @Override
    @NotNull
    @Transactional
    public boolean deleteOrder(@NotNull Integer id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new EntityNotFoundException("ORDER with id = '" + id + "' does not exist"));
    }

    public boolean isOwner(Integer orderId, Integer userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return order.getUser().getUserId().equals(userId);
    }
}
