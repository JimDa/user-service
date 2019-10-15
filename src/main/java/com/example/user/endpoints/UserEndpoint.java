package com.example.user.endpoints;

import com.example.user.feign.AuthClient;
import com.example.user.feign.RevokeTokenClient;
import com.example.user.service.UserService;
import dto.OAuthToken;
import dto.User;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserEndpoint {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private RevokeTokenClient revokeTokenClient;
    @Autowired
    @Qualifier(value = "clientDetails")
    private ResourceOwnerPasswordResourceDetails clientDetails;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> registerInfo) {
        String result = userService.register(registerInfo);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/queryUserByName")
    public ResponseEntity<User> queryUserByName(@RequestParam("userName") String userName) {
        User user = userService.queryUserByName(userName);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<OAuthToken> getToken(@RequestBody Map<String, String> loginInfo) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        String tokenStr = Base64.encodeBase64String(("fooClientIdPassword" + ":" + "secret").getBytes());

        OAuthToken token = authClient.getToken("password", username, password, "Basic " + tokenStr);
        return ResponseEntity.ok(token);
    }

    @DeleteMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String value = authorization.replace("Bearer ", "");
        ResponseEntity<Boolean> responseEntity = revokeTokenClient.revoke(value);
        if (responseEntity.getStatusCodeValue() != 200 || !responseEntity.getBody()) {
            return ResponseEntity.ok("退出登录失败！");
        }
        return ResponseEntity.ok("退出登录成功！");
    }

    @GetMapping(value = "/client-details")
    public ResponseEntity<ResourceOwnerPasswordResourceDetails> get() {
        return ResponseEntity.ok(clientDetails);
    }
}
