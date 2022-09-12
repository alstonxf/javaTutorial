# Mybatis从入门到精通系列 09——Mybatis多表查询之一对多查询
  本文针对 Mybatis 多表查询的一对一查询以及一对多查询，进行详细解析。

  本文案例主要以最为简单的用户和账户的模型来分析 Mybatis 多表关系。用户为 User 表，账户为 Account 表。一个用户（User）可以有多个账户（Account）。具体关系如下：

<img src="https://img-blog.csdnimg.cn/20210501115534413.png?#pic_center" alt="在这里插入图片描述" width="700"/>

---


 # 文章目录
一、 Mybatis 的多表查询概述
二、一对一查询
2.1 定义用户和账户信息的实体类
2.2 编写 SQL 语句
2.3 定义持久层账户的 Dao 接口
2.4 AccountDao.xml 文件中的查询配置信息
2.5 测试方法
三、一对多查询
3.1 在 User 类加入从表实体的集合引用
3.2 编写 SQL 语句
3.3 用户持久层 Dao 接口
3.4 用户持久层 Dao 映射文件
3.5 测试方法

---


## 一、 Mybatis 的多表查询概述

**表之间的关系有四种: 一对多、多对一、一对一、多对多**
- 举例：用户和订单就是一对多，订单和用户就是多对一- 特例: 如果拿出每一个订单，他都只能属于一个用户。所以Mybatis就把多对一看成了一对一。
---

**Mybatis 中的多表查询（一对多、一对一）**

示例： 用户和账户。 

  - 一个用户可以有多个账户- 一个账户只能属于一个用户（多个账户也可以属于一个用户） 
---


**步骤：**
1. **建立两张表:用户表，账户表** 让用户表和账户表之间具备一对多的关系: 需要使用外键在账户表中添加
1. **建立两个实体类:用户实体类和账户实体类** 让用户和账户的实体类能体现出一对多的关系
1. **建立两个配置文件** 用户的配置文件、账户的配置文件
1. **实现配置** 当我们查询用户时，可以同时得到用户下所包含的账户信息。 当我们查询账户时，可以同时得到账户的所属用户信息。
---


下面在 mysql 中建立 User 表和 Account 表，并在 account 建立索引，外键是否建立在本文的案例中没有强制要求。 <img src="https://img-blog.csdnimg.cn/20210501120630843.png?#pic_left" alt="在这里插入图片描述" width="700"/> <img src="https://img-blog.csdnimg.cn/20210501120752167.png?#pic_left" alt="在这里插入图片描述" width="700"/> <img src="https://img-blog.csdnimg.cn/20210501123116393.png#pic_left" alt="在这里插入图片描述" width="700"/>

---


## 二、一对一查询

**需求：** 查询所有账户信息，关联查询下单用户信息。

**注意：**   因为一个账户信息只能供某个用户使用，所以从查询账户信息出发关联查询用户信息为一对一查询。如果从用户信息出发查询用户下的账户信息则为一对多查询，因为一个用户可以有多个账户。

**工程目录：** <img src="https://img-blog.csdnimg.cn/20210507225805814.png?#pic_left" alt="在这里插入图片描述" width="300"/>

---


  实现有两种方式，分别是：定义 AccountUser 和 使用 resultMap，本文只讲述 resultMap 的实现方式。

---


### 2.1 定义用户和账户信息的实体类

  使用 resultMap，定义专门的 resultMap 用于映射一对一查询结果。通过面向对象的 (has a) 关系可以得知，我们可以在 Account 类中加入一个 User 类的对象来代表这个账户是哪个用户的。

**用户实体类：**

```java
public class User implements Serializable {
    private Integer id;
    private String username;
    private String address;
    private String sex;
    private Date birthday;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}

```

**账户实体类：**

```java
public class Account implements Serializable {
    private Integer id;
    private Integer uid;
    private Double money;

    //从表实体应该包含一个主表实体的对象引用
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", uid=" + uid +
                ", money=" + money +
                '}';
    }
}

```

---


### 2.2 编写 SQL 语句

```sql
 select u.*, a.id as aid, a.uid, a.money from account a, user u where u.id = a.uid

```

账户表中的数据： <img src="https://img-blog.csdnimg.cn/20210503105433664.png#pic_left" alt="在这里插入图片描述" width="200"/> 用户表中的数据： <img src="https://img-blog.csdnimg.cn/20210503105602280.png#pic_left" alt="在这里插入图片描述" width="400"/> 测试结果： <img src="https://img-blog.csdnimg.cn/20210503105703396.png#pic_left" alt="在这里插入图片描述" width="650"/>

---


### 2.3 定义持久层账户的 Dao 接口

```java
public interface IAccountDao {
    /**
     * 查询所有账户
     */
    List<Account> findAll();
}

```

---


### 2.4 AccountDao.xml 文件中的查询配置信息

resultMap 标签自定义某个 javabean 的封装规则： 

type：自定义规则的java类型。

 id：唯一id方便引用

id 标签指定主键列的封装规则，column 代表的是数据库的属性字段，property 代表的是实体类中的属性字段。

<font color="red"> **注意：** 当 sql 语句起了别名之后，column 应该修改为别名的字段</font>

association 表示多表查询中关联部分的映射配置，property属性和javaType属性是必需的，column不一定要写。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IAccountDao">

    <!-- 定义封装account和user的resultMap -->
    <resultMap id="accountUserMap" type="account">
        <id property="id" column="aid"/>
        <result property="uid" column="uid"/>
        <result property="money" column="money"/>
        <!-- 一对一的关系映射，配置封装user的内容 -->
        <association property="user" column="uid" javaType="user">
            <id property="id" column="id"/>
            <result property="username" column="username"/>
            <result property="address" column="address"/>
            <result property="sex" column="sex"/>
            <result property="birthday" column="birthday"/>
        </association>
    </resultMap>

    <!--配置查询所有-->
    <select id="findAll" resultMap="accountUserMap">
       select u.*, a.id as aid, a.uid, a.money from account a, user u where u.id = a.uid;
    </select>
    
</mapper>

```

---


### 2.5 测试方法

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
    public void destory() throws Exception {
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        //执行查询所有方法
        List<Account> accounts = accountDao.findAll();
        for (Account account:accounts) {
            System.out.println("---------每个账户的信息------");
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }
}

```

测试结果： <img src="https://img-blog.csdnimg.cn/20210503111205903.png#pic_left" alt="在这里插入图片描述" width="900"/>

---


## 三、一对多查询

**需求：** 查询所有用户信息及用户关联的账户信息。

**分析：**   用户信息和他的账户信息为一对多关系，并且查询过程中如果用户没有账户信息，此时也要将用户信息查询出来，因此我们可以用左外连接查询。

---


### 3.1 在 User 类加入从表实体的集合引用

```java
public class User implements Serializable {
    private Integer id;
    private String username;
    private String address;
    private String sex;
    private Date birthday;

    //一对多关系映射：主表实体应该包含从表实体的集合引用
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}


```

---


### 3.2 编写 SQL 语句

```sql
select u.*, a.id as aid, a.uid, a.money from user  u left outer join account a on u.id = a.uid;

```

account 表中的数据： <img src="https://img-blog.csdnimg.cn/20210503223351845.png?#pic_left" alt="在这里插入图片描述" width="450"/> user 表中的数据： <img src="https://img-blog.csdnimg.cn/2021050322342997.png?#pic_left" alt="在这里插入图片描述" width="480"/>

查询结果： <img src="https://img-blog.csdnimg.cn/20210503223306367.png?#pic_left" alt="在这里插入图片描述" width="620"/>

---


### 3.3 用户持久层 Dao 接口

```java
/**
 * 用户持久层接口
 */
public interface IUserDao {
    /**
     * 查询所有用户,同时获取到用户下所有账户的信息
     * @return
     */
    List<User> findAll();
}

```

---


### 3.4 用户持久层 Dao 映射文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IUserDao">

    <!--定义 user 的 resultMap -->
    <resultMap id="userAccountMap" type="user">
        <id property="id" column="id"></id>
        <result property="username" column="username"></result>
        <result property="address" column="address"></result>
        <result property="sex" column="sex"></result>
        <result property="birthday" column="birthday"></result>
        <!--配置user对象account集合的映射
            ofType集合中元素的类型-->
        <collection property="accounts" ofType="account">
            <id column="aid" property="id" ></id>
            <result column="uid" property="uid"></result>
            <result column="money" property="money"></result>
        </collection>
    </resultMap>

    <!--配置查询所有-->
    <select id="findAll" resultMap="userAccountMap">
        select u.*, a.id as aid, a.uid, a.money from user u left outer join account a on u.id = a.uid
    </select>
    
</mapper>

```

---


### 3.5 测试方法

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

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        for (User user:users) {
            System.out.println("------每个用户的信息-------");
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }
}

```

查询结果： <img src="https://img-blog.csdnimg.cn/20210503223643796.png?#pic_left" alt="在这里插入图片描述" width="900"/>

---


本文针对 Mybatis 中多表查询的一对一查询及一对多查询进行了总结归纳，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/116268829