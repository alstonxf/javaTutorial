spring
一、spring文件及注解
1、spring配置主要全部文件
pom.xml
applicationContext.xml
Main.java
UserDao.java
UserDaoImpl.java
2、spring配置主要全部注解
3、Spring重点基础知识
1.名词解释
2.Bean生命周期：1实例化、2属性赋值、3初始化、4使用、5销毁
3.Bean 标签属性
4.Spring核心：
4、bean实例化：无参、工厂静态、工厂实例
1.无参构造方法实例化：默认
2.工厂静态方法实例化：直接调用方法
3.工厂实例方法实例化：先有工厂对象再调方法
5、依赖注入DI：注入类型：对象、普通数据、集合数据
1.构造方法注入：有参构造
2.set方法注入：
3.p命名空间注入：(本质set)
1引入p命名空间：
2修改注入方式：
6、依赖注入类型：引用数据(对象)、普通数据、集合数据
1.普通数据
2.引用数据
3.集合数据
三、引入其他配置文件（分模块开发）
1、< import >标签
四、Spring的重点配置
1、< property >标签：属性注入、setter()
1. name属性：属性名称
1. value属性：注入的普通属性值
1. ref属性：注入的对象引用值
1. < set >标签：集合——引用ref
1. < list >标签：集合——引用ref
1. < map >标签：集合——键值对
1. < properties >标签：与map类似——键值对
2、< constructor-arg >标签：指定构造函数的参数注入
五、SpringAPI
1、ApplicationContext的继承
2、ApplicationContext的实现类
1.ClassPathXmlApplicationContext：类 路径 文件类型 应用 上下文——resource下文件(相对)
2.FileSystemXmlApplicationContext：文件 系统 文件类型 应用 上下文——磁盘绝对路径
3.AnnotationConfigApplicationContext：注解 配置 应用 上下文——注解开发
3、getBean()方法的使用
1.强转类型
2.直接获取类对象：类文件直接指定
3.通过java对象获取
六、配置数据源
1 、数据源（连接池）的作用
1.提高程序性能
2.事先实例化数据源、初始化部分连接资
3.使用连接资源时从数据源中获取
4.使用完毕后将连接资源归还给数据源
2 、引入相关坐标依赖
3、手动创建C3P0数据源：一定要注意空格、注意名称正确
4、手动创建Druid数据源：一定要注意空格、注意名称正确
5、手建C3P0(加载properties配置文件)：注意空格、名称正确
1.创建代码：
2.配置文件jdbc.properties：注意语句正确、空格、名称
七、Spring配置数据源：DataSource——.set方法注入
1、application配置文件：配置连接信息——com.mchange.v2.c3p0.ComboPooledDataSource
1.注意一个问题：&会报错——写成&
2、代替ComboPooledDataSource——>ClassPathXmlApplicationContext
八、Spring单独配置数据源：xml加载properties文件——分开数据库与Spring
1、spring加载properties文件步骤：
1.引入context命名空间和约束路径：
1命名空间：xmlns:context="http://www.springframework.org/schema/context"
2约束路径：http://www.springframework.org/schema/context
2.加载配置文件标签：< context:property-placeholder location="xx.properties"/ >
3.< property name="" value="${key}"/ >：spr语言——EL
4.DataSource.class
5.DruidDataSource.class
九、Spring注解开发
1、 Spring原始注解
@Component——id——实例化——随便那个——无语义
@Controller——Web层控制类实例化——语义化
@Service——Service层服务类实例化——语义化
@Repository——id——Dao层数据存储类实例化——语义化
@Autowired——无需Setter——字段按类型注入——可单独使用
@Qualifier——name/id——@Autowired+按名称注入——必须配合使用
@Resource——按名称注入＝@Autowired+@Qualifier——J2EE提供
@Value——注入普通属性
@Scope——Bean作用范围
@PostConstruct——Bean初始化方法——构造后
@PreDestroy——Bean销毁方法——销毁前
@Order——注入对象引用集合
2、组件扫描：component-scan
3、2种配置/注解方式对比：
1.原始xml配置方式
2.注解@方式！！！
2、Spring新注解
@Configuration——指定类为Spring配置类
@ComponentScan——指定Spring初始化容器扫描包
@Bean——用在方法上、方法返回值存储到Spring容器中
@PropertySource——加载properties文件配置
@Import——导入其他类配置
3、Spring对象创建——实例化——找到类创建对象——所在位置
@Component——随便
@Controller——web视图控制层
@Service——Service业务服务层
@Repository——Dao数据持久化层
4、Spring注入
1.对象注入：依据、bean(id)＝name、类、属性注入
@Resource：默认按名称byName注入——(J2EE提供注解)——需添加其依赖
1name: Spring 将 name 的属性值解析为 bean 的名称， 使用 byName 的自动注入策略
2type: Spring 将 type的属性值解析为 bean 的类型，使用 byType 的自动注入策略
3反射—— byName 自动注入策略
@Autowried：默认按类型byType注入
@Qualifier：默认类名
2.普通属性注入：——Value
————————————————

# 一、spring文件及注解

## 1、spring配置主要全部文件

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210720105344219.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzI1NDgyMzc1,size_16,color_FFFFFF,t_70)

pom.xml

```
 	 <dependencies>
        <!--————————————————添加Spring依赖支持————————————————-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.3.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.3.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>5.3.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>5.3.7</version>
        </dependency>
        <!--————————————————添加Spring测试支持————————————————-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.3.7</version>
        </dependency>
        <!--————————————————添加Spring-MVC视图表现层支持————————————————-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.7</version>
        </dependency>
        <!--————————————————添加Spring 数据库事务管理支持————————————————-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.3.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.3.7</version>
        </dependency>
        <!--————————————————添加Spring-aop面向切面编程支持————————————————-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.3.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>5.3.7</version>
        </dependency>
    </dependencies>
```

applicationContext.xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
		http://www.springframework.org/schema/mvc
		http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd
		http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context-3.2.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop-3.2.xsd
		http://www.springframework.org/schema/tx
		http://www.springframework.org/schema/tx/spring-tx-3.2.xsd ">


    <bean id="userDao"  class="com.remli.dao.impl.UserDaoImpl"></bean>


</beans>
————————————————
版权声明：本文为CSDN博主「REMLILI」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_25482375/article/details/118926636