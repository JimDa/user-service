package com.example.user.mapper;

import dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAccountMapper {
    List<User> selectByUsername(@Param("username") String username);

    void insert(User registerInfo);
}
