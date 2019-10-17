package com.example.user.service;

import com.example.user.mapper.UserAccountMapper;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User queryUserByName(String name) {
        return userAccountMapper.selectByUsername(name);
    }

    @Override
    public String register(User registerInfo) {
        String username = registerInfo.getUsername();
        User user = userAccountMapper.selectByUsername(registerInfo.getUsername());
        if (null != user) {
            return String.format("已有用户%s！", username);
        }
        registerInfo.setCreateDate(new Date());
        registerInfo.setCreator(registerInfo.getUsername());
        registerInfo.setPassword(bCryptPasswordEncoder.encode(registerInfo.getPassword()));
        userAccountMapper.insert(registerInfo);
        return "注册成功！";
    }
}
