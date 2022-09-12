package com.mybatis.mapper;

import com.mybatis.entity.User;

import java.util.List;
public interface UserMapper {

    public List<User> selectUserList();
}

