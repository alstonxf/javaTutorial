# Mybatis从入门到精通系列 08——动态 SQL 语句
Mybatis 的映射文件中，前面我们的 SQL 都是比较简单的，有些时候业务逻辑复杂时，我们的 SQL 是动态变化的，此时在前面的学习中我们的 SQL 就不能满足要求了。那么本文针对 Mybatis 动态 SQL 语句进行简单的操作。

后文应用 typeAliases 标签指定了所有实体类都注册别名，并且类名就是别名，参考链接如下： Mybatis从入门到精通系列 06——properties 和 typeAliase 标签总结</a> <img src="https://img-blog.csdnimg.cn/20210423225938742.png#pic_center" alt="在这里插入图片描述"/>

---


 # 文章目录
一、动态 SQL 的 if 标签
二、动态 SQL 的 where 标签
三、动态 SQL 的 foreach 标签
3.1 传入 List 集合
3.2 传入包装类 QueryVo
四、动态 SQL 的 bind 标签
五、动态 SQL 的 set 标签
六、动态 SQL 的 trim 标签
七、Mybatis 中简化编写的 SQL 片段

---


## 一、动态 SQL 的 if 标签

  我们根据实体类的不同取值，使用不同的 SQL 语句来进行查询。比如在 id 如果不为空时可以根据 id 查询，如果 username 不同空时还要加入用户名作为条件。这种情况在我们的多条件组合查询中经常会碰到。

**持久层 Dao 接口：**

```java
/**
 * 使用 if 标签
 */
List<User> findUserIfLabelByCondition(User user);

```

**持久层 Dao 映射配置：**

```xml
<!--根据条件查询，if 标签的使用-->
<select id="findUserIfLabelByCondition" resultType="user" parameterType="user">
    select * from user where 1 = 1
    <if test="id != null">
        and id = #{id}
    </if>
    <if test="username!= null">
        and username = #{sex}
    </if>
</select>

```

注意：if 标签的 test 属性中写的是对象的属性名，如果是包装类的对象要使用 OGNL 表达式的写法。 另外要注意 where 1=1 的作用！

**测试方法：**

```java
/**
 * 测试条件查询
 */
@Test
public void testFindByCondition(){
    User u = new User();
    u.setUsername("老王");
    u.setId(43);
    List<User> users = userDao.findUserIfLabelByCondition(u);
    for (User user:users){
        System.out.println(user);
    }
}

```

---


## 二、动态 SQL 的 where 标签

==为了简化上面 where 1=1 的条件拼装，我们可以采用标签来简化开发。==

<where> </where> 标签等同于 where 1=1

**持久层 Dao 接口：**

```java
/**
 * 使用 where 标签
 */
List<User> findUserWhereLabelByCondition(User user);

```

**持久层 Dao 映射配置：**

```xml
<!--根据条件查询，where 标签的使用-->
<select id="findUserWhereLabelByCondition" resultType="user" parameterType="user">
    select * from user
    <where>
        <if test="id != null">
	        and id = #{id}
	    </if>
	    <if test="username!= null">
	        and username = #{sex}
	    </if>
    </where>
</select>

```

**测试方法：**

```java
/**
 * 测试条件查询
 */
@Test
public void testFindUserWhereLabelByCondition(){
    User u = new User();
    u.setUsername("老王");
    u.setId(43);
    List<User> users = userDao.findUserWhereLabelByCondition(u);
    for (User user:users){
        System.out.println(user);
    }
}

```

---


## 三、动态 SQL 的 foreach 标签

**这里我们有一个需求：**

当传入多个 id 查询用户信息，用下边下面的 sql 实现：

select * from user where id in ( ? , ? , ? )

这样我们在进行范围查询时，就要将一个集合中的值，作为参数动态添加进来。

---


### 3.1 传入 List 集合

**持久层 Dao 接口：**

```java
/**
 * 根据 list 中提供的id集合，查询用户信息
 */
List<User> findUserInIdsByList(List list);

```

**持久层 Dao 映射配置：**

相当于执行sql：select * from user where 1=1 and id in (,,,,,,) 括号内是list的元素。

```xml

<!--根据 List 实现查询用户列表-->
<select id="findUserInIdsByList" resultType="user" parameterType="list">
    select * from user
    <where>
        <if test="list!=null and list.size()>0">
        <foreach collection="list" open="and id in (" close=")" item="sid" separator=",">
            #{sid}
        </foreach>
        </if>
    </where>
</select>

```

其中，foreach 标签用于遍历集合，它的属性：
- collection:代表要遍历的集合元素，注意编写时不要写#{}

- open:代表语句的开始部分

- close:代表结束部分

- item:代表遍历集合的每个元素，生成的变量名

- separator:代表分隔符

  **测试方法：**

```java
@Test
public void testFindInIdsByList(){
     List<Integer> list = new ArrayList<Integer>();
     list.add(41);
     list.add(42);
     list.add(47);

     List<User> users = userDao.findUserInIdsByList(list);
     for (User user:users){
         System.out.println(user);
     }
 }

```

---


### 3.2 传入包装类 QueryVo

在 QueryVo 中加入一个 List 集合用于封装参数：

（注：这里的 QuryVo 代表的是包装对象，读者可参考以下链接查看关于此类型参数的详情解析） Mybatis从入门到精通系列 05——Mybatis 传入参数总结 </a>

**QueryVo 实体类：**

```java
public class QueryVo {
    private User user;
    private List<Integer> ids;

    public User getUser(){
        return user;
    }
    
    public void setUser(User user){
        this.user = user;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}

```

**持久层 Dao 接口：**

```java
/**
 * 根据 queryvo 中提供的id集合，查询用户信息
 */
 List<User> findUserInIdsByQueryVo(QueryVo vo);

```

**持久层 Dao 映射配置：**

```xml
<!--根据queryvo中的id集合实现查询用户列表-->
<select id="findUserInIdsByQueryVo" resultType="user" parameterType="queryvo">
    select * from user
    <where>
        <if test="ids != null">
            <foreach collection="ids" open="and id in (" close=")" item="sid" separator=",">
                #{sid}
            </foreach>
        </if>
    </where>
</select>

```

**测试方法：**

```java
/**
 * 测试子查询
 */
@Test
public void testFindInIds(){
    QueryVo vo = new QueryVo();
    List<Integer> list = new ArrayList<Integer>();
    list.add(41);
    list.add(42);
    list.add(47);
    vo.setIds(list);
    List<User> users = userDao.findUserInIdsByQueryVo(vo);
    for (User user:users){
        System.out.println(user);
    }
}

```

---


## 四、动态 SQL 的 bind 标签

bind 标签可以使用 OGNL 表达式创建一个变量井将其绑定到上下文中，==其主要用于模糊查询，防止 SQL 注入。==

**持久层 Dao 接口：**

```java
/**
 * 使用 bind 标签
 */
List<User> findUserByBindLabel(User user);

```

**持久层 Dao 映射配置：**

```xml
<select id="findUserByBindLabel" parameterType="user" resultType="user">
 	<!-- bind 中的 username 是 user 的属性名-->        
    <bind name="nameLike" value="'%' + username + '%'"/>
    select * from user where username like #{nameLike}
</select>

```

**测试方法：**

```java
 @Test
public void testFindUserByBindLabel(){
    User u = new User();
    u.setUsername("王");

    List<User> users = userDao.findUserByBindLabel(u);
    for (User user:users){
        System.out.println(user);
    }
}

```

---


## 五、动态 SQL 的 set 标签

set 标签是 mybatis 提供的一个智能标记，我一般将其用在修改的 sql 中，例如以下情况：

**持久层 Dao 映射配置：**

```xml
<update id="updateUserBySetLabel" parameterType="user">
	update user
    <set>
        <if test="username != null">username = #{username},</if>
        <if test="sex != null">sex = #{sex},</if>
    </set>
    where id = #{id}
</update>

```

在上述的代码片段当中，假如说现在三个字段都有值得话，那么上面打印的SQL语句如下： 

update user set username=‘xxx’ , sex=‘xx’ where id=‘x’

<font color=red>set标记已经自动帮助我们把最后一个逗号给去掉了。</font>

参考博客：https://www.cnblogs.com/qiankun-site/p/5758383.html</a>

---


## 六、动态 SQL 的 trim 标签

  mybatis 的 trim 标签一般用于去除 sql 语句中多余的 and 关键字，逗号，或者给 sql 语句前拼接 “where“、“set“ 以及“values (“ 等前缀，或者添加“)“等后缀，可用于选择性插入、更新、删除或者条件查询等操作。

| 属性            | 描述                                                         |
| :-------------- | :----------------------------------------------------------- |
| prefix          | 给sql语句拼接的前缀                                          |
| suffix          | 给sql语句拼接的后缀                                          |
| prefixOverrides | 去除sql语句前面的关键字或者字符，该关键字或者字符由prefixOverrides属性指定，假设该属性指定为"AND"，当sql语句的开头为"AND"，trim标签将会去除该"AND" |
| suffixOverrides | 去除sql语句后面的关键字或者字符，该关键字或者字符由suffixOverrides属性指定 |

持久层 Dao 映射配置：

```xml
<select id="findUserByTrimLabel" parameterType="user" resultType="user">
 	select * from user
    <trim prefix="WHERE" prefixOverrides="AND">
        <if test="username != null">
            username = #{username}
        </if>
        <if test="sex != null">
            sex = #{sex}
        </if>
    </trim>
</select>

```

此处对于 trim 标签的使用讲解不是特别清楚，读者可参考以下链接：

参考博客：https://blog.csdn.net/wt_better/article/details/80992014

---


## 七、Mybatis 中简化编写的 SQL 片段

Sql 标签中可将重复的 sql 提取出来，使用时用 include 引用即可，最终达到 sql 重用的目的。

**定义代码片段：**

```xml
<!-- 抽取重复的语句代码片段 -->
<sql id="defaultSql">
	select * from user
</sql>

```

**引用代码片段：**

```xml
<!--配置查询所有-->
<select id="findAll" resultType="user">
    <include refid="defaultUser"></include>/*引用sql标签中的sql语句,refid为sql的id*/
</select>

<!--根据id查询用户-->
<select id="findById" parameterType="INT" resultType="user">
   <include refid="defaultUser"></include> where id = #{id}
</select>

```

---


本文针对 Mybatis 中的动态 SQL 语句使用进行了总结归纳，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/116074898