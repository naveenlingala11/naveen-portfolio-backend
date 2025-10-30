package com.naveen.portfolio;

import com.naveen.portfolio.model.User;
import com.naveen.portfolio.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}

    @Bean
    CommandLineRunner init(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User(null, "admin", encoder.encode("password123"), "ROLE_ADMIN");
                userRepo.save(admin);
                System.out.println("✅ Admin user created: username=admin, password=password123");
            }
        };
    }


}
