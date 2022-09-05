# MyBatis入门到精通——入门篇
**目录**

一、什么是 MyBatis？</a>

二、入门案例</a>

（1）创建maven项目</a>

（2）引入依赖包</a>

（3）创建数据库执行sql脚本</a>

三、入门程序源码</a>

（1）User.java</a>

（2）UserMapper.java</a>

（3）MybatisUtils.java</a>

（4）UserMapperTest.java</a>

（6）UserMapper.xml</a>

（7）mybatis-config.xml</a>

（8）pom.xml </a>

四、程序单元测试结果</a>

五、程序完整源码获取</a>

# 一、什么是 MyBatis？

>  
 MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。  
 Mybatis官网文档：https://mybatis.org/mybatis-3/zh/getting-started.html</a> 


# 二、入门案例

## （1）创建maven项目

在此之前，需要提前配置好java环境变量和maven环境变量。

<img alt="" height="582" src="https://img-blog.csdnimg.cn/0f1488d539464d0aa343fe7711e83d20.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAUm9jLXhi,size_20,color_FFFFFF,t_70,g_se,x_16" width="1200"/>

 <img alt="" height="923" src="https://img-blog.csdnimg.cn/d22eea9f56684adc800368428214d802.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAUm9jLXhi,size_20,color_FFFFFF,t_70,g_se,x_16" width="814"/>

 <img alt="" height="935" src="https://img-blog.csdnimg.cn/9bea62ab85be423db662169c3c349b9f.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAUm9jLXhi,size_20,color_FFFFFF,t_70,g_se,x_16" width="832"/>

 <img alt="" height="681" src="https://img-blog.csdnimg.cn/35dba23387d34deb8b842b87dd7872d8.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAUm9jLXhi,size_20,color_FFFFFF,t_70,g_se,x_16" width="1200"/>

## （2）引入依赖包

>  
 在https://mvnrepository.com/</a> 搜索添加到pom.xml中 
 注意：如果使用到lombok需要用IDEA转上lombok插件。 


```
        <!--mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.9</version>
        </dependency>
        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
```

## （3）创建数据库执行sql脚本

```
/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : mybatis-simple

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 16/04/2022 23:11:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin1', '123456');
INSERT INTO `t_user` VALUES (2, 'admin2', '123456');

SET FOREIGN_KEY_CHECKS = 1;

```

# 三、入门程序源码

**项目目录结构**

<img alt="" height="588" src="https://img-blog.csdnimg.cn/860e7d3540be418e8a468a2a420a89a0.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAUm9jLXhi,size_17,color_FFFFFF,t_70,g_se,x_16" width="428"/>

## （1）User.java

```
package com.mybatis.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;
}

```

## （2）UserMapper.java

```
package com.mybatis.mapper;

import com.mybatis.entity.User;

import java.util.List;
public interface UserMapper {

    public List<User> selectUserList();
}

```

## （3）MybatisUtils.java

```
package com.mybatis.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 获取sqlSessionFactory对象
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

}

```

## （4）UserMapperTest.java

```
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

```

## （6）UserMapper.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace=绑定一个对应的Mapper接口-->
<mapper namespace="com.mybatis.mapper.UserMapper">
    <!--查询用户列表-->
    <select id="selectUserList" resultType="com.mybatis.entity.User">
        select * from t_user
    </select>
</mapper>

```

## （7）mybatis-config.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis-simple"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>

    </environments>

    <mappers>
        <package name="com.mybatis.mapper"/>
    </mappers>
</configuration>

```

## （8）pom.xml 

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.mybatis</groupId>
    <artifactId>mybatis-simple</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!--mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.9</version>
        </dependency>
        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>


</project>
```

# 四、程序单元测试结果

运行UserMapperTest类，输出以下结果，就说明运行成功了。

<img alt="" height="359" src="https://img-blog.csdnimg.cn/21c2ba39fea54b3d8201d5a49186507d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAUm9jLXhi,size_20,color_FFFFFF,t_70,g_se,x_16" width="1045"/>

# 五、程序完整源码获取

>  
 https://download.csdn.net/download/qq_19309473/85157975</a> 

# **文章地址： **https://yang-roc.blog.csdn.net/article/details/124219227?spm=1001.2101.3001.6650.18&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EOPENSEARCH%7ERate-18-124219227-blog-115666342.pc_relevant_multi_platform_featuressortv2dupreplace&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EOPENSEARCH%7ERate-18-124219227-blog-115666342.pc_relevant_multi_platform_featuressortv2dupreplace&utm_relevant_index=19