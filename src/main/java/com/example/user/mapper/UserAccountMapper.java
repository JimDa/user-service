package com.example.user.mapper;

import dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAccountMapper {
    User selectByUsername(@Param("username") String username);

    void insert(User registerInfo);
}
