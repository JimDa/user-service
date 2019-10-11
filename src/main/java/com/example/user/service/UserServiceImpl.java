package com.example.user.service;

import com.example.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("globalUserInfo")
    private Map<String, User> globalUserInfo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User queryUserByName(String name) {
        return globalUserInfo.get(name);
    }

    @Override
    public String register(Map<String, String> registerInfo) {
        String username = registerInfo.get("username");
        if (globalUserInfo.containsKey(username)) {
            return String.format("已有用户%s！", username);
        }
        String password = registerInfo.get("password");
        String initialRole = "ROLE_PASSENGER";
        Map.Entry<String, User> entry = globalUserInfo.entrySet().stream().sorted((en1, en2) -> {
            Integer id1 = en1.getValue().getId();
            Integer id2 = en2.getValue().getId();
            if (id1 > id2) {
                return -1;
            } else if (id1.equals(id2)) {
                return 0;
            } else {
                return 1;
            }
        }).findFirst().get();
        User user = new User(entry.getValue().getId() + 1, username, bCryptPasswordEncoder.encode(password), initialRole);
        globalUserInfo.put(username, user);
        return "注册成功！";
    }
}
