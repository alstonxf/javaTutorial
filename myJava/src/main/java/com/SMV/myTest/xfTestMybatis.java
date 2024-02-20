package com.SMV.myTest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

public class xfTestMybatis {

    private static Logger logger = Logger.getLogger(xfTestMybatis.class.getName());

    public xfTestMybatis() {
    }

    public static void main(String[] args) throws IOException {
        logger.info("info：启动test1成功");

        // 1. Properties加载我们mybatis全局配置文件
        // 2. 创建我们SqlSessionFactoryBuilder对象
        // 3. SqlSessionFactoryBuilder的builder方法里面会解析mybatis全局配置文件
        // 4. 最后得到我们configuration所有的配置信息
        // 5. 接着就可以实例化我们的sqlsessionFactory对象
        // 6.  sqlsessionfactory对象中里面有这个事务管理器，来控制我们sql的事务的
        // 7. 接着创建executor执行器，来执行我们crud的操作的，
        // 8. 然后检查crud是否执行成功，如果执行失败就回到我们事务管理器回滚，
        // 9.  如果执行成功，就提交事务

        //1.读取配置文件
        String resourcePath = "com/myTest/mybatis-config-mytest.xml";// 1. Properties加载我们mybatis全局配置文件
        InputStream inputStream = Resources.getResourceAsStream(resourcePath);

        //2.创建SqlSessionFactory工厂：使用ibatis
        SqlSessionFactoryBuilder Builder = new SqlSessionFactoryBuilder();//2. 创建我们SqlSessionFactoryBuilder对象
        SqlSessionFactory sqlSessionFactory = Builder.build(inputStream);//3. SqlSessionFactoryBuilder的builder方法里面会解析mybatis全局配置文件

        //3.使用工厂生产SqlSession对象
        //这里的sqlSession对象就相当于jdbc中的Connection对象
        //返回sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();//参数如果设置为true，设置为自动提交commit

        //4.使用SqlSession创建Dao接口的代理对象
        //关联上接口，然后就可以调用接口方法了。
        UserDao UserDao1 = sqlSession.getMapper(UserDao.class);
        LocationDao LocationDao1 = sqlSession.getMapper(LocationDao.class);

        //5.使用代理对象执行方法
        //调用一个bean Location
        //使用LocationMapper.xml 调用LocationDao的方法
        List<Location> locationList = LocationDao1.getLocationList();
        System.out.println("使用LocationMapper.xml 调用LocationDao的方法");
        for (Location l:locationList){
            System.out.println(l.toString());
        }


        //返回List<User>
        System.out.println("获取到 getUserList：");
        System.out.println(UserDao1.getUserList());

        //如果数据库字段值与bean类field名不同，在maper.xml中需要使用resultmap定义映射关系
        System.out.println("根据ID查询用户 获取到 getUserListPassword：");
        System.out.println(UserDao1.getUserListPassword());

        //根据ID查询用户:闯入User，返回单条结果
        System.out.println("根据ID查询用户 获取到 getUserById：");
        System.out.println(UserDao1.getUserById(new User(1,null,null)));

        //根据用户名字查询所有user：传入参数类型为Map，返回单条String
        System.out.println("根据username查询所有user");
        HashMap<String, Object> n1 = new HashMap<>();
        n1.put("username","user1");
        System.out.println(UserDao1.getUserByName(n1));

        //根据用户名字或id查询所有user: 传入参数类型为Map,返回List
        System.out.println("根据用户名字或id查询所有user");
        HashMap<String,Object> idNameMap1 = new HashMap<>();
        idNameMap1.put("username1","user3");
        idNameMap1.put("id1","1");
        List<User> UserByNameOrIdList = UserDao1.getUserByNameOrId(idNameMap1);
        System.out.println(UserByNameOrIdList);

        //分页查询
        //我们通过mybatis自带的插件RowBounds对象进行分页：
        System.out.println("测试分页查询：方法一");
        RowBounds rowBounds = new RowBounds(0,2);
        //第二个参数是传递给查询的参数对象（在这里是 null）
        List<User> users1 = sqlSession.selectList("com.SMV.myTest.UserDao.getUserList", null, rowBounds);
        System.out.println("第二个参数是传递给查询的参数对象（在这里是 null）"+users1);
        //第二个参数是传递给查询的参数对象（在这里是 map）
        List<User> UserByNameOrIdList2 = sqlSession.selectList("com.SMV.myTest.UserDao.getUserByNameOrId",idNameMap1, rowBounds);
        System.out.println("第二个参数是传递给查询的参数对象（在这里是 map）"+UserByNameOrIdList2);

        //分页查询方法2 使用limit
        System.out.println("测试分页查询：方法二");
        UserDao UserDao2 = sqlSession.getMapper(UserDao.class);
        List<User> users2 =  UserDao2.queryUserListPage2();
        System.out.println(users2);

        //使用注解开发，用注解替代了xml文件。不过注解只能用于简单的sql。
        System.out.println("使用注解开发");
        UserDao UserDao3 = sqlSession.getMapper(UserDao.class);
        List<User> users3 =  UserDao3.queryUserListPage2();
        System.out.println(users3);

        //6.释放资源
        sqlSession.close();
        inputStream.close();
    }

}
