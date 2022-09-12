# Spring 从入门到精通系列 03——使用 Spring 的 IOC 解决程序耦合
本文针对 IOC 的概念与在 Spring 中如何使用 IOC 进行分析与讲解。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
一、IOC 的概念和作用
二、使用 Spring 的 IOC 解决程序耦合
2.1 Spring 基于 XML 的 IOC 环境搭建
2.2 ApplicationContext 的三个实现类
2.3 BeanFactory 和 和 ApplicationContext 的区别

---


## 一、IOC 的概念和作用

  控制反转（Inversion of Control，缩写为IoC），是面向对象编程中的一种设计原则，可以用来减低计算机代码之间的耦合度。其中最常见的方式叫做依赖注入（Dependency Injection，简称DI），还有一种方式叫“依赖查找”（Dependency Lookup）。通过控制反转，对象在被创建的时候，由一个调控系统内所有对象的外界实体将其所依赖的对象的引用传递给它。也可以说，依赖被注入到对象中。

以上这个概念来源于 百度百科，那么具体是什么意思呢？我们在前两篇文章中用了两方法来创建 accountService，分别是：

```java
IAccountService accountService1 = new AccountServiceImpl();
IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");

```

那么我们来分析一下，这两种方法的区别。

第一种是 new 对象名称的方式来创建，这种方法拥有自己控制创建对象的权力。

 <img src="https://img-blog.csdnimg.cn/20210601001940278.png#pic_left" alt="在这里插入图片描述" width="250"/> 

而第二种是把自主创建对象的权力 **给** 了名叫 BeanFactory 的类，再通过固定名称 “accountService” 得到的。<img src="https://img-blog.csdnimg.cn/20210601003331169.png#pic_left" alt="在这里插入图片描述" width="370"/>

这种被动接收的方式获取对象的思想就是控制反转，它是 spring 框架的核心之一。

<font color="red">**明确 IOC 的作用：** 削减计算机程序的耦合(解除我们代码中的依赖关系)。</font>

---


## 二、使用 Spring 的 IOC 解决程序耦合

### 2.1 Spring 基于 XML 的 IOC 环境搭建

我们通过案例，来对 Spring 的 IOC 进行分析与实现，创建工程目录如下：

 <img src="https://img-blog.csdnimg.cn/2021060100423726.png#pic_left" alt="在这里插入图片描述" width="250"/>

---


**导入依赖：**

```xml
<packaging>jar</packaging>

<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.0.2.RELEASE</version>
    </dependency>
</dependencies>

```

==注：<packaging>jar</packaging>，代表的意思是使用 jar 的打包形式。==

---


**业务层和持久层的接口和实现类 ：**

```java
/**
 * 持久层接口
 */
public interface IAccountDao {
    void insertAccount();
}

/**
 * 账户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao {

    public void insertAccount() {
        System.out.println("保存了账户");
    }
}

/**
 * 业务层的接口
 */
public interface IAccountService {

    /**
     * 模拟保存账户
     */
    void InsertAccount();
}


/**
 * 业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao = new AccountDaoImpl();

    public AccountServiceImpl() {
        System.out.println("accountService 对象创建了");
    }

    public void InsertAccount() {
        accountDao.insertAccount();
    }
}

```

---


**bean.xml：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--把对象的创建交给spring来管理-->
    <bean id="accountService" class="com.ithiema.service.impl.AccountServiceImpl" ></bean>
    <bean id="accountDao" class="com.ithiema.dao.impl.AccountDaoImpl"></bean>
</beans>

```

---


**Client.java：**

```java
/**
 * 模拟表现层，用于调用业务层
 */
public class Client {
    /**
     * 获取spring的ioc核心容器，并根据id获取对象
     */
    public static void main(String[] args) {
        //获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //两种方式获取对象
		    IAccountService accountService = (IAccountService) ac.getBean("accountService");
        IAccountDao accountDao = ac.getBean("accountDao", IAccountDao.class);
        //打印对象
        System.out.println(accountService);
        System.out.println(accountDao);
    }
}

```

**测试结果如下：** <img src="https://img-blog.csdnimg.cn/20210601012744697.png#pic_left" alt="在这里插入图片描述" width="450"/>

---


### 2.2 ApplicationContext 的三个实现类

通过查看 ApplicationContext 对象的继承体系得到如下简图：

<img src="https://img-blog.csdnimg.cn/20210601013055899.png#pic_center" alt="在这里插入图片描述"/> 查看 ApplicationContext 的继承体系，结果如下：

 <img src="https://img-blog.csdnimg.cn/20210601013620136.png#pic_left" alt="在这里插入图片描述" width="400"/> 

ApplicationContext 最常用的三个实现类分别是：

1. **ClassPathXmlApplicationContext** : 它可以加载类路径下的配置文件，要求配置文件必须在类路径下。不在的话，加载不了(更常用)
1. **FileSystemXmlApplicationContext** ： 它可以在加载此版任意路径下的配置文件（必须有访问权限）
1. **AnnotationConfigApplicationContext** ： 他是用于读取注解创建容器的。
---


### 2.3 BeanFactory 和 和 ApplicationContext 的区别
1. **ApplicationContext**：<font color="red">单例对象适用</font> <mark>更多采用此接口创建容器</mark> 他在创建核心容器时，创建对象采取的策略使采用<font color="red">立即加载</font>的方式。也就是说，只要一读取配置文件马上就创建配置文件中的对象。
1. **BeanFactory**： <font color="red">多例对象适用</font> 他在创建核心容器时，创建对象采取的策略是采用<font color="red">延迟加载</font>的方式。也就是说，什么时候根据id获取对象了，什么时候才真正创建对象。
---


本文针对 IOC 的概念与在 Spring 中如何使用 IOC 进行分析与讲解，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117432909