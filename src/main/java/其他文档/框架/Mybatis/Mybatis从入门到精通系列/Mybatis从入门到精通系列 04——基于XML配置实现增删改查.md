# Mybatis从入门到精通系列 04——基于XML配置实现增删改查
  本文主要应用XML配置方式实现数据库表的增删改查，并通过数据库的基本操作总结 MyBatis 与 JDBC 在编程方面的区别。

<img src="https://img-blog.csdnimg.cn/20210417223837180.png#pic_center" alt="在这里插入图片描述"/>

---


 # 文章目录
一、XML配置实现增删改查
1.1 添加操作
1.2 更新操作
1.3 删除操作
1.4 查询单个
1.5 模糊查询
1.6 分页查询
1.7 查询总的记录条数
1.8 queryVo作为查询条件的查询
二、Mybatis 与 与 JDBC 编程的比较

---


## 一、XML配置实现增删改查

  除了映射文件 IUerDao.xml 不同以外，其余配置都和 1 讲中相同，具体代码参考右侧链接：Mybatis从入门到精通系列 01——快速入门

测试类部分代码如下：

```java
package package01.test;

import com.itheima.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;

import java.io.InputStream;


public class MybatisTest4 {
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
    public void destroy() throws Exception {
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        in.close();
    }
}


```

  与之前代码不同的是，我们将关于读取配置文件、获取SqlSessionFactory等重复操作放在了具有 @Before 下面的方法中，作用是在单元测试之前执行。那么 @After 下面的方法则是在每次单元测试之后执行，这里用于释放资源与 **事务的提交**。（事务提交在下文添加操作详解）

---


### 1.1 添加操作

在持久层接口中添加方法：

```java
/**
 * 保存用户
 * @param user
 */
void saveUser(User user);

```

在用户的映射配置文件中配置：

```xml
<!--保存用户-->
<insert id="saveUser" parameterType="com.itheima.domain.User">
    <!--配置插入操作之后，获取插入数据的id-->
    <selectKey keyProperty="id" keyColumn="id" resultType="int" order="AFTER">
        select last_insert_id();
    </selectKey>
    insert into user(username, address, sex, birthday) values(#{username}, #{address}, #{sex}, #{birthday});
</insert>

```

**细节：**

- parameterType 属性：

    代表参数的类型，因为我们要传入的是一个类的对象，所以类型就写类的全名称。

- sql 语句中使用 #{ } 字符：

    它代表占位符，相当于原来 jdbc 部分中与处理对象 PrepareStatement 中的?，都是用于执行语句时替换实际的数据。具体的数据是由#{}里面的内容决定的。

- #{ } 中内容的写法：

    由于我们保存方法的参数是 一个 User 对象，此处要写 User 对象中的属性名称。它用的是 ognl 表达式。 如果参数类型是基本类型，#{ } 中的值可以随意写。

- ognl 表达式：

    它是 apache 提供的一种表达式语言。它是按照一定的语法格式来获取数据的。语法格式就是使用 #{对象.对象} 的方式。#{user.username}它会先去找 user 对象，然后在 user 对象中找到 username 属性，并调用 getUsername() 方法把值取出来。但是我们在 parameterType 属性上指定了实体类名称，所以可以省略 user，而直接写 username。

  **测试类中添加测试方法**：

```java
/**
 * 测试保存操作
 */
@Test
public void testSave(){
    User user = new User();
    user.setUsername("testInsertOne");
    user.setAddress("测试地址");
    user.setSex("男");
    user.setBirthday(new Date());
    System.out.println("保存之前:"+user);

    //执行保存方法
    userDao.saveUser(user);
    System.out.println("保存之后:"+user);
}

```

**需要说明的点：**

1. **关于获取新增用户的id**   
  
  关于获取新增用户的id，我们可以在 selectKey 标签中使用 select last_insert_id(); 获取id值，具体用法如下图： <img src="https://img-blog.csdnimg.cn/20210417231731274.png" alt="在这里插入图片描述"/> keyProperty：代表要返回的值名称 order：取值为 AFTER 代表插入后的行为 resultType：代表返回值的类型
  如果没有对于获取新增用户的 id 的需求，那么删除即可。
  
1. **关于事务提交**   

     如果没有 **sqlSession.commit()** 完成事务提交，那么添加操作就会向下方的截图一样，会造成事务回滚，即添加失败。具体原因在于下图中的 <font color="red">Setting autocommit to false on JDBC Connection</font>， ==mybatis 关闭了自动提交，需要手动提交，这里我们还没有手动提交事务。所以我们需要在事务结束之前添加 **sqlSession.commit()** 完成事务提交。==<img src="https://img-blog.csdnimg.cn/20210417230320768.png#pic_left" alt="在这里插入图片描述"/>
---


### 1.2 更新操作

在持久层接口中添加更新方法：

```java
/**
 * 更新用户
 * @param user
 */
void updateUser(User user);

```

在用户的映射配置文件中配置：

```xml
<!--更新用户-->
<update id="updateUser" parameterType="com.itheima.domain.User">
    update user set username=#{username}, address=#{address}, sex=#{sex}, birthday=#{birthday} where id=#{id}
</update>

```

测试类中添加测试方法：

```java
/**
 * 更新操作
 */
@Test
public void testUpdate(){
    User user = new User();
    user.setId(46);
    user.setUsername("mybatis upodateuser");
    user.setAddress("更新的地址");
    user.setSex("女");
    user.setBirthday(new Date());

    //执行保存方法
    userDao.updateUser(user);
}

```

---


### 1.3 删除操作

在持久层接口中添加删除方法：

```java
/**
 * 删除用户
 * @param userId
 */
void deleteUser(Integer userId);

```

在用户的映射配置文件中配置：

```xml
<!--删除用户-->
<!--基本类型或者基本类型包装类 并且 占位符只有一个参数时，可以随意写 -->
<!--如下文中占位符参数是 uid，也可以是其他任意字符-->
<delete id="deleteUser" parameterType="java.lang.Integer">
    delete from user where id = #{uid}
</delete>

```

测试类中添加测试方法：

```java
/**
 * 测试删除方法
 */
@Test
public void testDelete(){
    //执行删除方法
    userDao.deleteUser(75);
}

```

---


### 1.4 查询单个

在持久层接口中添加查询单个方法：

```java
/**
 *在持久层接口中添加查询单个方法：
 * 根据 id 查询用户信息
 * @param userId
 * @return
 */
User findById(Integer userId);

```

在用户的映射配置文件中配置：

```xml
<!--根据id查询用户-->
<select id="findById" parameterType="INT" resultType="com.itheima.domain.User">
    select * from user where id = #{uid}
</select>

```

测试类中添加测试方法：

```java
/**
 * 测试查询一个方法
 */
@Test
public void testFindOne(){
    //执行查询一个方法
    User user = userDao.findById(52);
    System.out.println(user);
}

```

---


### 1.5 模糊查询

在持久层接口中添加模糊查询方法：

```java
/**
 * 根据姓名模糊查询
 * @return
 */
List<User> findByName(String name);

```

模糊查询有两种实现方式：

**1、方式1** 在用户的映射配置文件中配置：

```xml
<!--根据名称模糊查询-->
<select id="findByName" parameterType="String" resultType="com.itheima.domain.User">
    select * from user where username like '%${value}%'
</select>

```

**值得注意的是：使用方式以配置映射文件， ${value} 写法是固定的，不能是其他写法。如不能是 ${name}等**

2、方式2

```xml
<!--根据名称模糊查询-->
<select id="findByName" parameterType="String" resultType="com.itheima.domain.User">
    select * from user where username like #{name}
</select>

```

测试类中添加测试方法：

```java
/**
 * 测试模糊查询
 */
@Test
public void testFindByName(){
    //执行查询一个方法
    //方式1
    List<User> users = userDao.findByName("王");
    //方式2
    List<User> users = userDao.findByName("%王%");
    for (User user:users){
        System.out.println(user);
    }
}

```

**扩展：**

**#{} 与${} 的区别：**
1. #{ } 表示一个占位符号   通过#{ }可以实现 preparedStatement 向占位符中设置值，自动进行 java 类型和 jdbc 类型转换，#{ } 可以有效防止 sql 注入。 #{ } 可以接收简单类型值或 pojo 属性值。 **如果 parameterType 传输单个简单类型值，#{ } 括号中可以是 value 或其它名称。**
1. ${ } 表示拼接 sql 串   通过${ }可以将 parameterType 传入的内容拼接在 sql中且不进行 jdbc 类型转换， ${ }可以接收简单类型值或 pojo 属性值，**如果 parameterType 传输单个简单类型值，${ }括号中只能是 value。**
---


### 1.6 分页查询

在持久层接口中添加分页查询方法：

```java
 /**
  * 分页查询
  * @return
  */
List<User> findByPage(@Param("start")int start, @Param("rows")int rows);

```

在用户的映射配置文件中配置：

```xml
<!--分页查询-->
<select id="findByPage" resultType="com.itheima.domain.User">
    select * from user limit #{start}, #{rows}
</select>

```

测试类中添加测试方法：

```java
/**
 * 测试分页查询
 */
@Test
public void testFindByPage(){
    //执行查询一个方法
    List<User> users = userDao.findByPage(0, 3);
    for (User user:users){
        System.out.println(user);
    }
}

```

---


### 1.7 查询总的记录条数

在持久层接口中添加查询总的记录条数：

```java
/**
 * 在持久层接口中添加查询总的记录条数：
 * 查询总用户数
 * @return
 */
int findTotal();

```

在用户的映射配置文件中配置：

```xml
<!--查询总用户数-->
<select id="findTotal" resultType="int">
    select count(*) from user;
</select>

```

测试类中添加测试方法：

```java
/**
 * 查询总记录数
 */
@Test
public void testFindTotal(){
    int total = userDao.findTotal();
    System.out.println(total);
}

```

---


### 1.8 queryVo作为查询条件的查询

  queryVo作为一个包装类，可以封装多个不同类型的成员变量，将queryVo作为输入参数，可以它的成员变量作为sql的值。

queryVo实体类：

```java
public class QueryVo {
    private User user;

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }
}

```

在持久层接口中添加根据QueryVo中的条件查询用户的方法：

```java
/**
 * 在持久层接口中添加根据QueryVo中的条件查询用户的方法：
 * 根据QueryVo中的条件查询用户
 * @param vo
 * @return
 */
List<User> findUserByVo(QueryVo vo);

```

在用户的映射配置文件中配置：

```xml
<!--根据queryvo的条件查询用户-->
<select id="findUserByVo" parameterType="com.itheima.domain.QueryVo" resultType="com.itheima.domain.User">
    select * from user where username like #{user.username}
</select>

```

测试类中添加测试方法：

```java
/**
 * 测试使用queryVo作为查询条件
 */
@Test
public void testFindUserByVo(){
    QueryVo queryVo = new QueryVo();
    User user = new User();
    user.setUsername("%王%");
    queryVo.setUser(user);

    //执行查询一个方法
    List<User> users = userDao.findUserByVo(queryVo);
    /* List<User> users = userDao.findByName("王");*/
    for (User u:users){
        System.out.println(u);
    }
}

```

---


## 二、Mybatis 与 与 JDBC 编程的比较
1、数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能，如果使用数据库连接池可解决此问题。 

​	解决：   

​		**在 SqlMapConfig.xml 中配置数据链接池，使用连接池管理数据库链接。** 

2、Sql 语句写在代码中造成代码不易维护，实际应用 sql 变化的可能较大，sql 变动需要改变 java 代码。 

​     解决：   

​        **将 Sql 语句配置在 XXXXmapper.xml 文件中与 java 代码分离。**

3、向sql语句传参数麻烦，因为sql语句的where 条件不一定，可能多也可能少，占位符需要和参数对应。

​     解决：   

​       **Mybatis自动将 java 对象映射至 sql 语句，通过 statement 中的 parameterType 定义输入参数的类型。**

 4、对结果集解析麻烦，sql 变化导致解析代码变化，且解析前需要遍历，如果能将数据库记录封装成 pojo 对象解析比较方便。

​     解决：

   **Mybatis自动将 sql执行结果映射至 java 对象，通过 statement 中的 resultType 定义输出结果的类型。**

---


本文针对基于XML利用 Mybatis 进行了CRUD 操作，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/115803352