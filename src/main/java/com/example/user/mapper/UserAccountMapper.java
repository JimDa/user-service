package com.example.user.mapper;

import dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAccountMapper {
    User selectByUsername(@Param("username") String username);

    void insert(User registerInfo);

    List<User> selectAllUsers();

    void relateRole(@Param("userId") Integer id, @Param("roleId") Integer i);
}
