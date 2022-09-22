

# Mybatis从入门到精通系列 14——基于注解配置的一对一查询与一对多查询

  上篇文章我们对 Mybatis 的 @Select、@Insert、@Delete、@Update以及 @Results 注解的进行了简单分析，本文我们将继续分析 Mybatis 中的其余注解。   实现关系映射之前需要在映射文件中通过配置 <resultMap>来实现，在使用注解开发时我们需要借助 @Results 注解，@Result 注解，@One 注解，@Many 注解。 <img src="https://img-blog.csdnimg.cn/20210512232825851.png" alt="在这里插入图片描述"/>

---


 # 文章目录
一、复杂关系映射的注解说明
二、使用注解实现一对一关系映射
2.1 环境准备
2.2 Account 实体类和 User 实体类
2.3 添加 账户 的持久层接口
2.4 添加 用户 的持久层接口
2.5 添加测试方法
三、使用注解实现一对多关系映射
3.1 User 实体类入 加入 Account 的集合
3.2 添加 用户 的持久层接口方法
3.3 添加 账户 的持久层接口方法
3.4 添加测试方法

## 新增字段

```sql
USE mybatisdb;
alter table user add userId int(11);
alter table user add userName varchar(30);
alter table user add userAddress varchar(30);
alter table user add userSex varchar(30);
alter table user add userBirthday varchar(30);
```



---


## 一、复杂关系映射的注解说明
**@Results 注解** 

==代替的是标签 <resultMap>==
该注解中可以使用单个@Result 注解，也可以使用@Result 集合
@Results（{@Result() ，@Result() }）或 @Results（ @Result() ）

  **@Resutl 注解**

==代替了 <id> 标签和 <result> 标签==
@Result 中 属性介绍：

id 是否是主键字段
column 数据库的列名
property 需要装配的属性名
one 需要使用的 @One 注解（@Result（one=@One）（）））
many 需要使用的 @Many 注解（@Result（many=@many）（）））

**@One 注解（一对一）**

>  <mark>代替了<assocation> 标签，是多表查询的关键，在注解中用来指定子查询返回单一对象</mark> @One 注解属性介绍： 

   - select 指定用的 来多表查询的 sqlmapper- fetchType 会覆盖全局的配置参数 lazyLoadingEnabled。 


>  
   使用格式：@Result(column=" “,property=”",one=@One(select="")) 


**@Many 注解（多对一）**

>  
   <mark>代替了 标签, 是是多表查询的关键，在注解中用来指定子查询返回对象集合。</mark> <mark>注意：聚集元素用来处理“一对多”的关系。需要指定映射的 Java 实体类的属性，属性的 javaType（一般为 ArrayList）但是注解中可以不定义；</mark> 


>  
   使用格式：@Result(property="", column="", many=@Many(select="")) 


---


## 二、使用注解实现一对一关系映射

**需求：** 加载账户信息时并且加载该账户的用户信息，根据情况可实现延迟加载。

---


### 2.1 环境准备

**工程目录：**

 <img src="https://img-blog.csdnimg.cn/20210518230625646.png#pic_left" alt="在这里插入图片描述" width="300"/>

---


**主配置文件：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!--引入外部配置文件-->
    <properties resource="jdbcConfig.properties"></properties>

    <!--配置开启二级缓存-->
    <settings>
        <setting name="cacheEnabled" value="true"></setting>
    </settings>

    <!--使用typeAliases配置别名，他只能配置domain中类的别名-->
    <typeAliases>
        <package name="com.itheima.domain"></package>
    </typeAliases>

    <!--配置环境-->
    <environments default="mysql">
        <!--配置mysql的环境-->
        <environment id="mysql">
            <!--配置事务-->
            <transactionManager type="JDBC"></transactionManager>
            <!--配置连接池-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!--配置映射文件的信息-->
    <mappers>
      <package name="com.itheima.dao"></package>
    </mappers>
</configuration>

```

---


**jdbcConfig.properties配置文件：**

```xml
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatisdb?useUnicode=true&amp;characterEncoding=UTF-8
jdbc.username=root
jdbc.password=000000

```

---


### 2.2 Account 实体类和 User 实体类

**账户实体类：**

```java
public class Account implements Serializable {
    private Integer id;
    private Integer uid;
    private Double money;

    //多对一，（mybatis中称之为一对一)的映射，一个账户只能属于一个用户
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


**用户实体类：**

```java
public class User implements Serializable {
    private Integer userId;
    private String userName;
    private String userAddress;
    private String userSex;
    private Date userBirthday;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userBirthday=" + userBirthday +
                '}';
    }
}

```

---


### 2.3 添加 账户 的持久层接口

```java
public interface IAccountDao {

    /**
     * 查询所有账户，并且获取每个账户所属的用户信息
     */
    @Select("select * from account ")
    @Results(id="accountMap", value={
        @Result(id=true, property = "id", column = "id"),
        @Result(property = "uid", column = "uid"),
        @Result(property = "money", column = "money"),
        @Result(property = "user", column = "uid",
                one = @One(select = "com.itheima.dao.IUserDao.findById", fetchType = FetchType.EAGER))
    })
    List<Account> findAll();
}

```

  关于 @One 注解中的 fetchType 属性有三个属性值：分别是 LAZY、EAGER、DEFAULT，代表的意思也就不言而喻了。这里我们实现的是一对一，所以选择了立即加载。

 <img src="https://img-blog.csdnimg.cn/20210518232559923.png?#pic_left" alt="在这里插入图片描述" width="300"/>

---


### 2.4 添加 用户 的持久层接口

```java
public interface IUserDao {

    /**
     * 根据id查询用户
     */
    @Select("select * from user where id = #{uid}")
    @Results(id = "userMap",value={
            @Result(id = true, property = "userId", column = "id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "userAddress", column = "address"),
            @Result(property = "userSex", column = "sex"),
            @Result(property = "userBirthday", column = "birthday")
    })
    User findById(Integer id);
}

```

  这里我们故意设置实体类属性和数据库字段不对应，那么就需要 @Results 注解进行重新和数据库字段建立对应关系。

---


### 2.5 添加测试方法

```java
public class AccountTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private IAccountDao accountDao;

    @Before
    public void Init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        accountDao = sqlSession.getMapper(IAccountDao.class);
    }

    @After
    public void destroy() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void testFindAll(){
        List<Account> accounts = accountDao.findAll();
        for (Account account: accounts){
            System.out.println("----每个账户信息----");
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }
}

```

数据库表中的数据：

**账户表：** <img src="https://img-blog.csdnimg.cn/20210518233511404.png#pic_left" alt="在这里插入图片描述" width="320"/>

**用户表：** <img src="https://img-blog.csdnimg.cn/20210518233602545.png#pic_left" alt="在这里插入图片描述" width="520"/>

---


**测试结果：** <img src="https://img-blog.csdnimg.cn/2021051823391647.png#pic_left" alt="在这里插入图片描述"/>

---


## 三、使用注解实现一对多关系映射

**需求：** 查询用户信息时，也要查询他的账户列表。使用注解方式实现。 **分析：** 一个用户具有多个账户信息，所以形成了用户(User)与账户(Account)之间的一对多关系。

---


### 3.1 User 实体类入 加入 Account 的集合

```java
public class User implements Serializable {
    private Integer userId;
    private String userName;
    private String userAddress;
    private String userSex;
    private Date userBirthday;

    //一对多关系映射，一个用户对应多个账户
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userBirthday=" + userBirthday +
                '}';
    }
}

```

---


### 3.2 添加 用户 的持久层接口方法

```java
public interface IUserDao {

    /**
     * 查询所有
     */
    @Select("select *from user")
    @Results(id = "userMap",value={
            @Result(id = true, property = "userId", column = "id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "userAddress", column = "address"),
            @Result(property = "userSex", column = "sex"),
            @Result(property = "userBirthday", column = "birthday"),
            @Result(property = "accounts", column = "id",
                    many = @Many(select = "com.itheima.dao.IAccountDao.findAccountByUid", fetchType = FetchType.LAZY))
    })
    List<User> findAll();

    /**
     * 根据id查询用户
     */
    @Select("select * from user where id = #{uid}")
    @ResultMap(value={"userMap"})
    User findById(Integer id);

}

```

@Many: 相当于<collection>的配置
1. select 属性：代表将要执行的 sql 语句1. fetchType 属性：代表加载方式，一般如果要延迟加载都设置为 LAZY 的值
---


### 3.3 添加 账户 的持久层接口方法

```java
public interface IAccountDao {

    /**
     * 查询所有账户，并且获取每个账户所属的用户信息
     */
    @Select("select * from account ")
    @Results(id="accountMap", value={
        @Result(id=true, property = "id", column = "id"),
        @Result(property = "uid", column = "uid"),
        @Result(property = "money", column = "money"),
        @Result(property = "user", column = "uid",
                one = @One(select = "com.itheima.dao.IUserDao.findById", fetchType = FetchType.EAGER))
    })
    List<Account> findAll();


    /**
     * 根据用户id查询账户信息
     */
    @Select("select * from account where uid = #{uid}")
    List<Account> findAccountByUid(Integer userId);
}

```

---


### 3.4 添加测试方法

```java
public class UserTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before
    public void Init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    public void destroy() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        for (User user: users){
            System.out.println("-------每个用户所对应的账户信息-------------");
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }
}

```

**测试结果：** <img src="https://img-blog.csdnimg.cn/20210518235153337.png#pic_left" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/20210518235232127.png#pic_left" alt="在这里插入图片描述"/>

---


  本文借鉴了黑马教程笔记并针对 Mybatis 中基于注解配置实现了一对多与多对多的查询，查询同时也应用了注解配置中的延迟加载与立即加载，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/116708906