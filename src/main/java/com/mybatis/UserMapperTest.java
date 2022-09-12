package com.mybatis;

import com.mybatis.entity.User;
import com.mybatis.mapper.UserMapper;
import com.mybatis.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {

    @Test
    public void test() {
        // 第一步: 获得sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        // 执行 这个过程就相当于创建一个interface的对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.selectUserList();
        userList.forEach(System.out::println);
        sqlSession.close();
    }
}

