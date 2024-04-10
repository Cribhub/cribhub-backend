package com.cribhub.backend;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.repositories.CribRepository;
import com.cribhub.backend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Profile({"dev","prod"})
	@Bean
	CommandLineRunner run(CustomerRepository customerRepository, CribRepository cribRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Crib crib = new Crib();
			crib.setCribId(1L);
			crib.setName("TestCrib");
			cribRepository.save(crib);

			String encodedPassword = passwordEncoder.encode("password");
			Customer customer = new Customer();
			customer.setUserId(1L);
			customer.setEmail("test@test.com");
			customer.setPassword(encodedPassword);
			customer.setUserName("test-user");
			customerRepository.save(customer);

			customer.setCrib(crib);
			customerRepository.save(customer);

			crib.getCribMembers().add(customer);
			cribRepository.save(crib);
		};
	}

}
