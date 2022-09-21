# Mybatis从入门到精通系列 11——Mybatis 延迟加载与立即加载
  在实际开发中很多时候我们并不需要总是加载用户信息时就一定要加载他的账户信息，此时就是我们所说的延迟加载。本文针对 Mybatis 延迟加载策略进行详细讲解。 <img src="https://img-blog.csdnimg.cn/20210508215926499.png" alt="在这里插入图片描述"/>

---


 # 文章目录
一、延迟加载与立即加载概述
二、延迟加载配置
三、使用 assocation 实现延迟加载
3.1 账户的持久层 DAO 接口
3.2 账户的持久层映射文件
3.3 用户的持久层接口和映射文件
3.4 开启 Mybatis 的延迟加载策略
3.5 编写测试只查账户信息不查用户信息
3.6 对于 aggressiveLazyLoading 的使用
四、使用 Collection 实现延迟加载
4.1 编写用户和账户持久层接口的方法
4.2 编写用户持久层映射配置
4.3 编写账户持久层映射配置
4.4 测试只加载用户信息

---


## 一、延迟加载与立即加载概述

**问题提出**：
 1、在一对多中，当我们有一个用户，它有100个账户。 

```
   在查询用户的时候，要不要把关联的账户查出来? 

   在查询账户的时候，要不要把关联的用户查出来? 
```

2、在查询用户时，用户下的账户信息是，什么时候使用，什么时候查询。 

3、在查询账户时，账户的所属用户信息，应该是随着账户查询是一起查询出来。 

---


**延迟加载与立即加载：**
1.  **延迟加载：** 在真正使用数据时才发起查询，不用的时候不查询。**按需加载。** 
1.  **立即查询：** 不管用不用，只要调用一个方法，马上发起查询。 
1.  **立即加载与延迟加载使用的情况：** 在对应的四种表关系中:一对多，多对一，一对一，多对多 

  <font color="red">一对多，多对多：通常情况下采用延迟加载。 
     </font> 

 <font color="red">多对一，一对一：通常情况下采用立即加载。</font>

---


## 二、延迟加载配置

|设置参数|描述|有效值|默认值|
| :-----| :-----| :-----| :-----|
|==lazyLoadingEnabled==|延迟加载的全局开关。当开启时，所有关联对象都会延迟加载，特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态。|true|false|
|==aggressiveLazyLoading==|当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载(参考lazyLoadTriggerMethods )|true|false|


针对参数 aggressiveLazyLoading 的作用说明：

**true：**   只要调用任意具有懒加载特性的对象的任意一个属性将完整加载整个对象，即触发级联效果。

**false：**   只加载调用的属性，不调用的属性不加载。

  后文案例会有相应的描述。

---

**工程目录：**

  <img src="https://img-blog.csdnimg.cn/20210509212501822.png?#pic_left" alt="在这里插入图片描述" width="280"/>

---


## 三、使用 assocation 实现延迟加载

**需求：** 查询账户信息同时查询用户信息。

**注意：** 账户对于用户实际上是一对一，采用立即加载即可。但为了方便比对 assocation 与 Collection 两种延迟加载方式，因此对于查询账户同时查询用户也采用了延迟加载。

---


### 3.1 账户的持久层 DAO 接口

```java
public interface IAccountDao {
    /**
     * 查询所有账户
     */
    List<Account> findAll();
}

```

---


### 3.2 账户的持久层映射文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IAccountDao">

    <!-- 定义封装account和user的resultMap -->
    <resultMap id="accountUserMap" type="account">
        <id property="id" column="id"/>
        <result property="uid" column="uid"/>
        <result property="money" column="money"/>
        <!-- 一对一的关系映射，配置封装user的内容 -->
        <association property="user" column="uid" javaType="user" select="com.itheima.dao.IUserDao.findById"/>
    </resultMap>

    <!--配置查询所有-->
    <select id="findAll" resultMap="accountUserMap">
       select * from account
    </select>
    
</mapper>

```

**select属性：** 查询用户的唯一标志

**column属性：** 用户根据id查询时，所需要的参数的值

---


### 3.3 用户的持久层接口和映射文件

持久层接口：

```java
public interface IUserDao {

    /**
     * 根据 id 查询用户信息
     */
    User findById(Integer userId);
}

```

映射文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IUserDao">

	<!--根据id查询用户-->
    <select id="findById" parameterType="INT" resultType="user">
        select * from user where id = #{id}
    </select>

</mapper>

```

---


### 3.4 开启 Mybatis 的延迟加载策略

在 SqlMapConfig 中加入以下配置信息：

```xml
<settings>
	<setting name="lazyLoadingEnabled" value="true"/>
	<setting name="aggressiveLazyLoading" value="false"/>
</settings>

```

---


### 3.5 编写测试只查账户信息不查用户信息

```java
public class AccountTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IAccountDao accountDao;

    @Before//单元测试之前执行
    public void init() throws Exception{
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象
        accountDao = sqlSession.getMapper(IAccountDao.class);
    }

    @After//单元测试之后执行
    public void destroy() throws Exception {
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        in.close();
    }
    
    @Test
    public void testFindAll() {
        //执行查询所有方法
        List<Account> accounts = accountDao.findAll();
    }
}

```

测试结果： 

<img src="https://img-blog.csdnimg.cn/20210508232545750.png?#pic_left" alt="在这里插入图片描述" width="700"/>   

结果表明将 Account 对象查询出来放入 List 集合中，并没有涉及到 User 对象，所以就没有发出 SQL 语句查询账户所关联的 User 对象的查询。

---


### 3.6 对于 aggressiveLazyLoading 的使用

基于以上配置，我们在测试方法中打印查询到的 account 的属性：

**aggressiveLazyLoading 设置为 false：**

```java
<settings>
    <!--开启Mybatis支持延迟加载-->
    <setting name="lazyLoadingEnabled" value="true"></setting>
    <setting name="aggressiveLazyLoading" value="false"></setting>
</settings>

 @Test
 public void testFindAll() {
     //执行查询所有方法
     List<Account> accounts = accountDao.findAll();
     for (Account account:accounts) {
         System.out.println("---------每个账户的信息------");
         System.out.println(account.getId());
     }
 }

```

运行结果：

 <img src="https://img-blog.csdnimg.cn/20210508234356577.png?#pic_left" alt="在这里插入图片描述" width="800"/> 

可以看到只执行 account 的 sql 语句，并且只加载调用的属性并没有加载 private List users;

---


**aggressiveLazyLoading 设置为 true：只要对这个类的任意操作将完整加载整个类的所有属性即执行级联的SQL语句**

同样测试方法的运行结果： <img src="https://img-blog.csdnimg.cn/20210508234827321.png?#pic_left" alt="在这里插入图片描述" width="900"/>

---


那么我们得到以下结论：

==**true：**   只要调用任意具有懒加载特性的对象的任意一个属性将完整加载整个对象，即触发级联效果。==

==**false：**   只加载调用的属性，不调用的属性不加载。==

参考博客：https://blog.csdn.net/qq_42650817/article/details/103262158

---


## 四、使用 Collection 实现延迟加载

同样我们也可以在一对多关系配置的结点中配置延迟加载策略。 <collection> 结点中也有 select 属性，column 属性。

**需求：** 完成加载用户对象时，查询该用户所拥有的账户信息。

---


### 4.1 编写用户和账户持久层接口的方法

**用户持久层接口：**

```java
public interface IUserDao {

    /**
     * 查询所有用户,同时获取到用户下所有账户的信息
     */
    List<User> findAll();
}

```

**账户持久层接口：**

```java
public interface IAccountDao {

    /**
     * 根据用户id查询账户
     */
    List<Account> findAccountByUid(Integer uid);
}

```

### 4.2 编写用户持久层映射配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IUserDao">

    <!--定义user的resultMap-->
    <resultMap id="userAccountMap" type="user">
        <id property="id" column="id"></id>
        <result property="username" column="username"></result>
        <result property="address" column="address"></result>
        <result property="sex" column="sex"></result>
        <result property="birthday" column="birthday"></result>
        <!--配置user对象account集合的映射-->
        <collection property="accounts" ofType="account" select="com.itheima.dao.IAccountDao.findAccountByUid" column="id">
        </collection>
    </resultMap>

    <!--配置查询所有-->
    <select id="findAll" resultMap="userAccountMap">
        select * from user
    </select>

    <!--根据id查询用户-->
    <select id="findById" parameterType="INT" resultType="user">
        select * from user where id = #{id}
    </select>

</mapper>

```

**<collection> 标签 ：** 主要用于加载关联的集合对象

**select 属性 ：** 用于指定查询 account 列表的 sql 语句，所以填写的是该 sql 映射的 id

**column 属性 ：** 用于指定 select 属性的 sql 语句的参数来源，上面的参数来自于 user 的 id 列，所以就写成 id 这一个字段名了

---


### 4.3 编写账户持久层映射配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IAccountDao">
	<!-- 根据用户 id 查询账户信息 -->
    <select id="findAccountByUid" resultType="account" parameterType="int">
       select * from account where uid = #{uid}
    </select>
</mapper>

```

---


### 4.4 测试只加载用户信息

```java
public class UserTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before//单元测试之前执行
    public void init() throws Exception{
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After//单元测试之后执行
    public void destory() throws Exception {
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        in.close();
    }

    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
    }
}

```

测试结果：

 <img src="https://img-blog.csdnimg.cn/2021050922310095.png#pic_left" alt="在这里插入图片描述" width="700"/> 

从结果中可以得出并没有加载 Account 账户信息。

---


本文针对 Mybatis 中延迟加载与立即加载进行了总结归纳，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/116545277