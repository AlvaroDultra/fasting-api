package com.fastingapp.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    private String secret = "fastingAppSecretKeyThatIsVeryLongAndSecureForJWTTokenGeneration2025";
    private long expiration = 86400000; // 24 horas em milissegundos
}