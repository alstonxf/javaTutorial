# Java Spring开发入门

# 目录

- [Spring概述](https://www.jb51.net/article/222633.htm#_label0)

- [Spring简单应用](https://www.jb51.net/article/222633.htm#_label1)

- - [Spring框架地基本使用](https://www.jb51.net/article/222633.htm#_lab2_1_0)

  - - [项目创建](https://www.jb51.net/article/222633.htm#_label3_1_0_0)
    - [添加依赖包](https://www.jb51.net/article/222633.htm#_label3_1_0_1)
    - [创建Spring配置文件](https://www.jb51.net/article/222633.htm#_label3_1_0_2)
    - [修改配置文件](https://www.jb51.net/article/222633.htm#_label3_1_0_3)
    - [修改测试类](https://www.jb51.net/article/222633.htm#_label3_1_0_4)
    - [运行结果](https://www.jb51.net/article/222633.htm#_label3_1_0_5)

- [总结](https://www.jb51.net/article/222633.htm#_label2)




## Spring概述

Spring就是为解决企业应用开发的复杂性而创建的，做为开源中间件，它使用基本的JavaBean来完成以前只可能有EJB（Java企业bean）完成的事情。Spring独立于各种应用服务器，甚至无须应用服务器的支持也能提供应用服务器的功能，同时为JavaEE应用程序开发提供继承的框架，是企业应用开发的“一站式”选择。Spring的用途不仅限于服务器的开发，任何Java应用都可以从Spring中受益。

> Spring是一个开源框架，它的功能都是从实际开发中抽取出来的。其主要优势之一是采用分层架构，整个框架由7个定义良好的模块（组件）构成，它们都统一构建于核心容器之上。

![img](https://img.jbzj.com/file_images/article/202109/202109120852051.png)

> Spring Core:核心容器提供Spring框架的基本功能，其主要组件BeanFactory是工厂模式的实现。它通过控制反转（IOC）机制，将应用程序配置和依赖性规范与实际的程序代码分离开。
> Spring Context:想Spring框架提供上下文信息，包括企业服务。
> Spring DAO：JDBC DAO抽象层提供了有用的一场层次结构，用来管理异常处理和不同数据库供应商抛出的错误信息。



## Spring简单应用

当创建一个简单的Java项目的时候，在控制台要求输出“Hello world!”,如果要求输出“Hello Beer!”等等，那么就要不断地去修改程序源代码。如果项目庞大，程序中涉及这一输出地语句不止一处，有很多处，且分散在整个项目地源码（往往有成千上万）中，如此大动干戈地改动程序，对于一个软件系统地维护来说将是灾难性地。

```
package` `org.example;``public` `class` `TestHello {``  ``private` `String message;``  ``public` `String getMessage() {``    ``return` `message;``  ``}``  ``public` `void` `setMessage(String message) {``    ``this``.message = message;``  ``}``}
package` `org.example;``public` `class` `Test ``{``  ``public` `static` `void` `main( String[] args )``  ``{``    ``TestHello testHello=``new` `TestHello();``    ``testHello.setMessage(``"Hello world!……"``);``    ``System.out.println(testHello.getMessage());``  ``}``}
```

下面使用Spring解决这一问题。使用Spring实现程序输出内容地灵活改变。



### Spring框架地基本使用



#### 项目创建

![img](https://img.jbzj.com/file_images/article/202109/202109120852062.png)

![img](https://img.jbzj.com/file_images/article/202109/202109120852063.jpg)

![img](https://img.jbzj.com/file_images/article/202109/202109120852064.png)

![img](https://img.jbzj.com/file_images/article/202109/202109120852065.png)



#### 添加依赖包

![img](https://img.jbzj.com/file_images/article/202109/202109120852076.jpg)

将下面这段依赖加入到红色方框里面<dependencies> </dependencies>之间。

![Java技术迷](https://img.jbzj.com/erweima/javajsmb.png)

```
<``dependency``>``   ``<``groupId``>org.springframework</``groupId``>``   ``<``artifactId``>spring-core</``artifactId``>``   ``<``version``>5.3.8</``version``>``  ``</``dependency``>``  ``<``dependency``>``   ``<``groupId``>org.springframework</``groupId``>``   ``<``artifactId``>spring-beans</``artifactId``>``   ``<``version``>5.3.8</``version``>``  ``</``dependency``>``  ``<``dependency``>``   ``<``groupId``>org.springframework</``groupId``>``   ``<``artifactId``>spring-context</``artifactId``>``   ``<``version``>5.3.8</``version``>``  ``</``dependency``>``  ``<``dependency``>``   ``<``groupId``>org.springframework</``groupId``>``   ``<``artifactId``>spring-context-support</``artifactId``>``   ``<``version``>5.2.12.RELEASE</``version``>``  ``</``dependency``>``  ``<``dependency``>``   ``<``groupId``>org.springframework</``groupId``>``   ``<``artifactId``>spring-expression</``artifactId``>``   ``<``version``>5.3.8</``version``>``  ``</``dependency``>``  ``<``dependency``>``   ``<``groupId``>commons-logging</``groupId``>``   ``<``artifactId``>commons-logging</``artifactId``>``   ``<``version``>1.2</``version``>``  ``</``dependency``>
```

加入完成之后，点击该按钮，稍等一会ideal自动帮我们下载依赖包。

![img](https://img.jbzj.com/file_images/article/202109/202109120852077.jpg)



#### 创建Spring配置文件

![img](https://img.jbzj.com/file_images/article/202109/202109120852078.jpg)



#### 修改配置文件

打开ApplicationContest.xml，对其进行修改：

> **id**:bean的唯一标识符，也就是相当于我们学的对象名。
>
> **class**:bean对象所对应的全限定名：包名+类型。
>
> **property**:相当于给对象中的属性设置一个值。
>
> **value**:具体的值，基本数据类型。

```
<?``xml` `version``=``"1.0"` `encoding``=``"UTF-8"``?>``<``beans` `xmlns``=``"http://www.springframework.org/schema/beans"``    ``xmlns:xsi``=``"http://www.w3.org/2001/XMLSchema-instance"``    ``xsi:schemaLocation``=``"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"``>``  ``<``bean` `id``=``"TestHello"` `class``=``"org.example.TestHello"``>``    ``<``property` `name``=``"message"``>``      ``<``value``>Hello World!</``value``>``    ``</``property``>``  ``</``bean``>``</``beans``>
```



#### 修改测试类

```
package` `org.example;``import` `javafx.application.Application;``import` `org.springframework.context.ApplicationContext;``import` `org.springframework.context.support.ClassPathXmlApplicationContext;``public` `class` `App {``  ``public` `static` `void` `main( String[] args ) {``    ``//获取ApplicationContext对象``    ``ApplicationContext application=``new` `ClassPathXmlApplicationContext(``"ApplicationContext.xml"``);``    ``//通过ApplicationContext获得TestHello对象``    ``//getBean（）方法中的参数即为配置文件中Bean的id的值``    ``TestHello testHello=(TestHello) application.getBean(``"TestHello"``);``    ``System.out.println(testHello.getMessage());``  ``}``}
```



#### 运行结果

若要程序输出“hello Beer！”，则只需要修改配置文件中的value值即可

```
<value>Hello Beer！</value>
```

![img](https://img.jbzj.com/file_images/article/202109/202109120852089.png)

这个例子看不出来Spring的优势，担当项目规模很大，且源代码中有很多处这样的输出语句时，Spring的有事就充分体现出来了。因为用了Spring只需修改配置文件一个地方的value值即可让所有的输出都跟着一致变化，而不用逐条烦琐地修改程序代码啦！