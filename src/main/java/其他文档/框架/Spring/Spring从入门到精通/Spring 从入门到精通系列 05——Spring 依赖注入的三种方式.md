# Spring 从入门到精通系列 05——Spring 依赖注入的三种方式
本文针对 Spring 的依赖注入的概述以及三种注入方式进行分析和讲解。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
一、spring的依赖注入概述
二、使用构造函数
三、set 方法注入
四、复杂类型的注入/集合类型的注入

---


## 一、spring的依赖注入概述
依赖注入：Dependency Injection 
  - IOC的作用：**降低程序间的耦合（依赖关系）**

  - 依赖关系的管理：以后都交给 spring 来维护

  - 在当前需要用到其他类的对象，由 spring 为我们提供，我们只需在配置文件中说明 

    

    <mark>依赖关系的维护：就称之为依赖注入</mark> 

**能注入的数据有三类**

```
基本类型和 String
其他 bean 类型（在配置文件中或者注解配置过的bean）
复杂类型/集合类型
```

**注入的方式:**

    使用构造函数
    使用set方法提供
    使用注解提供
---


## 二、使用构造函数

**使用的标签：** constructor-arg 

**出现的位置：** bean标签的内部

**标签中的属性：**
1. **type**: 用于指定要注入的数据的数据类型，该数据类型也是构造函数中某个或某些参数的类型

2. **index**: 用于指定要注入的数据给构造函数中指定索引位置的参数赋值，索引的位置是从 0 开始

3. **name: 用于指定给构造函数中知道那个名称的参数赋值 （常用的）** <mark>以上三个用于指定给构造函数中哪个参数赋值</mark>

4. **value**: 用于给基本类型和 String 类型的数据

5. **ref**: 用于指定其他的Bean类型数据。他指得就是在 spring 的 IOC 核心容器中出现过的 bean 对象

  **优势：** 在获取bean对象时，注入数据是必须的操作，否则对象无法创建成功。

  **弊端：** 改变了bean对象的实例化方式，是我们在创建对象时，如果用不到这些数据，也必须提供。

```xml
<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl1">
    <constructor-arg name="UserName" value="xiuyan"></constructor-arg>
    <constructor-arg name="age" value="18"></constructor-arg>
    <constructor-arg name="birthday" ref="now"></constructor-arg>
</bean>

<!--配置一个日期对象: 读取这个全限定类名，反射创建一个对象，并且存入IOC容器，可用 now 这个id取出来-->
<bean id="now" class="java.util.Date"></bean>

```

```java
public class AccountServiceImpl1 implements IAccountService {

    //如果是经常变化的数据，并不适用于注入的方式
    private String name;
    private Integer age;
    private Date birthday;

    public AccountServiceImpl1(String UserName, Integer age, Date birthday){
        this.name = UserName;
        this.age = age;
        this.birthday = birthday;
    }
}

```

---


<font size="5">使用构造函数注入演示：</font>

**bean.xml：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--构造函数注入-->
    <bean id="accountService1" class="com.itheima.service.impl.AccountServiceImpl1">
        <constructor-arg name="UserName" value="xiuyan"></constructor-arg>
        <constructor-arg name="age" value="18"></constructor-arg>
        <constructor-arg name="birthday" ref="now"></constructor-arg>
    </bean>

    <!--配置一个日期对象: 读取这个全限定类名，反射创建一个对象，并且存入IOC容器，可用 now 这个id取出来-->
    <bean id="now" class="java.util.Date"></bean>

</beans>

```

**注意：** 这个 name 属性值与构造函数中 **参数名** 相同。

**业务层实现类 AccountServiceImpl：**

```java
/**
 * 业务层实现类
 */
public class AccountServiceImpl1 implements IAccountService {

    //如果是经常变化的数据，并不适用于注入的方式
    private String name;
    private Integer age;
    private Date birthday;

    public AccountServiceImpl1(String UserName, Integer age, Date birthday){
        this.name = UserName;
        this.age = age;
        this.birthday = birthday;
    }

    public void InsertAccount() {
        System.out.println("service中的InsertAccount方法执行了");
        System.out.println("name : "+name);
        System.out.println("age : "+age);
        System.out.println("birthday :"+birthday);
    }

}

```

**模拟表现层 Client：**

```java
/**
 * 模拟表现层，用于调用业务层
 */
public class Client {

    public static void main(String[] args) {
        //1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2.根据id获取bean对象
        IAccountService as1 = (IAccountService) ac.getBean("accountService1");
        as1.InsertAccount();
    }
}

```

**测试结果如下：** <img src="https://img-blog.csdnimg.cn/20210601032230153.png#pic_left" alt="在这里插入图片描述" width="450"/>

---


## 三、set 方法注入

**涉及标签：** property 

**出现的位置：** bean标签的内部

**标签的属性：**
1. **name**: 用于指定注入时所调用的set方法名称。 <font color="red">name 为实现类中的set方法去掉 set 并把首字母变小写的字符串。 如：setUserName 在bean.xml中 name=userName </font>

1. **value**: 用于给基本类型和String类型的数据

3. **ref**: 用于指定其他的Bean类型数据。他只得就是在spring的IOC核心容器中出现过的bean对象
  **优势：** 创建对象时没有明确的限制，可以直接使用默认构造函数。 

  **弊端：** 如果有某个成员必须有值，则获取对象是时可能set方法没有执行。

---


<font size="5">使用 set 方法注入演示：</font>

**bean.xml：**

```xml
<bean id="accountService2" class="com.itheima.service.impl.AccountServiceImpl2">
	<property name="name" value="xiuyan"></property>
    <property name="age" value="22"></property>
    <property name="birthday" ref="now"></property>
</bean>

<bean id="now" class="java.util.Date"></bean>

```

**模拟表现层：**

```java
public static void main(String[] args) {
	//1.获取核心容器对象
	ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
	//2.根据id获取bean对象
	IAccountService as2 = (IAccountService) ac.getBean("accountService2");
	as2.InsertAccount();
}

```

**测试结果如下：**

 <img src="https://img-blog.csdnimg.cn/2021060103313341.png#pic_left" alt="在这里插入图片描述" width="450"/>

---


## 四、复杂类型的注入/集合类型的注入
1. **用于给 list 结构及和注入的标签： list array set**
1. **用于给 Map 结构注入的标签：map props**
---


**业务层实现类：**

```java
/**
 * 业务层实现类
 */
public class AccountServiceImpl3 implements IAccountService {

    private String[] myStrs;
    private List<String> myList;
    private Set<String> mySet;
    private Map<String, String> myMap;
    private Properties myProps;

    public void setMyStrs(String[] myStrs) {
        this.myStrs = myStrs;
    }

    public void setMyList(List<String> myList) {
        this.myList = myList;
    }

    public void setMySet(Set<String> mySet) {
        this.mySet = mySet;
    }

    public void setMyMap(Map<String, String> myMap) {
        this.myMap = myMap;
    }

    public void setMyProps(Properties myProps) {
        this.myProps = myProps;
    }

    public void InsertAccount() {
        System.out.println(Arrays.toString(myStrs));
        System.out.println(myList);
        System.out.println(mySet);
        System.out.println(myMap);
        System.out.println(myProps);
    }
}

```

**bean.xml：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--复杂类型的注入/集合类型的注入-->
    <bean id="accountService3" class="com.itheima.service.impl.AccountServiceImpl3">
        <property name="myStrs">
            <array>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </array>
        </property>
        <property name="myList">
            <list>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </list>
        </property>
        <property name="mySet">
            <set>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </set>
        </property>
        <property name="myMap">
            <map>
                <entry key="testA" value="aaa"></entry>
                <entry key="testB">
                    <value>BBB</value>
                </entry>
            </map>
        </property>
        <property name="myProps">
            <props><!--prop标签属性只有 key，value 等于标签体内容的值-->
               <prop key="testC">CCC</prop>
                <prop key="testD">DDD</prop>
            </props>
        </property>
    </bean>
</beans>

```

<font color="red">注意：list array set 同属于一个list 结构的标签，因此可以使用同一种结构注入，map 结构同理。如：</font>

```xml
<property name="myStrs">
   <list>
        <value>AAA</value>
        <value>BBB</value>
        <value>CCC</value>
    </list>
</property>
<property name="myList">
    <list>
        <value>AAA</value>
        <value>BBB</value>
        <value>CCC</value>
    </list>
</property>
<property name="mySet">
    <list>
        <value>AAA</value>
        <value>BBB</value>
        <value>CCC</value>
    </list>
</property>

```

**模拟表现层：**

```java
public static void main(String[] args) {
	//1.获取核心容器对象
	ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
	//2.根据id获取bean对象
	IAccountService as3 = (IAccountService) ac.getBean("accountService3");
	as3.InsertAccount();
}

```

**测试结果如下：** <img src="https://img-blog.csdnimg.cn/20210601034008677.png#pic_left" alt="在这里插入图片描述" width="450"/>

---


本文针对 Spring 的依赖注入的概述以及三种注入方式进行分析与讲解，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117433795