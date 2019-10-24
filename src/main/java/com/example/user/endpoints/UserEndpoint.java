package com.example.user.endpoints;

import com.example.user.feign.AuthClient;
import com.example.user.feign.RevokeAuthClient;
import com.example.user.service.IAlismsService;
import com.example.user.service.UserService;
import domain.User;
import dto.OAuthToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vo.LoginRequestVo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/user")
@RestController
@Api("主干接口：登入登出以及注册")
public class UserEndpoint {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private RevokeAuthClient revokeAuthClient;
    @Autowired
    private IAlismsService iAlismsService;

    @PostMapping(value = "/register")
    @ApiOperation("注册")
    public ResponseEntity<String> register(@Valid @RequestBody User registerInfo) {
        return userService.register(registerInfo);
    }

    @PostMapping(value = "/login")
    @ApiOperation("登入")
    public ResponseEntity<OAuthToken> getToken(@RequestBody @Valid LoginRequestVo loginInfo) {
        String tokenStr = Base64.encodeBase64String(("fooClientIdPassword" + ":" + "secret").getBytes());
        OAuthToken token = authClient.getToken("multi", loginInfo.getPrincipal(), loginInfo.getCredentials(), loginInfo.getLoginType(), "Basic " + tokenStr);
        return ResponseEntity.ok(token);
    }

    @DeleteMapping(value = "/logout")
    @ApiOperation("登出")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String value = authorization.replace("Bearer ", "");
        ResponseEntity<Boolean> responseEntity = revokeAuthClient.revoke(value);
        if (responseEntity.getStatusCodeValue() != 200 || !responseEntity.getBody()) {
            return ResponseEntity.status(500).body("退出登录失败！");
        }
        return ResponseEntity.ok("退出登录成功！");
    }

}
