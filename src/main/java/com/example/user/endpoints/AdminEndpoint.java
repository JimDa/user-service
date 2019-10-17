package com.example.user.endpoints;

import com.example.user.service.UserService;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/user-management")
@RestController
public class AdminEndpoint {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> queryAllUsers() {
        List<User> allUsers = userService.queryAll();
        return ResponseEntity.ok(allUsers);
    }
}
