# Mybatis从入门到精通系列 02——执行查询的底层原理分析
本文将对 Mybatis从入门到精通系列1讲 查询所有的入门案例进行底层的分析，并深入剖析 Mybatis 在持久层对数据做处理的相关操作。

<img src="https://img-blog.csdnimg.cn/20210415210539410.png#pic_center" alt="在这里插入图片描述"/>

---


 # 文章目录
一、入门案例设计模式分析
1.1 读取配置文件
1.2 创建 SqlSessionFactory工厂
1.3 使用工厂创建dao对象
1.4 使用SqlSession创建Dao接口的代理对象
二、Mybatis在执行查询所有的原理分析
2.1 配置文件的解析
2.2 SelectList 方法执行步骤
2.3 创建Dao接口的代理对象方法详解
三、Mybatis 执行查询整体流程原理

---


## 一、入门案例设计模式分析

在上一讲中，我们用下面代码实现了 mybatis 入门案例的编写，接下来我们分析一下其中蕴含的设计模式。

```java
public static void main(String[] args) throws Exception {<!-- -->
   //1.读取配置文件
    InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
    
    //2.创建SqlSessionFactory工厂
    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
    SqlSessionFactory factory = builder.build(in);

    //3.使用工厂生产SqlSession对象
    SqlSession sqlSession = factory.openSession();

    //4.使用SqlSession创建Dao接口的代理对象
    IUserDao userDao = sqlSession.getMapper(IUserDao.class);

    //5.使用代理对象执行方法
    List<User> users = userDao.findAll();
    for (User user:users) {<!-- -->
        System.out.println(user);
    }

    //6.释放资源
    sqlSession.close();
    in.close();
}

```

---


### 1.1 读取配置文件

```
//读取配置文件
InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");

```

读配置文件,若绝对路径或者相对路径作为 filePath 会面临很多问题。
- 绝对路径：D:/xxx/xxx/SqlMapConfig.xml- 
- 相对路径：src/java/main/SqlMapConfig.xml
  绝对路径局限性太过明显，电脑里没 D 盘，那么读取配置文件会出现异常。 相对路径来说若是 web 工程，项目部署之后，src目录将会消失，那么读取配置文件也会失败，因此在实际开发项目中，不会选择以上两种方式。

**因此读取配置文件的时候，我们只有两种选择：**

1. <font color="red">使用类加载器。它只能读取类路径的配置文件</font>
1. <font color="red">使用 ServletContext 对象的 getRealPath() 方法，他获取当前应用部署的绝对路径。</font>
---


### 1.2 创建 SqlSessionFactory工厂

```
 //创建SqlSessionFactory工厂
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory factory = builder.build(in);

```

  **创建工厂 Mybatis 使用了构建者模式。** 这里可以理解成，在构建工厂时， 我们在建造工厂时，需要请包工队来帮忙建造工厂，我们只需要给出项目工厂的需求以及资金即可。那么这里这个输入流 in 就相当如 “项目需求以及资金”。

<font color="red">构建这模式的优势在于：把对象的创建细节进行了隐藏，是使用者无需关注细节，直接调用方法就能拿到对象。</font>

---


### 1.3 使用工厂创建dao对象

```
//使用工厂创建dao对象
IUserDao userDao = new UserDaoImpl(factory);

```

这里使用 **工厂模式** 生产 SqlSession，这里起到了解耦作用，<font color="red">即降低了类之间的依赖关系。</font>

---


### 1.4 使用SqlSession创建Dao接口的代理对象

```
//使用SqlSession创建Dao接口的代理对象
IUserDao userDao = sqlSession.getMapper(IUserDao.class);

```

创建 Dao 接口实现类，使用了 **代理模式**，<font color="red">其优势在于不修改源码的基础上对已有方法进行增强。</font>

---


当然也可以采用链式编程的思想，直接一行创建出 Dao 接口的实现类。

```
IUserDao userDao = new SqlSessionFactoryBuilder().build(in).openSession().getMapper(IUserDao.class);

```

**但是由于每一个方法都有很多的重载方法，因此分步写可以使得构建过程更加灵活。**

<img src="https://img-blog.csdnimg.cn/20210415222249280.png#pic_center" alt="在这里插入图片描述"/>

---


## 二、Mybatis在执行查询所有的原理分析

Mybatis 在使用代理 dao 的方式实现增删改查时，只做了两件事：
1. 创建代理对象
1. 在代理对象中调用selectList
为了让 Mybatis 完整的实现以上两个功能以及让对应的类各司其职，就需要对以下几个方面做深入的分析。

---


### 2.1 配置文件的解析

配置连接数据库的信息，我们可以创建 Connection 对象

```xml
<!--配置连接数据库的4个基本信息-->
<property name="driver" value="com.mysql.jdbc.Driver"/>
<property name="url" value="jdbc:mysql://localhost:3306/mybatisdb"/>
<property name="username" value="root"/>
<property name="password" value="000000"/>

```

有了以下配置，就有了映射的配置信息

```
<mapper resource="com/itheima/dao/IUserDao.xml"/>

```

有了以下配置可以获取执行 SQL 语句，也就可以获取 PreparedStatement。此外，该配置中还封装了实体类的全限定类名。

```xml
<mapper namespace="com.itheima.dao.IUserDao">
    <!--配置查询所有-->
    <select id="findAll" resultType="com.itheima.domain.User">
        select *from user;
    </select>
</mapper>

```

以上的配置信息，我们应将以上配置信息读成一个流，解析配置文件。 这里读取配置文件的技术就是解析 XML 的技术，<font color="red">此处用到的是 dom4j 解析 XML 的技术。</font>

---


### 2.2 SelectList 方法执行步骤
1. **根据配置文件的信息创建 Connection 对象** 

   如：注册驱动，获取连接 

   **获取预处理对象 PreparedStatement** 此时需要 SQL 语句，因为执行方法 conn.perpareStatement(sql)。其中，这里的 sql 语句就是从映射配置文件 IUerDao.xml 的select标签 id 为 “findAll” 中取到，即上文中的第三个配置。 

   **执行查询** ResultSet resultSet = perpareStatement.executeQuery(); 

   **遍历结果集用于封装** 

   ```java
   List list = new ArrayList();
   while( resultSet.next() ){
     E elemect = (E)Class.forName(配置的全限定类名).newInstance() //对类进行实例化，其中配置的全限定类名是上文配置中的 resultType
     接下来就是进行实体类的封装，把每个 rs 的内容都添加到 element 中。
     此处使用反射封装（ 注：我们的实体类属性和表中的列名是一致的，那么就可以把表的列名看成是实体类的属性名，也就可以使用反射的方式根据名称获取每个属性，并把值赋进去）
     list.add(element) //把element加入到list中。
   }
   ```

   **返回 list**
   return list;
---


那么，想要让上方 SelectList 方法执行，我们需要提供两个信息：
1. 连接信息
1. 映射信息（它包含两个部分：<font color="red">① 执行的 sql 语句、② 封装结果的实体类全限定类名</font>）
  这里需要将映射信息的两部分定义成对象，这里命名为 Mapper。并将 Mapper 作为 map 对象的 value，map 的 key 就是 namespace+id，如下图：

<img src="https://img-blog.csdnimg.cn/20210416131956761.png?#pic_center" alt="在这里插入图片描述" width="350"/>

---


关于配置和SelectList方法执行的原理如下： <img src="https://img-blog.csdnimg.cn/20210416220402206.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzgxOTU2Ng==,size_16,color_FFFFFF,t_70#pic_center" alt="在这里插入图片描述"/>

---


### 2.3 创建Dao接口的代理对象方法详解

下面我们用伪代码的形式，分析 SqlSession 创建代理对象的细节

```
// 根据dao接口的字节码创建dao的代理对象
public <T> getMapper(Class<T> daoInterfaceClass){<!-- -->
	/**
	 * 类加载器:它使用的和被代理对象是相同的类加载器
	 * 代理对象要实现的接口∶和被代理对象实现相同的接口
	 * 如何代理∶它就是增强的方法，在不修改源码的基础上对已有方法进行增强。
	 * 		此处是一个InvocationHandler的接口
	 * 		在实现类中调用selectList方法
	 */
	Proxy.newProxyInstance(类加载器，代理对象要实现的接口字节码数组，如何代理);
}

```

---


Mybatis在执行查询所有的原理如下：<img src="https://img-blog.csdnimg.cn/20210416221603686.png#pic_center" alt="在这里插入图片描述"/>

---


## 三、Mybatis 执行查询整体流程原理

Mybatis 从配置文件到创建代理对象执行 selectList 方法的整体原理如下图所示： <img src="https://img-blog.csdnimg.cn/20210416233156183.png?#pic_center" alt="在这里插入图片描述"/>

---


本文借鉴了黑马教程的课堂笔记，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **https://blog.csdn.net/weixin_43819566/article/details/115740111