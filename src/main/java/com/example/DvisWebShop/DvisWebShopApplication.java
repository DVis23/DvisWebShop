package com.example.DvisWebShop;

import com.example.DvisWebShop.models.Role;
import com.example.DvisWebShop.models.RoleName;
import com.example.DvisWebShop.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class DvisWebShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(DvisWebShopApplication.class, args);
	}

	@Transactional
	@Bean
	CommandLineRunner commandLineRunner(
			RoleRepository roleRepository
			) {
		return args -> {
			roleRepository.save(new Role(RoleName.ROLE_USER));
			roleRepository.save(new Role(RoleName.ROLE_ADMIN));
		};
	}
}
