package com.naveen.portfolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    private final Environment env;

    @Value("${allowed.origins:}")
    private String allowedOrigins;

    public CorsConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Detect active profile
        String[] profiles = env.getActiveProfiles();
        String activeProfile = profiles.length > 0 ? profiles[0] : "default";

        if ("prod".equalsIgnoreCase(activeProfile)) {
            // ✅ Production
            config.setAllowedOrigins(List.of(
                    allowedOrigins.isEmpty() ? "https://naveenlingala.netlify.app" : allowedOrigins
            ));
        } else {
            // ✅ Development
            config.setAllowedOrigins(List.of(
                    allowedOrigins.isEmpty() ? "http://localhost:4200" : allowedOrigins
            ));
        }

        // Common allowed methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
