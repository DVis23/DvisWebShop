package com.example.DvisWebShop.repositories;

import com.example.DvisWebShop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByOrderId(Integer orderId);
}
