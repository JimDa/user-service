package com.example.user.service;

import dto.User;

import java.util.List;

public interface UserService {
    User queryUserByName(String name);

    String register(User registerInfo);

    List<User> queryAll();
}
