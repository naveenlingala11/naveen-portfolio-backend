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

    // Optional override via application-prod.properties or Render env vars
    @Value("${spring.web.cors.allowed-origins:}")
    private String allowedOriginsProperty;

    public CorsConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Detect active profile
        String[] profiles = env.getActiveProfiles();
        String activeProfile = profiles.length > 0 ? profiles[0] : "default";

        // Default origins
        if ("prod".equalsIgnoreCase(activeProfile)) {
            // ✅ Production origin (Netlify)
            if (allowedOriginsProperty != null && !allowedOriginsProperty.isEmpty()) {
                config.setAllowedOrigins(List.of(allowedOriginsProperty));
            } else {
                config.setAllowedOrigins(List.of("https://naveenlingala.netlify.app"));
            }
        } else {
            // ✅ Development origin
            config.setAllowedOrigins(List.of("http://localhost:4200"));
        }

        // Common allowed methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        // Apply CORS to all routes
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
