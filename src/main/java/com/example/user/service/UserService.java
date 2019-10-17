package com.example.user.service;

import dto.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User queryUserByName(String name);

    ResponseEntity<String> register(User registerInfo);

    List<User> queryAll();
}
