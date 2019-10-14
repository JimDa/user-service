package com.example.user.feign;

import dto.OAuthToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service-2", url = "http://127.0.0.1:8080/auth-service", path = "/oauth")
public interface RefreshAuthClient {
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    OAuthToken updateToken(@RequestParam("grant_type") String grantType,
                           @RequestParam("client_id") String clientId,
                           @RequestParam("client_secret") String clientSecret,
                           @RequestParam("refresh_token") String refreshToken
    );
}
