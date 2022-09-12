# Spring 从入门到精通系列 10—— 使用 Spring 进行面向切面编程（AOP）
本文针对 AOP 的概念作用进行了分析，并且对基于 XML 与注解两种方式配置 AOP 进行了解读。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
一、AOP 概述
	1.1 什么是 AOP
	1.2 AOP 的作用及优势
	1.3 AOP 的实现方式
	1.4 Spring 中 AOP 的 相关术语
二、Spring 中基于 XML 的 AOP 配置步骤
★
2.1 工程准备
2.2 配置 AOP 步骤
2.3 切入点表达式的写法
2.3.1 切入点表达式的通常写法
2.3.2 通用化切入点表达式
2.4 四种常用类型通知与环绕通知
2.4.1 四种常用类型通知
2.4.2 环绕通知
三、Spring 中基于注解的 AOP 配置步骤
3.1 工程准备
3.1 四种常用类型通知与环绕通知（了解）
3.2 环绕通知类型

---


## 一、AOP 概述

### 1.1 什么是 AOP

<img src="https://img-blog.csdnimg.cn/20210602110310850.png" alt="在这里插入图片描述"/>   

简单的说它就是把我们程序重复的代码抽取出来，在需要执行的时候，使用动态代理的技术，在不修改源码的基础上，对我们的已有方法进行增强。

---


### 1.2 AOP 的作用及优势
1. **作用：** 在程序运行期间，不修改源码对已有方法进行增强。

1. **优势：** 

   + 减少重复代码

   + 提高开发效率

   + 维护方便 
---


### 1.3 AOP 的实现方式

动态代理

---


### 1.4 Spring 中 AOP 的 相关术语
1.  **连接点：** 所谓连接点就是指那些被拦截的点。//业务层中所有的方法 
	
1.   **切入点：** 所谓切入点就是指我们要对哪些连接点进行拦截。//业务层中被增强的方法例如： 
	
	```java
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if("test".equals(method.getName())){
			return method.invoke(accountService, args);
		}
		//...增强操作...
	}
	
	//业务层
	void test();//是连接点，不是切入点。因为方法被拦截，没有被增强。
	
	```
	
	

//业务层
void test();//是连接点，不是切入点。因为方法被拦截，没有被增强。

3、**前置通知** 

4、**后置通知** 

5、**异常通知** 

6、**最终通知** 

7、**环绕通知**

 <img src="https://img-blog.csdnimg.cn/20210602113102987.png?#pic_left" alt="在这里插入图片描述" width="600"/> 



8、**Introduction（引介）：** 引介是一种特殊的通知在不修改类代码的前提下，Introduction可以在运行期为类动态地添加一些方法或Fields。（了解） 

9、**Target（目标对象）：** 代理的目标对象。 

10、**weaving（织入）：** 是指把增强应用到目标对象来创建新的代理对象的 **过程** 。spring采用动态代理织入，而AspectJ采用编译期织入和类装载期织入。 

11. **Proxy（代理）：** 一个类被 AOP 织入增强后，就产生一个结果代理类。 

12.  **Aspect（切面）：** 是切入点和通知（引介）的结合。 

---




## 二、Spring 中基于 XML 的 AOP 配置步骤 <font color="red">★</font>

为了演示 AOP 配置步骤，建立一个工程。工程目录如下：

 <img src="https://img-blog.csdnimg.cn/20210602130404560.png?#pic_left" alt="在这里插入图片描述" width="300"/>

---


### 2.1 工程准备

**导入依赖：**

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-context</artifactId>
		<version>5.0.2.RELEASE</version>
	</dependency>
	<dependency>
		<groupId>org.aspectj</groupId>
		<artifactId>aspectjweaver</artifactId>
		<version>1.8.7</version>
	</dependency>
</dependencies>

```

**账户业务层及其实现类代码：**

```java
public interface IAccountService {

    void saveAccount();

    void updateAccount(int i);

    int deleteAccount();
}

```

```java
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
        System.out.println("保存了账户");
    }

    public void updateAccount(int i) {
        System.out.println("更新了账户");
    }

    public int deleteAccount() {
        System.out.println("删除了账户");
        return 0;
    }
}

```

**日志的工具类：**

```java
/**
 * 用于记录日志的工具类，它里面提供了公共的代码
 */
public class Logger {

    /**
     * 用于打印日志：计划让其在切入点方法执行之前执行（切入点方法就是业务层方法）
     */
    public void printLog(){
        System.out.println("Logger类中的printLog方法开始执行了...");
    }
}

```

**bean.xml:**

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--配置spring的ioc， service对象配置进来-->
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"></bean>
    
</beans>

```

---


### 2.2 配置 AOP 步骤
1、把通知 Bean 交给 spring 来管理

```xml
<!--配置Logger类-->
<bean id="logger" class="com.itheima.utils.Logger"></bean>

```

2、使用 aop:config 标签标明开始AOP的配置

```xml
<!--配置aop-->
<aop:config>   
</aop:config>

```

3、使用 aop:aspect 标签标明配置切面
id属性： 是给切面提供一个唯一标识，一般使用 logAdvice 作为 id 值
ref属性： 是指定通知类bean的id

```xml
<!--配置aop-->
<aop:config>
    <!--配置切面-->
    <aop:aspect id="logAdvice" ref="logger">
    </aop:aspect>
</aop:config>
```

4、在 aop:aspect 标签的内部使用对应标签来配置通知的类型

我们现在示例是让 printLog 方法在切入点执行之前执行：所以是前置通知
aop:before: 表示配置前置通知 （后文会介绍其他四种通知类型）

method属性：用于指定logger类中那个方法是前置通知
pointcut属性：用于指定切入点表达式，该表达式的含义指的是对业务层中那些方法增强（建立前置通知和业务层之间的关系）

```xml
<!--配置aop-->
<aop:config>
    <!--配置切面-->
    <aop:aspect id="logAdvice" ref="logger">
        <!--配置通知的类型，并且建立通知方法和切入点方法的关联-->
        <aop:before method="printLog" pointcut="execution( * com.itheima.service.impl.*.*(..))"></aop:before>
    </aop:aspect>
</aop:config>
```

### 2.3 切入点表达式的写法

**关键字**： execution(表达式)

**表达式**： 访问修饰符 返回值 包名.包名.包名…类名.方法名(参数列表)

1、标准的表达式写法

```java
public void com.ithiema.service.impl.AccountServiceImpl.saveAccount()

```

2、访问修饰符可以省略

```java
void com.ithiema.service.impl.AccountServiceImpl.saveAccount()

```

3、返回值可以使用通配符，表示任意返回值

```xml
* com.ithiema.service.impl.AccountServiceImpl.saveAccount()
```


4、包名可以使用通配符，表示任意包。但是有几级包，就需要写几个 *.

```
* *.*.*.*.AccountServiceImpl.saveAccount())
```

5、包名可以使用…表示当前包及其子包

```
* *..AccountServiceImpl.saveAccount()
```

6、类名和方法名都可以使用 * 来实现通配

```
* *..*.*()
```

参数列表：

1、可以直接写数据类型：

+ 基本类型直接写名称 int
+ 引用类型写包名.类名的方式 java.lang.String

2、可以使用 通配符 表示任意类型，但是必须有参数
       可以使用…表示有无参数均可，有参数可以是任意类型

3、全通配写法：

```
* *..*.*(..)
```

#### **2.3.1 切入点表达式的通常写法**

<font color=red>实际开发中切入点表达式的通常写法：切到业务层实现类下的所有方法:</font>>

```
com.itheima.service.impl.*.*(..)
```

配置完成后的 bean.xml 文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--配置spring的ioc， service对象配置进来-->
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"></bean>

    <!--配置Logger类-->
    <bean id="logger" class="com.itheima.utils.Logger"></bean>

    <!--配置aop-->
    <aop:config>
        <!--配置切面-->
        <aop:aspect id="logAdvice" ref="logger">
            <!--配置通知的类型，并且建立通知方法和切入点方法的关联-->
            <aop:before method="printLog" pointcut="execution( * com.itheima.service.impl.*.*(..))"></aop:before>
        </aop:aspect>
    </aop:config>
</beans>

```

**测试方法：**

```java
public class AOPTest {
    public static void main(String[] args) {
        //1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        //2. 获取对象
        IAccountService as = (IAccountService) ac.getBean("accountService");

        //3. 执行方法
        as.saveAccount();
        as.deleteAccount();
        as.updateAccount(1);
    }
}

```

**执行结果：**

 <img src="https://img-blog.csdnimg.cn/20210602132252384.png?#pic_left" alt="在这里插入图片描述" width="600"/>

---


#### 2.3.2 通用化切入点表达式

当我们对切入点表达式重复的引用时，配置会显得很冗长，因此可利用配置切入点表达式解决该问题。

```xml
<aop:pointcut id="pt1" expression="execution( * com.itheima.service.impl.*.*(..))"></aop:pointcut>

```
- 配置切入点表达式 id 的属性用于指定表达式的唯一标识。expression 属性用于指定表达式内容
- 此标签既可以写在 aop:aspect 内部，也可以写在外部。- 标签写在内部只能当前切面使用，写在外面代表所有切面可用，<font color="red">但是一定要放在aop:config标签的最顶部</font>
**标签写在 aop:aspect 内部：**

```java
<!--配置aop-->
<aop:config>
    <!--配置切面-->
    <aop:aspect id="logAdvice" ref="logger">
        <!--配置前置通知：在切入点方法执行之前执行-->
        <aop:before method="beforePrintLog" pointcut-ref="pt1"></aop:before>
        <aop:pointcut id="pt1" expression="execution( * com.itheima.service.impl.*.*(..))"></aop:pointcut>
    </aop:aspect>
</aop:config>

```

**标签写在 aop:aspect 外部：**

```java
<!--配置aop-->
<aop:config>
	<!--配置切入点表达式-->
	<aop:pointcut id="pt1" expression="execution( * com.itheima.service.impl.*.*(..))"></aop:pointcut>
    <!--配置切面-->
    <aop:aspect id="logAdvice" ref="logger">
        <!--配置前置通知：在切入点方法执行之前执行-->
        <aop:before method="beforePrintLog" pointcut-ref="pt1"></aop:before>
    </aop:aspect>
</aop:config>

```

---


### 2.4 四种常用类型通知与环绕通知

#### 2.4.1 四种常用类型通知

**日志的工具类：**

```java
/**
 * 用于记录日志的工具类，它里面提供了公共的代码
 */
public class Logger {

    /**
     * 前置通知
     */
    public void beforePrintLog(){
        System.out.println("前置通知Logger类中的printLog方法开始执行了...");
    }

    /**
     * 后置通知
     */
    public void afterReturningPrintLog(){
        System.out.println("后置通知Logger类中的printLog方法开始执行了...");
    }

    /**
     * 异常通知
     */
    public void afterThrowingPrintLog(){
        System.out.println("异常通知Logger类中的printLog方法开始执行了...");
    }

    /**
     * 最终通知
     */
    public void afterPrintLog(){
        System.out.println("最终通知Logger类中的printLog方法开始执行了...");
    }
}

```

**bean.xml：**

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--配置spring的ioc， service对象配置进来-->
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"></bean>


    <!--配置Logger类-->
    <bean id="logger" class="com.itheima.utils.Logger"></bean>

    <!--配置aop-->
    <aop:config>
        <!--配置切面-->
        <aop:aspect id="logAdvice" ref="logger">
            <!--配置前置通知：在切入点方法执行之前执行-->
            <aop:before method="beforePrintLog" pointcut-ref="pt1"></aop:before>
            <!--配置后置通知：在切入点之后正常方法之后执行，他和异常通知永远只能执行一个-->
            <aop:after-returning method="afterReturningPrintLog"  pointcut-ref="pt1"></aop:after-returning>
            <!--配置异常通知：在切入点之后异常方法之后执行，他和异常通知永远只能执行一个-->
            <aop:after-throwing method="afterThrowingPrintLog"  pointcut-ref="pt1"></aop:after-throwing>
            <!--配置最终通知：无论切入点方法是否正常执行，他都会在其后面执行-->
            <aop:after method="afterPrintLog" pointcut-ref="pt1"></aop:after>
            
            <!--配置切入点表达式-->
            <aop:pointcut id="pt1" expression="execution( * com.itheima.service.impl.*.*(..))"></aop:pointcut>
        </aop:aspect>
    </aop:config>
</beans>

```

**测试结果：** <img src="https://img-blog.csdnimg.cn/20210602145520264.png?#pic_left" alt="在这里插入图片描述" width="500"/>

---


#### 2.4.2 环绕通知

Spring 为我们提供了一个接口，ProceedingJoinPoint。该接口有一个方法 proceed()，此方法就相当于明确调用切入点方法。 该接口可以作为环绕通知的方法参数，在程序执行时，spring框架会为我们提供该接口的实现类供我们使用。

<font color="red">spring中的环绕通知： 它是spring框架为我们提供的一种可以在代码中手动增强方法和执行的方式。</font>

在 Logger 中编写以下代码：

```java
/**
 * 环绕通知
 */
public Object aroundPrintLog(ProceedingJoinPoint pjp){
    Object rtValue = null;
    try {
        Object[] args = pjp.getArgs();
        System.out.println("Logger类中aroundPrintLog方法开始记录日志了。。前置");
        rtValue = pjp.proceed(args);
        System.out.println("Logger类中aroundPrintLog方法开始记录日志了。。后置");
        return rtValue;
    } catch (Throwable throwable) {
        System.out.println("Logger类中aroundPrintLog方法开始记录日志了。。异常");
        throw new RuntimeException(throwable);
    } finally {
        System.out.println("Logger类中aroundPrintLog方法开始记录日志了。。最终");
    }
}

```

注意：catch 的异常只能是 Throwable，其他异常获取不到。

**bean.xml：**

```java
<!--配置aop-->
<aop:config>
    <!--配置切面-->
    <aop:aspect id="logAdvice" ref="logger">
        <!-- 配置环绕通知 -->
        <aop:around method="aroundPrintLog" pointcut-ref="pt1"></aop:around>
        <!--配置切入点表达式-->
        <aop:pointcut id="pt1" expression="execution( * com.itheima.service.impl.*.*(..))"></aop:pointcut>
    </aop:aspect>
</aop:config>

```

测试方法结果：

 <img src="https://img-blog.csdnimg.cn/2021060215090764.png?#pic_left" alt="在这里插入图片描述" width="500"/>

---


## 三、Spring 中基于注解的 AOP 配置步骤

为了演示基于注解的AOP配置步骤，我们创建一个新的工程，工程目录如下：

 <img src="https://img-blog.csdnimg.cn/20210602152006963.png?#pic_left" alt="在这里插入图片描述" width="250"/>

---


### 3.1 工程准备

**bean.xml：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop 
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--配置spring创建容器是要扫描的包-->
    <context:component-scan base-package="com.itheima"></context:component-scan>

    <!--开启spring开启Aop的支持-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

</beans>

```

**账户业务层及其实现类：**

```xml
public interface IAccountService {
    void saveAccount();
}

```

```xml
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    public void saveAccount() {
        System.out.println("保存了账户");
    }
}

```

---


### 3.1 四种常用类型通知与环绕通知（了解）

```java
@Component("logger")
@Aspect//表示当前类是一个切面类
public class Logger {

    @Pointcut("execution( * com.itheima.service.impl.*.*(..))")
    private void pt1(){}
    
    /**
     * 前置通知
     */
    @Before("pt1()")
    public void beforePrintLog(){
        System.out.println("前置通知Logger类中的printLog方法开始执行了...");
    }

    /**
     * 后置通知
     */
    @AfterReturning("pt1()")
    public void afterReturningPrintLog(){
        System.out.println("后置通知Logger类中的printLog方法开始执行了...");
    }

    /**
     * 异常通知
     */
    @AfterThrowing("pt1()")
    public void afterThrowingPrintLog(){
        System.out.println("异常通知Logger类中的printLog方法开始执行了...");
    }

    /**
     * 最终通知
     */
    @After("pt1()")
    public void afterPrintLog(){
        System.out.println("最终通知Logger类中的printLog方法开始执行了...");
    }
    
}

```

**测试方法：**

```java
public static void main(String[] args) {
	//1.获取容器
	ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");	
	//2. 获取对象
	IAccountService as = (IAccountService) ac.getBean("accountService");	
	//3. 执行方法
	as.saveAccount();
}

```

<img src="https://img-blog.csdnimg.cn/20210602152831200.png?#pic_left" alt="在这里插入图片描述" width="650"/> 

从结果中看出，后置通知与最终通知顺序有所不同，这个是 Spring 内部的问题。解决该问题的方法是使用环绕通知，我们手动增强方法和执行的方式。

---


### 3.2 环绕通知类型

```java
@Component("logger")
@Aspect//表示当前类是一个切面类
public class Logger {

    @Pointcut("execution( * com.itheima.service.impl.*.*(..))")
    private void pt1(){}


    /**
     * 环绕通知
     */
    @Around(("pt1()"))
    public Object aroundPrintLog(ProceedingJoinPoint pjp){
        Object rtValue = null;
        try {
            Object[] args = pjp.getArgs();
            System.out.println("Logger类中aroundPrintLog方法开始记录日志了。。前置");
            rtValue = pjp.proceed(args);
            System.out.println("Logger类中aroundPrintLog方法开始记录日志了。。后置");
            return rtValue;
        } catch (Throwable throwable) {
            System.out.println("Logger类中aroundPrintLog方法开始记录日志了。。异常");
            throw new RuntimeException(throwable);
        } finally {
            System.out.println("Logger类中aroundPrintLog方法开始记录日志了。。最终");
        }
    }

}

```

方法执行结果如下：

 <img src="https://img-blog.csdnimg.cn/20210602153436538.png?#pic_left" alt="在这里插入图片描述" width="600"/>



---


本文针对 AOP 的概念作用进行了分析，并且对基于 XML 与注解两种方式配置 AOP 进行了解读，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **     https://blog.csdn.net/weixin_43819566/article/details/117463062