package com.example.user.endpoints;

import com.example.user.feign.RefreshAuthClient;
import dto.OAuthToken;
import org.apache.commons.codec.binary.Base64;
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
        String tokenStr = Base64.encodeBase64String(("fooClientIdPassword" + ":" + "secret").getBytes());
        OAuthToken oAuthToken = refreshAuthClient.updateToken("refresh_token", "Basic " + tokenStr, refreshToken);
        return ResponseEntity.ok(oAuthToken);
    }
}
