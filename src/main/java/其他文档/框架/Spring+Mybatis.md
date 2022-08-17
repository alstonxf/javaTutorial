Spring 如何整合 Mybatis 的详细讲解

康康要加油

于 2022-03-11 13:08:52 发布

1662
 收藏 5
分类专栏： Spring 文章标签： spring java jar
版权

Spring
专栏收录该内容
11 篇文章1 订阅
订阅专栏
文章目录
一、jar 包介绍
1、 Mybatis 的 jar 包：
2、 Mybatis 整合 Spring 的 jar 包：
3、 Spring 的 jar 包：
4、 数据库驱动 jar 包：
二、搭建环境
1、 创建项目
2、 添加 jar 包
3、 创建配置文件
4、 添加 log4j 配置文件
5、 创建映射配置文件与接口
6、 创建实体类
7、 创建业务层
三、配置 Spring 与 Mybatis 整合
1、 配置解析 properties 文件
1.1、 添加 db.properties 文件
1.2、 修改 Spring 配置文件
2、 配置数据源
3、 配置 SqlSessionFactoryBean
4、 配置 SqlSessionTemplate
5、 配置业务层依赖
5.1、 接口实现类
5.2、 配置文件
四、SqlSessionTemplate 对象的使用
1、 添加用户业务
1.1、 修改业务层
1.2、 创建测试类
2、 查询用户业务
2.1、 修改业务
2.2、 创建测试类
五、SqlSessionDaoSupport 的使用
1、 创建业务层
2、 修改配置文件
3、 创建测试类
六、MapperScannerConfigurer 对象的使用
1、 创建业务层
2、 创建配置文件
3、 创建测试类
一、jar 包介绍
1、 Mybatis 的 jar 包：
mybatis-3.5.5.jar
asm-7.1.jar
cglib-3.3.0.jar
commons-logging-1.2.jar
javassist-3.27.0-GA.jar
log4j-1.2.17.jar
log4j-api-2.13.3.jar
log4j-core-2.13.3.jar
ognl-3.2.14.jar
slf4j-api-1.7.30.jar
slf4j-log4j12-1.7.30.jar

2、 Mybatis 整合 Spring 的 jar 包：
mybatis-spring-2.0.4.jar

3、 Spring 的 jar 包：
spring-beans-5.2.7.RELEASE.jar
spring-context-5.2.7.RELEASE.jar
spring-core-5.2.7.RELEASE.jar
spring-expression-5.2.7.RELEASE.jar
spring-jdbc-5.2.7.RELEASE.jar
spring-tx-5.2.7.RELEASE.jar
spring-aop-5.2.7.RELEASE.jar

4、 数据库驱动 jar 包：
mysql-connector-java-5.1.48.jar

二、搭建环境
1、 创建项目


2、 添加 jar 包

3、 创建配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd">
</beans>
```


4、 添加 log4j 配置文件
log4j.rootLogger=debug,console

### appender.console输出到控制台 ###
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=<%d> %5p (%F:%L) [%t] (%c) - %m%n
log4j.appender.console.Target=System.out

### appender.logfile输出到日志文件 ###
log4j.appender.logfile=org.apache.log4j.RollingFileAppender
log4j.appender.logfile.File=SysLog.log
log4j.appender.logfile.MaxFileSize=500KB
log4j.appender.logfile.MaxBackupIndex=7
log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
log4j.appender.logfile.layout.ConversionPattern=<%d> %p (%F:%L) [%t] %c - %m%n
5、 创建映射配置文件与接口


6、 创建实体类


7、 创建业务层
public interface UsersService {
}

1
2
3
public class UsersServiceImpl implements UsersService {
}

1
2
3
三、配置 Spring 与 Mybatis 整合
1、 配置解析 properties 文件
1.1、 添加 db.properties 文件
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/bjsxt
jdbc.username=root
jdbc.userword=mysql
1
2
3
4
1.2、 修改 Spring 配置文件
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">


<!--    配置解析Properties工具类-->
    <context:property-placeholder location="db.properties"/>
</beans>
1
2
3
4
5
6
7
8
9
10
11
12
13
2、 配置数据源
<!--    配置数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
1
2
3
4
5
6
7
3、 配置 SqlSessionFactoryBean
SqlSessionFactoryBean 是初始化 Mybatis 框架的 Bean 对象。它是生产 SqlSessionFactory的一种工厂 Bean。在 Spring 整合 Mybatis 中，我们可以不需要 Mybatis 的配置文件，在该Bean 对象中可以完成对 Mybatis 框架的配置。 如果需要在 Mybatis 的配置文件中配置Mybatis 框架时，仍然可以使用 Mybatis 的配置文件，但是需要在 SqlSessionFactoryBean 对象的 configLocation 属性中指定 Mybatis 的配置文件的路径和名称。

    <!--        配置SqlSessionFactoryBean-->
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" value="dataSource"/>
        <property name="typeAliasesPackage" value="com.bjsxt.pojo"/>
        <property name="mapperLocations" value="com/bjsxt/mapper/*xml"/>
    </bean>
1
2
3
4
5
6
4、 配置 SqlSessionTemplate

<!--    配置SqlSessionTemplate-->
    <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactoryBean"/>
    </bean>

1
2
3
4
5
6
5、 配置业务层依赖
5.1、 接口实现类
public class UsersServiceImpl implements UsersService {
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
    
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}
1
2
3
4
5
6
7
8
9
10
11
5.2、 配置文件
<!--    配置业务层-->
    <bean id="usersService" class="com.bjsxt.service.impl.UsersServiceImpl">
        <property name="sqlSessionTemplate" ref="sqlSessionTemplate"/>
    </bean>
1
2
3
4
四、SqlSessionTemplate 对象的使用
1、 添加用户业务
1.1、 修改业务层
public interface UsersService {
    void addUsers(Users users);
}
1
2
3
    @Override
    public void addUsers(Users users) {
        UsersMapper usersMapper = this.sqlSessionTemplate.getMapper(UsersMapper.class);
        usersMapper.insertSelective(users);
    }
1
2
3
4
5
1.2、 创建测试类
 public class AddUsersTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UsersService usersService = (UsersService)applicationContext.getBean("usersService");
        Users users = new Users();
        users.setUsername("suibian33");
        users.setUsersex("male");
        usersService.addUsers(users);
    }
}
1
2
3
4
5
6
7
8
9
10
2、 查询用户业务
2.1、 修改业务
    List<Users> findUsersAll();
1
    //查询所有用户
    @Override
    public List<Users> findUsersAll() {
        UsersMapper usersMapper = this.sqlSessionTemplate.getMapper(UsersMapper.class);
        UsersExample usersExample = new UsersExample();
        return usersMapper.selectByExample(usersExample);
    }
1
2
3
4
5
6
7
2.2、 创建测试类
public class FindUsersAllTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UsersService usersService = (UsersService)applicationContext.getBean("usersService");
        List<Users> list = usersService.findUsersAll();
        list.forEach(System.out::println);
    }
}

1
2
3
4
5
6
7
8
9
五、SqlSessionDaoSupport 的使用
1、 创建业务层
public class UsersServiceImpl2 extends SqlSessionDaoSupport implements UsersService {
    @Override
    public void addUsers(Users users) {

    }
    
    @Override
    public List<Users> findUsersAll() {
        UsersMapper usersMapper = this.getSqlSessionTemplate().getMapper(UsersMapper.class);
        UsersExample usersExample = new UsersExample();
        return usersMapper.selectByExample(usersExample);
    }
}
1
2
3
4
5
6
7
8
9
10
11
12
13
2、 修改配置文件
    <bean id="usersService2" class="com.bjsxt.service.impl.UsersServiceImpl2">
        <property name="sqlSessionFactory" ref="sqlSessionFactoryBean"/>
    </bean>
1
2
3
3、 创建测试类
public class FindUsersAll2Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UsersService usersService = (UsersService)applicationContext.getBean("usersService");
        List<Users> list = usersService.findUsersAll();
        list.forEach(System.out::println);
    }
}

1
2
3
4
5
6
7
8
9
六、MapperScannerConfigurer 对象的使用
用于以自动扫描形式来配置 MyBatis 中映射器对象，可以通过配置包路径来自动扫描包接口生成映射器对象。

1、 创建业务层
public class UsersServiceImpl3 implements UsersService {
    private UsersMapper usersMapper;

    public UsersMapper getUsersMapper() {
        return usersMapper;
    }
    
    public void setUsersMapper(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }
    
    @Override
    public void addUsers(Users users) {
    
    }
    
    @Override
    public List<Users> findUsersAll() {
        UsersExample usersExample = new UsersExample();
        return this.usersMapper.selectByExample(usersExample);
    }
}


1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
2、 创建配置文件
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">


    <!--    配置解析Properties工具类-->
    <context:property-placeholder location="db.properties"/>


    <!--    配置数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>


    <!--        配置SqlSessionFactoryBean-->
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="typeAliasesPackage" value="com.bjsxt.pojo"/>
<!--        如果接口与映射配置文件在同一个包中，那么mapperLocations属性则不需要配置-->
<!--        <property name="mapperLocations" value="com/bjsxt/mapper/*.xml"/>-->
    </bean>


<!--    配置MapperScannerConfigurer-->
    <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.bjsxt.mapper"/>
    </bean>

    <bean id="usersService3" class="com.bjsxt.service.impl.UsersServiceImpl3">
        <property name="usersMapper" ref="usersMapper"/>
    </bean>
</beans>
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
3、 创建测试类
public class MapperScannerConfigurerTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext2.xml");
        UsersService usersService = (UsersService)applicationContext.getBean("usersService3");
        List<Users> list = usersService.findUsersAll();
        list.forEach(System.out::println);
    }
}

1
2
3
4
5
6
7
8
9

————————————————
版权声明：本文为CSDN博主「康康要加油」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/kangkang12221222/article/details/123410076