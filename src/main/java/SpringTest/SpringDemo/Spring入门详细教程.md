

#      框架的概念

(一)从现实生活的角度来看: 框架，就好像是 “风筝”骨架。 如果我给你一个“风筝”的骨架，你只需要去往这个“风筝骨架”上，贴上一层纸。一个现成的风筝就做好啦~~ 如果没有“风筝的骨架”，那你需要，自己去找“竹子”“木材”“铁丝”等等东西，然后还要“自己手工”的做出一个“风筝骨架”来。。。呵呵，想一想，如果你做100个风筝的话。。。估计就要把人给“累死”啦。。。
(二)从技术角度来看
框架就是半个做好的程序。如果我们要编写程序的话，只需要花费 一半的时间精力，就可以完成，整个程序了。。。 因为这个现成的“框架”，已经帮我们做好了了一半啦。。。 这就是为什么，要使用“框架”，因为这样能够让我们，更加快速的开发出程序来。。。 当然了，其实也可以不用框架，那样的话。。。就要上面那个“风筝”似的。。。太累了。。

总结：spring框架可以简化我们的开发，使开发更高效，快捷

控制翻转概念（IoC）
没用spring之前，如果想用自己创建的一个学生类

Student student = new Student

spring相当于一个容器，在spring的配置文件中管理这这些类，以前的方法是我们自己想用就new出来一个，是我们在控制这这些类做什么事，后者是spring在帮我们管理这些类，如：给学生类的成员变量赋值。

# [Spring入门详细教程（一） ](https://www.cnblogs.com/jichi/p/10165538.html)



## 一、spring概述

Spring是一个开放源代码的设计层面框架，他解决的是业务逻辑层和其他各层的松耦合问题，因此它将面向接口的编程思想贯穿整个系统应用。Spring是于2003 年兴起的一个轻量级的Java 开发框架，由Rod Johnson创建。简单来说，Spring是一个分层的JavaSE/EE full-stack(一站式) 轻量级开源框架。

## 二、Spring特点

1、方便解耦，简化开发。

2、AOP编程的支持。

3、声明式事务的支持。

4、方便程序的测试

5、方便集成各种优秀框架

## 三、spring下载

下载地址：https://repo.spring.io/libs-release-local/org/springframework/spring/

进入后可选择下载版本，选择版本后，进入目录结构。其中dist是最终发布版本，包含开发所需lib和源码。docs是开发文档。schema是一些约束文件。

## 四、spring搭建入门案例

1、在eclipse中创建一个动态的web工程。

2、导入spring的基础lib包到lib文件夹下。

![img](https://img2018.cnblogs.com/blog/1534147/201812/1534147-20181223191711064-1012610851.png)

3、编写一个实体User类



```
package SpringTest.SpringDemo.entity;

public class User {
    private String name;
    private Integer age ;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
}
```



4、创建applicationContext.xml文件

在src文件下，新建applicationContext.xml文件，同时引入约束。



```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:util="http://www.springframework.org/schema/util"
    xmlns:jee="http://www.springframework.org/schema/jee"
    xmlns:lang="http://www.springframework.org/schema/lang"
    xmlns:jms="http://www.springframework.org/schema/jms"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:jdbc="http://www.springframework.org/schema/jdbc"
    xmlns:cache="http://www.springframework.org/schema/cache"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xmlns:oxm="http://www.springframework.org/schema/oxm"
    xmlns:task="http://www.springframework.org/schema/task"
    xmlns:tool="http://www.springframework.org/schema/tool"
    xmlns:websocket="http://www.springframework.org/schema/websocket"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee.xsd
        http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang.xsd
        http://www.springframework.org/schema/jms http://www.springframework.org/schema/jms/spring-jms.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
        http://www.springframework.org/schema/cache http://www.springframework.org/schema/cache/spring-cache.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/oxm http://www.springframework.org/schema/oxm/spring-oxm.xsd
        http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task.xsd
        http://www.springframework.org/schema/tool http://www.springframework.org/schema/tool/spring-tool.xsd
        http://www.springframework.org/schema/websocket http://www.springframework.org/schema/websocket/spring-websocket.xsd">
        
</beans>
```



5、在beans内将user实体配置进去

```
<bean name="user" class="SpringTest.SpringDemo.entity.User"></bean>
```

6、书写测试代码



```
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDemo {
    @Test
    public void test1(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) ac.getBean("user");
        System.out.println(user);
    }
}
```



7、结果显示，将user对象交给spring容器管理成功

```
SpringTest.SpringDemo.entity.User@db5e9c
```

## 五、spring中的工厂BeanFactory与ApplicationContext的区别

1、BeanFactory

spring原始接口，功能较为单一，在从容器中获得对象的时候才会创建对象。

2、ApplicationContext

每次启动容器的时候，初始化容器中的所有对象并提供更多功能。

其中的实现类ClassPathXmlApplicationContext是加载类路径下的spring配置文件，FileSystemXmlApplicationContext是加载本地磁盘下spring的配置文件。

3、实现图

![img](https://img2018.cnblogs.com/blog/1534147/201812/1534147-20181223195645667-1181163749.png)

4、结论

web开发中,使用applicationContext。

在资源匮乏的环境可以使用BeanFactory。

## 六、spring的bean元素属性详解

Spring如同一个工厂，用于生产和管理Spring容器中的Bean。在实际开发中，最常采用XML格式的配置方式，我们将通过XML文件来注册并管理Bean之间的依赖关系。

在Spring中，XML配置文件的根元素是< beans >,< beans >中可以包含多个< bean >子元素，每一个< bean >子元素定义了一个Bean,并描述了该Bean如何被装配到Spring 容器中。< bean >子元素中包含多个属性和子元素，常用的属性和子元素如下表所示:



![在这里插入图片描述](https://img-blog.csdnimg.cn/20201116215534935.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzNDA0ODcz,size_16,color_FFFFFF,t_70#pic_center)

在Spring的配置文件中，通常一个普通的Bean只需要定义id(或name)和class两个属性即可。定义Bean的方式如下:



```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"                       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <!--将指定类配置给Spring 让Spring创建其对象的实例-->
        <!--使用id属性定义bean1,其对应的实现类为com.ssm.Bean1-->
        <bean id="bean1" class="com.ssm.Bean1"/>
        <!--使用name属性定义bean2,其对应的实现类为com.ssm.Bean2-->
        <bean name="bean2" class="com.ssm.Bean2"> </bean>
</beans>
```

需要注意的是: 如果在Bean中未指定id和name，那么Spring会将class值当作id使用。

### 1、class 指定Bean的实现类全名：

被容器管理对象的完整类名。指定Bean的实现类，它必须使用类的全限定名

### 2、name 被容器管理对象的名称：

被容器管理对象的名称，根据此名称从容器中获得该对象，可以重复，可以使用特殊字符。

Spring容器通过此属性进行配置和管理，name属性可以为Bean指定多个名称，每个名称之间用逗号或分号隔开

### 3、id 唯一标识符：

被容器管理对象的唯一标识，id不可重复，不可使用特殊字符，作用与name相同，建议使用name。

Bean的唯一标识符，Spring容器对Bean的配置、管理都通过该属性进行

### 4、scope 用于设定Bean实例的作用域：

　　用于设定Bean实例的作用域，其属性值有singleton(单例)、prototype(原型)、request、 session、global Session、application和websocket，默认值为singleton

​		(1)singleton(默认):单例对象，该对象在spring容器中只会存在一个。

　　(2)prototype:多例模式，配置为多例的对象不会在spring容器中创建，只有在从容器中要获取该对象的时候，容器对该对象进行创建，每次创建都是一个新的对象。注意在与struts2配合使用的时候，struts2中的action对象必须要配置成prototype这种多例模式。

　　(3)request:web项目中，创建一个对象，将其存放在request域中。

　　(4)session:web项目中，创建一个对象，将其存放在session域中。

### 5、init-method与destory-method

init-method是对象创建的时候所执行的方法，destory-method是对象销毁时所执行的方法。对象必须是单例的，同时容器关闭的时候，对象的销毁方法才会执行。

```
<bean name="user" class="SpringTest.SpringDemo.entity.User" init-method="init" destroy-method="destory"></bean>
```

```java
public class User {
    private String name;
    private Integer age ;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public void init(){
        System.out.println("初始");
    }
    public void destory(){
        System.out.println("销毁");
    }
}
```



### 6、constructor-arg	子元素

<bean>元素的子元素，可以使用此元素传入构造参数进行实例化。该元素的index属性指定构造参数的序号(从0开始)，type属性指定构造参数的类型，参数值可以通过ref属性或value属性直接指定，也可以通过ref或value子元素指定

### 7、property	子元素

<bean>元素的子元素，用于调用Bean实例中的setter()方法完成属性赋值，从而完成依赖注入。该元素的name属性指定 Bean实例中的相应属性名，ref属性或value属性用于指定参数值

###  8、ref	子元素

<constructor-arg>、<property> 等元素的属性或子元素，可以用于指定对Bean工厂中某个Bean实例的引用

### 9、value	子元素

<constructor-arg>、<property> 等元素的属性或子元素，可以用于直接给定一个常量值

### 10、list	依赖注入

用于封装List或数组属性的依赖注入

### 11、set	依赖注入

用于封装Set类型属性的依赖注入

### 12、map	依赖注入

用于封装Map类型属性的依赖注入

### 13、entry	依赖注入

''<map> 元素的子元素，用于设置一个键值对。其kev属性指定字符串类型的键值。 ref属性或 value 属性直接指定其值，也可以通过 ref或 value 子元素指定其值

## 七、Bean的作用域
### 作用域的种类
Spring中为Bean的实例定义了7种作用域，如下图所示:

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201116220049702.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzNDA0ODcz,size_16,color_FFFFFF,t_70#pic_center)


其中,singleton和prototype是常用的两种。

### singleton作用域

singleton是Spring容器默认的作用域，当Bean的作用域为singleton时，Spring容器就只会存在一个共享的Bean实例，并且所有对Bean的请求，只要id与该Bean的id属性相匹配，就会返回同一个Bean的实例。singleton作用域对于无会话状态的Bean(如Dao组件、Service组件)来说是最理想的选择。

在Spring配置文件中，Bean的作用域是通过< bean >元素的scope属性来指定的，该属性值可以设置为singleton、prototype、request、session、globalSession、application、websocket七个值。 比如，要将作用域定义成singleton,只需要将scope属性值设置为singleton,代码如下:

<bean id="scope" class="com.ssm.scope.Scope" scope="singleton">
1
测试singleton作用域
项目创建在上一节中，已经详细介绍过，这里就不多说了。

Scope.java
在com.ssm.scope中创建Scope类，该类不需要写任何方法

package com.ssm.scope;

public class Scope {
}


applicatonContext.xml
在com.ssm.scope包中创建Spring的配置文件applicationContext.xml,并在配置文件中创建一个id为scope的Bean,通过class属性指定其对应的实现类为Scope

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"

       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

<!--将指定类配置给Spring 让Spring创建其对象的实例-->
    <bean id="scope" class="com.ssm.scope.Scope" scope="singleton"></bean>
</beans>

ScopeTest.java
在com.ssm.scope包中创建测试类ScopeTest来测试singleton作用域，代码如下:

package com.ssm.scope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScopeTest {
    public static void main(String[] args) {
        //1.初始化Spring容器，加载配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        //2.输出获得的实例
        System.out.println(applicationContext.getBean("scope"));
        System.out.println(applicationContext.getBean("scope"));
    }

运行结果如下:

从中可以看出，两次输出的结果相同，这说明Spring容器只创建了一个Scope类的实例

注意: Spring容器默认的作用域就是singleton。

### prototype作用域
对需要保持会话状态的Bean应用使用prototype作用域。在使用prototype作用域时，Spring容器会为每个对该Bean的请求都创建一个新的实例。

要将Bean定义为prototype作用域，只需在配置文件中将< bean >元素的scope属性值设置为prototype即可，代码如下:

<bean id="scope" class="com.ssm.scope.Scope" scope="prototype">
1
修改完配置文件后，我们再次运行测试类ScopeTest,输出结果如下:

从中可以看到，两次输出的Bean实例并不相同，这说明在prototype作用域下创建了两个不同的Scope实例

## 八、Bean生命周期配置

init-method：指定类中的初始化方法名称

destroy-method：指定类中销毁方法名称

## 九、spring创建对象的方式

### 1、空参构造方法

```
<bean name="user" class="SpringTest.SpringDemo.entity.User" ></bean>
```

### 2、静态工厂实例化

（1）创建一个工厂类

```
public class UserFactory {
    public static User createUser(){
        return new User();
    }
}
```

（2）配置

```
<bean name="user" class="SpringTest.SpringDemo.factory.UserFactory" factory-method="createUser"></bean>
```

### 3、实例工厂实例化

（1）创建一个工厂类

```
public class UserFactory {
    public static User createUser(){
        return new User();
    }
}
```

（2）配置

```
<bean name="user" factory-bean="userFactory" factory-method="createUser"></bean>
```

# [Spring入门详细教程（二） ](https://www.cnblogs.com/jichi/p/10176601.html)

## 一、Bean的装配方式

Bean的装配可以理解为依赖关系注入，Bean的装配方式即Bean的依赖注入的方式。

Spring容器支持多种形式的Bean的装配方式，如基于XML的装配、基于注解的装配、自动装配等。其中最常用的是基于注解的装配。

## （一）基于XML的装配
Spring提供了两种基于XML的装配方式：设值注入、构造注入

Spring实例化Bean的过程中，Spring首先会调用Bean的默认构造方法来实例化Bean对象，然后通过反射的方式调用setter方法来注入属性值。

#### 1、set方法注入

##### **常用set方法**

Bean类必须提供一个默认的无参构造方法
Bean类必须为需要注入的属性提供对应的setter方法
在配置文件中，需要使用 <property>元素为每个属性注入值

```java
<bean name="user" class="SpringTest.SpringDemo.entity.User" >
　　<property name="name" value="小明"></property>
　　<property name="age" value="18"></property>
</bean>
```

##### **p名称空间注入（不常用）**

P命名空间注入本质也是set方法注入，但比起上述的set方法注入更加方便，主要体现在配置文件中，如下：

首先，使用p命名空间给属性赋值需要在beans标签中加一条属性用于引入P命名空间：

xmlns:p="http://www.springframework.org/schema/p"

使用则是p：加上变量名=一个值

p:name="小白" p:age="10"

```java
xmlns:p="http://www.springframework.org/schema/p"
<bean name="user" class="SpringTest.SpringDemo.entity.User" p:name="小白" p:age="10"></bean>
```

##### **spel表达式注入（不常用）**

```java
    <bean name="user" class="SpringTest.SpringDemo.entity.User">
        <property name="name" value="小红"></property>
        <property name="age" value="18"></property>
    </bean>
    <bean name="user1" class="SpringTest.SpringDemo.entity.User">
        <property name="name" value="#{user.name}"></property>
        <property name="age" value="#{user.age}"></property>
    </bean>
```



#### 2、构造方法注入

Bean类必须提供有参构造方法
在配置文件中，需要使用<constructor-arg>元素来定义构造方法的参数，也可以使用其value属性来设置该参数的值

```
package SpringTest.SpringDemo.entity;

public class User {
		//含参数构造器
    public User(String name,Integer age){

    }
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void init() {
        System.out.println("初始");
    }

    public void destory() {
        System.out.println("销毁");
    }
}
```

```java
<bean name="user" class="SpringTest.SpringDemo.entity.User" >
    <constructor-arg name="name" value="小红" ></constructor-arg>
　　<constructor-arg name="age" value="50"></constructor-arg>
</bean>
```



## （二）基于注解的装配
#### spring常用注解

Spring中定义了一系列的注解，常用的注解如下：

##### 1、@Component：

描述Spring中的Bean，可以作用在任何层次

##### 2、@Repository：

用于将持久层（Dao层）的类标识为Spring中的Bean

##### 3、@Service：

用于将业务层（Service层）的类标识为Spring中的Bean

##### 4、@Controller：

用于将控制层（Controller层）的类标识为Spring中的Bean

##### 5、@Autowired：

用于对Bean的属性变量、属性的setter方法以及构造方法进行标注，配合对应的注解处理器完成Bean的自动装配工作。默认按照Bean的类型进行装配

引用类型的装配方式

```
    @Autowired
    private Car car;

```

##### 6、@Resource：

其作用与@Autowired一样。其区别在于@Autowired默认Bean的类型装配，而@Resource默认按照Bean的实例名称进行装配
@Resource中有两个重要属性：name和type。Spring将name属性解析为Bean实例名称，type属性解析为Bean实例类型。如果指定name属性，则按实例名称进行装配；如果指定type属性，则按Bean类型进行装配；如果都不指定，则先按Bean的实例名称装配，如果不能匹配，再按照Bean类型进行装配；如果都无法匹配，则抛出 NoSuchBeanDefinitionException异常。

引用类型的装配方式

```
    @Resource
    private Car car;
```

### 

##### 7、@Qualifier：

与@Autowired注解配合使用，会将默认的按Bean类型装配修改为Bean的实例名称装配，Bean的实例名称由 @Qualifier注解的参数指定

```java
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

}
```

```java
@Service("accountService")
public class AccountServiceImpl implements AccountService {
     @Resource(name="accountDao")
     private AccountDao accountDao; 
 
}
```


此时，在配置文件中，Spring注解提供了另外一种高效的注解配置方式。

含义是：告知Spring在创建容器时要扫描的包（通知Spring扫描指定包下的所有Bean）


    <context:component-scan base-package="cn.itcast"></context:component-scan>
##### 8、@Scope注解，作用在类上。

```
@Scope(scopeName="singleton")  //单例模式
public class User {
}
@Scope(scopeName="prototype")  //多例模式
public class User {
}
```

##### 9、@Value用于注入普通类型值

第一种方式：作用在属性上，通过反射的filed值，破坏了对象的封装性。

```
@Value("xiaohei")
private String name;
```

第二种方式：通过set方法赋值，不破坏对象的封装性。

```
    @Value("xiaobai")
    public void setName(String name) {
        this.name = name;
    }
```

##### 10、@PostConstruct与@PreDestroy

```
    @PostConstruct   //创建对象前调用
    public void init(){
        System.out.println("初始");
    }
    @PreDestroy　　   //对象销毁前调用
    public void destory(){
        System.out.println("销毁");
    }
```



#### spring注解配置

##### 1、开启注解扫描

```
<context:component-scan base-package="SpringTest.SpringDemo.entity"></context:component-scan>
```

扫描SpringTest.SpringDemo.entity下的所有类中的注解。

##### 2、在类上添加注解

```
@Component
public class User {
}
```

## （三）自动装配 

自动装配：将一个Bean自动地注入到其他Bean的Property中

Spring的 <bean>元素中包含一个 autowire 属性，我们可以通过设置 autowire 的属性值来自动装配Bean。

autowire属性的5个值：

default（默认）：由<beans>的default-autowire属性值来确定。
byName：根据属性的名称自动装配。容器将根据名称查找与属性完全一致的Bean，并将其属性自动装配
byType：根据属性的数据类型自动装配。
constructor：根据构造函数参数的数据类型，进行byType模式的自动装配
no：在默认情况下，不使用自动装配，Bean依赖必须通过ref元素定义



## 二、spring复杂类型注入



```
public class Collection {

    public String[] arr;
    
    public List<String> list;
    
    public Map<String,Object> map;

    public Properties props;

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    @Override
    public String toString() {
        return "Collection [arr=" + Arrays.toString(arr) + ", list=" + list + ", map=" + map + ", props=" + props + "]";
    }
    
} 
```



### 1、数组类型注入

```java
        <bean name="collect" class="SpringTest.SpringDemo.entity.Collection">
            <property name="arr">
                <array>
                    <value>xiaohei</value>
                    <value>xiaobai</value>
                </array>            
            </property>        
        </bean>
```

### 2、list类型注入

```
        <bean name="collect" class="SpringTest.SpringDemo.entity.Collection">
            <property name="list">
                <list>
                    <value>xiaohei</value>
                    <value>xiaobai</value>
                </list>            
            </property>        
        </bean>
```

### 3、map类型注入

```
        <bean name="collect" class="SpringTest.SpringDemo.entity.Collection">
            <property name="map">
                <map>
                    <entry key="name" value="xiaohei"></entry>
                    <entry key="age" value="18"></entry>
                </map>            
            </property>
        </bean>
```

### 4、properties类型注入

```
        <bean name="collect" class="SpringTest.SpringDemo.entity.Collection">
            <property name="props">
                <props>
                    <prop key="name">xiaohei</prop>
                    <prop key="age">18</prop>
                </props>
            </property>
        </bean>
```

## 三、配置spring随web项目启动初始化

在web.xml中配置。

```
 <listener>
      <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
 <context-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:applicationContext.xml</param-value>
  </context-param>
```

## 四、引入其他配置文件

引入其他配置文件（分模块开发）
实际开发中，Spring的配置内容非常多，这就导致Spring配置很繁杂且体积很大，所以，可以将部分配置拆解到其他配置文件中，而在Spring主配置文件通过以下两种方法加载


### 方式一：载入时罗列

```
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext1.xml","applicationContext2.xml")
```

### 方式二：通过import标签

```
<import resource="applicationContext.xml"></import>
```

## 五、spring注解配置

### 1、开启注解扫描

```
<context:component-scan base-package="SpringTest.SpringDemo.entity"></context:component-scan>
```

扫描SpringTest.SpringDemo.entity下的所有类中的注解。

### 2、在类上添加注解

```
@Component
public class User {
}
```

## 六、spring常用注解

### 1、@Componet，@Controller，@Service，@Repository 四个组件注解，作用在类上。四个注解并无区别，只是为了方便区分。

### 2、@Scope注解，作用在类上。

```
@Scope(scopeName="singleton")  //单例模式
public class User {
}
@Scope(scopeName="prototype")  //多例模式
public class User {
}
```

### 3、@Value用于注入普通类型值

第一种方式：作用在属性上，通过反射的filed值，破坏了对象的封装性。

```
@Value("xiaohei")
private String name;
```

第二种方式：通过set方法赋值，不破坏对象的封装性。

```
    @Value("xiaobai")
    public void setName(String name) {
        this.name = name;
    }
```

### 4、@Autowired，@Resource，@Qualifier注解 

引用类型的装配方式，详细区别请看之前的博客。

```
    @Autowired
    private Car car;
    @Resource
    private Car car;
```

### 5、@PostConstruct与@PreDestroy

```
    @PostConstruct   //创建对象前调用
    public void init(){
        System.out.println("初始");
    }
    @PreDestroy　　   //对象销毁前调用
    public void destory(){
        System.out.println("销毁");
    }
```

## 七、spring与junit整合测试

1、导入spring基础包，与aop包和test包，可从lib中找到。

2、在测试类上添加注解

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestJunit {
    
    @Resource
    private User user;
    
    @Test
    public void test1(){
        System.out.println(user);
    }
}
```

# [Spring入门详细教程（三）](https://www.cnblogs.com/jichi/p/10177004.html)

## 一、aop的概念

在软件业，**AOP为Aspect Oriented Programming的缩写，意为：面向切面编程**，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。**AOP是可以通过预编译方式和运行期动态代理实现在不修改源代码的情况下给程序动态统一添加功能的一种技术。**

AOP主要实现功能日志记录，性能统计，安全控制，事务处理，异常处理等等。将日志记录，性能统计，安全控制，事务处理，异常处理等代码从业务逻辑代码中划分出来，通过对这些行为的分离，我们希望可以将它们独立到非指导业务逻辑的方法中，进而改变这些行为的时候不影响业务逻辑的代码。

AOP主要思想总结为横向重复，纵向抽取。

## 二、spring实现aop的原理及底层实现

spring实现aop的底层使用了两种代理机制。一种是jdk的动态代理，一种是cglib的动态代理。下面来分析一下两种代理模式。

1、jdk的动态代理

被代理对象必须要实现接口才能产生代理对象，如果被代理对象不能实现接口，则这种方式的动态代理技术无效。

接下来做一个底层代码的编写来进行理解。

（1）首先jdk的动态代理要求被代理对象必须实现接口。我们准备一个接口以及一个接口的实现类。

```
public interface UserDao {
    public void saveUser();
}
public class UserDaoImpl implements UserDao {
    public void saveUser(){
        System.out.println("保存用户");
    }
}
```

（2）建立一个UserDao的动态代理类，实现接口InvocationHandler。

```java
public class UserProxy implements InvocationHandler{
    private UserDao userDao ;
    
    public UserProxy(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao createProxy(){
        UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(userDao.getClass().getClassLoader(),userDao.getClass().getInterfaces(), this);
        return userDaoProxy;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理");
        return method.invoke(userDao, args);
    }

}
```



（3）进行单元测试，发现第一个方法执行的时候没有被动态代理，第二个执行的时候进行了动态代理。

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestJunit {
    @Test
    public void test3(){
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        userDaoImpl.saveUser();
        UserProxy userProxy = new UserProxy(userDaoImpl);
        UserDao createProxy = userProxy.createProxy();
        createProxy.saveUser();
    }
}
```



2、cglib动态代理

针对一些不能实现接口的代理对象产生代理，可以对没有被final修饰的任何对象进行继承代理，其底层应用的是字节码增强的技术，生成代理对象的子类对象。如果被final修饰，类不可继承，便不可使用cglib动态代理。

（1）创建一个cglib动态代理对象实现接口。



```java
public class CglibProxy implements MethodInterceptor{

    private UserDaoImpl userDaoImpl;
    
    public CglibProxy(UserDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }
    
    public UserDaoImpl createProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDaoImpl.class);
        enhancer.setCallback(this);
        UserDaoImpl udi = (UserDaoImpl) enhancer.create();
        return udi;
    }
    
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object obj = methodProxy.invokeSuper(proxy, args);
        System.out.println("动态代理");
        return obj;
    }

}
```



（2）进行单元测试。



```java
    public void test4(){
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        userDaoImpl.saveUser();
        CglibProxy cglib = new CglibProxy(userDaoImpl);
        UserDaoImpl userDaoImpl2 = cglib.createProxy();
        userDaoImpl2.saveUser();
    }
```



可以发现第一个saveUser没有执行动态代理，第二个执行了动态代理。

结论：两种代理技术针对不同情况，互相弥补，从而使任何对象都可以实现动态代理。spring在进行aop的时候，默认使用jdk的动态代理技术，当发现jdk的动态代理技术不好使的情况下，使用cglib动态代理技术，保证被代理对象能够被正常代理。如需使用cglib动态代理可以再spring的配置文件中进行配置。

```
<aop:conﬁg proxy-target-class="true">
```

## 三、aop开发中的相关概念

1、Joinpoint（连接点）：目标对象中，所有可以增强的方法。

2、Pointcut（切入点）：目标对象中，已经增强的方法。

3、Advice（通知）：对于目标对象来说，需要给目标对象增强的方法。

4、Target（目标对象）：被代理对象。

5、Weaving（织入）：将通知应用到切入点的过程。

6、Proxy（代理）：将通知织入到目标对象后，形成的增强后的对象。

7、Aspect（切面）：切入点和通知的结合。

## 四、spring中aop的实现方式

分两种方式介绍，一种是xml配置方式，一种是注解方式。

1、xml配置方式

（1）实现spring的aop需要导入aop包，aspect包，aopalliance包，weaver包。在[spring教程一](https://www.cnblogs.com/jichi/p/10165538.html)中可以找到获取这些包的方法。

（2）编写需要增加的方法类。



```java
public class UserDaoImpl{
    
    public void saveUser(){
        System.out.println("保存用户");
    }
    public void deleteUser(){
        System.out.println("删除用户");
    }
}
```



（3）编写通知，也就是说想要增加的代码方法。



```java
public class UserAdvice{
    
    public void before(){
        System.out.println("前置通知");
    }
    
    public void afterReturning(){
        System.out.println("后置通知（不发生异常的情况下调用）");
    }

    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("执行前");
        Object proceed = pjp.proceed();
        System.out.println("执行后");
        return proceed;
    }
    
    public void afterThrowException(){
        System.out.println("发生异常调用");
    }
    
    public void after(){
        System.out.println("后置通知，发生异常也会调用");
    }
}
```



（4）在spring的配置文件中进行配置



```
    <bean name = "userDaoImpl" class="SpringTest.SpringDemo.aop.UserDaoImpl"></bean>
    <bean name="userAdvice" class="SpringTest.SpringDemo.aop.UserAdvice"></bean>
    <aop:config>
        <aop:pointcut expression="execution(* SpringTest.SpringDemo.aop..UserDaoImpl.*(..))" id="pc"/>
        <aop:aspect ref="userAdvice">
            <aop:before method="before" pointcut-ref="pc"/>
            <aop:after-returning method="afterReturning" pointcut-ref="pc"/>
            <aop:around method="around" pointcut-ref="pc"/>
            <aop:after-throwing method="afterThrowException" pointcut-ref="pc"/>
            <aop:after method="after" pointcut-ref="pc"/>
        </aop:aspect>
    </aop:config>
```



（5）进行单元测试



```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestAop {

    @Resource
    private UserDaoImpl userDaoImpl;
    
    @Test
    public void test1(){
        userDaoImpl.saveUser();
    }
}
```



结果如下：织入成功。

![img](https://img2018.cnblogs.com/blog/1534147/201812/1534147-20181226222249566-1468063743.png)

2、注解配置方式

（1）同第一种方式需要导入包

（2）编写需要增加的方法类。



```java
public class UserDaoImpl{
    
    public void saveUser(){
        System.out.println("保存用户");
    }
    public void deleteUser(){
        System.out.println("删除用户");
    }
}
```



（3）编写通知，也就是说想要增加的代码方法。



```java
public class UserAdvice{
    
    public void before(){
        System.out.println("前置通知");
    }
    
    public void afterReturning(){
        System.out.println("后置通知（不发生异常的情况下调用）");
    }

    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("执行前");
        Object proceed = pjp.proceed();
        System.out.println("执行后");
        return proceed;
    }
    
    public void afterThrowException(){
        System.out.println("发生异常调用");
    }
    
    public void after(){
        System.out.println("后置通知，发生异常也会调用");
    }
}
```



（4）在spring配置文件中进行配置，并开启注解aop

```
    <bean name = "userDaoImpl" class="SpringTest.SpringDemo.aop.UserDaoImpl"></bean>
    <bean name="userAdvice" class="SpringTest.SpringDemo.aop.UserAdvice"></bean>
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
```

（5）在通知类上打上aspect的注解。在方法上打上相应注解



```java
@Aspect
public class UserAdvice{
    
    @Before("execution(* SpringTest.SpringDemo.aop..UserDaoImpl.*(..))")
    public void before(){
        System.out.println("前置通知");
    }
    
    @AfterReturning("execution(* SpringTest.SpringDemo.aop..UserDaoImpl.*(..))")
    public void afterReturning(){
        System.out.println("后置通知（不发生异常的情况下调用）");
    }

    @Around("execution(* SpringTest.SpringDemo.aop..UserDaoImpl.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("执行前");
        Object proceed = pjp.proceed();
        System.out.println("执行后");
        return proceed;
    }
    
    @AfterThrowing("execution(* SpringTest.SpringDemo.aop..UserDaoImpl.*(..))")
    public void afterThrowException(){
        System.out.println("发生异常调用");
    }
    
    @After("execution(* SpringTest.SpringDemo.aop..UserDaoImpl.*(..))")
    public void after(){
        System.out.println("后置通知，发生异常也会调用");
    }
}
```



优化方式：每个方法都配置方法抽取，显得比较臃肿，可以进行提取，方法如下



```java
@Aspect
public class UserAdvice{
    
    @Pointcut("execution(* SpringTest.SpringDemo.aop..UserDaoImpl.*(..))")
    public void adc(){}
    
    @Before("UserAdvice.adc()")
    public void before(){
        System.out.println("前置通知");
    }
    
    @AfterReturning("UserAdvice.adc()")
    public void afterReturning(){
        System.out.println("后置通知（不发生异常的情况下调用）");
    }

    @Around("UserAdvice.adc()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("执行前");
        Object proceed = pjp.proceed();
        System.out.println("执行后");
        return proceed;
    }
    
    @AfterThrowing("UserAdvice.adc()")
    public void afterThrowException(){
        System.out.println("发生异常调用");
    }
    
    @After("UserAdvice.adc()")
    public void after(){
        System.out.println("后置通知，发生异常也会调用");
    }
}
```

# [Spring入门详细教程（四）](https://www.cnblogs.com/jichi/p/10211475.html)

## 一、spring整合jdbc继承jdbcdaosupport的方式

1、导入所需jar包。

除了之前介绍的spring的基础包，还需要导入数据库连接池包，jdbc驱动包，spring的jdbc包，spring的事务。

2、书写dao层代码。



```java
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
    @Override
    public void save(User u) {
        String sql = "insert into user values('1',?) ";
        super.getJdbcTemplate().update(sql, u.getName());
    }
    @Override
    public void delete(Integer id) {
        String sql = "delete from user where id = ? ";
        super.getJdbcTemplate().update(sql,id);
    }
    @Override
    public void update(User u) {
        String sql = "update  user set name = ? where id=? ";
        super.getJdbcTemplate().update(sql, u.getName(),u.getId());
    }
    @Override
    public User getById(Integer id) {
        String sql = "select * from user where id = ? ";
        return super.getJdbcTemplate().queryForObject(sql,new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int arg1) throws SQLException {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                return u;
            }}, id);
        
    }
    @Override
    public int getTotalCount() {
        String sql = "select count(*) from user  ";
        Integer count = super.getJdbcTemplate().queryForObject(sql, Integer.class);
        return count;
    }
    @Override
    public List<User> getAll() {
        String sql = "select * from user  ";
        List<User> list = super.getJdbcTemplate().query(sql, new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int arg1) throws SQLException {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                return u;
            }});
        return list;
    }
}
```



3、建立数据库链接配置文件

db.properties

```
jdbc.jdbcUrl=jdbc:mysql:///spring
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.user=root
jdbc.password=1234
```

4、在spring容器中进行配置



```java
<!-- 指定spring读取db.properties配置 -->
<context:property-placeholder location="classpath:db.properties"  />
<!-- 将连接池放入spring容器 -->
<bean name="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" >
    <property name="jdbcUrl" value="${jdbc.jdbcUrl}" ></property>
    <property name="driverClass" value="${jdbc.driverClass}" ></property>
    <property name="user" value="${jdbc.user}" ></property>
    <property name="password" value="${jdbc.password}" ></property>
</bean>
<!-- 将UserDao放入spring容器 -->
<bean name="userDao" class="SpringTest.SpringDemo.jdbctemplate.UserDaoImpl" >
    <property name="dataSource" ref="dataSource" ></property>
</bean>
```



5、由于userDaoImpl已经继承了jdbcDaoSupport。jdbcDaoSupport中已经定义了jdbcTemplate，同时内置了setDataSource。可以自动将连接池放入。源码如下：



```java
/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.jdbc.core.support;

import java.sql.Connection;
import javax.sql.DataSource;

import org.springframework.dao.support.DaoSupport;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.SQLExceptionTranslator;

/**
 * Convenient super class for JDBC-based data access objects.
 *
 * <p>Requires a {@link javax.sql.DataSource} to be set, providing a
 * {@link org.springframework.jdbc.core.JdbcTemplate} based on it to
 * subclasses through the {@link #getJdbcTemplate()} method.
 *
 * <p>This base class is mainly intended for JdbcTemplate usage but can
 * also be used when working with a Connection directly or when using
 * {@code org.springframework.jdbc.object} operation objects.
 *
 * @author Juergen Hoeller
 * @since 28.07.2003
 * @see #setDataSource
 * @see #getJdbcTemplate
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
public abstract class JdbcDaoSupport extends DaoSupport {

    private JdbcTemplate jdbcTemplate;


    /**
     * Set the JDBC DataSource to be used by this DAO.
     */
    public final void setDataSource(DataSource dataSource) {
        if (this.jdbcTemplate == null || dataSource != this.jdbcTemplate.getDataSource()) {
            this.jdbcTemplate = createJdbcTemplate(dataSource);
            initTemplateConfig();
        }
    }

    /**
     * Create a JdbcTemplate for the given DataSource.
     * Only invoked if populating the DAO with a DataSource reference!
     * <p>Can be overridden in subclasses to provide a JdbcTemplate instance
     * with different configuration, or a custom JdbcTemplate subclass.
     * @param dataSource the JDBC DataSource to create a JdbcTemplate for
     * @return the new JdbcTemplate instance
     * @see #setDataSource
     */
    protected JdbcTemplate createJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Return the JDBC DataSource used by this DAO.
     */
    public final DataSource getDataSource() {
        return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
    }

    /**
     * Set the JdbcTemplate for this DAO explicitly,
     * as an alternative to specifying a DataSource.
     */
    public final void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initTemplateConfig();
    }

    /**
     * Return the JdbcTemplate for this DAO,
     * pre-initialized with the DataSource or set explicitly.
     */
    public final JdbcTemplate getJdbcTemplate() {
      return this.jdbcTemplate;
    }

    /**
     * Initialize the template-based configuration of this DAO.
     * Called after a new JdbcTemplate has been set, either directly
     * or through a DataSource.
     * <p>This implementation is empty. Subclasses may override this
     * to configure further objects based on the JdbcTemplate.
     * @see #getJdbcTemplate()
     */
    protected void initTemplateConfig() {
    }

    @Override
    protected void checkDaoConfig() {
        if (this.jdbcTemplate == null) {
            throw new IllegalArgumentException("'dataSource' or 'jdbcTemplate' is required");
        }
    }


    /**
     * Return the SQLExceptionTranslator of this DAO's JdbcTemplate,
     * for translating SQLExceptions in custom JDBC access code.
     * @see org.springframework.jdbc.core.JdbcTemplate#getExceptionTranslator()
     */
    protected final SQLExceptionTranslator getExceptionTranslator() {
        return getJdbcTemplate().getExceptionTranslator();
    }

    /**
     * Get a JDBC Connection, either from the current transaction or a new one.
     * @return the JDBC Connection
     * @throws CannotGetJdbcConnectionException if the attempt to get a Connection failed
     * @see org.springframework.jdbc.datasource.DataSourceUtils#getConnection(javax.sql.DataSource)
     */
    protected final Connection getConnection() throws CannotGetJdbcConnectionException {
        return DataSourceUtils.getConnection(getDataSource());
    }

    /**
     * Close the given JDBC Connection, created via this DAO's DataSource,
     * if it isn't bound to the thread.
     * @param con Connection to close
     * @see org.springframework.jdbc.datasource.DataSourceUtils#releaseConnection
     */
    protected final void releaseConnection(Connection con) {
        DataSourceUtils.releaseConnection(con, getDataSource());
    }

}
```



6、编写测试类



```java
    @Test
    public void fun2() throws Exception{
        User u = new User();
        u.setName("tom");
        ud.save(u);
    }
```



7、执行成功

## 二、spring整合jdbctemplate

1、导入所需jar包。

除了之前介绍的spring的基础包，还需要导入数据库连接池包，jdbc驱动包，spring的jdbc包，spring的事务。

2、配置jdbctemplate



```
<!-- 指定spring读取db.properties配置 -->
<context:property-placeholder location="classpath:db.properties"  />
<!-- 1.将连接池放入spring容器 -->
<bean name="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" >
    <property name="jdbcUrl" value="${jdbc.jdbcUrl}" ></property>
    <property name="driverClass" value="${jdbc.driverClass}" ></property>
    <property name="user" value="${jdbc.user}" ></property>
    <property name="password" value="${jdbc.password}" ></property>
</bean>
<!-- 2.将JDBCTemplate放入spring容器 -->
<bean name="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate" >
    <property name="dataSource" ref="dataSource" ></property>
</bean>
<!-- 3.将UserDao放入spring容器 -->
<bean name="userDao" class="SpringTest.SpringDemo.jdbctemplate.UserDaoImpl" >
    <property name="jdbcTemplate" ref="jdbcTemplate" ></property>
</bean>
```



3、书写dao层代码



```
public class UserDaoImpl  implements UserDao {
    
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void save(User u) {
        String sql = "insert into user values('1',?) ";
        jdbcTemplate.update(sql, u.getName());
    }
    @Override
    public void delete(Integer id) {
        String sql = "delete from user where id = ? ";
        jdbcTemplate.update(sql,id);
    }
    @Override
    public void update(User u) {
        String sql = "update  user set name = ? where id=? ";
        jdbcTemplate.update(sql, u.getName(),u.getId());
    }
    @Override
    public User getById(Integer id) {
        String sql = "select * from user where id = ? ";
        return jdbcTemplate.queryForObject(sql,new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int arg1) throws SQLException {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                return u;
            }}, id);
        
    }
    @Override
    public int getTotalCount() {
        String sql = "select count(*) from user  ";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }
    @Override
    public List<User> getAll() {
        String sql = "select * from user  ";
        List<User> list = jdbcTemplate.query(sql, new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int arg1) throws SQLException {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                return u;
            }});
        return list;
    }
}
```



4、书写测试方法



```
    @Test
    public void fun2() throws Exception{
        User u = new User();
        u.setName("tom");
        ud.save(u);
    }
```

## 三、spring中jdbctemplate的相关方法

1、update

用来执行insert，update，delete语句。



```
    @Override
    public void save(User u) {
        String sql = "insert into user values('1',?) ";
        jdbcTemplate.update(sql, u.getName());
    }
    @Override
    public void delete(Integer id) {
        String sql = "delete from user where id = ? ";
        jdbcTemplate.update(sql,id);
    }
    @Override
    public void update(User u) {
        String sql = "update  user set name = ? where id=? ";
        jdbcTemplate.update(sql, u.getName(),u.getId());
    }
```



2、查询某一具体类



```
    @Override
    public int getTotalCount() {
        String sql = "select count(*) from user  ";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }
```



3、将查询的数据封入实体类（单个对象，实现rowmapper接口）



```
    @Override
    public User getById(Integer id) {
        String sql = "select * from user where id = ? ";
        return jdbcTemplate.queryForObject(sql,new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int arg1) throws SQLException {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                return u;
            }}, id);
        
    }
```



4、将查询的数据封入实体类（list对象，实现rowmapper接口）



```
    @Override
    public List<User> getAll() {
        String sql = "select * from user  ";
        List<User> list = jdbcTemplate.query(sql, new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int arg1) throws SQLException {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                return u;
            }});
        return list;
    }
```



5、根据数据库查出的字段与实体类字段名自动对应



```
    @Override
    public List<User> getAll() {
        String sql = "select * from user  ";
        List<User> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
        return list;
    }
```

# [spring入门详细教程（五）](https://www.cnblogs.com/jichi/p/10229812.html)

## 一、什么是事务

事务用白话来说比较好理解，我们举个例子。比如说你做两件事要达成一个目的。其中有一件事失败，你就相当于没做。如果两件事都成功，这件事你才算做的成功。用官方话来解释，就是事务是逻辑上的一组操作，组成这组操作的各个逻辑单元，要么一起成功，要么一起失败。

## 二、事务的特性

了解了事务的概念，我们就可以了解事务的特性了。

事务有四大特性：原子性，一致性，隔离性，持久性。

原子性：强调事务的不可分割。

一致性：事务的执行的前后数据的完整性保持一致。

隔离性：一个事务执行的过程中，不应受到其他事务的干扰。

持久性：事务一旦结束，数据就持久到数据库。

## 三、事务的并发问题

事务三大问题，脏读，幻读，不可重复读。

脏读：一个事务读到了另一个事务的未提交的数据。

不可重复读：一个事务读到了另一个事务已经提交的update的数据导致多次查询结果不一致。

虚幻读：一个事务读到了另一个事务已经提交的insert的数据导致多次查询结果不一致。

## 四、事务并发问题的解决：事务隔离级别

1、未提交读：脏读，不可重复读，虚幻读都有可能发生。

2、已提交读：避免脏读。不可重复读和虚幻读都可能发生。

3、可重复读：避免脏读和不可重复读。虚幻读可能发生。

4、串行化：避免以上所有的读问题。

事务的隔离级别越高，性能会降的越低。其实就是牺牲性能来提高准确性。在实际中，一般选取中间的隔离级别。

mysql默认隔离级别：可重复读。

oracle默认隔离级别：已提交读。

## 五、spring封装事务

首先spring封装了我们需要进行的事务操作。事务操作是什么呢。比如说我们首先需要打开事务，进行操作后，提交事务。如果发生错误，回滚事务，如果中途未发生错误，则事务进行提交。如果spring没有对事务进行封装，我们需要没进行一次操作都重新写事务的处理代码。spring封装后，事务代码被封装，我们不用一遍遍的重复编写代码，配置好后，不用书写事务代码。接下来我们了解几个spring中事务的相关概念。

1、PlatformTransactionManager平台事务管理器

在不同的平台，操作代码的事务各不相同，spring为我们提供了一个接口。这个接口就是PlatformTransactionManager。

我们可以看一下里面的源码。提供了一个获得事务的方法，一个提交事务的方法以及一个回滚事务的方法。此处贴一下源码



```
/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.transaction;

/**
 * This is the central interface in Spring's transaction infrastructure.
 * Applications can use this directly, but it is not primarily meant as API:
 * Typically, applications will work with either TransactionTemplate or
 * declarative transaction demarcation through AOP.
 *
 * <p>For implementors, it is recommended to derive from the provided
 * {@link org.springframework.transaction.support.AbstractPlatformTransactionManager}
 * class, which pre-implements the defined propagation behavior and takes care
 * of transaction synchronization handling. Subclasses have to implement
 * template methods for specific states of the underlying transaction,
 * for example: begin, suspend, resume, commit.
 *
 * <p>The default implementations of this strategy interface are
 * {@link org.springframework.transaction.jta.JtaTransactionManager} and
 * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager},
 * which can serve as an implementation guide for other transaction strategies.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 16.05.2003
 * @see org.springframework.transaction.support.TransactionTemplate
 * @see org.springframework.transaction.interceptor.TransactionInterceptor
 * @see org.springframework.transaction.interceptor.TransactionProxyFactoryBean
 */
public interface PlatformTransactionManager {

    /**
     * Return a currently active transaction or create a new one, according to
     * the specified propagation behavior.
     * <p>Note that parameters like isolation level or timeout will only be applied
     * to new transactions, and thus be ignored when participating in active ones.
     * <p>Furthermore, not all transaction definition settings will be supported
     * by every transaction manager: A proper transaction manager implementation
     * should throw an exception when unsupported settings are encountered.
     * <p>An exception to the above rule is the read-only flag, which should be
     * ignored if no explicit read-only mode is supported. Essentially, the
     * read-only flag is just a hint for potential optimization.
     * @param definition TransactionDefinition instance (can be {@code null} for defaults),
     * describing propagation behavior, isolation level, timeout etc.
     * @return transaction status object representing the new or current transaction
     * @throws TransactionException in case of lookup, creation, or system errors
     * @throws IllegalTransactionStateException if the given transaction definition
     * cannot be executed (for example, if a currently active transaction is in
     * conflict with the specified propagation behavior)
     * @see TransactionDefinition#getPropagationBehavior
     * @see TransactionDefinition#getIsolationLevel
     * @see TransactionDefinition#getTimeout
     * @see TransactionDefinition#isReadOnly
     */
    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;

    /**
     * Commit the given transaction, with regard to its status. If the transaction
     * has been marked rollback-only programmatically, perform a rollback.
     * <p>If the transaction wasn't a new one, omit the commit for proper
     * participation in the surrounding transaction. If a previous transaction
     * has been suspended to be able to create a new one, resume the previous
     * transaction after committing the new one.
     * <p>Note that when the commit call completes, no matter if normally or
     * throwing an exception, the transaction must be fully completed and
     * cleaned up. No rollback call should be expected in such a case.
     * <p>If this method throws an exception other than a TransactionException,
     * then some before-commit error caused the commit attempt to fail. For
     * example, an O/R Mapping tool might have tried to flush changes to the
     * database right before commit, with the resulting DataAccessException
     * causing the transaction to fail. The original exception will be
     * propagated to the caller of this commit method in such a case.
     * @param status object returned by the {@code getTransaction} method
     * @throws UnexpectedRollbackException in case of an unexpected rollback
     * that the transaction coordinator initiated
     * @throws HeuristicCompletionException in case of a transaction failure
     * caused by a heuristic decision on the side of the transaction coordinator
     * @throws TransactionSystemException in case of commit or system errors
     * (typically caused by fundamental resource failures)
     * @throws IllegalTransactionStateException if the given transaction
     * is already completed (that is, committed or rolled back)
     * @see TransactionStatus#setRollbackOnly
     */
    void commit(TransactionStatus status) throws TransactionException;

    /**
     * Perform a rollback of the given transaction.
     * <p>If the transaction wasn't a new one, just set it rollback-only for proper
     * participation in the surrounding transaction. If a previous transaction
     * has been suspended to be able to create a new one, resume the previous
     * transaction after rolling back the new one.
     * <p><b>Do not call rollback on a transaction if commit threw an exception.</b>
     * The transaction will already have been completed and cleaned up when commit
     * returns, even in case of a commit exception. Consequently, a rollback call
     * after commit failure will lead to an IllegalTransactionStateException.
     * @param status object returned by the {@code getTransaction} method
     * @throws TransactionSystemException in case of rollback or system errors
     * (typically caused by fundamental resource failures)
     * @throws IllegalTransactionStateException if the given transaction
     * is already completed (that is, committed or rolled back)
     */
    void rollback(TransactionStatus status) throws TransactionException;

}
```



我们通过查看接口发现AbstractPlatformTransactionManager实现了这一接口。其中提供了一些方法。继续查看谁继承了该抽象类。

发现有两个：DataSourceTransactionManager与HibernateTransitionmanager。

DataSourceTransactionManager：这一事务管理类，在用spring的jdbc以及mybaties进行操作事务的时候使用。

HibernateTransitionmanager：在用hiberante进行操作事务的时候使用。

2、TransactionDefinition事务定义信息

主要记录了事务的定义信息相关。

记录了事务的隔离级别，事务传播行为，超时信息，只读信息的基本信息。此处可以看一下源码感受一下，这里面的一些信息，后面会讲解



```
/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.transaction;

import java.sql.Connection;

/**
 * Interface that defines Spring-compliant transaction properties.
 * Based on the propagation behavior definitions analogous to EJB CMT attributes.
 *
 * <p>Note that isolation level and timeout settings will not get applied unless
 * an actual new transaction gets started. As only {@link #PROPAGATION_REQUIRED},
 * {@link #PROPAGATION_REQUIRES_NEW} and {@link #PROPAGATION_NESTED} can cause
 * that, it usually doesn't make sense to specify those settings in other cases.
 * Furthermore, be aware that not all transaction managers will support those
 * advanced features and thus might throw corresponding exceptions when given
 * non-default values.
 *
 * <p>The {@link #isReadOnly() read-only flag} applies to any transaction context,
 * whether backed by an actual resource transaction or operating non-transactionally
 * at the resource level. In the latter case, the flag will only apply to managed
 * resources within the application, such as a Hibernate {@code Session}.
 *
 * @author Juergen Hoeller
 * @since 08.05.2003
 * @see PlatformTransactionManager#getTransaction(TransactionDefinition)
 * @see org.springframework.transaction.support.DefaultTransactionDefinition
 * @see org.springframework.transaction.interceptor.TransactionAttribute
 */
public interface TransactionDefinition {

    /**
     * Support a current transaction; create a new one if none exists.
     * Analogous to the EJB transaction attribute of the same name.
     * <p>This is typically the default setting of a transaction definition,
     * and typically defines a transaction synchronization scope.
     */
    int PROPAGATION_REQUIRED = 0;

    /**
     * Support a current transaction; execute non-transactionally if none exists.
     * Analogous to the EJB transaction attribute of the same name.
     * <p><b>NOTE:</b> For transaction managers with transaction synchronization,
     * {@code PROPAGATION_SUPPORTS} is slightly different from no transaction
     * at all, as it defines a transaction scope that synchronization might apply to.
     * As a consequence, the same resources (a JDBC {@code Connection}, a
     * Hibernate {@code Session}, etc) will be shared for the entire specified
     * scope. Note that the exact behavior depends on the actual synchronization
     * configuration of the transaction manager!
     * <p>In general, use {@code PROPAGATION_SUPPORTS} with care! In particular, do
     * not rely on {@code PROPAGATION_REQUIRED} or {@code PROPAGATION_REQUIRES_NEW}
     * <i>within</i> a {@code PROPAGATION_SUPPORTS} scope (which may lead to
     * synchronization conflicts at runtime). If such nesting is unavoidable, make sure
     * to configure your transaction manager appropriately (typically switching to
     * "synchronization on actual transaction").
     * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#setTransactionSynchronization
     * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#SYNCHRONIZATION_ON_ACTUAL_TRANSACTION
     */
    int PROPAGATION_SUPPORTS = 1;

    /**
     * Support a current transaction; throw an exception if no current transaction
     * exists. Analogous to the EJB transaction attribute of the same name.
     * <p>Note that transaction synchronization within a {@code PROPAGATION_MANDATORY}
     * scope will always be driven by the surrounding transaction.
     */
    int PROPAGATION_MANDATORY = 2;

    /**
     * Create a new transaction, suspending the current transaction if one exists.
     * Analogous to the EJB transaction attribute of the same name.
     * <p><b>NOTE:</b> Actual transaction suspension will not work out-of-the-box
     * on all transaction managers. This in particular applies to
     * {@link org.springframework.transaction.jta.JtaTransactionManager},
     * which requires the {@code javax.transaction.TransactionManager} to be
     * made available it to it (which is server-specific in standard Java EE).
     * <p>A {@code PROPAGATION_REQUIRES_NEW} scope always defines its own
     * transaction synchronizations. Existing synchronizations will be suspended
     * and resumed appropriately.
     * @see org.springframework.transaction.jta.JtaTransactionManager#setTransactionManager
     */
    int PROPAGATION_REQUIRES_NEW = 3;

    /**
     * Do not support a current transaction; rather always execute non-transactionally.
     * Analogous to the EJB transaction attribute of the same name.
     * <p><b>NOTE:</b> Actual transaction suspension will not work out-of-the-box
     * on all transaction managers. This in particular applies to
     * {@link org.springframework.transaction.jta.JtaTransactionManager},
     * which requires the {@code javax.transaction.TransactionManager} to be
     * made available it to it (which is server-specific in standard Java EE).
     * <p>Note that transaction synchronization is <i>not</i> available within a
     * {@code PROPAGATION_NOT_SUPPORTED} scope. Existing synchronizations
     * will be suspended and resumed appropriately.
     * @see org.springframework.transaction.jta.JtaTransactionManager#setTransactionManager
     */
    int PROPAGATION_NOT_SUPPORTED = 4;

    /**
     * Do not support a current transaction; throw an exception if a current transaction
     * exists. Analogous to the EJB transaction attribute of the same name.
     * <p>Note that transaction synchronization is <i>not</i> available within a
     * {@code PROPAGATION_NEVER} scope.
     */
    int PROPAGATION_NEVER = 5;

    /**
     * Execute within a nested transaction if a current transaction exists,
     * behave like {@link #PROPAGATION_REQUIRED} else. There is no analogous
     * feature in EJB.
     * <p><b>NOTE:</b> Actual creation of a nested transaction will only work on
     * specific transaction managers. Out of the box, this only applies to the JDBC
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager}
     * when working on a JDBC 3.0 driver. Some JTA providers might support
     * nested transactions as well.
     * @see org.springframework.jdbc.datasource.DataSourceTransactionManager
     */
    int PROPAGATION_NESTED = 6;


    /**
     * Use the default isolation level of the underlying datastore.
     * All other levels correspond to the JDBC isolation levels.
     * @see java.sql.Connection
     */
    int ISOLATION_DEFAULT = -1;

    /**
     * Indicates that dirty reads, non-repeatable reads and phantom reads
     * can occur.
     * <p>This level allows a row changed by one transaction to be read by another
     * transaction before any changes in that row have been committed (a "dirty read").
     * If any of the changes are rolled back, the second transaction will have
     * retrieved an invalid row.
     * @see java.sql.Connection#TRANSACTION_READ_UNCOMMITTED
     */
    int ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;

    /**
     * Indicates that dirty reads are prevented; non-repeatable reads and
     * phantom reads can occur.
     * <p>This level only prohibits a transaction from reading a row
     * with uncommitted changes in it.
     * @see java.sql.Connection#TRANSACTION_READ_COMMITTED
     */
    int ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;

    /**
     * Indicates that dirty reads and non-repeatable reads are prevented;
     * phantom reads can occur.
     * <p>This level prohibits a transaction from reading a row with uncommitted changes
     * in it, and it also prohibits the situation where one transaction reads a row,
     * a second transaction alters the row, and the first transaction re-reads the row,
     * getting different values the second time (a "non-repeatable read").
     * @see java.sql.Connection#TRANSACTION_REPEATABLE_READ
     */
    int ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;

    /**
     * Indicates that dirty reads, non-repeatable reads and phantom reads
     * are prevented.
     * <p>This level includes the prohibitions in {@link #ISOLATION_REPEATABLE_READ}
     * and further prohibits the situation where one transaction reads all rows that
     * satisfy a {@code WHERE} condition, a second transaction inserts a row
     * that satisfies that {@code WHERE} condition, and the first transaction
     * re-reads for the same condition, retrieving the additional "phantom" row
     * in the second read.
     * @see java.sql.Connection#TRANSACTION_SERIALIZABLE
     */
    int ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;


    /**
     * Use the default timeout of the underlying transaction system,
     * or none if timeouts are not supported.
     */
    int TIMEOUT_DEFAULT = -1;


    /**
     * Return the propagation behavior.
     * <p>Must return one of the {@code PROPAGATION_XXX} constants
     * defined on {@link TransactionDefinition this interface}.
     * @return the propagation behavior
     * @see #PROPAGATION_REQUIRED
     * @see org.springframework.transaction.support.TransactionSynchronizationManager#isActualTransactionActive()
     */
    int getPropagationBehavior();

    /**
     * Return the isolation level.
     * <p>Must return one of the {@code ISOLATION_XXX} constants
     * defined on {@link TransactionDefinition this interface}.
     * <p>Only makes sense in combination with {@link #PROPAGATION_REQUIRED}
     * or {@link #PROPAGATION_REQUIRES_NEW}.
     * <p>Note that a transaction manager that does not support custom isolation levels
     * will throw an exception when given any other level than {@link #ISOLATION_DEFAULT}.
     * @return the isolation level
     */
    int getIsolationLevel();

    /**
     * Return the transaction timeout.
     * <p>Must return a number of seconds, or {@link #TIMEOUT_DEFAULT}.
     * <p>Only makes sense in combination with {@link #PROPAGATION_REQUIRED}
     * or {@link #PROPAGATION_REQUIRES_NEW}.
     * <p>Note that a transaction manager that does not support timeouts will throw
     * an exception when given any other timeout than {@link #TIMEOUT_DEFAULT}.
     * @return the transaction timeout
     */
    int getTimeout();

    /**
     * Return whether to optimize as a read-only transaction.
     * <p>The read-only flag applies to any transaction context, whether
     * backed by an actual resource transaction
     * ({@link #PROPAGATION_REQUIRED}/{@link #PROPAGATION_REQUIRES_NEW}) or
     * operating non-transactionally at the resource level
     * ({@link #PROPAGATION_SUPPORTS}). In the latter case, the flag will
     * only apply to managed resources within the application, such as a
     * Hibernate {@code Session}.
     <<     * <p>This just serves as a hint for the actual transaction subsystem;
     * it will <i>not necessarily</i> cause failure of write access attempts.
     * A transaction manager which cannot interpret the read-only hint will
     * <i>not</i> throw an exception when asked for a read-only transaction.
     * @return {@code true} if the transaction is to be optimized as read-only
     * @see org.springframework.transaction.support.TransactionSynchronization#beforeCommit(boolean)
     * @see org.springframework.transaction.support.TransactionSynchronizationManager#isCurrentTransactionReadOnly()
     */
    boolean isReadOnly();

    /**
     * Return the name of this transaction. Can be {@code null}.
     * <p>This will be used as the transaction name to be shown in a
     * transaction monitor, if applicable (for example, WebLogic's).
     * <p>In case of Spring's declarative transactions, the exposed name will be
     * the {@code fully-qualified class name + "." + method name} (by default).
     * @return the name of this transaction
     * @see org.springframework.transaction.interceptor.TransactionAspectSupport
     * @see org.springframework.transaction.support.TransactionSynchronizationManager#getCurrentTransactionName()
     */
    String getName();

}
```



3、TransactionStatus事务状态信息

记录事务的状态信息，可以看看源码。理解一下大神的思想。



```
/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.transaction;

import java.io.Flushable;

/**
 * Representation of the status of a transaction.
 *
 * <p>Transactional code can use this to retrieve status information,
 * and to programmatically request a rollback (instead of throwing
 * an exception that causes an implicit rollback).
 *
 * <p>Derives from the SavepointManager interface to provide access
 * to savepoint management facilities. Note that savepoint management
 * is only available if supported by the underlying transaction manager.
 *
 * @author Juergen Hoeller
 * @since 27.03.2003
 * @see #setRollbackOnly()
 * @see PlatformTransactionManager#getTransaction
 * @see org.springframework.transaction.support.TransactionCallback#doInTransaction
 * @see org.springframework.transaction.interceptor.TransactionInterceptor#currentTransactionStatus()
 */
public interface TransactionStatus extends SavepointManager, Flushable {

    /**
     * Return whether the present transaction is new (else participating
     * in an existing transaction, or potentially not running in an
     * actual transaction in the first place).
     */
    boolean isNewTransaction();

    /**
     * Return whether this transaction internally carries a savepoint,
     * that is, has been created as nested transaction based on a savepoint.
     * <p>This method is mainly here for diagnostic purposes, alongside
     * {@link #isNewTransaction()}. For programmatic handling of custom
     * savepoints, use SavepointManager's operations.
     * @see #isNewTransaction()
     * @see #createSavepoint
     * @see #rollbackToSavepoint(Object)
     * @see #releaseSavepoint(Object)
     */
    boolean hasSavepoint();

    /**
     * Set the transaction rollback-only. This instructs the transaction manager
     * that the only possible outcome of the transaction may be a rollback, as
     * alternative to throwing an exception which would in turn trigger a rollback.
     * <p>This is mainly intended for transactions managed by
     * {@link org.springframework.transaction.support.TransactionTemplate} or
     * {@link org.springframework.transaction.interceptor.TransactionInterceptor},
     * where the actual commit/rollback decision is made by the container.
     * @see org.springframework.transaction.support.TransactionCallback#doInTransaction
     * @see org.springframework.transaction.interceptor.TransactionAttribute#rollbackOn
     */
    void setRollbackOnly();

    /**
     * Return whether the transaction has been marked as rollback-only
     * (either by the application or by the transaction infrastructure).
     */
    boolean isRollbackOnly();

    /**
     * Flush the underlying session to the datastore, if applicable:
     * for example, all affected Hibernate/JPA sessions.
     */
    @Override
    void flush();

    /**
     * Return whether this transaction is completed, that is,
     * whether it has already been committed or rolled back.
     * @see PlatformTransactionManager#commit
     * @see PlatformTransactionManager#rollback
     */
    boolean isCompleted();

}
```



4、事务的传播行为

一般来说我们都是一个业务层进行业务操作，如果涉及到一个业务层需要调用另一个业务层。这时就需要涉及到事务的传播行为用来规定在两个多个业务层中如何进行事务的管理。

（1）PROPAGATION_REQUIRED：支持当前事务，如果不存在，则新建一个（默认）。

（2）PROPAGATION_SUPPORTS：支持当前事务，如果不存在，则不使用事务。

（3）PROPAGATION_MANDATORY：支持当前事务，如果不存在，则抛出异常。

（4）PROPAGATION_REQUIRES_NEW：如果当前事务不存在，则新建一个事务。如果当前事务存在，挂起这个事务再新建一个事务。

（5）PROPAGATION_NOT_SUPPORTED：不支持当前事务，以不使用事务的方式运行，如果当前存在事务，则挂起事务。

（6）PROPAGATION_NEVER：以非事务的方式运行，如果存在事务，抛出异常。

（7）PROPAGATION_NESTED：如果当前事务存在，则嵌套事务来执行。

## 六、spring的事务三种实现方式

三种方式分别为编码式，xml配置方式，注解配置方式。编码式了解即可，实际开发都是后两种。

1、编码式

（1）在spring容器中配置核心事务管理器，依赖于连接池，连接池的配置方式可参考之前的文章。

```
<bean name="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager" >
    <property name="dataSource" ref="dataSource" ></property>
</bean>
```

（2）在spring容器中配置事务模板对象

```
<bean name="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate" >
    <property name="transactionManager" ref="transactionManager" ></property>
</bean>
```

（3）在业务层中配置事务模板对象



```
<bean name="accountDao" class="SpringTest.SpringDemo.dao.AccountDaoImpl" >
    <property name="dataSource" ref="dataSource" ></property>
</bean>
<bean name="accountService" class="SpringTest.SpringDemo.service.AccountServiceImpl" >
    <property name="ad" ref="accountDao" ></property>
    <property name="tt" ref="transactionTemplate" ></property>
</bean>  
```



（4）编写事务实现代码，在需要事务的方法中，用模板对象调用excute方法，实现其中的接口。重写接口中的方法，讲需要进行事务管理的代码放入其中。



```
public class AccountServiceImpl implements AccountService {

    private AccountDao ad ;
    private TransactionTemplate tt;

    @Override
    public void transfer(final Integer from,final Integer to,final Double money) {
        tt.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus arg0) {
                //减钱
                ad.decreaseMoney(from, money);
                //加钱
                ad.increaseMoney(to, money);
            }
        });
    }
}
```



2、xml方式

（1）导包

aopaliance，springaop，aspectJ，springaspectss

（2）配置事务管理器

```
<bean name="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager" >
    <property name="dataSource" ref="dataSource" ></property>
</bean>
```

（3）配置事务的通知，根据方法名进行匹配。



```
<tx:advice id="txAdvice" transaction-manager="transactionManager" >
    <tx:attributes>
        <!-- 以方法为单位,指定方法应用什么事务属性
            isolation:隔离级别
            propagation:传播行为
            read-only:是否只读
　　　　　　　timeout:过期时间
　　　　　　　 rollback-for
　　　　　　　　no-rollback-for

         -->
        <tx:method name="save*" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="false" />
        <tx:method name="persist*" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="false" />
        <tx:method name="update*" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="false" />
        <tx:method name="modify*" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="false" />
        <tx:method name="delete*" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="false" />
        <tx:method name="remove*" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="false" />
        <tx:method name="get*" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="true" />
        <tx:method name="find*" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="true" />
        <tx:method name="transfer" isolation="REPEATABLE_READ" propagation="REQUIRED" read-only="false" />
    </tx:attributes>
</tx:advice>
```



（4）配置织入。首先配置切点。表示对哪些方法要进行增强。配置切面。pointcut-ref切点的名称，advice-ref通知的名称。

```
<aop:config  >
    <aop:pointcut expression="execution(* SpringTest.SpringDemo.service.*ServiceImpl.*(..))" id="txPc"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="txPc" />
</aop:config>
```

3、注解方式

（1）配置事务管理器

```
<bean name="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager" >
    <property name="dataSource" ref="dataSource" ></property>
</bean>
```

（2）开启事务管理的注解

```
<tx:annotation-driven/>
```

（3）在使用事务的类上添加注解，在需要事务的方法上，打上注解，在注解中配置事务隔离级别，事务传播等信息。默认对该类中所有的方法，执行类上的配置，如果在某个方法上进行配置，则执行方法上的配置。



```
@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=true)
public class AccountServiceImpl implements AccountService {

    private AccountDao ad ;
    private TransactionTemplate tt;
    
    @Override
    @Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
    public void transfer(final Integer from,final Integer to,final Double money) {
                //减钱
                ad.decreaseMoney(from, money);
                int i = 1/0;
                //加钱
                ad.increaseMoney(to, money);
    }
}
```