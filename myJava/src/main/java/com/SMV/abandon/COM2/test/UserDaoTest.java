package com.SMV.abandon.COM2.test;

import com.SMV.abandon.COM2.utils.MybatisUtils;
import com.SMV.abandon.COM2.dao.UserDaoMapper;

import com.SMV.abandon.COM2.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {
    //查询所有用户
    @Test
    public void getUserList() {
        //我们通过工程类创建SqlSession操作数据库的对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //然后通过sqlsession对象获取我们dao层的接口
        //方式一
        UserDaoMapper mapper = sqlSession.getMapper(UserDaoMapper.class);
        //方式二(不推荐使用)
//        List<UserDao> UserDao = sqlSession.selectList("com.yunduo.dao.UserDao.class");

        //返回的对象执行自己的接口
        List<User> userList = mapper.getUserList();
        //如果这里打印出错，那么就是没有配置myabtis-config.xml中mappers中注册到mybatis中，以及要配置maven的pom.xml中build设置过滤
        //输出
        System.out.println("获取到userList:");
        for (User user : userList) {
            System.out.println(user);
        }
        //关闭数据库对象
        sqlSession.close();
    }

    //根据ID查询用户
    @Test
    public void getUserById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDaoMapper user = sqlSession.getMapper(UserDaoMapper.class);
        System.out.println("获取到 getUserById：");
        System.out.println(user.getUserById(new User(1,null,null)));
        sqlSession.close();
    }


    //新增用户
    @Test
    public void AddUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDaoMapper mapper = sqlSession.getMapper(UserDaoMapper.class);
        int lao = mapper.AddUser(new User(5, "老六", "1234567"));
        //增删改，必须提交事务
        sqlSession.commit();
        if (lao>0){
            System.out.println("新增成功");
        }else{
            System.out.println("新增失败");
        }
    }

    //根据ID修改用户
    @Test
    public void UpdateUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDaoMapper mapper = sqlSession.getMapper(UserDaoMapper.class);
        int i = mapper.UpdateUserById(new User(5, null, "123456"));
        //提交事务
        sqlSession.commit();
        if (i>0){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }
    }

    //根据用户ID清除用户
    @Test
    public void DeleteUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDaoMapper user = sqlSession.getMapper(UserDaoMapper.class);
        int i=0;
        try{
            i = user.DeleteUserById(new User(5, null, null));
            sqlSession.commit();
        }catch(Exception e){
            e.getMessage();
        }finally {
            sqlSession.rollback();
        }
        if (i>0){
            System.out.println("清除成功");
        }else{
            System.out.println("清除失败");
        }
    }
}

