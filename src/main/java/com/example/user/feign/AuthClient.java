package com.example.user.feign;

import dto.OAuthToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service", url = "http://localhost:8081/auth-service", path = "/oauth")
public interface AuthClient {
    @PostMapping(value = "/token")
    OAuthToken getToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestHeader("Authorization") String authorization
    );
}
