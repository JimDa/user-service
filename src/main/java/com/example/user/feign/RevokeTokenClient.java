package com.example.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth-service-1", url = "http://127.0.0.1:8080/auth-server", path = "/oauth")
public interface RevokeTokenClient {
    @RequestMapping(value = "/token/revokeById/{tokenId}", method = RequestMethod.POST)
    Object revoke(@PathVariable("tokenId") String tokenId);
}
