package com.example.user.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "security.oauth2.client")
public class AutoSecurityConfig {
    private String clientId;
    private String clientSecret;
    private String accessTokenUri;
    private String grantType;
    private String scope;
}
