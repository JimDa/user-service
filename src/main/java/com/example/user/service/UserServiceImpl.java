package com.example.user.service;

import com.example.user.mapper.UserAccountMapper;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public ResponseEntity<String> register(User registerInfo) {
        String username = registerInfo.getUsername();
        User user = userAccountMapper.selectByUsername(registerInfo.getUsername());
        if (null != user) {
            return ResponseEntity.status(400).body(String.format("已有用户%s！", username));
        }
        registerInfo.setCreateDate(new Date());
        registerInfo.setCreator(registerInfo.getUsername());
        registerInfo.setPassword(bCryptPasswordEncoder.encode(registerInfo.getPassword()));
        userAccountMapper.insert(registerInfo);
        userAccountMapper.relateRole(registerInfo.getId(), 3);
        return ResponseEntity.ok("注册成功！");
    }

    @Override
    public List<User> queryAll() {
        List<User> allUsers = userAccountMapper.selectAllUsers();
        return allUsers;
    }
}
