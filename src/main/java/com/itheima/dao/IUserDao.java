package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 用户的持久层  接口
 */

public interface IUserDao {
    /**
     * 查询操作
     */
    //改用注解，第二步：删除dao的xml文件，改为直接在注解写sql。 如果不删除dao的xml 文件，会报错的。因为机器不知道要执行注解还是xml文件的sql。
//    @Select("select * from user;")
    List<User> findAll();
}

