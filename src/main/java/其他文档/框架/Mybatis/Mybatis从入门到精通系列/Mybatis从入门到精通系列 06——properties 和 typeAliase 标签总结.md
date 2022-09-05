# Mybatis从入门到精通系列 06——properties 和 typeAliase 标签总结
  本文针对 properties 标签和 typeAliase 标签的使用进行了总结归纳

<img src="https://img-blog.csdnimg.cn/20210421235216382.png#pic_center" alt="在这里插入图片描述"/>

---


 # 目录
一、properties 标签的使用
1.1 在标签内容下配置数据库的信息
1.2 通过属性引用外部配置文件信息
1.3 properties的属性
1.4 properties标签 resource 和 url 属性的两种写法:
二、typeAliase 标签的使用
2.1 typeAliase 标签指定别名
2.2 pakage 标签指定 dao 接口所在包

---


## 一、properties 标签的使用

properties 标签主要有两个作用：
- 在标签内部配置连接数据库的信息- 通过属性引用外部配置文件信息
---


### 1.1 在标签内容下配置数据库的信息

  下方配置连接池的property标签中的value的取值等于 ${ 上方 property 的 name 名称}，并且上下方 property 的 name 应该相同。

如：

```xml
<properties>
    <property name="driver" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/mybatisdb?useUnicode=true&amp;amp;characterEncoding=UTF-8"/>
    <property name="username" value="root"/>
    <property name="password" value="000000"/>
</properties>

<!--配置环境-->
<environments default="mysql">
    <!--配置mysql的环境-->
    <environment id="mysql">
        <!--配置事务-->
        <transactionManager type="JDBC"></transactionManager>
        <!--配置连接池-->
        <dataSource type="POOLED">
            <property name="driver" value="${driver}"/>
            <property name="url" value="${url}"/>
            <property name="username" value="${username}"/>
            <property name="password" value="${password}"/>
        </dataSource>
    </environment>
</environments>

```

---


### 1.2 通过属性引用外部配置文件信息

下方配置连接池的 property 标签中的 value 的取值等于配置文件中 key 应该相同。

jdbcConfig.properties

```xml
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatisdb?useUnicode=true&amp;amp;characterEncoding=UTF-8
jdbc.username=root
jdbc.password=000000

```

SqlMapConfig.xml

```xml
 <!--外部引入 jdbcConfig.properties 文件-->
<properties resource="jdbcConfig.properties"> </properties>

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

```

---


### 1.3 properties的属性
1. **resource 属性：**   用于指定配置文件的位置，是按照类路径的写法来写，并且必须存在于类路径下。

2. url 属性:   

   ​		是要求按照 Url 的写法来写地址   URL: Uniform Resource Locator 统一资源定位符。 它是可以唯一标识一个资源的位置。   写法:     http://localhost : 8080/ mybatisserver/demo1Servlet     协议  主机 端口  URI
---


### 1.4 properties标签 resource 和 url 属性的两种写法:

properties标签引用配置文件 jdbcConfig.properties 的基于 resource 和 url 属性的两种写法:

```xml
<!--resource-->
<properties resource="jdbcConfig.properties" ></properties>

<!--url-->
<properties url="file:///E:/xxx/xxx/jdbcConfig.properties"></properties>

```

---


## 二、typeAliase 标签的使用

### 2.1 typeAliase 标签指定别名

  typeAliases 标签用于配置实体类别名，其内容标签 typeAlias 的 type 属性指定的是实体类全限定类名，alias 属性指定别名。

如：

```xml
<typeAliases>
  	<typeAlias type="com.itheima.domain.User" alias="user"></typeAlias>
</typeAliases>

```

经过上面的配置，那么在用户映射配置文件中，用到全限定类名的地方可以用 **别名** 代替， 如：

```xml
<!--配置查询所有-->
<select id="findAll" resultType="user">
    select * from user;
</select>

```

<font color="red">注意： mysql只在windows下 **不区分** 大小写</font>

---


  当然 typeAliases 标签也支持使用 package 标签指定包下的所有实体类都注册别名，**并且类名就是别名，不再区分大小写**。

```xml
<typeAliases>
    <!--用于指定要配置的别名的包，当指定之后，该包下的实体类都会注册别名-->
    <package name="com.itheima.domain"></package>
</typeAliases>

```

---


### 2.2 pakage 标签指定 dao 接口所在包

  package 标签是用于指定 dao 接口所在包，==当指定了之后就不需要再在mybatis核心配置文件注册 mapper 以及 resource 或者 class 了。==

```xml
<!--配置映射文件的信息-->
<mappers>
    <!--package标签是用于指定dao接口所在包，当指定了之后就不需要再写 mapper以及resource或者class了-->
    <package name="com.itheima.dao"></package>
</mappers>

```

## 注意： configuration表现中的元素是有顺序的

```
properties->settings->typeAliases->typeHandlers->objectFactory->
objectWrapperFactory->plugins->environments->databaseProvider->mappers
```



---



本文针对 properties 标签和 typeAliase 标签的使用进行了总结归纳，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~



# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/115982103