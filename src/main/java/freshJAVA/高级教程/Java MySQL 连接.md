# Java MySQL 连接

本章节我们为大家介绍 Java 如何使用 使用 JDBC 连接 MySQL 数据库。

Java 连接 MySQL 需要驱动包，最新版下载地址为：**http://dev.mysql.com/downloads/connector/j/**，解压后得到 jar 库文件，然后在对应的项目中导入该库文件。

![img](https://www.runoob.com/wp-content/uploads/2016/08/38EF259A-CC97-4001-96AD-C6648BE2A4F4.jpg)

你可以下载本站提供的 jar 包：**[mysql-connector-java-5.1.39-bin.jar](http://static.runoob.com/download/mysql-connector-java-5.1.39-bin.jar)**

本实例使用的是 Eclipse，导入 jar 包：

![img](https://www.runoob.com/wp-content/uploads/2013/12/191E2E30-DD23-41C8-A419-DFEAEAE06BF6.jpg)

> **MySQL 8.0 以上版本的数据库连接有所不同：**
>
> - 1、MySQL 8.0 以上版本驱动包版本 [mysql-connector-java-8.0.16.jar](https://static.runoob.com/download/mysql-connector-java-8.0.16.jar)。
> - 2、**com.mysql.jdbc.Driver** 更换为 **com.mysql.cj.jdbc.Driver**。
> - MySQL 8.0 以上版本不需要建立 SSL 连接的，需要显示关闭。
> - allowPublicKeyRetrieval=true 允许客户端从服务器获取公钥。
> - 最后还需要设置 CST。
>
> 加载驱动与连接数据库方式如下：
>
> ```
> Class.forName("com.mysql.cj.jdbc.Driver");
> conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","root","password");
> ```

------

## 创建测试数据

接下来我们在 MySQL 中创建 RUNOOB 数据库，并创建 websites 数据表，表结构如下：

```
CREATE TABLE `websites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL DEFAULT '' COMMENT '站点名称',
  `url` varchar(255) NOT NULL DEFAULT '',
  `alexa` int(11) NOT NULL DEFAULT '0' COMMENT 'Alexa 排名',
  `country` char(10) NOT NULL DEFAULT '' COMMENT '国家',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
```

插入一些数据：

INSERT INTO `websites` VALUES ('1', 'Google', 'https://www.google.cm/', '1', 'USA'), ('2', '淘宝', 'https://www.taobao.com/', '13', 'CN'), ('3', '菜鸟教程', 'http://www.runoob.com', '5892', ''), ('4', '微博', 'http://weibo.com/', '20', 'CN'), ('5', 'Facebook', 'https://www.facebook.com/', '3', 'USA');

数据表显示如下：

![img](https://www.runoob.com/wp-content/uploads/2013/12/mysql-data.jpg)

------

## 连接数据库

以下实例使用了 JDBC 连接 MySQL 数据库，注意一些数据如用户名，密码需要根据你的开发环境来配置：

## MySQLDemo.java 文件代码：

```
package com.runoob.test;
 
import java.sql.*;
 
public class MySQLDemo {
 
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB";
 
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
 
 
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";
 
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name, url FROM websites";
            ResultSet rs = stmt.executeQuery(sql);
        
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");
    
                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", 站点名称: " + name);
                System.out.print(", 站点 URL: " + url);
                System.out.print("\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
```

以上实例执行输出结果如下：

![img](https://www.runoob.com/wp-content/uploads/2016/08/result.jpg)