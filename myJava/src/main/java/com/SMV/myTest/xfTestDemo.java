package com.SMV.myTest;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

public class xfTestDemo {

    private static Logger logger = Logger.getLogger(xfTestMybatis.class.getName());

    public xfTestDemo() {
    }

    public static void main(String[] args) throws IOException {
        //读取xml文件的配置然后创建sqlsession
        logger.info("info：启动test1成功");

        //1.读取配置文件方式1
        String resourcePath = "com/myTest/mybatis-config-mytest.xml";// 1. Properties加载我们mybatis全局配置文件
        InputStream inputStream = Resources.getResourceAsStream(resourcePath);

        // 1.读取配置文件方式2：适用于配置文件在文件系统中的情况
//        String resourcePath = "/Users/lixiaofeng/myGitProjects/myJava/myJava/src/main/resources/SMV/myTest/mybatis-config-mytest.xml";
//        InputStream inputStream = new FileInputStream(resourcePath);
        // 1.读取配置文件方式3：需要修改路径为相对于类路径的路径
//        String resourcePath = "SMV/myTest/mybatis-config-mytest.xml";
//        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);


        //2.创建SqlSessionFactory工厂
        // 使用 Reader 读取 MyBatis 配置文件
        Reader reader = new InputStreamReader(inputStream);
        // 创建 XMLConfigBuilder 解析器，传入 Reader、environment（可为 null）、properties（可为 null）
        XMLConfigBuilder parser = new XMLConfigBuilder(reader, null, null);
        // 解析 MyBatis 配置文件，得到 Configuration 对象
        Configuration configuration = parser.parse();
        // 使用 SqlSessionFactoryBuilder 根据 Configuration 构建 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        // 关闭 Reader 和 InputStream 资源
        reader.close();
        inputStream.close();


        //3.使用工厂生产SqlSession对象
        //这里的sqlSession对象就相当于jdbc中的Connection对象
        //返回sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();//参数如果设置为true，设置为自动提交commit

        //4.使用SqlSession创建Dao接口的代理对象
        //关联上接口，然后就可以调用接口方法了。
        LocationDao LocationDao1 = sqlSession.getMapper(LocationDao.class);
        UserDao UserDao1 = sqlSession.getMapper(UserDao.class);
        MailDao MailDao1 = sqlSession.getMapper(MailDao.class);


        //5.使用代理对象执行方法
        //调用一个bean Location
        //使用LocationMapper.xml 调用LocationDao的方法
        List<Location> locationList = LocationDao1.getLocationList();
        System.out.println("使用LocationMapper.xml 调用LocationDao的方法");
        for (Location l:locationList){
            System.out.println(l.toString());
        }


        //调用相同bean USER,用的mapper1
        System.out.println("获取到 getUserList：");
        System.out.println(UserDao1.getUserList());

        //获取mail
        String mail1 = MailDao1.getMailUrl();
        System.out.println("只设置接口也能查询=getMailUrl》"+mail1);
        //获取mailList
        List<String> mail2 = MailDao1.getMailUrlList();
        System.out.println("只设置接口也能查询=getMailUrlList》"+ Arrays.toString(mail2.toArray()));

        //分页查询
        //我们通过mybatis自带的插件RowBounds对象进行分页
        System.out.println("测试分页查询：方法一");
        RowBounds rowBounds = new RowBounds(0,2);
        List<User> user = sqlSession.selectList("com.SMV.myTest.UserDao.getUserList", null, rowBounds);
        for (User user1 : user) {
            System.out.println(user1);
        }
        //分页查询方法2 使用limit
        System.out.println("测试分页查询：方法二");
        UserDao UserDao2 = sqlSession.getMapper(UserDao.class);
        List<User> users2 =  UserDao2.queryUserListPage2();
        for (User u : users2) {
            System.out.println(u);
        }

        //使用注解开发，用注解替代了xml文件。不过注解只能用于简单的sql。
        System.out.println("使用注解开发");
        UserDao UserDao3 = sqlSession.getMapper(UserDao.class);
        List<User> users3 =  UserDao3.queryUserListPage2();
        for (User u : users3) {
            System.out.println(u);
        }
        //6.释放资源
        sqlSession.close();
        inputStream.close();
    }

}
