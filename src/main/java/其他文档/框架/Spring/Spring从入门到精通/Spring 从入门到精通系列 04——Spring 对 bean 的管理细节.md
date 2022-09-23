# Spring 从入门到精通系列 04——Spring 对 bean 的管理细节
  Spring 对 bean 的管理细节包括：创建 bean的三种方式、bean 标签的作用范围以及 bean 对象的生命周期，本文针对此部分进行分析与解读。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
一、创建bean的三种方式
1.1 使用默认构造函数创建。
1.2 使用普通工厂中的方法创建对象
1.3 使用工厂中的静态方法创建对象
二、bean 标签的作用范围调整
三、bean 对象的生命周期

---


## 一、创建bean的三种方式

### 1.1 使用默认构造函数创建。

在spring的配置文件中使用bean标签，配以id和class属性之后，**且没有其他属性和标签时**。采用的就是 **默认构造函数** 创建bean对象，此时如果类中没有默认构造函数，则对象 **无法创建**。

```xml
<bean id="accountService" class="AccountServiceImpl" ></bean>

```

---


### 1.2 使用普通工厂中的方法创建对象

这里我们模拟，调用jar包中的工厂，并用工厂里的方法来创建 bean 对象，并存入spring 容器

```xml
<bean id="instanceFactory" class="InstanceFactory"></bean>
<bean id="accountService" factory-bean="instanceFactory" factory-method="getAccountService"></bean> 

```

**InstanceFactory.java：**

```java
/**
 * 模拟一个工厂类（该类可能是存在与jar包中的，无法通过修改源码的方式提供默认构造函数）
 */
public class InstanceFactory {
    public IAccountService getAccountService(){
        return new AccountServiceImpl();
    }
}

```

---


### 1.3 使用工厂中的静态方法创建对象

使用某个类中的静态方法创建对象，并存入spring容器中

```xml
<bean id="accountService" class="StaticFactory" factory-method="getAccountService"></bean>

```

**StaticFactory.java：**

```java
/**
 * 模拟一个工厂类（该类可能是存在与jar包中的，无法通过修改源码的方式提供默认构造函数）
 */
public class StaticFactory {
    public static IAccountService getAccountService(){
        return new AccountServiceImpl();
    }
}

```

---


## 二、bean 标签的作用范围调整

bean标签的**scope属性**：
-  作用：<mark>用于指定bean的作用范围</mark>  
  
-  取值：常用是单例和多例 
  
  1. **singleton : 单例（默认值）**
  2. **prototype ：多例**
  
  request ：作用域web应用的请求范围
  session ：作用域web应用的会画范围
  global-session ：作用域集群环境的会话范围（全局会话范围），当不是集群环境时，它就是session
---


## 三、bean 对象的生命周期
1、**单例对象** 

出生：当容器创建时对象出生 

活着：只要容器还在，对象一直在 

死亡：容器销毁，对象消亡 

总结：单例对象的生命周期和容器相同

2、**多例对象** 

出生：当我们使用对象时，spring框架为我们创建 

活着：对象只要是在使用过程中就一直活着 

==**死亡：当对象长时间不用，或者没有别的对象引用时，由 java 垃圾回收器回收**==

---


为了演示 bean 对象的生命周期，我们创建一个工程，工程目录如下：

<img src="https://img-blog.csdnimg.cn/20210601022940502.png#pic_left" alt="在这里插入图片描述" width="250"/> 

注意：factory 包下的类不用看，演示 bean 生命周期涉及不到。

**业务层接口及其实现类：**

```java
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

    public AccountServiceImpl() {
        System.out.println("对象创建了。。。。");
    }

    public void InsertAccount() {
        System.out.println("service中的InsertAccount方法执行了");
    }

    public void init(){
        System.out.println("对象初始化了。。。。");
    }

    public void destroy(){
        System.out.println("对象销毁了。。。。");
    }
}

```

**bean.xml:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl" scope="singleton"
        init-method="init" destroy-method="destroy"></bean>

</beans>

```

**Client.java:**

```java
/**
 * 模拟表现层，用于调用业务层
 */
public class Client {
    public static void main(String[] args) {
        //1.获取核心容器对象
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2.根据id获取bean对象
        IAccountService as = (IAccountService) ac.getBean("accountService");
        as.InsertAccount();
        //手动关闭容器
        ac.close();
    }
}

```

**注意：** 如果采用 ApplicationContext ac = new ClassPathXmlApplicationContext(“bean.xml”) 的方式获取核心容器，那么容器就没有手动关闭容器 close() 的方法。因为该方法是实现接口，由于 **多态**，容器对象只能调用父接口的方法，而接口中没有 close() 方法。但是如果采用该方法来演示 bean 生命周期的话，由于 java 垃圾回收机制的影响，容器还没来得及调用销毁方法，就已经释放了。

**测试结果如下：**

 <img src="https://img-blog.csdnimg.cn/20210601023422316.png?#pic_left" alt="在这里插入图片描述" width="500"/>

---


本文针对 Spring 对 bean 的管理细节进行分析与讲解，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117433760