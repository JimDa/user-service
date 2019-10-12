package com.example.user.service;

import dto.User;

import java.util.Map;

public interface UserService {
    User queryUserByName(String name);

    String register(Map<String, String> registerInfo);
}
