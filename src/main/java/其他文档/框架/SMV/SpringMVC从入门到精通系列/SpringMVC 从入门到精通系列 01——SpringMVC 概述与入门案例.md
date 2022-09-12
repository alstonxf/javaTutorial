# SpringMVC 从入门到精通系列 01——SpringMVC 概述与入门案例
  本文是SpringMVC 从入门到精通系列的第一篇，主要针对 SpringMVC 概述进行阐述，并实现入门案例，分析其大致流程。

---


 # 文章目录
一、三层架构和MVC
1.1 三层架构
1.2 MVC模型
二、SpringMVC的入门案例
2.1 SpringMVC 的概述
2.2 SpringMVC 的入门程序
2.3 入门案例的执行过程分析
2.4 mvc:annotation-driven 说明
2.5 RequestMapping 注解
补充：创建 maven 工程过慢的解决方案

---

## 一、三层架构和MVC

### 1.1 三层架构

1. 开发服务器端程序，一般都基于两种形式，一种 C/S 架构程序，一种 B/S 架构程序

2. 使用Java语言基本上都是开发 B/S 架构的程序，B/S 架构又分成了三层架构

   三层架构 

   1. **表现层：** WEB层，用来和客户端进行数据交互的。==表现层一般会采用 MVC 的设计模型==

   1. **业务层：** 处理公司具体的业务逻辑的

   1. **持久层：** 用来操作数据库的

### 1.2 MVC模型

1. MVC 全名是 Model View Controller 模型视图控制器，每个部分各司其职。
2. Model： 数据模型，JavaBean的类，用来进行数据封装。
3. View： 指JSP、HTML用来展示数据给用户
4. Controller： 用来接收用户的请求，整个流程的控制器。用来进行数据校验等。

## 二、SpringMVC的入门案例

### 2.1 SpringMVC 的概述
 **SpringMVC的概述** 

1. 是一种基于 Java 实现的 MVC 设计模型的请求驱动类型的轻量级 WEB 框架。
2. SpringMVC属于SpringFrameWork 的后续产品，已经融合在Spring Web Flow里面。Spring 框架提供了构建 Web 应用程序的全功能 MVC 模块。
3. 使用 Spring 可插入的 MVC 架构，从而在使用 Spring 进行 WEB 开发时，可以选择使用 Spring 的 SpringMVC 框架或集成其他 MVC 开发框架，如 Struts1(现在一般不用)，Struts2 等。

**SpringMVC在三层架构中的位置** 

 <img src="https://img-blog.csdnimg.cn/20210603002251669.png#pic_left" alt="在这里插入图片描述" width="900"/>

**SpringMVC的优势**

1. 清晰的角色划分

   前端控制器（DispatcherServlet）
   请求到处理器映射（HandlerMapping）
   处理器适配器（HandlerAdapter）
   视图解析器（ViewResolver）
   处理器或页面控制器（Controller）
   验证器（ Validator）
   命令对象（Command 请求参数绑定到的对象就叫命令对象）
   表单对象（Form Object 提供给表单展示和提交到的对象就叫表单对象）。
   分工明确，而且扩展点相当灵活，可以很容易扩展，虽然几乎不需要。

2. 由于命令对象就是一个 POJO，无需继承框架特定 API，可以使用命令对象直接作为业务对象。

3. 和 Spring 其他框架无缝集成，是其它 Web 框架所不具备的。

4. 可适配，通过 HandlerAdapter 可以支持任意的类作为处理器。

5. 可定制性，HandlerMapping、ViewResolver 等能够非常简单的定制。

6. 功能强大的数据验证、格式化、绑定机制。

7. 利用 Spring 提供的 Mock 对象能够非常简单的进行 Web 层单元测试。

8. 本地化、主题的解析的支持，使我们更容易进行国际化和主题的切换。

9. 强大的 JSP 标签库，使 JSP 编写更容易。
   ………………


### 2.2 SpringMVC 的入门程序


 <img src="https://img-blog.csdnimg.cn/20210603003250233.png#pic_left" alt="在这里插入图片描述" width="450"/>
 **1. 创建 WEB 工程，引入开发的 jar 包，具体的坐标如下：**

```xml
<!-- 版本锁定 -->
<properties>
	<spring.version>5.0.2.RELEASE</spring.version>
</properties>

<dependencies>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-context</artifactId>
		<version>${spring.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-web</artifactId>
		<version>${spring.version}</version>
		</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-webmvc</artifactId>
		<version>${spring.version}</version>
		</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>servlet-api</artifactId>
		<version>2.5</version>
		<scope>provided</scope>
	</dependency>
	<dependency>
		<groupId>javax.servlet.jsp</groupId>
		<artifactId>jsp-api</artifactId>
		<version>2.0</version>
		<scope>provided</scope>
	</dependency>
</dependencies>

```

---


**2. 配置核心的控制器（配置DispatcherServlet）**

前端控制器实际上就是 servlet，因此在 web.xml 中配置 <servlet> <servlet-mapping>等，web.xml 代码如下：

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>

  <!--前端控制器-->
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--全局初始化参数-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>

  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>

```

---


**3. 编写springmvc.xml的配置文件**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--开启包的扫描-->
    <context:component-scan base-package="com.itheima"></context:component-scan>

    <!--视图的解析器对象-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"></property><!--文件所在目录-->
        <property name="suffix" value=".jsp"></property><!--文件后缀-->
    </bean>

    <!--开启springMvc框架注解的支持-->
    <mvc:annotation-driven conversion-service="conversionService"/>

</beans>

```

---


**4. 编写 index.jsp 和 HelloController 控制器类**

**index.jsp:**

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>入门程序</h3>
    <a href="hello">入门程序</a><br>
</body>
</html>

```

**HelloController.java:**

```java
//控制器类
@Controller
public class HelloController {

    @RequestMapping(path="/hello")
    public String sayHello(){
        System.out.println("hello");
        return "success";
    }
}

```

---


**5. 在 WEB-INF 目录下创建 pages 文件夹，编写 success.jsp 的成功页面**

**success.jsp：**

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h3>入门成功</h3>

</body>
</html>

```

**6. 启动 Tomcat 服务器，进行测试**

---


### 2.3 入门案例的执行过程分析

#### **1. 入门案例的执行流程**

1. 当启动 Tomcat 服务器的时候，因为配置了 load-on-startup 标签，所以会创建 DispatcherServlet 对象，

   就会加载 springmvc.xml 配置文件

2. springmvc.xml 配置中开启了注解扫描，那么 HelloController 对象就会被创建

3. 从 index.jsp 发送请求，请求会先到达 DispatcherServlet 核心控制器，根据配置 @RequestMapping 注解

   找到执行的具体方法

4. 根据执行方法的返回值，再根据配置的视图解析器，去指定的目录下查找指定名称的JSP文件（success.jsp）

5. Tomcat服务器渲染页面，做出响应

   

<img src="https://img-blog.csdnimg.cn/2021060301174525.png#pic_left" alt="在这里插入图片描述"/>

#### **2. 入门案例的详细执行流程** 

 <img src="https://img-blog.csdnimg.cn/202106030125078.png?#pic_left" alt="在这里插入图片描述" width="800"/>

1.  **DispatcherServlet ：前端控制器**   用户请求到达前端控制器，它就相当于 mvc 模式中的 c，dispatcherServlet 是整个流程控制的中心，由它调用其它组件处理用户的请求，dispatcherServlet 的存在降低了组件之间的耦合性。 
1.  **HandlerMapping ：处理器映射器**   HandlerMapping 负责根据用户请求找到 Handler 即处理器，SpringMVC 提供了不同的映射器实现不同的映射方式，例如：配置文件方式，实现接口方式，注解方式等。 
1.  **Handler ：处理器**   它就是我们开发中要编写的具体业务控制器。由 DispatcherServlet 把用户请求转发到 Handler。由 Handler 对具体的用户请求进行处理。 
1.  **HandlAdapter ：处理器适配器**   通过 HandlerAdapter 对处理器进行执行，这是适配器模式的应用，通过扩展适配器可以对更多类型的处理器进行执行。 
1.  **View Resolver ：视图解析器**   View Resolver 负责将处理结果生成 View 视图，View Resolver 首先根据逻辑视图名解析成物理视图名即具体的页面地址，再生成 View 视图对象，最后对 View 进行渲染将处理结果通过页面展示给用户。
1.  **View ：视图**   SpringMVC 框架提供了很多的 View 视图类型的支持，包括：jstlView、freemarkerView、pdfView等。我们最常用的视图就是 jsp。一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户，需要由程序员根据业务需求开发具体的页面。 
---


### 2.4 mvc:annotation-driven 说明

  在 SpringMVC 的各个组件中，处理器映射器、处理器适配器、视图解析器称为 SpringMVC 的三大组件。使用 <mvc:annotation-driven> 自动加载 RequestMappingHandlerMapping（处理映射器） 和 RequestMappingHandlerAdapter（ 处理适配器 ），可用在 SpringMVC.xml 配置文件中使用 <mvc:annotation-driven> 替代注解处理器和适配器的配置。

---


### 2.5 RequestMapping 注解
1、**RequestMapping** 注解的作用是建立请求 URL 和处理方法之间的对应关系

2、**RequestMapping** 注解可以作用在方法和类上

1. 作用在类上：第一级的访问目录

2. 在方法上：第二级的访问目录
3. 细节：路径可以不编写 / 表示应用的根目录开始
4. 细节：${ pageContext.request.contextPath }也可以省略不写，但是路径上不能写 /

3、**RequestMapping**的属性

1. path： 指定请求路径的url

2. value： value 属性和 path 属性是一样的

   当属性只有 value，且 value 只有一个时，value=可以省略，即 @RequestMapping(value="/hello")=@RequestMapping(“hello”)

3. method： 指定该方法的请求方式

   @RequestMapping(value="/hello", method={RequestMethod.POST})

4. params： 指定限制请求参数的条件，要求请求参数的 key 和 value 必须和配置的一模一样。

5. headers： 发送的请求中必须包含的请求头

### 补充：创建 maven 工程过慢的解决方案

在以下界面中输入键值对：archetypeCatalog internal 
 <img src="https://img-blog.csdnimg.cn/20210603014117293.png?#pic_left" alt="在这里插入图片描述" width="600"/>

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117489594