package com.example.user.feign;

import dto.OAuthToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://localhost:8081/auth-service", path = "/oauth")
public interface AuthClient {
    @PostMapping(value = "/token")
    OAuthToken getToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("login_type") String loginType,
            @RequestHeader("Authorization") String authorization
    );
}
