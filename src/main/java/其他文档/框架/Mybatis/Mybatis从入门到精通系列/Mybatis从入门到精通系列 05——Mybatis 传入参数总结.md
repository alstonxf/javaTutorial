# Mybatis从入门到精通系列 05——Mybatis 传入参数总结
  本文针对 Mybatis 传入类型的参数做出一定量的总结。

<img src="https://img-blog.csdnimg.cn/20210420220236102.png#pic_center" alt="在这里插入图片描述"/>

---


 # 文章目录
一、简单类型的参数
1.1 单个参数
1.2 多个参数
二、pojo 对象和 pojo 包装对象
2.1 传递 pojo 对象
2.2 传递 pojo 包装对象
三、传入参数类型是 Map
四、传入参数类型是 List

---


## 一、简单类型的参数

### 1.1 单个参数

<font color=red>基本类型或者基本类型包装类并且占位符只有一个参数时，可以随意写，并且 parameterType 可写可不写。</font>

```cpp
/**
 * 根据 id 查询用户信息
 */
User findById(Integer userId);

<!--根据id查询用户-->
<select id="findById" resultType="com.ithiema.domain.User">
    select * from user where id = #{uid}
</select>

```

---


### 1.2 多个参数

<font color=red>多个参数时，#{ } 中的参数名与方法中的 @Param() 里的参数名一致，并且 parameterType 不用写。</font>

```cpp
/**
 * 分页查询
 */
List<User> findByPage(@Param("start")int start, @Param("rows")int rows);

<!--分页查询-->
<select id="findByPage" resultType="com.ithiema.domain.User">
    select * from user limit #{start}, #{rows}
</select>

```

---


## 二、pojo 对象和 pojo 包装对象

  Mybatis 使用 ognl 表达式解析对象字段的值，#{ } 或者 ${ } 括号中的值为 pojo 属性名称。这里简单介绍一下 ONGL 表达式，它是通过对象的取值方法来获取数据，**在写法上把 get 给省略了**。

如：获取用户的名称

- 类中的写法: user. getUsername() ，而 OGNL 表达式中的写法为: user.username。
- <font color=red>mybatis中为什么能直接写 username，而不用 user. 呢。因为在parameterType 中已经提供了属性所属的类，所以此时不需要写对象名。</font>
### 2.1 传递 pojo 对象

```cpp
/**
 * 更新用户
 */
void updateUser(User user);

<!--更新用户-->
<update id="updateUser" parameterType="com.ithiema.domain.User">
    update user set username=#{username}, address=#{address}, sex=#{sex}, birthday=#{birthday} where id=#{id}
</update>

```

### 2.2 传递 pojo 包装对象

  pojo 包装对象，顾名思义就是把实体类包装起来，它可以将实体类和其他成员变量封装在一起。这里我们拿 pojo 包装对象 queryVo 举例，将 queryVo 作为输入参数，可以它的成员变量作为 sql 的值。

```cpp
/**
 * 根据QueryVo中的条件查询用户
 */
List<User> findUserByVo(QueryVo vo);

<!--根据queryvo的条件查询用户-->
<select id="findUserByVo" parameterType="com.ithiema.domain.QueryVo" resultType="com.ithiema.domain.User">
    select * from user where username like #{user.username}
</select>

```

---


## 三、传入参数类型是 Map

```c
/**
 * 传递Map
 */
public List<User> getBeanList(HashMap map);  

<select id="getBeanList" parameterType="hashmap" resultType="com.itheima.domain.User">
　　select * from user where id=#{MapKey1} and code = #{MapKey2}  
</select>  

```

其中 hashmap 是 mybatis 自己配置好的直接使用就行。map 中 key 的名字是那个就在 #{ } 使用那个

参考博客：https://www.cnblogs.com/mingyue1818/p/3714162.html</a>

---


## 四、传入参数类型是 List

```c
/**
 * 传递List
 */
List<User> findByList(List list);

<!--根据 List 集合中的id集合实现查询用户列表-->
<select id="findByList" resultType="com.itheima.domain.User" parameterType="java.util.List">
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

传入 List 集合的这种形式除了以上这种，还可以把 List 放在包装类中，通过 NGOL 表达式来获得，后面会一一介绍。

---


本文基于之前遇到的参数类型进行总结，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/115920315