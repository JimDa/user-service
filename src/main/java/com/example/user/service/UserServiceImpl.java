package com.example.user.service;

import com.example.user.mapper.UserAccountMapper;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("globalUserInfo")
    private Map<String, User> globalUserInfo;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User queryUserByName(String name) {
        return globalUserInfo.get(name);
    }

    @Override
    public String register(User registerInfo) {
        String username = registerInfo.getUsername();
        List<User> users = userAccountMapper.selectByUsername(registerInfo.getUsername());
        if (globalUserInfo.containsKey(username) || !CollectionUtils.isEmpty(users)) {
            return String.format("已有用户%s！", username);
        }
        registerInfo.setPassword(bCryptPasswordEncoder.encode(registerInfo.getPassword()));
        userAccountMapper.insert(registerInfo);
        return "注册成功！";
    }
}
