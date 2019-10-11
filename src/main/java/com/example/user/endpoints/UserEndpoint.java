package com.example.user.endpoints;

import com.example.user.dto.OAuthToken;
import com.example.user.dto.User;
import com.example.user.feign.AuthClient;
import com.example.user.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@RequestMapping("/user")
@RestController
public class UserEndpoint {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthClient authClient;
    @Resource(name = "tokenServices")
    private ConsumerTokenServices tokenServices;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

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
            String tokenValue = authorization.substring("Bearer ".length());
            tokenServices.revokeToken(tokenValue);
            SecurityContextHolder.clearContext();
//            authClient.revoke(authorization);
            Set<String> keys = stringRedisTemplate.keys("*");
            keys.forEach(k->stringRedisTemplate.delete(k));

        }
        return ResponseEntity.ok("退出登录成功！");
    }
}
