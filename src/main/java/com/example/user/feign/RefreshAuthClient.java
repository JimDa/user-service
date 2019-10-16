package com.example.user.feign;

import dto.OAuthToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service", url = "http://127.0.0.1:8081/auth-service", path = "/oauth", contextId = "refresh")
public interface RefreshAuthClient {
    @PostMapping(value = "/token")
    OAuthToken updateToken(@RequestParam("grant_type") String grantType,
                           @RequestHeader("Authorization") String clientAuth,
                           @RequestParam("refresh_token") String refreshToken
    );
}
