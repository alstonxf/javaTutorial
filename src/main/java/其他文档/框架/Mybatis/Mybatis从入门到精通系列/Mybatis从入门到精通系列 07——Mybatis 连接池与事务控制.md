# Mybatis从入门到精通系列 07——Mybatis 连接池与事务控制
  本文针对 Mybatis 自身的连接池技术进行深入解析，并对 Mybatis 事务控制的底层原理进行分析。 <img src="https://img-blog.csdnimg.cn/20210423225636472.png#pic_center" alt="在这里插入图片描述"/>

---


 # 文章目录
一、Mybatis 的连接池技术
1.1 mybatis的连接池分类
1.2 Mybatis 中数据源的配置
1.3 Mybatis 中 DataSource 的存取
1.4 Mybatis 中连接的获取过程分析
二、Mybatis 的事务控制

---


## 一、Mybatis 的连接池技术

  连接池就是用于存储连接的一个容器，容器其实就是一个集合对象，该集合必须是线程安全的，不能两个线程拿到统一连接，并且该集合还必须实现队列的特性：先进先出。

---


### 1.1 mybatis的连接池分类

Mybatis 将它自己的数据源分为三类：
1. UNPOOLED 不使用连接池的数据源1. POOLED 使用连接池的数据源1. JNDI 使用 JNDI 实现的数据源
  相应地，MyBatis 内部分别定义了实现了 java.sqlx.DataSource 接口的 UnpooledDataSource，PooledDataSource 类来表示 UNPOOLED、POOLED 类型的数据源。

**在以上这三种数据源中，我们一般采用的是 POOLED 数据源，来为了更好的管理数据库连接。**

---


### 1.2 Mybatis 中数据源的配置

数据源配置就是在 Mybatis 主配置文件 SqlMapConfig.xml 文件中，具体配置如下：

```xml
<!--配置连接池-->
<dataSource type="POOLED">
    <property name="driver" value="${jdbc.driver}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</dataSource>

```

  MyBatis 在初始化时，根据的 type 属性来创建相应类型的的数据源 DataSource，相应的三类数据源的三种实例如下：
- type=”POOLED”：MyBatis 会创建 PooledDataSource 实例- type=”UNPOOLED” ： MyBatis 会创建 UnpooledDataSource 实例- type=”JNDI”：采用服务器提供的JNDI技术实现，来获取DataSource对象，不同的服务器所能拿到的DataSource对象不同。（注意：如果不是web或者maven的war工程，是不能使用的）
开发中使用的是tomcat服务器，采用连接池就是 dbcp 连接池。

---


下面我们分别通过 type 设置为 POOLED 与 UNPOOLED，对比说明其内在原理

  通过分别设置 type 设置为 POOLED 与 UNPOOLED，并测试查询得到以下结果：

<img src="https://img-blog.csdnimg.cn/2021042222533477.png#pic_center" alt="在这里插入图片描述"/>   分析上图， type 设置为 POOLED 的日志中，MyBatis 是先从 连接池中取到连接用完在归还到池中，而 UNPOOLED 每次是创建连接关闭连接。

---


### 1.3 Mybatis 中 DataSource 的存取

  在二讲中我们提过，MyBatis 是通过工厂模式来创建数据源 DataSource 对象的，那么实际上 MyBatis 定义了抽象的工厂接口：org.apache.ibatis.datasource.DataSourceFactory，通过其 getDataSource() 方法来返回数据源 DataSource，源码如下图： <img src="https://img-blog.csdnimg.cn/20210422231057529.png?#pic_left" alt="在这里插入图片描述" width="450"/>   MyBatis 创建了 DataSource 实例后，会将其放到 Configuration 对象内的 Environment 对象中， 供以后使用。

---


具体分析过程如下：

1、先进入 XMLConfigBuilder 类中，可以找到如下代码： <img src="https://img-blog.csdnimg.cn/20210422231842278.png#pic_left" alt="在这里插入图片描述" width="650"/> 2、分析 configuration 对象的 environment 属性，结果如下： <img src="https://img-blog.csdnimg.cn/20210422231937806.png#pic_left" alt="在这里插入图片描述" width="650"/>

---


### 1.4 Mybatis 中连接的获取过程分析

  **当我们需要创建 SqlSession 对象并需要执行 SQL 语句时，这时候 MyBatis 才会去调用 dataSource 对象来创建java.sql.Connection对象。 也就是说，java.sql.Connection对象的创建一直延迟到执行SQL语句的时候。**

如：

```java
@Test
public void testSql() throws Exception {
	InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
	SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
	SqlSession sqlSession = factory.openSession();
	List<User> list = sqlSession.selectList("findAll");//触发 MyBatis 在底层执行下面这个方法来创建 java.sql.Connection 对象
	System.out.println(list.size());
}

```

  只有当第 4 句 sqlSession.selectList(“findAll”)，才会触发 MyBatis 在底层执行下面这个方法来创建 java.sql.Connection 对象。

通过断点调试，在 PooledDataSource 中找到如下 popConnection()方法，如下所示：<img src="https://img-blog.csdnimg.cn/20210423074554750.png?#pic_left" alt="在这里插入图片描述" width="650"/> 得出 PooledDataSource 工作原理如下： <img src="https://img-blog.csdnimg.cn/20210423215338656.png?#pic_left" alt="在这里插入图片描述" width="650"/>

---


连接获取的源码如下： <img src="https://img-blog.csdnimg.cn/20210423215940438.png" alt="在这里插入图片描述"/>   最后可以发现，真正连接打开的时间点，只是在执行SQL语句时，才会进行。其实这样做我们也可以进一步发现，数据库连接是我们最为宝贵的资源，只有在要用到的时候，才去获取并打开连接，当我们用完了就再立即将数据库连接归还到连接池中。

---


## 二、Mybatis 的事务控制

  Mybatis 中事务的提交方式，本质上就是调用 JDBC 的 setAutoCommit() 来实现事务控制，并且 sqlSession 对象的 commit 方法和 rollback 方法实现事物的提交和回滚。

通过源码我们发现，无论进行怎样的数据库操作，最终都会进行原始的 jdbc 提交操作。 <img src="https://img-blog.csdnimg.cn/20210423221343177.png?#pic_left" alt="在这里插入图片描述" width="550"/>   通过测试添加我们发现，在创建 SqlSession 对象的时候，让其设置了事务的提交。通过下方截图我们发现，它默认设置了事务提交是 <font color="red">Setting autocommit to false on JDBC Connection</font>，言外之意没有设置自动提交。所以我们只能每次设置手动提交。 <img src="https://img-blog.csdnimg.cn/20210423223924342.png?#pic_left" alt="在这里插入图片描述" width="550"/>   通过分析我们能够发现之前的 CUD 操作过程中，我们都要手动进行事务的提交，原因是setAutoCommit() 方法在执行时它的值被设置为 false 了，所以我们在 CUD 操作中，必须通过 sqlSession.commit() 方法来执行提交操作。

  但是通过 SqlSessionFactory 源码我们发现，openSession 的重载方法中有一个方法参数类型是 boolean，由此我们可以通过调用这个重载方法设置 setAutoCommit() 事务的为自动提交。 <img src="https://img-blog.csdnimg.cn/20210423222650959.png?#pic_left" alt="在这里插入图片描述" width="550"/>

---


单元测试之前执行与之后的代码如下：

```java
@Before//单元测试之前执行
public void init() throws Exception{
    //1.读取配置文件
    in = Resources.getResourceAsStream("SqlMapConfig.xml");
    //2.获取SqlSessionFactory
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
    //3.获取SqlSession对象
    sqlSession = factory.openSession(true);//参数为true，设置为自动提交commit
    //4.获取dao的代理对象
    userDao = sqlSession.getMapper(IUserDao.class);
}

@After//单元测试之后执行
public void destory() throws Exception {
    //释放资源
    sqlSession.close();
  	in.close();
}

```

运行的结果如下：<img src="https://img-blog.csdnimg.cn/20210423224143313.png?#pic_left" alt="在这里插入图片描述" width="670"/>   我们发现，此时事务就设置为自动提交了，同样可以实现 CUD 操作时记录的保存。虽然这也是一种方式，但就编程而言，设置为自动提交方式为 false 再根据情况决定是否进行提交，这种方式更常用。因此我们可以根据业务情况来决定提交是否进行提交。

---


本文针对 Mybatis 自身的连接池技术与事务控制的原理进行分析，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~
# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/116034163