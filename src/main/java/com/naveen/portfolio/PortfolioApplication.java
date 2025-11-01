package com.naveen.portfolio;

import com.naveen.portfolio.model.User;
import com.naveen.portfolio.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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

    @PostConstruct
    public void printEnv() {
        System.out.println("🔎 SPRING_DATASOURCE_URL = " + System.getenv("SPRING_DATASOURCE_URL"));
        System.out.println("🔎 SPRING_DATASOURCE_USERNAME = " + System.getenv("SPRING_DATASOURCE_USERNAME"));
        System.out.println("🔎 SPRING_DATASOURCE_PASSWORD = " + System.getenv("SPRING_DATASOURCE_PASSWORD"));
    }

    @Bean
    CommandLineRunner printEnv(
            @Value("${spring.profiles.active:}") String profile,
            @Value("${spring.datasource.url:}") String url) {
        return args -> {
            System.out.println("🔍 Active Profile: " + profile);
            System.out.println("🗄️  Datasource URL: " + url);
        };
    }

}
