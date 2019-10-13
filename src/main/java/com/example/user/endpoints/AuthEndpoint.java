package com.example.user.endpoints;

import com.example.user.feign.RefreshAuthClient;
import dto.OAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-management")
public class AuthEndpoint {
    @Autowired
    private RefreshAuthClient refreshAuthClient;

    @PostMapping("/update-token")
    public ResponseEntity<OAuthToken> updateToken(String refreshToken) {
        OAuthToken oAuthToken = refreshAuthClient.updateToken("refresh_token", "fooClientIdPassword", "secret", refreshToken);
        return ResponseEntity.ok(oAuthToken);
    }
}
