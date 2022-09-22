# Mybatis从入门到精通系列 10——Mybatis多表查询之多对多查询
  本文针对 Mybatis 多表查询的多对多查询进行详细讲解。

  本文案例主要以用户和角色的模型来分析 Mybatis 多表关系。用户为 User 表，角色表为 Role 表，中间表为 User_role。一个用户可以拥有多个角色，一个角色可以赋予多个用户，role_desc 代表角色描述。具体关系如下：

<img src="https://img-blog.csdnimg.cn/20210507214611540.png?#pic_center" alt="在这里插入图片描述" width="700"/>

---


 # 文章目录
一、多表查询（多对多）分析步骤
二、多对多查询
2.1 编写角色实体类
2.2 编写 SQL 语句
2.3 定义持久层角色的 Dao 接口
2.4 IRoleDao.xml 文件中的查询配置信息
2.5 测试方法
2.6 （补充）实现 User 到 到 Role 的多对多

---


## 一、多表查询（多对多）分析步骤

  多对多关系其实我们看成是双向的一对多关系，日常生活中这种关系也非常的常见，比如：一个用户可以拥有多个角色，一个角色可以赋予多个用户。   

​       示例：一个用户可以有多个角色，一个角色可以赋予多个用户

---


**步骤:**
1. **建立三张表:用户表，角色表，用户角色表** 让用户表和角色表具有多对多的关系。需要使用中间表，中间表中包含各自的主键，在中间表中是外键。
1. **建立两个实体类:用户实体类和角色实体类** 让用户和角色的实体类能体现出来多对多的关系, 各自包含对方一个集合引用。
1.  **建立两个配置文件** 用户的配置文件 角色的配置文件
1.  **实现配置** 当我们查询用户时，可以同时得到用户所包含的角色信息 当我们查询角色时，可以同时得到角色的所赋子用户信息
---

下面在 mysql 中建立 User 表、role表以及 user_role 表，并在 user_role 建立索引，索引名称以及外键是否建立在本文的案例中没有强制要求。

```sql

-- ----------------------------
-- Table structure for role
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mybatisdb;
USE mybatisdb;
DROP TABLE IF EXISTS role;
CREATE TABLE IF NOT EXISTS role (
  id int(11) NOT NULL AUTO_INCREMENT,
  role_name varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  role_desc varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mybatisdb;
USE mybatisdb;
DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS user_role (
  uid int(11) NOT NULL AUTO_INCREMENT,
	rid int(11) NOT NULL ,
  PRIMARY KEY (uid) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;
```

<img src="https://img-blog.csdnimg.cn/20210507220359237.png#pic_left" alt="在这里插入图片描述" width="700"/> <img src="https://img-blog.csdnimg.cn/20210507220836678.png?#pic_left#pic_left" alt="在这里插入图片描述" width="700"/> <img src="https://img-blog.csdnimg.cn/20210507220932899.png#pic_left#pic_left" alt="在这里插入图片描述" width="700"/> <img src="https://img-blog.csdnimg.cn/20210507221143560.png#pic_left#pic_left" alt="在这里插入图片描述" width="700"/>



## 二、多对多查询

**需求：** 实现查询所有对象并且加载它所分配的用户信息。

**分析：**   查询角色我们需要用到 role表，但角色分配的用户的信息我们并不能直接找到用户信息，而是要通过中间表(user_role 表)才能关联到用户信息。

**工程目录：**

 <img src="https://img-blog.csdnimg.cn/20210507224533319.png?#pic_left" alt="在这里插入图片描述" width="300"/>

---


### 2.1 编写角色实体类

需要说明的是：文中为了方便观察查询结果，toString 方法并没有打印多对多关系映射的 List 集合。

```java
public class Role implements Serializable {

    private Integer roleId;
    private String roleName;
    private String roleDesc;

    //多对多关系映射，一个角色可以赋予多个用户
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }
}

```

---


### 2.2 编写 SQL 语句

```sql
select u.*, r.id as rid, r.role_name, r.role_desc from role r
 left outer join user_role ur on r.id = ur.rid
 left outer join user u on u.id = ur.uid

```

```sql
-- ----------------------------
-- Records of user
-- ----------------------------
REPLACE INTO user VALUES (41, 'user1', date_format('20180227', '%Y%m%d'),'m','address1');
REPLACE INTO user VALUES (42, 'user2', date_format('20180302', '%Y%m%d'),'m','address2');
REPLACE INTO user VALUES (43, 'user2', date_format('20220904', '%Y%m%d'),'f','address3');
REPLACE INTO user VALUES (45, 'admin4', date_format('20150905', '%Y%m%d'),'f','address4');
REPLACE INTO user VALUES (46, 'admin3', date_format('20210904', '%Y%m%d'),'f','address3');
REPLACE INTO user VALUES (47, 'admin4', date_format('20220905', '%Y%m%d'),'f','address4');



-- ----------------------------
-- Records of role
-- ----------------------------
REPLACE INTO role VALUES (1,'student','study');
REPLACE INTO role VALUES (2,'teacher','manage student');
REPLACE INTO role VALUES (3,'boss','manage employee');
REPLACE INTO role VALUES (4,'employee','work');

-- ----------------------------
-- Records of user_role
-- ----------------------------
REPLACE INTO user_role VALUES (43,1);
REPLACE INTO user_role VALUES (43,2);
REPLACE INTO user_role VALUES (44,1);
REPLACE INTO user_role VALUES (45,2);
REPLACE INTO user_role VALUES (46,3);


```

用户表中的数据：

```
mysql> select * from user;
+----+----------+---------------------+------+----------+
| id | username | birthday            | sex  | address  |
+----+----------+---------------------+------+----------+
|  1 | admin1   | 2022-09-01 00:00:00 | m    | address1 |
|  2 | admin2   | 2022-09-02 00:00:00 | m    | address2 |
|  3 | admin3   | 2022-09-04 00:00:00 | f    | address3 |
| 41 | user1    | 2018-02-27 00:00:00 | m    | address1 |
| 42 | user2    | 2018-03-02 00:00:00 | m    | address2 |
| 43 | user2    | 2022-09-04 00:00:00 | f    | address3 |
| 45 | admin4   | 2015-09-05 00:00:00 | f    | address4 |
| 46 | admin3   | 2021-09-04 00:00:00 | f    | address3 |
| 47 | admin4   | 2022-09-05 00:00:00 | f    | address4 |
+----+----------+---------------------+------+----------+
9 rows in set (0.00 sec)

```

角色表中的数据： 

```
mysql> select * from role;
+----+-----------+-----------------+
| id | role_name | role_desc       |
+----+-----------+-----------------+
|  1 | student   | study           |
|  2 | teacher   | manage student  |
|  3 | boss      | manage employee |
|  4 | employee  | work            |
+----+-----------+-----------------+
4 rows in set (0.01 sec)
```

用户角色表中的数据：

```

mysql> select * from user_role;
+-----+-----+
| uid | rid |
+-----+-----+
|  43 |   2 |
|  44 |   1 |
|  45 |   2 |
|  46 |   3 |
+-----+-----+
4 rows in set (0.00 sec)

mysql>
```

查询结果：

```
mysql> select u.*, r.id as rid, r.role_name, r.role_desc from role r
    ->  left outer join user_role ur on r.id = ur.rid
    ->  left outer join user u on u.id = ur.uid;
+------+----------+---------------------+------+----------+-----+-----------+-----------------+
| id   | username | birthday            | sex  | address  | rid | role_name | role_desc       |
+------+----------+---------------------+------+----------+-----+-----------+-----------------+
| NULL | NULL     | NULL                | NULL | NULL     |   1 | student   | study           |
|   45 | admin4   | 2015-09-05 00:00:00 | f    | address4 |   2 | teacher   | manage student  |
|   43 | user2    | 2022-09-04 00:00:00 | f    | address3 |   2 | teacher   | manage student  |
|   46 | admin3   | 2021-09-04 00:00:00 | f    | address3 |   3 | boss      | manage employee |
| NULL | NULL     | NULL                | NULL | NULL     |   4 | employee  | work            |
+------+----------+---------------------+------+----------+-----+-----------+-----------------+
5 rows in set (0.02 sec)

mysql>
```



---


### 2.3 定义持久层角色的 Dao 接口

```java
public interface IRoleDao {
    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();
}

```

---


### 2.4 IRoleDao.xml 文件中的查询配置信息

<font color="red">**注意：** 当 sql 语句起了别名之后，column 应该修改为别名的字段</font>

对于配置标签中的属性，在下文链接中有详细解析👇： Mybatis从入门到精通系列 09——Mybatis多表查询之一对多查询</a>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IRoleDao">

    <!--定义 role 的resultMap-->
    <resultMap id="roleMap" type="role">
        <id property="roleId" column="rid"></id>
        <result property="roleName" column="role_name"></result>
        <result property="roleDesc" column="role_desc"></result>
        <collection property="users" ofType="user">
            <id column="id" property="id"></id>
            <result column="username" property="username"></result>
            <result column="address" property="address"></result>
            <result column="sex" property="sex"></result>
            <result column="birthday" property="birthday"></result>
        </collection>
    </resultMap>

    <!--配置查询所有-->
    <select id="findAll" resultMap="roleMap">
        select u.*, r.id as rid, r.role_name, r.role_desc from role r
         left outer join user_role ur on r.id = ur.rid
         left outer join user u on u.id = ur.uid
    </select>
    
</mapper>

```

---


### 2.5 测试方法

```java
public class RoleTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IRoleDao roleDao;

    @Before//单元测试之前执行
    public void init() throws Exception{
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象
        roleDao = sqlSession.getMapper(IRoleDao.class);
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
        List<Role> roles = roleDao.findAll();
        for (Role role:roles){
            System.out.println("-----每个角色的信息-----");
            System.out.println(role);
            System.out.println(role.getUsers());
        }
    }
}

```

查询结果如下： 

```
18:23:29.524 [main] DEBUG com.itheima.dao.IRoleDao.findAll - <==      Total: 5
-----每个角色的信息-----
Role{roleId=1, roleName='student', roleDesc='study'}
[]
-----每个角色的信息-----
Role{roleId=2, roleName='teacher', roleDesc='manage student'}
[User{id=45, username='admin4', birthday=Sat Sep 05 00:00:00 CST 2015, sex='f', address='address4'}, User{id=43, username='user2', birthday=Sun Sep 04 00:00:00 CST 2022, sex='f', address='address3'}]
-----每个角色的信息-----
Role{roleId=3, roleName='boss', roleDesc='manage employee'}
[User{id=46, username='admin3', birthday=Sat Sep 04 00:00:00 CST 2021, sex='f', address='address3'}]
-----每个角色的信息-----
Role{roleId=4, roleName='employee', roleDesc='work'}
[]
```



---


### 2.6 （补充）实现 User 到 到 Role 的多对多

**实体类：**

```java
public class User {
    private Integer id;
    private String username;
    private String address;
    private String sex;
    private Date birthday;

    //多对多关系映射，一个用户可以具备多个角色
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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


**用户持久层接口：**

```java
public interface IUserDao {
    /**
     * 查询所有用户,同时获取到用户下所有角色的信息
     * @return
     */
    List<User> findAll();
}

```

---


**编写 SQL 语句：**

```sql
select u.*, r.id as rid, r.role_name, r.role_desc from user u
  left outer join user_role ur on u.id = ur.uid
  left outer join role r on r.id = ur.rid

```

---


**IUserDao.xml 文件中的查询配置信息：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IUserDao">

    <!--定义user的resultMap-->
    <resultMap id="userMap" type="user">
        <id property="id" column="id"></id>
        <result property="username" column="username"></result>
        <result property="address" column="address"></result>
        <result property="sex" column="sex"></result>
        <result property="birthday" column="birthday"></result>
        <!--配置角色集合的映射-->
        <collection property="roles" ofType="role">
            <id property="roleId" column="rid"></id>
            <result property="roleName" column="role_name"></result>
            <result property="roleDesc" column="role_desc"></result>
        </collection>
    </resultMap>

    <!--配置查询所有-->
    <select id="findAll" resultMap="userMap">
        select u.*, r.id as rid, r.role_name, r.role_desc from user u
         left outer join user_role ur on u.id = ur.uid
         left outer join role r on r.id = ur.rid
    </select>

</mapper>

```

**测试方法：**

```java
/**
 * 测试查询所有
 */
@Test
public void testFindAll() {
    List<User> users = userDao.findAll();
    for (User user:users) {
        System.out.println("------每个用户的信息-------");
        System.out.println(user);
        System.out.println(user.getRoles());
    }
}

```

**查询结果：** <img src="https://img-blog.csdnimg.cn/20210507225430960.png?#pic_left" alt="在这里插入图片描述"/>

---


本文针对 Mybatis 中多表查询多对多查询进行了总结归纳，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/116503508