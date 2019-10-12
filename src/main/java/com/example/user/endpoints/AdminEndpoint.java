package com.example.user.endpoints;

import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/user-management")
@RestController
public class AdminEndpoint {
    @Autowired
    @Qualifier("globalUserInfo")
    private Map<String, User> globalUserInfo;

    @RequestMapping(value = "/all")
    @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')")
    public List<User> queryAllUsers() {
        Collection<User> users = globalUserInfo.values();
        return new ArrayList<>(users);
    }
}
