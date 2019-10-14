package com.example.user.feign;

import dto.OAuthToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://127.0.0.1:8081/auth-service", path = "/oauth")
public interface AuthClient {
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    OAuthToken getToken(@RequestParam("grant_type") String grantType,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestHeader("Authorization") String authorization
    );
}
