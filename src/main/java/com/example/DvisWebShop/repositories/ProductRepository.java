package com.example.DvisWebShop.repositories;

import com.example.DvisWebShop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
