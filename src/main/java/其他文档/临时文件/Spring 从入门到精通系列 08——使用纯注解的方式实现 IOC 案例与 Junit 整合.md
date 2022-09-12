# Spring 从入门到精通系列 08——使用纯注解的方式实现 IOC 案例与 Junit 整合
本文针对Spring使用纯注解开发，并对 Spring 整合 Junit 做了一定的分析。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
一、 Spring中的新注解
1.1 Configuration
1.2 ComponentScan
1.3 Bean
1.4 Import
1.5 PropertySource
二、Spring 整合 Junit （了解）
2.1 问题分析
2.2 Spring 整合 junit配置

---


## 一、 Spring中的新注解

### 1.1 Configuration
1.  **作用：** 指定当前类是一个配置类 <li> **细节：** 当配置类作为 **AnnotationConfigApplicationContext** 对象创建的参数时，该注解可以不写。 <pre><code class="prism language-java">//SpringConfiguration是配置类的类名
ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
</code></pre> </li>
---


### 1.2 ComponentScan
1. **作用：** 用于通过注解指定spring在创建容器时要扫描的包<li>**属性：** 
  <ul><li>value：它和 basePackage的作用是一样的，都是用于指定创建容器是要扫描的包。我们使用此注解就等同于在xml中配置：<pre><code class="prism language-java"><context:component-scan base-package="com.itheima"></context:component-scan>
</code></pre> </li></ul> </li>
---


### 1.3 Bean
1. **作用：** 用于把当前方法的返回值作为bean对象存入spring的ioc容器中。<li>**属性：** 
  1. name：用于指定bean的id。当不写时，默认值时当前方法的名称。 </li>1. **细节：** 当我们使用注解配置方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象 查找的方式和 Autowired注解的作用是一样的
---


通过以上三个注解，我们就可实现完全脱离 bean.xml 的方式。新建配置类，代码如下：

```java
@Configuration
@ComponentScan(basePackages = "com.itheima")
public class SpringConfiguration {

    @Bean(name = "runner")
    public QueryRunner createQueryRunner(DataSource dataSource){
        return new QueryRunner(dataSource);
    }

    @Bean(name="dataSource")
    public DataSource createDataSource1(){
        try{
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass("com.mysql.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/springdb");
            ds.setUser("root");
            ds.setPassword("000000");
            return ds;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

```

测试方法：

```java
@Test
public void testQueryRunner(){
    //获取容器
    ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    //获取service层对象
    IAccountService accountService = (IAccountService) ac.getBean("accountService");
    //执行查询方法
    Account account = accountService.findAccountById(1);
    System.out.println(account);
}

```

测试结果： <img src="https://img-blog.csdnimg.cn/20210602004652235.png#pic_left" alt="在这里插入图片描述" width="500"/>

---


### 1.4 Import
1.  **作用：** 用于导入其他配置类 <li> **属性：用于指定其他配置类下的字节码。当我们使用import注解之后，有import注解的类就是父配置类，而导入的就是子配置类。** 在SpringConfiguration.java下 <pre><code class="prism language-java">@Configuration
@ComponentScan(basePackages = "com.itheima")
@Import(JdbcConfig.class)
public class SpringConfiguration {...}
</code></pre> 在新创建的类，用于存放配置信息jdbcConfig.java下 <pre><code class="prism language-java">@Configuration //可加可不加
public class JdbcConfig {...配置信息...}
</code></pre> 在test类下创建容器 <pre><code class="prism language-java">ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
</code></pre> </li>
---


### 1.5 PropertySource
1.  **作用：** 用于指定 properties 文件的位置。 <li> **属性：** 
  1. value:指定文件的名称和路径 关键字：classpath，表示类路径下 （这里我们举例，jdbcConfig.properties 在 resources 文件夹下） **jdbcConfig.properties:** <pre><code class="prism language-java">jdbc2.driver=com.mysql.jdbc.Driver
jdbc2.url=jdbc:mysql://localhost:3306/springdb
jdbc2.username=root
jdbc2.password=000000
</code></pre> **父配置类：** <pre><code class="prism language-java">@Configuration
@ComponentScan(basePackages = "com.itheima")
@Import(JdbcConfig.class)
@PropertySource("classpath:jdbcConfig.properties")
public class SpringConfiguration {
}
</code></pre> **子配置类：** <pre><code class="prism language-java">/**
 * 用于与数据库做交互的配置类
 */
@Configuration
public class JdbcConfig {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    /**
     * 用于创建一个QueryRunner对象
     * @param dataSource
     * @return
     */
    @Bean(name = "runner")
    @Scope("prototype")
    public QueryRunner createQueryRunner(DataSource dataSource){
        return new QueryRunner(dataSource);
    }

    /**
     * 创建数据源对象
     * @return
     */
    @Bean(name="dataSource")
    public DataSource createDataSource1(){
        try{
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
</code></pre> </li><li> **细节：** 当配置类需要配置多个数据库的时候，有以下两种解决方案。 
  <ol><li> QueryRunner的参数可以根据变量名称，如：ds2来指定注入的数据库配置源信息 <pre><code class="prism language-java">@Bean(name = "runner")
@Scope("prototype")
public QueryRunner createQueryRunner(DataSource ds2){ //ds2表示指定创建 @Bean(name="ds2")的数据库源对象
    return new QueryRunner(ds2);
}
</code></pre> </li><li> 用Qualifier注解进行单独配置：**他在给类成员注入时不能单独（使用需和Autowired配合），但是在给方法参数注入时可以**。 <pre><code class="prism language-java">@Bean(name = "runner")
@Scope("prototype")
public QueryRunner createQueryRunner(@Qualifier("ds2") DataSource dataSource){
    return new QueryRunner(dataSource);
}
</code></pre> <pre><code class="prism language-java">@Bean(name="ds2")
   public DataSource createDataSource2(){
       try{
           ComboPooledDataSource ds = new ComboPooledDataSource();
           ds.setDriverClass(driver);
           ds.setJdbcUrl("jdbc:mysql://localhost:3306/springdb2(test)");
           ds.setUser(username);
           ds.setPassword(password);
           return ds;
       }catch (Exception e){
           throw new RuntimeException(e);
       }
   }
</code></pre> </li>
---


## 二、Spring 整合 Junit （了解）

Spring 整合 Junit，只在测试工程师方面起到作用，更加关注功能的实现，而不会关注是否创建 ioc 容器。

### 2.1 问题分析

**应用程序的入口** : main方法

**junit单元测试中，没有main方法也能执行**
1. junit集成了main方法1. 该方法就会判断当前测试类中哪些方法有 @Test注解1. junit就让有Test注解的方法执行
**junit不会管我们是否采用spring框架**
1. 在执行测试方法时，junit根本不知道我们是不是使用了spring框架1. 所以也就不会为我们读取配置文件/配置类创建spring核心容器
**由以上三点可知** ：当测试方法时，没有ioc容器，就算写了Autowired注解，也无法实现注入。那么我们需要解决的是，将原本不能加载 main 方法换掉，换成能加载的，从而实现创建容器。

---


### 2.2 Spring 整合 junit配置
<li> **导入spring整合junit的jar（坐标）** <pre><code class="prism language-java"><dependency>
	<groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.0.2.RELEASE</version>
</dependency>
</code></pre> </li>1.  **使用junit提供的一个注解把原有的main方法替换了，替换成spring提供的 @Runwith** <li> **告知Spring的运行器，spring和ioc创建是基于xml还是注解的，并且说明位置 @ContextConfiguration** 
  <ol>1. localtions: 指定xml文件的位置，加上classpath关键字，表示在类路径下1. classes: 指定注解所在位置
**当我们使用spring 5.x版本的时候，要求junit的jar必须是4.1.2及以上**

如：基于注解配置

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest {

   @Autowired
   private IAccountService as;

   @Test
   public void testFindAll(){ //测试查询所有方法
       List<Account> accounts = as.findAllAccount();
       for(Account account:accounts){
           System.out.println(account);
       }
   }
}

```

如：基于XML配置

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {

   @Autowired
   private IAccountService as = null;

   @Test
   public void testFindAll(){
       List<Account> accounts = as.findAllAccount();
       for(Account account:accounts){
           System.out.println(account);
       }
   }
}

```

---

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117457592