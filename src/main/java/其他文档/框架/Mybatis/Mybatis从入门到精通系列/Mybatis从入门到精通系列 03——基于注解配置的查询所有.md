# Mybatis从入门到精通系列 03——基于注解配置的查询所有
  本文针对 Mybatis从入门到精通系列1讲中的入门案例进行注解实现。 <img src="https://img-blog.csdnimg.cn/20210417214428523.png#pic_center" alt="在这里插入图片描述"/>

---


## 一、Mybatis中注解与 xml 配置的差异概述

  Mybatis 基于注解配置与 XML 配置的不同之处除了在于工程目录和dao接口不同以外，在应用方面也有出入。注解配置适用于简单的数据处理，理解起来比较容易，而配置文件扩展强，更适用于维护。此外，在动态 sql 语句方面，注解开发相对 XML 配置开发局限一些。

  下面我们来基于注解方式实现数据库的查询。

---


## 二、Mybatis 基于注解配置的查询所有

<font size="5">工程目录：</font>

  注解配置摒弃了XML的映射配置文件的创建。  

 <img src="https://img-blog.csdnimg.cn/20210417215232639.png?#pic_left" alt="在这里插入图片描述" width="380"/>

---


### XML向注解方式改造工程时主要的改造点

由XML向注解方式改造工程时，主要的改造点有三处
1. 主配置文件 SqlMapConfig.xml
1. 映射配置文件 Mapper.xml
1. Java映射类
---


<font size="5">1. 主配置文件 SqlMapConfig.xml：</font>

  与 XML 开发不同的是，SqlMapConfig.xml 中 mapper标签中的 resource 属性改为 class 属性，其属性值为dao的全限定类名。

```xml
<!--指定映射配置文件的位置， 映射配置文件指的是每个dao独立的配置文件-->
<!--如果使用注解来配置，此处应该使用class属性指定被注解的dao全限定类名-->
<mappers>
    <mapper class="com.itheima.dao.IUserDao"/>
</mappers>

```

---


<font size="5">2. 映射配置文件 Mapper.xml：</font>

这里比较方便了，因为要改造为注解方式，IUerDao.xml 文件直接不需要了

---


<font size="5">3. 用户的持久层接口：</font>

```java
/**
 * 用户的持久层  接口
 */
public interface IUserDao {
    /**
     * 查询操作
     */
    @Select("select * from user")
    List<User> findAll();
}

```

---


除了以上需要做变动，其余的不需要改变，具体代码参考右侧链接：Mybatis从入门到精通系列 01——快速入门</a>

---


项目运行结果： <img src="https://img-blog.csdnimg.cn/20210417222043453.png?" alt="在这里插入图片描述"/>

---

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/115802480