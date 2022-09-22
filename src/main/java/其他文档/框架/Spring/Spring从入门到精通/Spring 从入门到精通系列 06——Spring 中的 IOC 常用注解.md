# Spring 从入门到精通系列 06——Spring 中的 IOC 常用注解
  Spring 的 IOC 常用注解主要有用于创建对象的、用于注入数据的、用于改变作用范围的以及和生命周期相关的四部分，本文针对此块内容，进行深入分析与解读。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
Spring 中的 IOC 常用注解
一、用于创建对象的
1.1 Component
1.2 Controller（一般用在表现层）
1.3 Service（一般用在业务层）
1.4 Repository（一般用于持久层）
二、用于注入数据的
2.1 Autowired
2.2 Qualifier
2.3 Resource
2.4 Value
三、用于改变作用范围的
四、和生命周期相关的(了解)

---


## Spring 中的 IOC 常用注解

之前 xml 的配置：

```xml

<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"
      scope="" init-method="" destroy-method="">
   <property name="" value=""
   | ref="" >
</property>
        </bean>

```

对比之前的 xml 配置，注解开发中都有注解与之相对应。

下面我们创建一个项目来演示 Spring 中的 IOC 常用注解。工程目录如下：

 <img src="https://img-blog.csdnimg.cn/20210601040648206.png#pic_left" alt="在这里插入图片描述" width="250"/>

 **导入依赖：**

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.0.2.RELEASE</version>
    </dependency>
</dependencies>

```

使用注解配置需要在bean.xml中进行配置，具体配置如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--告知spring在创建容器时要扫描的包，配置所需要的标签不是在beans的约束，而是一个名称为context名称空间和约束中-->
    <context:component-scan base-package="com.itheima"></context:component-scan>
</beans>
```

---


## 一、用于创建对象的

他们的作用就和XML配置文件中编写一个 标签实现的功能是一样的

#### 1.1 Component
1、**作用**：用于把当前类对象存入spring容器中
2、**属性**：value:用于指定bean的id。当我们不写时，他默认值是当前类名，且首字母改小写。 属性 value 当只有一个值时，value书写可以省略。
如: 在创建: AccountServiceImpl 的对象的时候。传入的参数为 accountServiceImpl
表现层：IAccountService as = (IAccountService) ac.getBean("accountServiceImpl");
也可以通过在类上面配置 @Component("accountService")。
则表现层传入的参数应保持一样：IAccountService as = (IAccountService) ac.getBean("accountService");

#### 1.2 Controller（一般用在表现层）

#### 1.3 Service（一般用在业务层）

#### 1.4 Repository（一般用于持久层）

<font color="red">以上三个注解他们的作用和属性与 Component 是一模一样的，因此可以混用。但是为清晰，还是按照标准来。</font> 他们三个是spring框架为我们提供明确的三层使用的注释，使我们的三层对象更加清晰。

---


## 二、用于注入数据的

他们的作用就和XML配置文件中的 bean 标签中写一个 标签的的作用是一样的

#### 2.1 Autowired
**作用**： 

1、自动按照类型注入。只要容器中有 唯一 的一个 bean 对象类型和要注入的变量类型匹配，就可以注入成功。

2、如果 ioc 容器中没有任何 bean 的类型和要注入的变量类型匹配，则报错。

3、如果 ioc 容器中有多个bean的类型和要注入的变量类型匹配，先根据类型 （如：IAccountDao）找到范围，并在范围中在根据 变量的名称 （如：accountDao）与 ioc 容器的 key 相匹配，最终注入。

**出现的位置**： 可以是变量上，也可以是方法上

**细节**：在使用 Autowired注解注入时，set 方法就不是必须的了。


---


#### 2.2 Qualifier
1、**作用**：在按照类中注入的基础之上再按照名称注入。他在给类成员注入时不能单独（使用需和Autowired配合），但是在给方法参数注入时可以。

2、**属性**：用于指定 bean 的 id，无须考虑 Autowired，但是还需要写上 Autowired。如:

```java
@Autowired
@Qualifier("accountDao1")
private IAccountDao accountDao = null;

```

---


#### 2.3 Resource
1. **作用**：直接按照 bean 的 id 注入。它可以独立使用。

2.  **属性**： name: 用于指定 bean 的 id。如:

   ```java
   @Resource(name = "accountDao1")
   private IAccountDao accountDao = null ;
   
   ```

   <font color="red">以上三个注入只能诸如其他的bean类型的数据，而基本类型和String类型无法使用上述注解实现。 另外，集合类型的注入只能通过XML来实现。</font>

---


#### 2.4 Value
1. **作用**：用于注入基本类型和String类型的数据

2. **属性**： value：用于指定数据的值。它可以使用spring中的SpEl（也就是spring的el表达式） 

   SpEl的写法也是 ${表达式}
---


## 三、用于改变作用范围的

他们的作用就和在 bean 标签中使用 scope 的属性实现的功能是一样的
**Scope:**
**作用**：用于指定bean的作用范围

**属性**：value:指定范围的取值。常用取值：singleton, prototype
@Scope(“prototype”)
在类上使用，如：

```java
@Component("accountService")
@Scope("prototype")
public class AccountServiceImpl implements IAccountService {...}
```



## 四、和生命周期相关的(了解)

他们的作用就和在 bean 标签中使用 init-method 和 destroy-method 的作用是一样的

**PostConstruct**

1. 作用：用于指定初始化方法

   ```java
   @PostConstruct
   public void init(){
   System.out.println("初始化");
   }
   ```

**PreDestroy**

2、作用：用于指定销毁方法

```java
@PreDestroy
public void destroy(){
System.out.println("销毁方法Service");//这里单例对象需要手动关闭才会执行该方法
}
```

---


本文针对 Spring 对 IOC 常用注解进行分析与讲解，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117433811