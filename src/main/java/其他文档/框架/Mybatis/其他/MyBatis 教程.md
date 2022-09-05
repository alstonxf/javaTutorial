# Mybatis

## 什么是Mybatis
- MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。
- MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
- MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。
### 持久层

#### 数据持久化
- 持久化就是将程序的数据在持久状态和瞬间状态转化的过程- 持久化就是将我们内存中的数据存在数据库中，

- 数据库(jdbc)，IO文件持久化

- 生活：冷藏，罐头

  

  #### 为什么需要持久化

- 内存珍贵

- 有一些对象数据，不能让他丢掉，让他保存起来
#### 持久层

Dao，Service，Controller层…
- 完成持久化工作的代码块
- 层界限十分明显
#### 为什么需要Mybatis
- - 方便 
  - 传统的jdbc代码太复杂。简化，框架。 
  - 帮助程序员将数据存入到数据库中 
  - 不用Mybatis也可以，mybatis更容易上手。技术没有高低之分 

  - 优点：
    - <img src="https://img-blog.csdnimg.cn/img_convert/3c73a3755a1fa132181461dc1bc8ef2a.png" alt="image-20220223092923094"/> 
      **最重要得是使用的人很多**

---


## 第一个Mybatis程序

思路：搭建环境–>导入Mybatis–>编写代码–>测试

### **搭建环境**

```mysql
-- 创建数据库
create database mybatis;
-- 进入数据库
use mybatis;
-- 创建表
create table user(
-- not null 不允许为空 PRIMARY KEY唯一主键
id int(20) not null PRIMARY KEY,
-- default null 默认为null
name varchar(30) default null,
pwd varchar(30) default null
-- engine=innodb设置数据库引擎为innodb，
-- default charset=utf8;设置默认字符集为utf-8
)engine=innodb default charset=utf8;

-- 新增数据
insert into user(id,name,pwd)values(1,'星辰','123456');
insert into user(id,name,pwd)values(2,'张三','123456');
insert into user(id,name,pwd)values(3,'李四','123456');
insert into user(id,name,pwd)values(4,'王五','123456');

```

### **新建项目**

#### 1、新建一个普通的maven项目（不要选择任何模板）

####  清除src目录

####  导入maven依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<!--    夫项目是我们的mybatis，子项目可以引用夫项目的依赖-->
    <parent>
        <artifactId>mybatis</artifactId>
        <groupId>org.example</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>mybatis-01</artifactId>

<!--    这里一定要写，不然会读取不到我们的mapper-->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                    <include>**/*.properties</include>
                </includes>
            </resource>

            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.xml</include>
                    <include>**/*.properties</include>
                </includes>
            </resource>
        </resources>
    </build>
</project>

```

如果pom文件还没mybatis的包，需要加上


```xml
   <!-- mybatil框架        -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.5</version>
    </dependency>
```



#### **创建配置文件**

##### 1、在子模块的src/resources下创建一个mybatis-config.xml，编写mybatis的核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--核心配置文件-->
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="0000"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="com/yunduo/dao/UserDaoMapper.xml"/>
    </mappers>
</configuration>

```

###### 注意：[配置xml文件的约束时：URL is not registered (Settings | Languages & Frameworks | Schemas and DTDs)](https://www.cnblogs.com/song77/p/12295451.html)

在我们配置xml文件时，为了我们代码规范并且有代码提示，我们经常会配置文件约束，dtd是专门约束限制xml文件的，如果我们发现xml报错，并且放在语句上方提示URI is not registered ( Setting | Project Settings | Schemas and DTDs )”，翻译过来就是统一资源标识符没有注册。以下是解决方法：

（1）可以看到提示的错误：首先将红色代码进行复制

![img](https://img2018.cnblogs.com/common/1765606/202002/1765606-20200211160432074-508703128.png)

（2）首先在IEDA编辑器中点击设置

![img](https://img2018.cnblogs.com/common/1765606/202002/1765606-20200211160522658-1699503188.png)

（3）在详细设置中按照以下步骤将复制的代码填入提示框中确认即可

![img](https://img2018.cnblogs.com/common/1765606/202002/1765606-20200211160545303-1350165043.png)



##### **2.编写mybtais工具类**

```java
package com.yunduo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * Mybatis工具类
 * 工厂类
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static{
        try{
            //使用mybatis第一步，获取SqlSessionFactory对象
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch (Exception exception){
            exception.getMessage();
        }
    }
    //这里的sqlSession对象就相当于jdbc中的Connection对象
    //返回sqlSession对象
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}

```

#####  **3. 编写代码操作数据库**

###### 实体类

```mysql
package com.yunduo.pojo;

public class User {
    private int id;
    private String name;
    private String pwd;

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}


```

###### 接口

```mysql
package com.yunduo.dao;

import com.yunduo.pojo.User;

import java.util.List;

public interface UserDao {
    List<User> getUserList();
}

```

###### 接口对应sql语句

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace命名空间，需要绑定对应的dao接口-->
<mapper namespace="com.yunduo.dao.UserDao">
<!--    这里ID对应我们接口中的方法  resultType代表返回类型-->
    <select id="getUserList" resultType="com.yunduo.pojo.User">
        select * from user
  </select>
</mapper>

```

###### 测试

```java
package com.dao;

import com.yunduo.pojo.User;
import com.yunduo.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDao {

    @Test
    public void test(){
        //我们通过工程类创建SqlSession操作数据库的对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //然后通过sqlsession对象获取我们dao层的接口
        //方式一
        com.yunduo.dao.UserDao mapper = sqlSession.getMapper(com.yunduo.dao.UserDao.class);
        //方式二(不推荐使用)
//        List<UserDao> UserDao = sqlSession.selectList("com.yunduo.dao.UserDao.class");

        //返回的对象执行自己的接口
        List<User> userList = mapper.getUserList();
        //如果这里打印出错，那么就是没有配置myabtis-config.xml中mappers中注册到mybatis中，以及要配置maven的pom.xml中build设置过滤
        //输出
        for (User user : userList) {
            System.out.println(user);
        }
        //关闭数据库对象
        sqlSession.close();

    }
}


```

<img src="https://img-blog.csdnimg.cn/img_convert/617551dbb8e95ec4d7cd5ed224f1e36b.png" alt="image-20220223150616516"/>

### CRUD
1. **我们对应的mapper.xml中的namespace一定要和我们对应的接口地址一致，否认绑定不上**
    <img src="https://img-blog.csdnimg.cn/img_convert/a0bc6b7be0aec604e8fd0d5d6fadae6a.png" alt="image-20220223151259665"/>

1. ID：对应的就是我们namespace中的方法名字

1. ResultType：sql语句的返回值

1.  parameteType：我们sql语句中的参数类型

1.  增删改：必须提交事务

  

  **Dao**

###### Pojo

```java
package com.yunduo.pojo;

public class User {
    private int id;
    private String name;
    private String pwd;

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}

```

###### UserMapper

```java
package com.yunduo.dao;

import com.yunduo.pojo.User;

import java.util.List;

public interface UserDaoMapper {
    //查询所有用户
    List<User> getUserList();
    //根据ID查询用户
    User getUserById(User user);
    //新增用户
    int AddUser(User user);
    //根据ID修改用户
    int UpdateUserById(User user);
    //根据用户ID清除用户
    int DeleteUserById(User user);
}


```

###### UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace命名空间，需要绑定对应的dao接口-->
<mapper namespace="com.yunduo.dao.UserDaoMapper">
<!--    这里ID对应我们接口中的方法  resultType代表返回类型-->
    <select id="getUserList" resultType="com.yunduo.pojo.User" parameterType="com.yunduo.pojo.User">
        select * from user
  </select>
    <select id="getUserById" resultType="com.yunduo.pojo.User" parameterType="com.yunduo.pojo.User">
        select * from user where id=#{id}
    </select>
    <insert id="AddUser" parameterType="com.yunduo.pojo.User">
        insert into user(id,name,pwd)values(#{id},#{name},#{pwd})
    </insert>
    <update id="UpdateUserById" parameterType="com.yunduo.pojo.User">
        update user set pwd=#{pwd} where id=#{id}
    </update>
    <delete id="DeleteUserById" parameterType="com.yunduo.pojo.User">
        delete from user where id=#{id}
    </delete>

</mapper>

```

###### Test

```java
package com.dao;

import com.yunduo.dao.UserDaoMapper;
import com.yunduo.pojo.User;
import com.yunduo.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDao {
    //查询所有用户
    @Test
    public void getUserList() {
        //我们通过工程类创建SqlSession操作数据库的对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //然后通过sqlsession对象获取我们dao层的接口
        //方式一
        UserDaoMapper mapper = sqlSession.getMapper(UserDaoMapper.class);
        //方式二(不推荐使用)
//        List<UserDao> UserDao = sqlSession.selectList("com.yunduo.dao.UserDao.class");

        //返回的对象执行自己的接口
        List<User> userList = mapper.getUserList();
        //如果这里打印出错，那么就是没有配置myabtis-config.xml中mappers中注册到mybatis中，以及要配置maven的pom.xml中build设置过滤
        //输出
        for (User user : userList) {
            System.out.println(user);
        }
        //关闭数据库对象
        sqlSession.close();

    }
    //根据ID查询用户
    @Test
    public void getUserById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDaoMapper user = sqlSession.getMapper(UserDaoMapper.class);
        System.out.println(user.getUserById(new User(1,null,null)));
        sqlSession.close();
    }
    //新增用户
    @Test
    public void AddUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDaoMapper mapper = sqlSession.getMapper(UserDaoMapper.class);
        int lao = mapper.AddUser(new User(5, "老六", "1234567"));
        //增删改，必须提交事务
        sqlSession.commit();
        if (lao>0){
            System.out.println("新增成功");
        }else{
            System.out.println("新增失败");
        }
    }
    //根据ID修改用户
    @Test
    public void UpdateUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDaoMapper mapper = sqlSession.getMapper(UserDaoMapper.class);
        int i = mapper.UpdateUserById(new User(5, null, "123456"));
        //提交事务
        sqlSession.commit();
        if (i>0){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败");
        }
    }
    //根据用户ID清除用户
    @Test
    public void DeleteUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDaoMapper user = sqlSession.getMapper(UserDaoMapper.class);
        int i=0;
        try{
            i = user.DeleteUserById(new User(5, null, null));
            sqlSession.commit();
        }catch(Exception e){
            e.getMessage();
        }finally {
            sqlSession.rollback();
        }
        if (i>0){
            System.out.println("清除成功");
        }else{
            System.out.println("清除失败");

        }    }
}

```

#### **利用Map来传递xml中sql参数**

>   Map传递参数，直接在sql中取出来【parameterType=“map”】 
 对象传递参数，直接在sql中取对象的属性【parameterType=“com.yunduo.pojo.User”】 
 只有一个基本类型参数的情况下，可以直接在sql中取到【parameterType=“int”】 
 多个参数用Map或者注解 


```
//根据ID查询用户
User getUserById2(HashMap<String,Object> map);

```

```
<!--    这里如果传递是一个map的话，那么我们传递的参数和key对应即可-->
    <select id="getUserById2" resultType="com.yunduo.pojo.User" parameterType="map">
        select * from user where id=#{kkk}
    </select>

```

```java
@Test
public void get(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserDaoMapper user = sqlSession.getMapper(UserDaoMapper.class);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("kkk",1);
    User userById2 = user.getUserById2(map);
    System.out.println(userById2);
}

```

<img src="https://img-blog.csdnimg.cn/img_convert/3cfc4948fb8e2a772a181e19aae24810.png" alt="image-20220223161821360"/>

#### **模糊查询**

>   我们不能在sql语句中写入%#{value}% 
 我们需要在我们传递参数的时候给参数加上%参数%即可 

##### **方案一**  在sql中加通配符进行拼接 


```xml
<select id="getUserLike" resultType="com.yunduo.pojo.User" parameterType="map">
    select * from user where pwd like '%'#{value}'%'
</select>

```

```java
public void get(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserDaoMapper user = sqlSession.getMapper(UserDaoMapper.class);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("value","1");
    List<User> userLike = user.getUserLike(map);
    for (User user1 : userLike) {
        System.out.println(user1);
    }
}

```

##### **方案二** 在代码中给参数做通配符进行拼接 


```xml
<select id="getUserLike" resultType="com.yunduo.pojo.User" parameterType="map">
    select * from user where pwd like #{value}
</select>

```

```java
public void get(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserDaoMapper user = sqlSession.getMapper(UserDaoMapper.class);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("value","%1%");
    List<User> userLike = user.getUserLike(map);
    for (User user1 : userLike) {
        System.out.println(user1);
    }
}

```

## 配置解析

>  
 之前的都只是入门，真正掌握的在下面 

- MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。 配置文档的顶层结构如下

  ```
  configuration（配置）
  properties（属性）
  settings（设置）
  typeAliases（类型别名）
  typeHandlers（类型处理器）
  objectFactory（对象工厂）
  plugins（插件）
  environments（环境配置）
  environment（环境变量）
  transactionManager（事务管理器）
  dataSource（数据源）
  databaseIdProvider（数据库厂商标识）
  mappers（映射器）
  ```
### 环境配置
1. mybatis可以配置多套数据库
2. 不过要记住，尽管可以配置多个环境，但每个SqlSessionFactory只能选择一套环境
3. 要学会配置多套环境
4. **Mybatis默认的事务管理器是jdbc，默认的链接池是POOLED**

### 属性（properties）

>  
 这些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置。 


**编写一个外部的properties文件配置我们的数据库链接信息**

db.properties

```apl
Driver=com.mysql.jdbc.Driver
Url=jdbc:mysql://localhost:3306/mybatis
UserName=root
PassWord=0000

```

在我们mybatis核心文件mybatis-config.xml中引入我们的db.properties

<img src="https://img-blog.csdnimg.cn/img_convert/014eab66c490d2afe38044084b097393.png" alt="image-20220223200622076"/>

<img src="https://img-blog.csdnimg.cn/img_convert/bc424ad55b09c101d1e77a3e6b552dc5.png" alt="image-20220223200944537"/>

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--核心配置文件-->

<configuration>
<!--    //引入外部配置文件-->
    <properties resource="db.properties"/>

    <environments default="development">
        <environment id="development">
            <!--    //事务是采用jdbc-->
            <transactionManager type="JDBC"/>
            <!--    //数据源用的是POOLED-->
            <dataSource type="POOLED">
<!--                这里的${Driver}引入的是我们外部配置文件的key-->
                <property name="driver" value="${Driver}"/>
                <property name="url" value="${Url}"/>
                <property name="username" value="${UserName}"/>
                <property name="password" value="${PassWord}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="com/yunduo/dao/UserDaoMapper.xml"/>
    </mappers>
</configuration>

```
- 可以直接引入外部文件- 可以在其中增加一些属性配置- 如果两个文件有同一个字段，优先使用外部配置文件
  
  #### **类型别名（typeAliases）**
  
- 类型别名是为java类型对象设置一个短的名字 

- 存在的意义仅在于用来减少类完全限定名的冗余 

  ```xml
      <typeAliases>
  <!--        这里默认别名到一个对象-->
          <typeAlias alias="user" type="com.yunduo.pojo.User"/>
      </typeAliases>
  
  ```

  

- 也可以指定一个报名，Mybatis会在包名下面搜索需要的Java Bean，比如： 扫码实体类的包，它的默认别名就为了这个类的类名，首字母小写！

  ```xml
      <typeAliases>
  <!--        这里别名到一个包，包下面的对象别名就是包名-->
          <package name="com.yunduo.pojo"/>
      </typeAliases>
  ```

  

-  **怎么选择那种呢** 

   如果实体类少，就使用第一种
   

 如果实体类十分多，建议使用第二种 

   两个区别就是第一个可以自定义别名，第二个不能自定义别名只能用类的名称当别名

如果你使用的是第二种包扫描，又想自定义别名，那么就可以使用注解来起别名 <img src="https://img-blog.csdnimg.cn/img_convert/06c1a48fc5ac53ef34691aca989ba4e4.png" alt="image-20220223203148070"/> </li>
<img src="https://img-blog.csdnimg.cn/img_convert/8f6e6ebfb6399488ee63abe865f0faf4.png" alt="image-20220223203237876"/>

### 设置

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为

**开启缓存和懒加载**

<img src="https://img-blog.csdnimg.cn/img_convert/5f493ab7994117da4233bb82f0ced1c1.png" alt="image-20220224085737170"/>

**日志**

<img src="https://img-blog.csdnimg.cn/img_convert/f9fca3da16963530dbce6753edaac178.png" alt="image-20220224085749028"/>

### 其他配置
- typeHandlers（类型处理器）

- objectFactory（对象工厂）

- plugins（插件）

  

  ### 插件
1. MyBatis Generator Core
1. MyBatis Plus
1.  通用Mapper
### 映射器

>  
 MapperRegistry：注册绑定我们的Mapper文件 

#### **方式一**【推荐】

使用绝对定位定位到我们对应的映射器的xml文件

```xml
<mappers>
    <mapper resource="com/yunduo/dao/UserDaoMapper.xml"/>
</mappers>

```

#### **方式二**

使用class定位我们接口
1. 接口和他的mapper配置文件必须同名1. 接口和他的mapper配置文件必须在同一个包下
```xml
<mappers>
    <mapper class="com.yunduo.dao.UserDaoMapper"/>
</mappers>

```

#### **方式三**

使用包扫描
1. 接口和他的mapper配置文件必须同名
1. 接口和他的mapper配置文件必须在同一个包下
```xml
<mappers>
    <package name="com.yunduo.dao"/>
</mappers>

```

### 生命周期

<img src="https://img-blog.csdnimg.cn/img_convert/1905f270ac61a381b629be9e920fbf92.png" alt="image-20220224093626594"/>

生命周期，和作用域，是至关重要的，因为错误的使用会导致非常严重的**并发问题**

**SqlSessionFactoryBuilder**
- 一旦创建了 SqlSessionFactory，就不再需要它了- 因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）
**SqlSessionFactory**
-  说白了就是数据库链接池 -  SqlSessionFactory一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例 -  因此SqlsessionFactory的最佳作用域是应用作用域 -  最简单的就是使用**单例模式**或者静态单例模式 
**SqlSession**
- 链接到线程池的一个请求！ 
  -  SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。
  - 用完之后赶紧关掉 
    <img src="https://img-blog.csdnimg.cn/img_convert/37cebc2ece10d8e0a282f9869aa04f3d.png" alt="image-20220224093749575"/>

## 解决属性名和字段名不一致问题

数据库中的字段

<img src="https://img-blog.csdnimg.cn/img_convert/71a7026b37ebe5e0033457e73169009b.png" alt="image-20220224094429168"/>

新建一个项目，测试实体类字段不一致的情况

<img src="https://img-blog.csdnimg.cn/img_convert/4ca801d2e93a54f3e912e022dd05ef79.png" alt="image-20220224105159286"/>

>  
 这里会发现，我们数据库中的字段和我们实体类里面的字段不同 


就会出现问题，查询出来的不一样的字段为null

<img src="https://img-blog.csdnimg.cn/img_convert/ea55ed4fbc5c4f60e0be0f7dc1628353.png" alt="image-20220224105255591"/>

**解决方案**

### ResultMap

```
数据库 id name pwd
实体类 id name password
我们将pwd绑定到我们实体类中的password


```

```xml
<!--    这里的我们使用resultMap getuser是给这个select取名字 -->
    <select id="getUserList" resultMap="getuser" parameterType="user">
        select * from user
  </select>

<!--    这里使用resultMap标签，id对应select中的resultMap定义的名称，type表示我们需要解决字段不一致的实体类-->
    <resultMap id="getuser" type="user">
<!--        使用result标签，进行映射不一致的属性字段   column对应数据库中的字段，property对应我们修改对应实体类中的属性-->
        <result column="pwd" property="password"/>
    </resultMap>

```
- resultmap元素是mybatis中最强大的一个元素- RestultMao的设计思想是，对应简单的语句根本不需要配置显式的结果映射，而对于复杂一点的语句只需要描述他们的关系就行了- resultmap最优秀的地方在于，虽然你对他已经相当了解，但是根本就不需要显示地用到他们
## 日志

### 日志工厂

>   如果一个数据库操作，出现了异常，我们需要排错。日志就是最好的助手！ 
 以往我们一般输出东西我们都是System.out.println，Debug 
 现在：日志工厂来帮我们 


<img src="https://img-blog.csdnimg.cn/img_convert/d3ba24c8a1e80916293d29b6d399fad8.png" alt="image-20220224143506362"/>
- SLF4J- LOG4J【掌握】- LOG4J2- JDK_LOGGING- COMMONS_LOGGING- STDOUT_LOGGING【掌握】- NO_LOGGING
在Mybatis中具体使用哪一个日志实现，在设置中设定！

#### 标准日志

在mybatis核心配置文件中写入

```xml
    <settings>
<!--        标准的日志工厂-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>

```

<img src="https://img-blog.csdnimg.cn/img_convert/5f130ef9efadcf68139e488f6b8ac2cc.png" alt=""/>

#### Log4J

#####  什么是log4j 

- Log4j是[Apache](https://baike.baidu.com/item/Apache/8512995)的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是[控制台](https://baike.baidu.com/item/控制台/2438626)、文件、[GUI](https://baike.baidu.com/item/GUI)组件

- 我们也可以控制每一条日志的输出格式

- 通过定义每一条日志信息的级别

- 可以通过一个[配置文件](https://baike.baidu.com/item/配置文件/286550)来灵活地进行配置，而不需要修改应用的代码。

  

##### 使用

1. ###### pom导入log4j包

   ```xml
   <!-- 注意：1.2.17下载报错，请使用1.2.12 -->
   <dependency>
       <groupId>log4j</groupId>
       <artifactId>log4j</artifactId>
       <version>1.2.12</version>
   </dependency>
   ```

2. ##### 在resources，下创建一个log4j.properties配置文件

```xml
#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=【%c】-%m%n

#文件输出的相关设置
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/yunduo.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yyy-MM-dd}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```

#####  log4j默认3种级别 

  <blockquote> 
   info，debug，error 
  </blockquote> </li>

```java
package SMV.myTest;

import org.apache.log4j.Logger;

import java.io.IOException;


public class TEST1 {
    //    然后创建 Logger写入器：
    private static Logger logger = Logger.getLogger(TEST1.class.getName());

    public TEST1() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        logger.info("info：启动test1成功");
        logger.debug("debug：启动test1成功");
        logger.error("error：启动test1成功");
    }
}
```

打印输出
INFO  09-04 13:51:27,978 info：启动test1成功 (myTest.java:22) 
DEBUG 09-04 13:51:27,981 debug：启动test1成功 (myTest.java:23) 
ERROR 09-04 13:51:27,981 error：启动test1成功 (myTest.java:24) 

3、Mybatis怎么使用Log4j打印sql日志？

在mybatis核心配置文件中引入

```xml
<settings>
    <setting name="logImpl" value="LOG4J"/>
</settings>

```

<img src="https://img-blog.csdnimg.cn/img_convert/2b056e0ad83d53258347f67927dee9e0.png" alt="image-20220224165002858"/>

## 

## 分页

为什么要分页？因为一次性查询很多，会很耗时，就使用分页

### 方法一：使用limit

### 方法二：使用代码形式进行分页

```
//查询所有用户
List<User> getUserList();

```

```xml
<select id="getUserList" resultTypr="user" parameterType="user">
      select * from user
</select>

```

测试

```java
public void query(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    //我们通过mybatis自带的插件RowBounds对象进行分页
    RowBounds rowBounds = new RowBounds(0,2);
    List<User> user = sqlSession.selectList("com.yunduo.dao.UserDaoMapper.getUserList", null, rowBounds);
    for (User user1 : user) {
        System.out.println(user1);
    }
}

```

## 使用注解开发

>  
 注解只能完成简单sql语句，复杂的建议使用xml形式 


```
@Select("select * from user")
List<User> queryUserList();

```

```
<!--    映射器注册-->
    <mappers>
        <mapper class="dao.UserMapper05"/>
    </mappers>

```

```java
public void test1(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper05 mapper = sqlSession.getMapper(UserMapper05.class);
    List<User> users = mapper.queryUserList();
    for (User user : users) {
        System.out.println(user);
    }
    sqlSession.close();
}

```

本质：用的反射机制实现

底层：动态代理。

<img src="https://img-blog.csdnimg.cn/img_convert/a2448a68e3736ff3630aeb6a578fd779.png" alt="image-20220224193531506"/>

## #号和$符号

#号是预编译是占位符，（prepared statement）编译好SQL语句再取值

$符号是取值后在编译，（createStatement）取值以后再去编译SQL语句

## Mybatis执行流程
1. Properties加载我们mybatis全局配置文件
2. 创建我们SqlSessionFactoryBuilder对象
3. SqlSessionFactoryBuilder的builder方法里面会解析mybatis全局配置文件
4.  最后得到我们configuration所有的配置信息
5. 接着就可以实例化我们的sqlsessionFactory对象
6.  sqlsessionfactory对象中里面有这个事务管理器，来控制我们sql的事务的
7. 接着创建executor执行器，来执行我们crud的操作的，
8. 然后检查crud是否执行成功，如果执行失败就回到我们事务管理器回滚，
9.  如果执行成功，就提交事务
1. 最后关闭
<img src="https://img-blog.csdnimg.cn/img_convert/716fa2a8514a9a5c22ba205329762803.png" alt="image-20220224195844821"/>

<img src="https://img-blog.csdnimg.cn/img_convert/776d40f9050eec8277d3c005a26ff1ba.png" alt="image-20220224195930917"/>

<img src="https://img-blog.csdnimg.cn/img_convert/5d0b7395b9e7abc77dfaee56be336bbf.png" alt="image-20220224195940236"/>

## 复杂查询

### 多对一

<img src="https://img-blog.csdnimg.cn/img_convert/544d02fc5607d06a6b479a93b7b43f8f.png" alt="image-20220224203220475"/>
- 多个学生对应一个老师- 对于学生这边而言，**关联**…多个学生，关联一个老师【多对一】- 对应老师这边而言，**集合**…一个老师有很多个学生【一对多】
<img src="https://img-blog.csdnimg.cn/img_convert/5966ac37b00fdba33481a51d7db0eeb3.png" alt="image-20220224203935675"/>

**测试环境搭建**
1. 导入lombok
1. 新建实体类Teacher，Student
1. 建立对应的Mapper接口
1. 建立Mapper.xml文件
1. 在核心配置文件中注册我们mapper接口
1. 测试查询环境搭建成功
#### 按照查询嵌套处理

<img src="https://img-blog.csdnimg.cn/img_convert/fb8696aeba86b7b3ce2f6d854760d03b.png" alt="image-20220225103556786"/>

<img src="https://img-blog.csdnimg.cn/img_convert/0bfce3592e4502d83f3594cf6fc5bc6b.png" alt="image-20220225103609971"/>

#### 按照结果嵌套处理

```xml
    <resultMap id="getstudent" type="student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
<!--        通过查询出来的结果去老师对象中映射-->
        <association property="teacher" javaType="teacher">
<!--            老师对象中的属性 对应 数据库中结果的字段-->
            <result property="name" column="teacher"/>
        </association>
    </resultMap>

    <select id="queryStudentList" resultMap="getstudent">
    select s.id id,s.name name,t.name teacher from student s,teacher t where s.tid=t.id
    </select>

```

<img src="https://img-blog.csdnimg.cn/img_convert/82a6a076201142f316a2d1b7c46317d7.png" alt=""/>

回顾mysql多对一查询方式
- 子查询 （select s.id,s.name,(select t.name from teacher t where s.tid=t.id) teacher from student s）
- 联表查询 （select s.id id,s.name name,t.name teacher from student s,teacher t where s.tid=t.id）
### 一对多

根据结果嵌套处理

```xml
<!--按照结果嵌套查询-->
    <resultMap id="queryTeacher" type="Teacher">
        <result property="id" column="tid"/>
        <result property="name" column="tname"/>
<!--        复杂属性条件，我们需要单独处理，对象使用association  集合使用collection
            javaType="" 指定属性的类型！
            集合中的泛型消息，我们使用ofType
-->
        <collection property="students" ofType="Student">
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="stid"/>
        </collection>
    </resultMap>

    <select id="queryTeacherList" resultMap="queryTeacher">
        select t.id tid,t.name tname,s.id sid,s.name sname,s.tid stid from teacher t,student s where s.tid=t.id and t.id=#{id}
    </select>

```

>  
 Teacher(id=1, name=秦老师, students=[Student(id=1, name=小明, tid=1), Student(id=2, name=小红, tid=1), Student(id=3, name=小张, tid=1), Student(id=4, name=小李, tid=1), Student(id=5, name=小王, tid=1)]) 


### 小结
1. 小结
   关联-association【多对一】
   集合-collection 【一对多】
   javaType & ofType
   javatype用来指定实体类中属性的类型
   oftypr用来指定映射到List或者集合中的pojo类型，泛型中的约束类型！

2. 注意点：

   保证SQL的可读性，尽量保证通俗易懂
   注意一对多和多对一中，属性名和字段的问题！
   如果问题不好排查错误，可以使用日志，建议使用Log4j
   慢SQL 1s 1000s

   

   面试高频

   Mysql引擎
   InnoDB底层原理
   索引
   索引优化
## 动态SQL

**什么是动态SQL：动态SQL就是根据不同的条件生成不同的SQL语句**

<img src="https://img-blog.csdnimg.cn/img_convert/ba3a170731a655a9f3c1931ddfbcdce5.png" alt="image-20220225150500957"/>

### IF

```
<select id="queryStudentList" resultType="Student" parameterType="map">
    select * from student
    <where>
        <if test="id!=null">
            id=#{id}
        </if>
        <if test="name!=null">
            and name=#{name}
        </if>
        <if test="tid!=null">
            and tid=#{tid}
        </if>
    </where>
</select>

```

```
SqlSession sqlSession = MybatisUtils.getSqlSession();
StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
HashMap<String, Object> map = new HashMap<String, Object>();
map.put("id",1);
map.put("name","小明");
map.put("tid",1);
List<Student> students = mapper.queryStudentList(map);
for (Student student : students) {
    System.out.println(student);
}

```

```
SELECT *
FROM student
WHERE id = 1
	AND name = '小明'
	AND tid = 1
==>com.yun.dao.StudentMapper.queryStudentList, cost=405ms
Student(id=1, name=小明, tid=1)

```

### choose（when，outerwise）

>  
 有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。 


```
<select id="queryStudentList2" resultType="Student" parameterType="map">
    select * from student
    <where>
        <choose>
            <!--这里条件匹配，从上往下，只能匹配一个 -->
            <when test="id!=null">
                id=#{id}
            </when>
            <when test="name!=null">
                and name=#{name}
            </when>
            <when test="tid!=null">
                and tid=#{tid}
            </when>
       <!-- 如果上面都不满足，就执行otherwise中的语句 -->
            <otherwise>
                and id=5
            </otherwise>
        </choose>
    </where>
</select>

```

```
    public void test2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("id",1);
//        map.put("name","小明");
        List<Student> students = mapper.queryStudentList2(map);
        for (Student student : students) {
            System.out.println(student);
        }
    }

load mybatis-log agent success.
==>com.yun.dao.StudentMapper.queryStudentList2
SELECT *
FROM student
WHERE id = 5
==>com.yun.dao.StudentMapper.queryStudentList2, cost=294ms
Student(id=5, name=小王, tid=1)

```

### Trim（where set）

>  
 前面几个例子已经方便地解决了一个臭名昭著的动态 SQL 问题。现在回到之前的 “if” 示例，这次我们将 “state = ‘ACTIVE’” 设置成动态条件，看看会发生什么。 


```
select * from student
<where>
    <if test="id!=null">
        id=#{id}
    </if>
    <if test="name!=null">
        and name=#{name}
    </if>
    <if test="tid!=null">
        and tid=#{tid}
    </if>
</where>

```

```
@Test
public void test1(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id",1);
    map.put("name","小明");
    map.put("tid",1);
    //这里我们就算什么都不传递，它会自动去掉where
    List<Student> students = mapper.queryStudentList(map);
    for (Student student : students) {
        System.out.println(student);
    }
}

load mybatis-log agent success.
==>com.yun.dao.StudentMapper.queryStudentList
SELECT *
FROM student
WHERE id = 1
	AND name = '小明'
	AND tid = 1
==>com.yun.dao.StudentMapper.queryStudentList, cost=310ms
Student(id=1, name=小明, tid=1)

```

**Set**

通常用于update语句

```
<update id="updateStudentByid" parameterType="map">
    update student
    <set>
        <if test="name!=null">
            name=#{name},
        </if>
        <if test="tid!=null">
            tid=#{tid},
        </if>
    </set>
    where id=#{id}
</update>

```

```
public void test3(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id",1);
    map.put("name","星辰");
    mapper.updateStudentByid(map);
    //提交事务
    sqlSession.commit();
    sqlSession.close();
}

UPDATE student
SET name = '星辰'
WHERE id = 1

```

### SQL片段

>  
 有的时候，我们可能会将一些功能的部分抽取出来，方便复用【工具类】 

1. 使用sql标签抽取出公共部分1. 在需要使用的地方，使用include标签引用即可
```
<!--    sql工具类-->
    <sql id="if-sql">
        <if test="id!=null">
            id=#{id}
        </if>
        <if test="name!=null">
            and name=#{name}
        </if>
        <if test="tid!=null">
            and tid=#{tid}
        </if>
    </sql>

<!-- 使用include标签引用-->
    <select id="queryStudentList" resultType="Student" parameterType="map">
        select * from student
        <where>
            <include refid="if-sql"></include>
        </where>
    </select>

```

```
public void test1(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id",1);
    //这里我们就算什么都不传递，它会自动去掉where
    List<Student> students = mapper.queryStudentList(map);
    for (Student student : students) {
        System.out.println(student);
    }
}

SELECT *
FROM student
WHERE id = 1
==>com.yun.dao.StudentMapper.queryStudentList, cost=364ms
Student(id=1, name=星辰, tid=1)

```

**注意：**
1. 最好基于单张表查询1. 不要存在where标签
### Foreach

>  
 需求，我想查出id为1，5，9，11的用户信息 
 select * from student where id in(1,5,9,11) 
 但是这样参数是固定的，我们需要传递一个可变长的list参数。 


```
<!--    select * from student where id in(1,2,5)-->
    <select id="querystudent" parameterType="list" resultType="student">
        select * from student
        <where>
            <foreach collection="list" item="id"
                     open="id in("  separator="," close=")">
                #{id}
            </foreach>
        </where>
    </select>

```

```
public void test4(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    arrayList.add(1);
    arrayList.add(5);
    List<Student> querystudent = mapper.querystudent(arrayList);
    for (Student student : querystudent) {
        System.out.println(student);
    }
}

SELECT *
FROM student
WHERE id IN (1, 5)
==>com.yun.dao.StudentMapper.querystudent, cost=255ms
Student(id=1, name=星辰, tid=1)
Student(id=5, name=小王, tid=1)

```

## 缓存

>  
 查询：链接数据库，耗资源 
 ​ 一次查询的结果，给他暂存在一个可以直接取到的地方！---->内存：缓存 
 我们再次查询相同数据的时候，直接走缓存，就不用走数据库了 

<li> 什么是缓存？ 
  1. 是存放在内存中的临时数据1. 将用户经常查询的数据放在缓存（内存）中，用户去查询数据就不用从磁盘上（数据库）查询，而是直接从缓存中查询，从而提高查询效率，解决了高并发系统问题。 </li><li> 为什么使用缓存？ 
  1. 减少和数据库交互的次数，减少系统的开箱，提高系统效率 </li><li> 什么样的数据能使用缓存 
  1. 经常查询并且不改变的数据【可以使用缓存】1. 增删改是不能用缓存的【不难使用缓存】 </li>- 减少和数据库交互的次数，减少系统的开箱，提高系统效率
### 怎么使用缓存
- Mybatis包含了一个非常强大的查询缓存特性，它可以非常方便地定制和配置缓存。缓存可以极大的提升查询效率。<li>Mybatis系统中默认定义了两级缓存：**一级缓存**，**二级缓存** 
  
  <ul>- 默认情况下，只有一级缓存开启，（Sqlsession级别的缓存，也成为本地缓存）- 二级缓存需要手动开启和配置，他是基于namespace级别的缓存- 为了提高高扩展性，Mybatis定义了缓存接口cache，我们可以通过实现cache接口来自定义二级缓存
### 一级缓存

>  
 仅在一个sqlsession中查询相同的才有效 

1. 开启日志1. 测试链接一个Sqlsession查询两次相同记录1. 查看日志输出
可以发现，myabtis中只执行了一次sql，那么第二次就是从缓存中拿取的

<img src="https://img-blog.csdnimg.cn/img_convert/1419365304798aa5ee5fda33caa23304.png" alt="image-20220227153217271"/>

SQL

<img src="https://img-blog.csdnimg.cn/img_convert/bc4ea5ed9a19e702a5cabf05f01a47da.png" alt="image-20220227153023234"/>

当我们查询不一样的数据的时候，会查询出两条sql

<img src="https://img-blog.csdnimg.cn/img_convert/62b033e35fa2ca3942886487e7f65920.png" alt="image-20220227153612905"/>

#### 缓存失效的情况

>  
 如果当前sqlsession中有查询又有增删改，那么就会出现缓存失效情况 


<img src="https://img-blog.csdnimg.cn/img_convert/712fef65dd4945fef55f11e73c1def6a.png" alt="image-20220227154542234"/>

<img src="https://img-blog.csdnimg.cn/img_convert/635e8e2198eae8b304a83a63b275cb61.png" alt="image-20220227154535672"/>
1.  查询出不同的东西 1.  增删改可能会对我们查询的数据做出更改，所以会清除当前缓存 1.  查询不同Mapper <li> 手动清除缓存 <pre><code class="prism language-java">sqlSession.clearCache();
</code></pre> </li>
#### 小结

>  
 Mybatis一级缓存是默认开启的，只在一次sqlsession中开启和关闭之间是有效的。 


一级缓存就是一个map，第一次获取时候put进去，后续在取得时候直接get

<img src="https://img-blog.csdnimg.cn/img_convert/099ab0055e4e9328f4d2bad6c591d3ff.png" alt="image-20220227160009103"/>

### 二级缓存

>  
 生效条件，当前sqlsession死亡后，才会将当前得数据保存进二级缓存中。 

- 二级缓存也叫全局缓存，一级缓存作用域太低，所以诞生了二级缓存- 基于namespace级别得缓存，一个名称空间，对应一个二级缓存；<li>工作机制 
  <ul>- 一个会话查询一条语句，这个数据就会被放在当前会话得一级缓存中；<li>如果当前会话关闭了，这个会话对应得一级缓存就没了；但是我们想要得是，会话关闭了，一级缓存中得数据被保存到二级缓存中； 
    <ul>- 新的会话查询信息，就可以从二级缓存中获取内容；- 不同得mapper查出得数据会被放在对应得缓存（map）中
  **使用**
  <li> 在mybatis配置中心开启 <pre><code class="prism language-xml"><!--        开启缓存-->
        <setting name="cacheEnabled" value="true"/>
  </code></pre> </li><li> 在需要使用二级缓存得Mapper中添加开启 <pre><code class="prism language-xml"><!--方法一：-->
  <cache/>

<!--方法二，自定义参数-->
<!--    开启缓存 eviction清除策略，flushInterval刷新时间，size缓存大小，readOnly是否只读-->
    <cache
            eviction="FIFO"
            flushInterval="60000"
            size="512"
            readOnly="true"/>
    />
</code></pre> </li>
**问题:**

使用缓存，一定要得我们pojo进行序列化，实现Serializable接口

**测试**

mapper

```
<!--    开启缓存 eviction清除策略，flushInterval刷新时间，size缓存大小，readOnly是否只读-->
    <cache/>

    <resultMap id="queryUserListmap" type="com.yunduo.pojo.User">
        <result property="password" column="pwd"/>
    </resultMap>

    <select id="queryUserList" resultMap="queryUserListmap" parameterType="com.yunduo.pojo.User">
        select * from user where id=#{id}
    </select>

```

pojo

```
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private int id;
    private String name;
    private String password;
}

```

test

```
@Test
public void test1(){
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User user = mapper.queryUserList(1);
    System.out.println(user);
    sqlSession.close();

    SqlSession sqlSession1 = MybatisUtils.getSqlSession();
    UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
    User user1 = mapper1.queryUserList(1);
    System.out.println(user1);
    sqlSession.close();
    System.out.println(user==user1);
}


```

结果

```
Cache Hit Ratio [com.yunduo.dao.UserMapper]: 0.0
Opening JDBC Connection
Created connection 127702987.
==>  Preparing: select * from user where id=? 
==> Parameters: 1(Integer)
<==    Columns: id, name, pwd
<==        Row: 1, 星辰, 123456
<==      Total: 1
==>com.yunduo.dao.UserMapper.queryUserList
SELECT *
FROM user
WHERE id = 1
==>com.yunduo.dao.UserMapper.queryUserList, cost=316ms
User(id=1, name=星辰, password=123456)
Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@79c97cb]
Returned connection 127702987 to pool.
Cache Hit Ratio [com.yunduo.dao.UserMapper]: 0.5
User(id=1, name=星辰, password=123456)
false

```

#### 小结
- 只要开启了二级缓存，在同一个Mapper下才有效- 所有得数据都会先放在一级缓存中；- 只有当会话提交，或者关闭得时候，才会提交到二级缓存中！
### 缓存原理
1. 先查询二级缓存有没有1. 在查询一级缓存有没有1. 最后查询数据库
<img src="https://img-blog.csdnimg.cn/img_convert/6b671f5c59b621dc4d0b397336face2c.png" alt="image-20220227163544770"/>

### 自定义缓存

>  
 Ehcache是一种广泛用于Java的分布式缓存，主要面向通用缓存 


**使用**
<li> 导包 <pre><code class="prism language-xml"><!-- https://mvnrepository.com/artifact/org.mybatis.caches/mybatis-ehcache -->
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.1.0</version>
</dependency>
</code></pre> </li>1.  修改mapper中cache中的type 
```
    <cache type="org.mybatis.caches.ehcache"/>

```
1. 添加配置文件
```
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">
    <!--
       diskStore：为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。参数解释如下：
       user.home – 用户主目录
       user.dir  – 用户当前工作目录
       java.io.tmpdir – 默认临时文件路径
     -->
    <diskStore path="java.io.tmpdir/Tmp_EhCache"/>
    <!--
       defaultCache：默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。
     -->
    <!--
      name:缓存名称。
      maxElementsInMemory:缓存最大数目
      maxElementsOnDisk：硬盘最大缓存个数。
      eternal:对象是否永久有效，一但设置了，timeout将不起作用。
      overflowToDisk:是否保存到磁盘，当系统当机时
      timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
      timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
      diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
      diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
      diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
      memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
      clearOnFlush：内存数量最大时是否清除。
      memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。
      FIFO，first in first out，这个是大家最熟的，先进先出。
      LFU， Less Frequently Used，就是上面例子中使用的策略，直白一点就是讲一直以来最少被使用的。如上面所讲，缓存的元素有一个hit属性，hit值最小的将会被清出缓存。
      LRU，Least Recently Used，最近最少使用的，缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
   -->
    <defaultCache
            eternal="false"
            maxElementsInMemory="10000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="259200"
            memoryStoreEvictionPolicy="LRU"/>

    <cache
            name="cloud_user"
            eternal="false"
            maxElementsInMemory="5000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="1800"
            memoryStoreEvictionPolicy="LRU"/>

</ehcache>

```
# **文章地址： **https://blog.csdn.net/oemciemcier/article/details/126115853