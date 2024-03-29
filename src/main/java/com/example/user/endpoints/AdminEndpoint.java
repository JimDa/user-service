package com.example.user.endpoints;

import com.example.user.service.UserService;
import domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("用户管理")
@RequestMapping(value = "/user-management")
public class AdminEndpoint {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    @ApiOperation("获取所有用户信息")
    @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> queryAllUsers() {
        List<User> allUsers = userService.queryAll();
        return ResponseEntity.ok(allUsers);
    }
}
