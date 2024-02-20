package com.SMV.abandon.mybatis.mapper;

import com.SMV.abandon.mybatis.entity.User;

import java.util.List;
public interface UserMapper {

    public List<User> selectUserList();
}

