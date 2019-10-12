package com.example.user.endpoints;

import com.example.user.dto.OAuthToken;
import com.example.user.dto.User;
import com.example.user.feign.AuthClient;
import com.example.user.feign.RevokeTokenClient;
import com.example.user.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
        if (!StringUtils.isEmpty(authorization) && authorization.contains("Bearer")) {
            revokeTokenClient.revoke(authorization);
        }
        return ResponseEntity.ok("退出登录成功！");
    }
}
