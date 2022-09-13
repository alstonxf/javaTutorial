# Mybatis从入门到精通系列 01——快速入门
  本文是 mybatis 从入门到精通系列的第一篇，本文将讲解 mybatis 概述、传统jdbc中存在的问题以及基础入门案例的搭建。

<img src="https://img-blog.csdnimg.cn/20210413143119930.png#pic_center" alt="在这里插入图片描述"/>

---


 # 文章目录
一、Mybatis概述
二、传统 jdbc 回顾与问题分析
三、Mybaits 入门案例
3.1 环境搭建
3.2 入门案例编写
四、注意事项

---


## 一、Mybatis概述

  mybaits 是一个持久层框架，用 java 编写的，它封装了 jdbc 操作的很多细节，使开发者 **只需要关注sql语句** 的本身，而无需关注注册驱动，创建链接等繁杂过程。   mybatis 通过 xml 或注解的方式将要执行的各种 statement 配置起来，并通过 java 对象和 statement 中 sql 的动态参数进行映射生成最终执行的 sql 语句，最后由 mybatis 框架执行 sql 并将结果映射为 java 对象并返回。

  采用 **ORM** 思想解决了实体和数据库映射的问题，对 jdbc 进行了封装，屏蔽了 jdbc api 底层访问细节，使我们不用与 jdbc api 打交道，就可以完成对数据库的持久化操作。

---


## 二、传统 jdbc 回顾与问题分析

<font size="5">传统 jdbc 回顾：</font>

```java
public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            //通过驱动管理类获取数据库链接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8","root", "root");
            //定义 sql 语句 ?表示占位符
            String sql = "select * from user where username = ?";
            //获取预处理 statement
            preparedStatement = connection.prepareStatement(sql);
            //设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的参数值
            preparedStatement.setString(1, "王五");
            //向数据库发出 sql 执行查询，查询出结果集
            resultSet = preparedStatement.executeQuery();
            //遍历查询结果集
            while(resultSet.next()){
                System.out.println(resultSet.getString("id")+""+resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

```

---


<font size="5">jdbc 问题分析：</font>
1. 数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能，如果使用数据库链接池可解决此问题。
1. Sql 语句在代码中硬编码，造成代码不易维护，实际应用 sql 变化的可能较大，sql 变动需要改变 java代码。
1. 使用 preparedStatement 向占有位符号传参数存在硬编码，因为 sql 语句的 where 条件不一定，可能多也可能少，修改 sql 还要修改代码，系统不易维护。
1. 对结果集解析存在硬编码（查询列名），sql 变化导致解析代码变化，系统不易维护，如果能将数据库记录封装成 pojo 对象解析比较方便。
---


## 三、Mybaits 入门案例

**案例工程目录结构：** <img src="https://img-blog.csdnimg.cn/20210417210621113.png#pic_left" alt="在这里插入图片描述" width="400"/>

### 3.1 环境搭建

**步骤：**
1. 创建数据库
1. 创建maven工程并导入坐标
1. 创建实体类和Dao的接口
1. 创建mybatis的主配置文件 SqlMapConfig.xml
1. 创建映射配置文件 IUerDao.xml
---


<font size="5" id="step1">1、创建数据库：</font>

首先在 mysql 中创建数据库，这里我们命名为 mybatisdb。创建好数据库之后，接下来我们创建 user 表，并添加如下图中的 5 条属性，注意设置 id 自增。

```sql

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
CREATE DATABASE IF NOT EXISTS mybatisdb;
USE mybatisdb;
DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  birthday datetime DEFAULT NULL,
  sex varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  address varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO user VALUES (1, 'admin1', date_format('20220901', '%Y%m%d'),'m','address1');
INSERT INTO user VALUES (2, 'admin2', date_format('20220902', '%Y%m%d'),'m','address2');
INSERT INTO user VALUES (3, 'admin3', date_format('20220904', '%Y%m%d'),'f','address3');
INSERT INTO user VALUES (4, 'admin4', date_format('20220905', '%Y%m%d'),'f','address4');

SET FOREIGN_KEY_CHECKS = 1;
```

 <img src="https://img-blog.csdnimg.cn/20210417213810931.png?#pic_left" alt="在这里插入图片描述" width="600"/>

---


<font size="5" id="step1">2、创建maven工程并导入坐标：</font>

Idea 中点击 File → New → Project

 <img src="https://img-blog.csdnimg.cn/20210413151051491.png?#pic_left" alt="在这里插入图片描述" width="450"/> 

在下面界面点击 Maven → Next（当然也可以勾选 Create from archetype， 然后选中方式2中的框中的选项）

**方式1：** <img src="https://img-blog.csdnimg.cn/20210413151322359.png?#pic_left" alt="在这里插入图片描述" width="500"/> 

**方式2：** <img src="https://img-blog.csdnimg.cn/20210414145234640.png?#pic_left" alt="在这里插入图片描述" width="500"/>

**当然选择方式2构建 maven 工程不会有 test 目录，用于日常学习和测试建议选择方式1**。

在以下界面输入项目名称，以及选择项目位置，完成创建。   groupId 一般分为多个段，这里我是两段，第一段为域，第二段为公司名称。域又分为 org、com、cn 等等许多，其中 org 为非营利组织，com 为商业组织。举个 apache 公司的 tomcat 项目例子：groupId 是 org.apache，它的域是org（因为 tomcat 是非营利项目），公司名称是 apache，artigactId 是 tomcat。此外，artifactId 和 项目名称是同一个。

借鉴博客：https://blog.csdn.net/qq_30137611/article/details/76762070</a> <img src="https://img-blog.csdnimg.cn/20210413151929311.png?#pic_left" alt="在这里插入图片描述" width="550"/>

---


POM文件导入坐标：

```xml
<dependencies>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.4.5</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.6</version>
    </dependency>
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.12</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13</version>
    </dependency>
</dependencies>

```

---


<font size="5">3、创建实体类和Dao的接口</font>

实体类：

```java
package com.itheima.domain;
import java.util.Date;

/**
 * 用户的实体类
 */
public class User {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

```

Dao的接口：

```java
package com.itheima.dao;
import com.itheima.domain.User;
import java.util.List;

/**
 * 用户的持久层  接口
 */
public interface IUserDao {
    /**
     * 查询操作
     */
    List<User> findAll();
}

```

---

<font size="5">4、创建 mybatis 的主配置文件 SqlMapConfig.xml</font>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<!--mybatis主配置文件-->
<configuration>
    <!--配置环境-->
    <environments default="mysql">
        <!--配置mysql的环境-->
        <environment id="mysql">
            <!--配置事务的类型-->
            <transactionManager type="JDBC"/>
            <!--配置数据源（连接池）-->
            <dataSource type="POOLED">
                <!--配置连接数据库的4个基本信息-->
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatisdb"/>
                <property name="username" value="root"/>
                <property name="password" value="000000"/>
            </dataSource>
        </environment>
    </environments>

    <!--指定映射配置文件的位置， 映射配置文件指的是每个dao独立的配置文件-->
    <mappers>
        <mapper resource="com/itheima/dao/IUserDao.xml"/>
    </mappers>
</configuration>

```

---

<font size="5">5、创建映射配置文件 IUserDao.xml</font>

**要求：**
1. <font color="red">创建位置：必须和持久层接口在相同的包中。</font>（idea中是在resources文件夹下创建相同的目录结构）

2. <font color="red">**名称：必须以持久层接口名称命名文件名，扩展名是.xml**</font> 

   <img src="https://img-blog.csdnimg.cn/20210415105111933.png?#pic_left" alt="在这里插入图片描述" width="400"/>
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.itheima.dao.IUserDao">
    <!--配置查询所有-->
    <select id="findAll" resultType="com.itheima.domain.User">
        select *from user;
    </select>
</mapper>

```

---


### 3.2 入门案例编写

```java
package package01.test;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MybatisTest {
    /**
     * 入门案例
     */
    public static void main(String[] args) throws Exception {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("com/SqlMapConfig.xml");
//        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");

        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);

        //3.使用工厂生产SqlSession对象
        SqlSession sqlSession = factory.openSession();

        //4.使用SqlSession创建Dao接口的代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();
        for (User user:users) {
            System.out.println(user);
        }

        //6.释放资源
        sqlSession.close();
        in.close();
    }
}


```

控制台打印结果： <img src="https://img-blog.csdnimg.cn/20210415110143921.png?#pic_left" alt="在这里插入图片描述" width="850"/>

---


## 四、注意事项
1. IUserDao 和 IUserMapper 一样的。 

2. 在idea中创建目录的时候，包在创建的过程中xx.xx.xxx具有层级结构，**resources 目录在创建的时候，不具有层级结构。** 

3. mybatis的映射配置文件位置 <font color="red">**必须**</font> 和 dao 接口的包结构相同 

4.  **映射配置文件 IUerDao.xml 的 mapper 标签 namespace 属性的取值必须是 dao 接口的全限定类名** 

5.   **映射配置文件 IUerDao.xml 的操作配置(select)， id属性的取值必须是dao接口的方法名** 

    ==<font color="red">遵从 3、4、5 之后， 在开发中就无需再写dao的实现类</font>==
---


  本文借鉴了黑马教程的课堂笔记，后续将会陆续更新 mybatis 底层原理的讲解，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **https://blog.csdn.net/weixin_43819566/article/details/115666342