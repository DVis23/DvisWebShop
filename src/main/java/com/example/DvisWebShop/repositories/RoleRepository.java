package com.example.DvisWebShop.repositories;

import com.example.DvisWebShop.models.Role;
import com.example.DvisWebShop.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName roleName);
}
