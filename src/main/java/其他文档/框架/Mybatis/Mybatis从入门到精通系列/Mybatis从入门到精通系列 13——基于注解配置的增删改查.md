# Mybatis从入门到精通系列 13——基于注解配置的增删改查
  Mybatis 开发也可以使用注解，使用注解的好处就是可以减少编写 Mapper 映射文件，适合简单的数据处理，如动态 SQL 语句在注解开发中也可以使用，但是不如映射文件灵活。本文我们针对 Mybatis 注解开发中的常用注解以及基本的 CRUD 进行分析。 <img src="https://img-blog.csdnimg.cn/20210510231551120.png" alt="在这里插入图片描述"/>

---


 # 文章目录
一、Mybatis 的常用注解说明
二、Mybatis 注解开发需要说明的问题
三、使用 Mybatis 注解实现基本 CRUD
3.1 编写实体类
3.2 基于注解开发实现增删改查
3.3 编写测试方法
四、使用 Results 注解实现实体类和数据库字段实现对应
4.1 修改实体类
4.2 使用注解方式开发持久层接口
4.3 需要注意的点：实体类与数据库字段不对应

---


## 一、Mybatis 的常用注解说明

```css
@Insert:实现新增
@Update:实现更新
@Delete:实现删除
@Select:实现查询
@Result:实现结果集封装
@Results:可以与@Result 一起使用，封装多个结果集
@ResultMap:实现引用@Results 定义的封装
@One:实现一对一结果集封装
@Many:实现一对多结果集封装
@SelectProvider: 实现动态 SQL 映射
@CacheNamespace:实现注解二级缓存的使用

```

---


## 二、Mybatis 注解开发需要说明的问题
1. <font color="red">注解开发中 **不能** 和 XML 开发的配置共存</font>，即使在主配置文件 SqlConfig.xml 中使用mapper 标签并指定class路径，也会报错。 

   ```
   <!--如果使用注解来配置，此处应该使用class属性指定被注解的dao全限定类名-->
   <mappers>
       <mapper class="com.itheima.dao.IUserDao"/>
   </mappers>
   ```
   
2. <font color="red">但是更一般的情况我们会选择使用 package 标签指定带有注解的dao接口的所在位置。</font>

   ```
   <!--带有注解的dao接口的所在位置-->  
   <mappers> 
   	<package name="com.itheima.dao"></package>
   </mappers> 
   ```
   
---


## 三、使用 Mybatis 注解实现基本 CRUD

**工程目录：**

 <img src="https://img-blog.csdnimg.cn/20210510233817725.png#pic_left" alt="在这里插入图片描述" width="300"/>

---


### 3.1 编写实体类

```java
public class User {
    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

```

---


### 3.2 基于注解开发实现增删改查

其中，@SelectKey注解的作用是获取插入操作之后的id，没有实际需求可以省略。${value} 写法是固定的，不能是其他写法。如不能是 ${name}，这里和基于 XML 配置都是对应的。

**代码如下：**

```java
public interface IUserDao {

    /**
     * 查询所有
     */
    @Select("select *from user")
    List<User> findAll();

    /**
     * 添加用户
     */
    @Insert("insert into user(username, address, sex, birthday) values(#{username}, #{address}, #{sex}, #{birthday})")
    @SelectKey(keyColumn="id",keyProperty="id",resultType=Integer.class,before =
            false, statement = { "select last_insert_id()" })
    void insertUser(User user);

    /**
     * 更新
     */
    @Update("update user set username=#{username}, sex=#{sex}, address=#{address}, birthday=#{birthday} where id=#{id}")
    void updateUser(User user);

    /**
     * 删除用户
     */
    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);

    /**
     * 根据id查询用户
     */
    @Select("select * from user where id = #{uid}")
    User findById(Integer id);


    /**
     * 根据用户名称模糊查询实现1
     */
    @Select("select * from user where username like #{name}")
    List<User> findUserByNameMethod1(String name);

    /**
     * 根据用户名称模糊查询实现2
     */
    @Select("select * from user where username like '%${value}%'")  //${value}是固定写法
    List<User> findUserByNameMethod2(String username);

    /**
     * 查询总用户数
     */
    @Select("select count(*) from user")
    int findTotalUser();
}

```

---


### 3.3 编写测试方法

```java
public class AnnotationCRUDTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before
    public void Init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
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
            System.out.println(user);
        }
    }

    @Test
    public void testInsert(){
        User user =  new User();
        user.setUsername("annotation_user01");
        user.setBirthday(new Date());
        user.setAddress("山西省太原市");
        //插入之前
        System.out.println(user);
        userDao.insertUser(user);
        //插入之后
        System.out.println(user);
    }

    @Test
    public void testUpdate(){
        User user =  new User();
        user.setId(77);
        user.setUsername("update_user");
        user.setAddress("北京市");
        user.setBirthday(new Date());
        userDao.updateUser(user);
    }

    @Test
    public void testDelete(){
        userDao.deleteUser(77);
    }

    @Test
    public void testFindById(){
        User user = userDao.findById(41);
        System.out.println(user);
    }

    @Test
    public void testFindUserByNameMethod1(){
        String name = "%王%";
        List<User> users = userDao.findUserByNameMethod1(name);
        for(User user: users){
            System.out.println(user);
        }
    }

    @Test
    public void testFindUserByNameMethod2(){
        List<User> users = userDao.findUserByNameMethod2("王");
        for(User user: users){
            System.out.println(user);
        }
    }

    @Test
    public void testFindTotalUser(){
        int totalUser = userDao.findTotalUser();
        System.out.println(totalUser);
    }
}

```

---


## 四、使用 Results 注解实现实体类和数据库字段实现对应

@Results 注解和 XML 配置文件中的 ResultMap 标签的作用是一样。

### 4.1 修改实体类

我们故意和数据库表的列名不一致，数据库各属性字段名如下： <img src="https://img-blog.csdnimg.cn/2021051211182788.png#pic_left" alt="在这里插入图片描述" width="600"/> 

**实体类代码如下：**

```java
public class User {
    private Integer userId;
    private String userName;
    private Date userBirthday;
    private String userSex;
    private String userAddress;

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

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userBirthday=" + userBirthday +
                ", userSex='" + userSex + '\'' +
                ", userAddress='" + userAddress + '\'' +
                '}';
    }
}

```

---


### 4.2 使用注解方式开发持久层接口

下面我们简单举例查询说明 @Results 注解的使用

```java
/**
 * 使用 Results 注解
 */
public interface IUserDaoByResults {

    /**
     * 查询所有
     */
    @Select("select *from user")
    @Results(id="userMap",
        value= {
                @Result(id=true,column="id",property="userId"),
                @Result(column="username",property="userName"),
                @Result(column="sex",property="userSex"),
                @Result(column="address",property="userAddress"),
                @Result(column="birthday",property="userBirthday")
        })
    List<User> findAll();

    /**
     * 根据id查询用户
     */
    @Select("select * from user where id = #{uid}")
    @ResultMap("userMap")
    User findById(Integer id);

    /**
     * 根据用户名称模糊查询实现1
     */
    @Select("select * from user where username like #{name}")
    @ResultMap("userMap")
    List<User> findUserByNameMethod1(String name);
}

```

**测试方法：**

```java
@Test
public void testFindAll(){
    List<User> users = userDao.findAll();
    for (User user: users){
        System.out.println(user);
    }
}

@Test
public void testFindById(){
    User user = userDao.findById(42);
    System.out.println(user);
}

@Test
public void testFindByName(){
    List<User> users = userDao.findUserByName("%王%");
    for (User user: users){
        System.out.println(user);
    }
}  

```

---


### 4.3 需要注意的点：实体类与数据库字段不对应

  在之前我们说过 #{} 使用的是 ognl 表达式，#{username} 实际上是调用了 #{user.getUsername}，对象.get属性() 的方法。 因此在实体类与数据库字段不对应的时候，我们应该注意 #{属性} 中方法名字是否写错。具体 ognl 用法的详细解析，请查看以下链接👇： Mybatis从入门到精通系列 04——基于XML配置实现增删改查：https://blog.csdn.net/weixin_43819566/article/details/115803352</a>

那么我们以添加用户举例：

**持久层接口：**

```java
@Insert("insert into user(username, address, sex, birthday) values(#{userName}, #{userAddress}, #{userSex}, #{userBirthday})")
public void insertUser(User user);

```

测试方法如下：

```java
 @Test
 public void testInsertUser(){
     User user =  new User();
     user.setUserName("annotation_user01");
     user.setUserSex("男");
     user.setUserBirthday(new Date());
     user.setUserAddress("山东省烟台市");
     userDao.insertUser(user);
 }

```

---


本文针对基于注解实现了 Mybatis 进行了CRUD 操作，并对部分常用注解进行总结，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/116615063