# Spring 从入门到精通系列 01——Spring概述与体系结构总结
本文是 Spring 从入门到精通系列 的第一篇，主要对 Spring 进行介绍以及 Spring 的体系结构进行总结。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
Spring概述
一、Spring 是什么
二、Spring 的优势
三、spring 的体系结构
3.1 Core Container 核心容器
3.2 Data Access/Integration 数据访问/集成
3.3 Web
3.4 其他

---


### Spring概述

### 一、Spring 是什么

  Spring是分层的 Java SE/EE应用 <font color="red">full-stack</font> 轻量级开源框架，以 <font color="red">IOC（Inverse Of Control：反转控制）和 AOP（Aspect Oriented Programming：面向切面编程）</font>为内核，提供了展现层 SpringMVC 和持久层 Spring JDBC 以及业务层事务管理等众多的企业级应用技术，还能整合开源世界众多著名的第三方框架和类库，逐渐成为使用最多的Java EE 企业应用开源框架。

---

### 二、Spring 的优势

 + **方便解耦，简化开发** 通过 Spring提供的 IoC容器，可以将对象间的依赖关系交由 Spring进行控制，避免硬编码所造成的过度程序耦合。
 + **AOP编程的支持** Spring提供面向切面编程，可以方便的实现对程序进行权限拦截、运行监控等功能。
 + **声明式事务的支持** 可以将我们从单调烦闷的事务管理代码中解脱出来，通过声明式方式灵活的进行事务的管理，提高开发效率和质量。
 + **方便程序的测试** Spring对Junit4支持，可以通过注解方便的测试Spring程序。
 + **方便继承各种优秀的框架** Spring不排斥各种优秀的开源框架，其内部提供了对各种优秀框架的直接支持（如：Struts、Hibernate、MyBatis等）。
 + **降低JAVAEE API的使用难度** Spring对JavaEE开发中非常难用的一些API（JDBC、JavaMail、远程调用等），都提供了封装，使这些API应用难度大大降低。

---


### 三、spring 的体系结构

Spring框架至今已集成了20多个模块，这些模块分布在以下模块中：
- 核心容器（Core Container）
- 数据访问/集成（Data Access/Integration）层
- Web层
- AOP（Aspect Oriented Programming）模块
- 植入（Instrumentation）模块
- 消息传输（Messaging）
- 测试（Test）模块Spring体系结构如下图： <img src="https://img-blog.csdnimg.cn/20210531195231401.png?#pic_left" alt="在这里插入图片描述"/> 
- 注：以下内容，了解即可。

---


#### 3.1 Core Container 核心容器

**Spring 其他部分想要运行，都需要 Spring 核心容器 Core Container 的支持。**

核心容器由核心，Beans，上下文和表达式语言模块组成，它们的细节如下：
-  核心模块 提供了框架的基本组成部分，包括 IoC 和依赖注入功能。
-  Beans 模块 提供 BeanFactory，它是一个工厂模式的复杂实现。
-  上下文模块 建立在由核心和 Bean 模块提供的坚实基础上，它是访问定义和配置的任何对象的媒介。ApplicationContext 接口是上下文模块的重点。
-  表达式语言模块在运行时提供了查询和操作一个对象图的强大的表达式语言。
---


#### 3.2 Data Access/Integration 数据访问/集成

数据访问/集成层包括 JDBC，ORM，OXM，JMS 和事务处理模块，它们的细节如下：
-  **JDBC** 模块提供了删除冗余的 JDBC 相关编码的 JDBC 抽象层。 
-  **ORM** 模块为流行的对象关系映射 API，包括 JPA，JDO，Hibernate 和 iBatis，提供了集成层。 
-  **OXM** 模块提供了抽象层，它支持对 JAXB，Castor，XMLBeans，JiBX 和 XStream 的对象/XML 映射实现。 
-  Java 消息服务 **JMS** 模块包含生产和消费的信息的功能。 
-  **事务**模块为实现特殊接口的类及所有的 POJO 支持编程式和声明式事务管理。 
---


#### 3.3 Web

Web 层由 Web、Servlet、Struts 和 Portlet 组件，它们的细节如下：
- **Web** 模块提供了基本的 Web 开发集成特性，例如多文件上传功能、使用的 Servlet 监听器的 IoC 容器初始化以及 Web 应用上下文。
- **Servlet** 模块包括 Spring 模型—视图—控制器（MVC）实现 Web 应用程序。
- **Struts** 模块包含支持类内的 Spring 应用程序，集成了经典的 Struts Web 层。
- **Portlet** 模块提供了在 Portlet 环境中使用 MV C实现，类似 Web-Servlet 模块的功能。
---


#### 3.4 其他

还有其他一些重要的模块，像 AOP，Aspects，Instrumentation，Web 和测试模块，它们的细节如下：
-  **AOP** 模块提供了面向方面的编程实现，允许你定义方法拦截器和切入点对代码进行干净地解耦，它实现了应该分离的功能。 -  **Aspects** 模块提供了与 AspectJ 的集成，这是一个功能强大且成熟的面向切面编程（AOP）框架。 
-  **Instrumentation** 模块在一定的应用服务器中提供了类 instrumentation 的支持和类加载器的实现。 
-  **Messaging** 模块为 STOMP 提供了支持作为在应用程序中 WebSocket 子协议的使用。它也支持一个注解编程模型，它是为了选路和处理来自 WebSocket 客户端的 STOMP 信息。 
-  **Test** 测试模块支持对具有 JUnit 或 TestNG 框架的 Spring 组件的测试。 
参考博客：https://wiki.jikexueyuan.com/project/spring/architecture.html

---

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117427010