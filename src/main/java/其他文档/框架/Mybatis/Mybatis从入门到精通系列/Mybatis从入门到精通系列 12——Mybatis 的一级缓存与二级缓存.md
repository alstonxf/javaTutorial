# Mybatis从入门到精通系列 12——Mybatis 的一级缓存与二级缓存
  Mybatis 框架也有提供了缓存策略，通过缓存策略来减少数据库的查询次数，从而提高性能。Mybatis中缓存分为一级缓存，二级缓存。

<img src="https://img-blog.csdnimg.cn/20210509224707560.png?#pic_center" alt="在这里插入图片描述" width="600"/>

---


 # 文章目录
一、问题题出
二、Mybatis 的一级缓存
2.1 一级缓存概述
2.2 一级缓存实现
2.2 一级缓存注意事项
三、Mybatis 的二级缓存
3.1 二级缓存概述
3.2 二级缓存的开启与关闭
四、补充：注解配置开启二级缓存

---


## 一、问题题出
1.  什么是缓存 <mark>存在于内存中的临时数据。</mark> 
1.  为什么使用缓存 <mark>减少和数据库的交互次数，提高执行效率。</mark> 
1.  什么样的数据能使用缓存，什么样的数据不能使用 
  <blockquote> 
   **适用于缓存:** 经常查询并且不经常改变的。数据的正确与否对最终结果影响不大的。 **不适用于缓存:** ① 经常改变的数据; ②数据的正确与否对最终结果影响很大的。   例如：商品的库存，银行的汇率，股市的牌价。 
  </blockquote> </li>
---


## 二、Mybatis 的一级缓存

### 2.1 一级缓存概述

**一级缓存:**   它指的是 Mybatis中 **SqlSession 对象的缓存**。当我们执行查询之后，查询的结果会同时存入到 SqlSession 为我们提供一块区域中。该区域的结构是一个 Map。当我们再次查询同样的数据，**Mybatis 会先去 Sqlsession 中查询是否有，有的话直接拿出来用。** 当 SqlSession对象消失时，Mybatis的一级缓存也就消失了。

---


### 2.2 一级缓存实现

**示例代码1：**

```java
@Test
public void testFirstLevelCache_1() {
    User user1 = userDao.findById(41);
    System.out.println(user1);
    User user2 = userDao.findById(41);
    System.out.println(user2);
    System.out.println(user1==user2);//打印true
}

```

测试结果：

<img src="https://img-blog.csdnimg.cn/20210509230302818.png?#pic_center" alt="在这里插入图片描述"/>

  我们可以发现，虽然在上面的代码中我们查询了两次，但最后只执行了一次数据库操作，这就是 Mybatis 提供给我们的一级缓存在起作用了。因为一级缓存的存在，导致第二次查询 id 为 41 的记录时，并没有发出 sql 语句从数据库中查询数据，而是从一级缓存中查询。

---

**示例代码2：**

```java
@Test
public void testFirstLevelCache_2() {
     User user1 = userDao.findById(41);
     System.out.println(user1);
	// sqlSession.close();
	// sqlSession = factory.openSession();

     sqlSession.clearCache();//此方法也可以清空缓存
     userDao = sqlSession.getMapper(IUserDao.class);

     User user2 = userDao.findById(41);
     System.out.println(user2);
     System.out.println(user1==user2);
 }

```

测试结果：

<img src="https://img-blog.csdnimg.cn/20210509230622546.png?#pic_center" alt="在这里插入图片描述"/> 从以上测试结果我们发现，当清空缓存之后，Mybatis 会重新发出 sql 语句进行数据库的查询。

---


### 2.2 一级缓存注意事项

  一级缓存是 SqlSession 范围的缓存，当调用SqISession 的修改，添加，删除，commit(), close()等方法时，就会清空一级缓存。

```java
@Test
public void testClearCache(){
    //1. 查询
    User user1 = userDao.findById(41);
    System.out.println(user1);

    //2. 更新
    user1.setUsername("update clear cache user");
    user1.setAddress("河北省秦皇岛市");
    userDao.updateUser(user1);

    //3. 再次查询
    User user2 = userDao.findById(41);
    System.out.println(user2);
    System.out.println(user1==user2);
}

```

测试结果：

<img src="https://img-blog.csdnimg.cn/20210509231338704.png?#pic_center" alt="在这里插入图片描述"/>

---


## 三、Mybatis 的二级缓存

### 3.1 二级缓存概述

  它指的是Mybatis中 SqlSessionFactory 对象的缓存。由同一个 SqlSessionFactory 对象创建的 SqlSession 共享其缓存。

---


### 3.2 二级缓存的开启与关闭

**使用步骤：**

第一步 : 让 Mybatis 框架支持二级缓存(在SqlMapConfig.xml中配置) 

第二步 : 让当前的映射文件支持二级缓存(在IUserDao.xml中配置) 

第三步 : 让当前的操作支持二级缓存(在select标签中配置)

1、第一步：主配置文件中支持二级缓存 

```xml
<settings>
  <setting name="cacheEnabled" value="true"></setting> 
</settings>
```

因为 cacheEnabled 的取值默认就为 true，所以这一步可以省略不配置。为 true 代表开启二级缓存；为false 代表不开启二级缓存。 

2、第二步：映射文件支持二级缓存

```xml
<mapper namespace="xxx.XxxMapper" >
    <!-- 启动当前命名空间的二级缓存 -->
    <cache></cache>
</mapper>   
```

3、第三步：让当前的操作支持二级缓存

```xml
<!--根据id查询用户，添加 useCache 属性，并指定为true-->
<select id="findById" parameterType="INT" resultType="user" useCache="true">
    select * from user where id = #{id}
</select>
```

示例：

```java
@Test
public void testFirstLevelCache(){
    SqlSession sqlSession1 = factory.openSession();
    IUserDao dao1 = sqlSession1.getMapper(IUserDao.class);
    User user1 = dao1.findById(41);
    System.out.println(user1);
    sqlSession1.close();//一级缓存消失，

    SqlSession sqlSession2 = factory.openSession();
    IUserDao dao2 = sqlSession2.getMapper(IUserDao.class);
    User user2 = dao2.findById(41);
    System.out.println(user2);
    sqlSession2.close();

    //System.out.println(sqlSession1==sqlSession2);
    System.out.println(user1 == user2);//false
    // 因为二级缓存存放的内容是数据，而不是对象。将第一次查询得到对象的数据保存到缓存中，第二次查询时，将数据封装到新的对象user2里面.
    // 虽然是false,但是只发起了一次查询，证明二级缓存开启成功
}

```

测试结果：

 <img src="https://img-blog.csdnimg.cn/20210510002736496.png?#pic_left" alt="在这里插入图片描述" width="800"/>

---


## 四、补充：也可以使用注解配置开启二级缓存

基于注解配置开启二级缓存，第二步与基于XML配置有所区别，其需要在持久层接口名的上方添加@CacheNamespace(blocking = true)

**实例：**

```java
@CacheNamespace(blocking = true)
public interface IUserDao {

	/**
     * 根据id查询用户
     */
    @Select("select * from user where id = #{uid}")
    User findById(Integer id);
}

```

**测试方法：**

```java
 @Test
 public void testFindById(){
     SqlSession session1 = factory.openSession();
     IUserDao userDao1 = session1.getMapper(IUserDao.class);
     User user1 = userDao1.findById(41);
     System.out.println(user1);

     session1.close();//一级缓存消失

     SqlSession session2 = factory.openSession();
     IUserDao userDao2 = session2.getMapper(IUserDao.class);
     User user2 = userDao2.findById(41);
     System.out.println(user2);

     session2.close();

     System.out.println(user1==user2);
 }

```

**测试结果：**<img src="https://img-blog.csdnimg.cn/20210519111544442.png?#pic_left" alt="在这里插入图片描述"/> 打印false，但是只发起了一次查询，证明二级缓存开启成功。

---


本文针对 Mybatis 中一级缓存和二级缓存进行了总结归纳，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/116572478