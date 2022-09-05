# Mybatis从入门到精通系列 16——Spring 整合 Mybatis 完整案例演示
本文我们针对 Spring 整合 Mybatis 进行详细分析，并演示完整案例。 <img src="https://img-blog.csdnimg.cn/20210527134146792.png" alt="在这里插入图片描述"/>

---


 # 文章目录
1. 工程目录
2. 导入 POM 坐标
3. 顾客的实体类和映射接口
4. 顾客类的映射文件
5. Mybatis 配置文件
6. Spring 配置文件
7. 测试方法

---


<font size="5">需求：根据 ID 查询顾客信息</font>

---


#### 1. 工程目录

<img src="https://img-blog.csdnimg.cn/20210527134442588.png#pic_left" alt="在这里插入图片描述" width="350"/>

---


#### 2. 导入 POM 坐标

```xml
<properties>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <spring.version>5.0.2.RELEASE</spring.version>
    <slf4j.version>1.6.6</slf4j.version>
    <log4j.version>1.2.12</log4j.version>
    <mysql.version>5.1.6</mysql.version>
    <mybatis.version>3.4.5</mybatis.version>
</properties>

<dependencies>
    <!-- spring -->
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.6.8</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aop</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-tx</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
        <scope>provided</scope>
    </dependency>
    <!-- log start -->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>${log4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <!-- log end -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>${mybatis.version}</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>1.3.0</version>
    </dependency>
    <dependency>
        <groupId>c3p0</groupId>
        <artifactId>c3p0</artifactId>
        <version>0.9.1.2</version>
        <type>jar</type>
        <scope>compile</scope>
    </dependency>
</dependencies>

```

---


#### 3. 顾客的实体类和映射接口

```java
public class Customer implements Serializable {
    private Integer id;
    private String username;
    private String jobs;
    private String phone;

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

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", jobs='" + jobs + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

```

---


```java
@Repository
public interface CustomerDao {

    /**
     * 根据 id 查询 Customer
     */
    Customer findById(Integer id);
}

```

---


#### 4. 顾客类的映射文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.CustomerDao">

    <!--配置查询所有-->
    <select id="findById" resultType="Customer">
        select * from t_customer where id = #{id}
    </select>

</mapper>

```

---


#### 5. Mybatis 配置文件

  与之前的 mybatis 注配置文件不同，之前实在mybatis-config.xml 主配置文件中配置数据库连接的，现在要把这些放在spring的配置文件中，所以mybatis配置文件中只写类的别名和引用的Mapper。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--使用typeAliases配置别名，他只能配置domain中类的别名-->
    <typeAliases>
        <package name="com.itheima.po" />
    </typeAliases>


    <!--配置映射文件的信息-->
    <mappers>
        <package name="com.itheima.dao" />
    </mappers>
</configuration>

```

---


#### 6. Spring 配置文件

  在 Spring 配置文件 applicationContext.xml中，我们要配置数据库连接池，sqlSessionFactory 对象，以及 CustomerDao 接口的所在包（CustomerMapper对象）。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--开启注解的扫描，希望处理service和dao-->
    <context:component-scan base-package="com.itheima" />


    <!--spring区整合mybatis框架-->
    <!--配置连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.jdbc.Driver" />
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/ssm" />
        <property name="user" value="root" />
        <property name="password" value="000000" />
    </bean>

    <!--配置sqlSessionFactory工厂-->
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <property name="configLocation" value="mybatis-config.xml"/>
    </bean>

    <!--配置CustomerDao的接口所在包-->
    <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.itheima.dao" />
    </bean>

    <!--配置Spring框架声明式事务管理-->
    <!--配置事务管理器-->
    <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!--配置事务通知-->
    <tx:advice id="txAdvice" transaction-manager="dataSourceTransactionManager">
        <tx:attributes>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="*" isolation="DEFAULT"/>
        </tx:attributes>
    </tx:advice>

    <!--配置AOP增强-->
    <aop:config>
        <aop:advisor advice-ref="txAdvice" pointcut="execution(* com.itheima.service.impl.*ServiceImpl.*(..))"/>
    </aop:config>

</beans>


```

---


#### 7. 测试方法

```java
public class testSpringIngrateMybatis {

    public static void main(String[] args) {
        //加载配置文件
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //获取对象
        CustomerService customerService = (CustomerService) ac.getBean("customerService");
        //执行方法
        Customer customer = customerService.findById(1);
        System.out.println(customer);
    }
}

```

测试结果： <img src="https://img-blog.csdnimg.cn/20210527140223693.png#pic_left" alt="在这里插入图片描述" width="700"/>

---


本文针对 Spring 整合 Mybatis 演示完整案例，总结归纳，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

参考博客：Spring整合mybatis完整示例：https://blog.csdn.net/qq_34273888/article/details/81108752</a>
# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117326915