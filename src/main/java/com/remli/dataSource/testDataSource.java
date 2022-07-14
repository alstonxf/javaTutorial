package com.remli.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.util.ResourceBundle;

public class testDataSource{
    public static void main(String[] args) {

    }

    @Test
    /*测试手动创建c3p0数据源*/
    public void test1() throws Exception {
        /*创建数据源*/
        /*康波坡尔*/
        /*注意一个问题保证mysql语句的正确、Mysql 8.0.19和以前版本连接语句略微不同*/
        /*实例化对象*/
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        /*对象调用方法——并set方法赋值*/
        /*赋值驱动类数据库连接信息*/
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/remli?useSSL=false&serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("lixiaofeng");

        /*创建对象获取连接信息*/
        Connection connection = dataSource.getConnection();

        /*打印对象信息*/
        System.out.println("\n地址："+connection);

        /*连接关闭、归还资源*/
        connection.close();
    }

    //4、手动创建Druid数据源：一定要注意空格、注意名称正确
    @Test
    /*测试手动创建druid数据源*/
    /*如果找不到DruidDataSource请在maven里清理clean里面的jar包*/
    public void test2() throws Exception {
        /*创建数据源*/
        /*注意一个问题保证mysql语句的正确、Mysql 8.0.19和以前版本连接语句略微不同*/
        /*实例化对象*/
        DruidDataSource dataSource = new DruidDataSource();

        /*对象调用方法——并set方法赋值*/
        /*赋值驱动类数据库连接信息*/
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");/*存在耦合*/
        dataSource.setUrl("jdbc:mysql://localhost:3306/remli?useSSL=false&serverTimezone=UTC");/*存在耦合*/
        dataSource.setUsername("root");/*存在耦合*/
        dataSource.setPassword("lixiaofeng");/*存在耦合*/

        /*创建对象获取连接信息*/
        DruidPooledConnection connection = dataSource.getConnection();

        /*打印对象信息*/
        System.out.println("地址4："+connection);

        /*连接关闭、归还资源*/
        connection.close();
    }

    //    5、手建C3P0(加载properties配置文件)：注意空格、名称正确
//    1.创建代码：
    @Test
    /*测试手动创建c3p0数据源(加载properties配置文件)*/
    public void test3() throws Exception {

        /*读取配置文件*/
        ResourceBundle rb = ResourceBundle.getBundle("jdbc");
        String driver = rb.getString("jdbc.driver");
        String url = rb.getString("jdbc.url");
        String username = rb.getString("jdbc.username");
        String password = rb.getString("jdbc.password");

        /*创建数据源对象、设置连接参数*/
        /*康波坡尔*/
        /*注意一个问题保证mysql语句的正确、Mysql 8.0.19和以前版本连接语句略微不同*/
        /*实例化对象*/
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        /*对象调用方法——并set方法赋值*/
        /*赋值驱动类数据库连接信息*/
        /*注意值不能加双引号*/
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        /*创建对象获取连接信息*/
        Connection connection = dataSource.getConnection();

        /*打印对象信息*/
        System.out.println("\n地址5："+connection);

        /*连接关闭、归还资源*/
        connection.close();

    }

}
