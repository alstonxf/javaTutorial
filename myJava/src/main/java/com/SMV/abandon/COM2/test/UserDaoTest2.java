package com.SMV.abandon.COM2.test;

import com.SMV.abandon.COM2.dao.UserDaoMapper;
import com.SMV.abandon.COM2.pojo.User;
import com.SMV.abandon.COM2.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;

public class UserDaoTest2 {

    @Test
    public void get(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDaoMapper user = sqlSession.getMapper(UserDaoMapper.class);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("kkk",1);
        User userById2 = user.getUserById2(map);
        System.out.println(userById2);
    }


}
