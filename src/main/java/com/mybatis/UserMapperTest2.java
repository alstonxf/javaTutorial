package com.mybatis;

import com.mybatis.entity.User;
import com.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserMapperTest2 {

    @Test
    public void test() throws IOException {
        //1.读取配置文件
        String resource = "com/mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //2.创建SqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //3.使用工厂生产SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4.使用SqlSession创建Dao接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //5.使用代理对象执行方法
        List<User> userList = mapper.selectUserList();
        userList.forEach(System.out::println);

        //6.释放资源
        sqlSession.close();
        inputStream.close();
    }
}

