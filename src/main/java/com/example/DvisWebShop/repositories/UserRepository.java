package com.example.DvisWebShop.repositories;

import com.example.DvisWebShop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
