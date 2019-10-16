package com.example.user.service;

import dto.User;

public interface UserService {
    User queryUserByName(String name);

    String register(User registerInfo);
}
