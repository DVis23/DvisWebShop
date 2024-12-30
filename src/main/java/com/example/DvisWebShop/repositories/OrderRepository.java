package com.example.DvisWebShop.repositories;

import com.example.DvisWebShop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
