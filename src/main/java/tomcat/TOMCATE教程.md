# Tomcat的基本使用及相关知识的概述（超详细版）

编程开发分享者

于 2020-07-11 23:45:10 发布

6823
 收藏 163
分类专栏： Tomcat 文章标签： tomcat servlet http
版权

Tomcat
专栏收录该内容
1 篇文章1 订阅
订阅专栏
絮絮叨叨：
在深入了解Tomcat源码之前，本来是想亲自写一篇Tomcat的基本使用教程的，在网上兜兜转转发现了这篇博客：https://project.myBlog.blogStudy.csdn.net/weixin_40396459/article/details/81706543，写的特别详细，虽然有点老，但对于Tomcat入门来说是无所谓的，足够让初学者了解如何使用这只“猫”。在这里我就不重复造轮子了，将这篇博客贴在这里，方便大家阅读。在这篇文章之后，我们开始Tomcat的深入分析，以后每写一篇，我都会贴在这里，有哪里不对的还请大家不吝指教。下一篇文章是搭建tomcat的源码项目，将tomcat当做一个普通项目来运行，剖析里面的源码。

文章末尾有福利~

正文
1、JavaWeb概念
Java web，是用java技术来解决相关web互联网领域的技术的总称。web包括：web服务器和web客户端两部分。
java在最早web客户端的应用有java applet程序，不过这种技术在很久之前就已经被淘汰了。java在服务器端的应用非常丰富，
比如Servlet，jsp和第三方框架等等。java技术对web领域的发展注入了强大的动力

简单的说，就是使用java语言实现浏览器可以访问的程序内容。称之为Java Web。

javaweb开发是基于请求和响应的：

请求：浏览器（客户端）向服务器发送信息
响应：服务器向（客户端）浏览器回送信息
请求和响应是成对出现的。






2、web资源分类：
所谓web资源即放在Internet网上供外界访问的文件或程序，又根据它们呈现的效果及原理不同，将它们划分为静态资源和动态资源。

静态web资源：固定不变数据文件（静态网页 HTML、CSS文件、文本、音频、视频）
静态web技术：HTML+CSS+JavaScript

动态web资源：一段服务程序，运行后，生成的数据文件

动态web技术：servlet，jsp，php， .net ,ruby、python等等

3、常见的web服务器
web服务器简介：

Tomcat：由Apache组织提供的一种Web服务器，提供对jsp和Servlet的支持。它是一种轻量级的javaWeb容器（服务器），也是当前应用最广的JavaWeb服务器（免费）。
Jboss：是一个遵从JavaEE规范的、开放源代码的、纯Java的EJB服务器，它支持所有的JavaEE规范（免费）。
GlassFish： 由Oracle公司开发的一款JavaWeb服务器，是一款强健的商业服务器，达到产品级质量（应用很少，收费）。
Resin：是CAUCHO公司的产品，是一个非常流行的应用服务器，对servlet和JSP提供了良好的支持，性能也比较优良，resin自身采用JAVA语言开发（收费，应用比较多）。
WebLogic：是Oracle公司的产品，是目前应用最广泛的Web服务器，支持JavaEE规范，而且不断的完善以适应新的开发要求，适合大型项目（收费，用的不多，适合大公司）。
3.1、Tomcat服务器
开源小型web服务器 ，完全免费，主要用于中小型web项目，只支持Servlet和JSP 等少量javaee规范（就是JavaWeb编程接口）



3.2、tomcat服务器与servlet版本的关系
servlet：sun公司提供的用于开发动态web资源的技术。
jsp：（java server page），java提供的一门开发web网页的技术。

tomcat软件：java开发的。java软件运行的时候需要jdk。



向下兼容。tomcat7也支持servlet3.0/jsp2.2规范，可以支持javaee6.0当前企业常用的版本 6.* / 7.*/8.*


3.3、tomcat下载和安装说明
到http://tomcat.apache.org  下载

1） Tomcat首页



2）Tomcat下载



3）下载后的包



4）安装：解压



5）tomcat的安装目录介绍：

bin：可以执行文件。
conf：tomcat服务器的配置文件
lib：tomcat启动后需要依赖的jar包
logs：tomcat工作之后的日志文件
webapps：是tomcat布暑工程的目录。
work：jsp文件在被翻译之后，保存在当前这个目录下，session对象被序列化之后保存的位置

3.4、Tomcat服务器启动（**重点）

注意事项：

1、JAVA_HOME:环境变量。并且配置到jdk的目录，其完整过程如下：




打开控制台（cmd命令打开窗口）。输入java -version测试



2、启动tomcat目录。 tomcat目录/bin/startup.bat(window启动文件) 找到startup.bat 双击运行。会有一个黑窗口，黑窗口不要关闭。（如果关闭，相当于把tomcat停止了。）




3、在浏览器地址栏中输入：http://localhost:8080 或者 http://127.0.0.1:8080localhost ，如果看到如下页面，证明启动成功



3.5、配置tomcat的端口（****重点）
tomcat默认的端口是8080（访问端口）


http的默认端口是80，如果访问的时候输入http://www.baidu.com相当于http://www.baidu.com:80。当真正在项目上线之后，通常采用80，修改方法如下：
1）找到tomcat目录/conf/server.xml
2）修改port的值，将port端口的值修改为80


3）然后在浏览器中输入 http://127.0.0.1:80 或 http://127.0.0.1 访问测试



访问成功！！！

3.6、catalina run 启动Tomcat
Tomcat启动，还有一种启动的方法就是在命令行中，先把你的当前目录切换 到你tomcat目录\bin目录下，如下是我的位置



再执行catalina run 这个命令启动Tomcat。这个命令有什么好处。当Tomcat启动失败的时候，会有一闪而过的情况，

当我们使用catalina run 这个命令启动Tomcat的时候，哪怕有错误，我们也可以清楚的看到tomcat失败的原因。不会一闪而过。

3.7、tomcat关闭
有三种方法。

第一种：Ctrl+C键 关闭Tomcat服务器
第二种：点击Tomcat窗口的右上角关闭按钮 （暴力停止服务器）
第三种：找到tomcat目录/bin/shutdown.bat文件，双击执行关闭Tomcat。

 

4、常用的布署工程到Tomcat中的两种方式
把我们自己书写的html，servlet这些信息，部署到tomcat的方式。

4.1、第一种方法：在tomcat目录/conf/server.xml 配置---了解
在conf/server.xml文件的host元素中配置，例如：
在host标签内书写如下内容

<Context  path="/atguigu"  docBase="D:\atguigu"/>
<Context  path=”浏览器要访问的目录---虚拟目录”  docBase=”网站所在磁盘目录”/>


配置好之后，要重启服务器。

缺点（Tomcat7.0之后）：如果配置错误：tomcat会启动失败。（如果tomcat里面存放的其他的网站），其他网站也会停机。

4.2、第二种方式：将网站目录复制到tomcat/webapps目录（常用，必须掌握）
有一个网站（一个文件夹），把文件夹复制到tomcat的webapps目录下。

文件夹的名字，就是网站或者工程的访问目录.相当于之前配置 <Context path=”” 的配置

4.3、把网站目录压缩成war包部署到tomcat中
war包：就是一个压缩文件 zip格式的压缩文件。 只不过扩展名不是.zip 而是.war

把我们的项目进行压缩zip，改成war，把war文件拷贝到tomcat/webapps目录下

步骤1、把文件夹中的内容压缩成zip的格式，点击一个要部署的文件夹下面，全选 然后压缩
步骤2、修改文件的后缀名为.war
步骤3.把war文件复制到webapps目录下。tomcat会自己把war的文件进行解压
4.4、webapps目录下/ROOT工程的访问
当我们在浏览器中直接输入http://ip地址:端口号   那么 默认访问的是Tomcat目录/webapps/ROOT目录
如果webapps下面有一个ROOT的项目。那么在访问的时候，直接可以省略项目的名字/ 表示找到root目录

 

5、整合Tomcat和Eclipse开发工具中（***常用必须掌握）
5.1、打开Eclipse的Server视图窗口
第一种情况，直接打开Servers窗口


第二种情况，搜索Servers窗口打开

图一，打开总的eclipse视图

图二：输入Server过滤出服务器窗口选项

Servers服务器窗口已成功打开，如下图：


5.2、创建Tomcat 服务器
1）在Servers窗口中，点击 创建 server 的文字提示连接。如下图：


2）创建一个新的Tomcat服务器实例 


3）点击Browse按钮，打开目录选择窗口。选择Tomcat 解压目录



4）选择Tomcat目录，然后点击确定按钮


5）Tomcat目录选择好之后，点击 【Next】按钮继续操作



6）点击 【Finish】按钮结束操作

7）Tomcat 服务器创建成功！！！


5.3、启动Eclipse中的Tomcat服务器
1）Debug模式启动Tomcat服务器

提示当前为Debug模式启动！！！


Tomcat启动成功的控制台提示！！！



2）Run模式启动Tomcat服务器

Run模式启动显示


Tomcat启动成功的控制台提示！！！


5.4、停止 Eclipse 中的Tomcat 服务器
暴力停止 Tomcat （相当于电脑被拔掉电源一样。没有执行关机的准备操作。）


正常停止 Tomcat （相当于点击操作系统中的关机按钮，执行关机保存操作，然后关机）


5.5、配置Eclipse 中的Tomcat 布暑的Web工程路径
1）打开Servers窗口，双击Tomcat v6.0 Server 服务器打开 Tomcat的配置窗口


这里是Eclipse把工程发布后的三种不同的选项。


2）Tomcat 位置-选项介绍说明：

2.1、User workspance metadata (does not modify Tomcat installation) 将在eclipse的工作区间目录下eclipse的工作空间目录\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\ 有一份tomcat的拷贝所有布暑的web工程都会布暑到eclipse的工作空间目录\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps目录中
2.2、 User Tomcat installation (takes control of Tomcat installation) 在原Tomcat目录下做更改操作所有布暑的web工程都会发布到Tomcat目录下的\wtpwebapps目录中
2.3、 User custom location ( does not modify Tomcat installtion ) 自定义一个目录去布暑Web工程

比如操作如下：
一：选择自定义的布暑目录


二：选择你自定义的目录，然后点击确定。之后Tomcat的一些目录会被拷贝过来。布暑的工程也在这个目录下的wtpwebapps目录下

三：选中你要布暑的工程，右键选择菜单 Run As --->>> Run on Server 将工程布暑到刚刚自定义的目录下。

四：选择你当前的web工程需要使用的哪个服务器运行。
如果勾选中 Always use this server when running this project 表示下次运行服务器的时候默认使用当勾时时候选择的服务器运行。


五：等待Tomcat启动，web工程布暑成功后。查看





六：在浏览器中输入http://127.0.0.1:8080/day06/index.html 测试查看

6 HTTP协议介绍
6.1、 HTTP协议
1.HTTP（hypertext transport protocol），即超文本传输协议。这个协议详细规定了浏览器和万维网服务器之间互相通信的规则。
2.客户端与服务端通信时传输的内容我们称之为报文。
3.HTTP就是一个通信规则，这个规则规定了客户端发送给服务器的报文格式，也规定了服务器发送给客户端的报文格式。实际我们要学习的就是这两种报文。客户端发送给服务器的称为”请求报文“，服务器发送给客户端的称为”响应报文“。

大白话说，什么是协议。是双方相互约定好的规则；比如：租房协议：租房协议就是租客和房东之间相互约定好的租房规则 

6.2、请求的协议格式
请求的HTTP协议格式：


请求行   
请求头
空行
请求体

GET请求协议格式  （get请求没有请求体）


POST请求协议格式


6.3、常见请求头的说明


GET /Hello/index.jsp HTTP/1.1：GET请求，请求服务器资源的路径 Hello/index.jsp，  协议为http   版本为1.1；
Host:localhost：请求的主机名为localhost；
User-Agent: Mozilla/4.0 (compatible; MSIE 8.0…：与浏览器和OS相关的信息。有些网站会显示用户的系统版本和浏览器版本信息，这都是通过获取User-Agent头信息而来的；
Accept: */*：告诉服务器，当前客户端可以接收的数据类型， */*，就表示什么都可以接收；
Accept-Language: zh-CN：当前客户端支持的语言，可以在浏览器的工具选项中找到语言相关信息；
Accept-Encoding: gzip, deflate：支持的压缩格式。数据在网络上传递时，可能服务器会把数据压缩后再发送；
Connection: keep-alive：客户端支持的链接方式，保持一段时间链接，默认为3000ms；

6.4、get请求和post请求都分别是哪些？

GET请求 

1）、输入浏览器地址栏输入地址，直接按回车  
2）、点击<a>超链接   
3）、GET请求 表单提交  
4）、script src=””，引入外部文件 
5）、img src=”路径”,引入图片
6）、引入外部css。。。

POST请求

1）只有表单提交的时候method=post,提交表单就是发post请求
6.5、响应的协议格式
响应的HTTP协议格式

响应首行
响应头信息
空行
响应体
6.6、常见的响应码
响应码对浏览器来说很重要，它告诉浏览器响应的结果；

200：请求成功，浏览器会把响应体内容（通常是html）显示在浏览器中；
404：请求的资源没有找到，说明客户端错误的请求了不存在的资源；
500：请求资源找到了，但服务器内部出现了错误；
302：请求重定向，当响应码为302时，表示服务器要求浏览器重新再发一个请求，服务器会发送一个响应头Location，它指定了新请求的URL地址；

7、servlet（重点*****）
7.1、servlet简介
servlet 是运行在 Web 服务器中的小型 Java 程序。servlet 通常通过 HTTP（超文本传输协议）接收和响应来自 Web 客户端的请求。 
要实现此接口，可以编写一个扩展 javax.servlet.GenericServlet 的一般 servlet，或者编写一个扩展 javax.servlet.http.HttpServlet 的 HTTP servlet。 
此接口定义了初始化 servlet 的方法、为请求提供服务的方法和从服务器移除 servlet 的方法。这些方法称为生命周期方法，它们是按以下顺序调用的： 

1.构造 servlet，然后使用 init 方法将其初始化。 
2.处理来自客户端的对 service 方法的所有调用。 
3.从服务中取出 servlet，然后使用 destroy 方法销毁它，最后进行垃圾回收并终止它。
Servlet：

1、接受浏览器发送过来的消息。
2、给浏览器返回消息。浏览器认识html。可以动态去输出html
7.2、servlet快速入门
7.2.1、如何创建动态的Web工程
1）先创建 动态的Web工程


2)  配置工程的选项


3）勾选生成web.xml配置文件




4）动态web工程创建完成！！！


5）web工程的介绍和说明


7.2.2、手动编写servlet实现
写servlet做两件事

1、实现servlet接口。 由sun公司定义的一个接口。(定义一个规范)
2、把类部署到web服务器中（tomcat）。
sun公司定义一个servlet的规范。定义了servlet应该有哪些方法，以及方法需要的参数。

1、实现servlet接口（javax.servlet.Servlet）
2、重写service方法(service方法每次请求都会调用一次)

package com.atguigu.web;

import java.io.IOException;

import javax.servlet.Servlet;

import javax.servlet.ServletConfig;

import javax.servlet.ServletException;

import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;

public class Hello implements Servlet{

@Override

public void destroy() {

// TODO Auto-generated method stub

System.out.println("Servlet销毁了！");

}

@Override

public ServletConfig getServletConfig() {

// TODO Auto-generated method stub

return null;

}

@Override

public String getServletInfo() {

// TODO Auto-generated method stub

return null;

}

@Override

public void init(ServletConfig arg0) throws ServletException {

// TODO Auto-generated method stub

System.out.println("ServerConfig 初始化了");

}

@Override

public void service(ServletRequest arg0, ServletResponse arg1)

throws ServletException, IOException {

// TODO Auto-generated method stub

System.out.println("hello servlet service方法被调用");

}

}


当浏览器输入地址，访问servlet的时候，servlet会执行servcie方法。

3、在WebContent/WEB-INF/web.xml中配置servlet的访问路径 。浏览器访问servlet的路径

web.xml（新建web工程的时候，eclipse自动创建出来的）的位置：

在web.xml的根标签下，直接书写如下内容。


<?xml version="1.0" encoding="UTF-8"?>

<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">

<display-name>day06</display-name>

<servlet>

<servlet-name>Hello</servlet-name>

<servlet-class>com.atguigu.web.Hello</servlet-class>

</servlet>

<servlet-mapping>

<servlet-name>Hello</servlet-name>

<url-pattern>/hello</url-pattern>

</servlet-mapping>

<welcome-file-list>

<welcome-file>index.html</welcome-file>

<welcome-file>index.htm</welcome-file>

<welcome-file>index.jsp</welcome-file>

<welcome-file>default.html</welcome-file>

<welcome-file>default.htm</welcome-file>

<welcome-file>default.jsp</welcome-file>

</welcome-file-list>

</web-app>



4、把项目部署到tomcat中，去启动tomcat。在地址栏中输入信息，访问servlet


控制台打印：


7.2.3、访问servlet的细节
浏览器地址栏中输入：http://localhost:8080/day06/hello
访问过程分析：


7.2.4、servlet生命周期
Servlet的生命周期

1.调用 init 方法 初始化Servlet
2.调用 Servlet中的service方法 处理请求操作
3.调用 destory方法 执行Servlet销毁的操作
init方法：当服务器创建一个serlvet的时候，会去调用init方法。当我们第一次去访问一个servlet的时候，会去创建这个servlet对象。并且只会创建一次。如果配置了load-on-startup 表示服务器启动的时候就创建servlet实例。
service方法：客户端每一次请求，tomcat都会去调用servcie方法。处理用户的请求。并且给其响应。每一次请求都会调用servcie方法。
estroy 方法：当服务器销毁一个servlet的时候，会调用里面的destory方法。当我们的web服务器，正常关闭的时候，会去调用destroy方法。否则不会调用destroy的方法。

7.3、使用Eclipse创建Servlet程序（重点*****）
1）通过Eclipse自动新建一个Servlet程序


2）修改Servlet的访问url地址


3）勾选需要生成的Servlet方法



4) 查看自动生成的结果内容！！！


5) 在浏览器中输出http://127.0.0.1:80/day06/helloServlet 访问测试


访问成功

7.4、Servlet是单例的。Servlet中的变量，它有线程安全问题。
7.4.1、全局变量，数据不安全。

7.4.2、方法内的局部变量，数据安全

————————————————
版权声明：本文为CSDN博主「编程开发分享者」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://project.myBlog.blogStudy.csdn.net/chaogu94/article/details/107292921





# 【Tomcat】Tomcat 介绍及使用教程

吞吞吐吐大魔王

已于 2022-03-13 14:29:23 修改

4133
 收藏 58
分类专栏： JavaEE 文章标签： tomcat java-ee java
版权

JavaEE
专栏收录该内容
8 篇文章2 订阅
订阅专栏
文章目录
1. Tomcat 介绍
2. 下载安装
2.1 Windows 中安装
2.2 Linux 中安装
2.3 访问 Tomcat
3. Tomcat 的目录结构
4. Tomcat 的配置文件
5. WEB 应用部署目录结构
6. 部署静态页面
6.1 部署单个 HTML
6.2 部署带有 CSS、JavaScript、图片的 HTML
6.3 部署 HTML 到单独的目录中
6.4 部署博客系统页面
6.5 部署博客系统到云服务器
1. Tomcat 介绍
Apache Tomcat 是由 Apache Software Foundation（ASF）开发的一个开源 Java WEB 应用服务器。

由于 Tomcat 是由 Java 语言实现的，因此需要运行在 Java 虚拟机上，所以使用前要先安装 JDK，以提供运行环境

Tomcat 实现了几个 Java EE 规范，包括 Java Servlet、Java Server Pages（JSP）、Java Expression Language 和 Java WebSocket 等。在下载好 Tomcat 后，可以在它的 lib 目录中看到相关的 Java EE 规范 API 源码的引用



Tomcat 实现的几个 Java EE 规范中，有一个很重要的规范 Servlet，通过它我们可以运行自己编写的 Servlet 应用程序处理动态请求，即实现动态页面

Tomcat 的 Connector 组件实现了 HTTP 请求的解析，可以把 Tomcat 看作是一个 HTTP 服务器，Tomcat 可以通过 Connector 组件接收 HTTP 请求并解析，然后把解析后的信息交给 Servlet 处理：

对于静态资源（html/css/js 等）请求：Tomcat 提供默认的 Servlet 来处理响应
对于动态请求：可以映射到自己编写的 Servlet 应用程序来处理
2. 下载安装
这里以 Tomcat 8 为例，分别在 Windows 和 Linux 系统中演示下载安装 Tomcat 的方法。

注意： 由于 Tomcat 运行在 Java 虚拟机上，因此在安装 Tomcat 之前要先下载好 JDK

2.1 Windows 中安装
进入 Tomcat 官网：https://tomcat.apache.org/

在下载栏点击 Tomcat 8



选择 zip 压缩包，下载后解压即可



打开 Tomcat 文件夹，进入 bin 目录，双击 startup.bat 就可以运行 Tomcat 服务器



注意： 由于 CMD 默认的编码方式是 GBK，而 Tomcat 的默认编码方式是 UTF-8，因此在双击 startup.bat 启动 Tomcat 时，CMD 上显示的会是乱码。但该问题可以不处理，因为并不需要通过 CMD 来查看 Tomcat 的启动状况及其它情况，可以使用其它方式。



2.2 Linux 中安装
首先切换到 opt 目录（opt 是给主机额外安装软件（安装包）所存放的目录）

cd /opt
1
通过 wget 方式来下载 Tomcat 的安装包

wget https://mirrors.cnnic.cn/apache/tomcat/tomcat-8/v8.5.73/bin/apache-tomcat-8.5.73.tar.gz
1
将下载的 Tomcat 压缩包进行解压

tar xzf apache-tomcat-8.5.73.tar.gz
1
之后通过 startup.sh 运行 Tomcat 服务器

/opt/apache-tomcat-8.5.73/bin/startup.sh
1
2.3 访问 Tomcat
当我们运行 Tomcat 之后，就可以在浏览器中通过 服务器IP端口号 来进行访问。

如果你是使用的服务器，那么 IP 地址就为服务器的公网 IP
如果你是使用的自己的主机，那么 IP 地址就为 127.0.0.1
Tomcat 的默认端口号为 8080（可以在 conf 目录中手动修改）
访问成功后，会显示如下页面：



注意： 如果你想要访问其他人的 Tomcat，要么他人 Tomcat 使用的是外网 IP，要么你和他的外网必须相同，即在同一局域网下。只有这两种情况你才能访问到他人的 Tomcat，否则就访问不了

当前我们使用的 IP 协议是 IPv4，这个协议下的 IP 地址是一个32位、4个字节的整数，由于该数字并不大，但是全世界联网的设备已经超过了这个数字，因此想要每台主机都有一个独立的 IP 地址是不够的。

因此就采用了 NAT 机制，即多台主机使用一个外网 IP，外网是不同重复的，并且每个相同的外网下的不同主机都有自己独自的内网 IP。如此一来，就很好的解决了 IP 地址不够的问题。

3. Tomcat 的目录结构
当安装好 Tomcat 后，打开它的文件夹，可以看到以下目录



bin： 存放各种启动、关闭和其它程序的脚本（*.sh 文件是针对 Unix 系统使用的，*.bat 文件是针对 Windows 系统使用的，* 相同的程序的功能是一样的）

conf： 配置文件及相关数据文件存放的目录，如存放 server.xml、tomcat-users.xml、web.xml

lib： Tomcat 使用的库文件存放的目录，如存放 Servlet 规范的 API

logs： 默认日志文件存放的目录，如存放访问日志（可以通过 server.xml 文件将日志配置到其它目录）

temp： 临时文件的工作目录，如上传大文件时的缓存数据会存储在这里

webapps： 存放 web 应用、用来程序部署的目录（可以通过 server.xml 文件配置）

一个具有独立完整功能的网站，可以称为一个 web 应用。一个 Tomcat 的服务器上可以同时部署多个这样的 web 应用。这些 web 应用以目录的形式被存放到 webapps 目录中

work： Tomcat 的工作目录，如存放 JSP 编译后的类文件

4. Tomcat 的配置文件
在 Tomcat 的 conf 目录中，存放了 Tomcat 的配置文件，打开该目录，可以看到主要有以下几个配置文件



catalina.policy： 当基于 -securty 选项启动 Tomcat 实例时会读取此配置文件。此文件是 Java 的安全策略配置文件，用于配置访问 codebase（代码库）或某些 Java 类的权限
catalina.properties： Java 属性定义文件，设定类加载器路径、安全包列表和一些调整性能的参数信息
context.xml： 为部署与此 Tomcat 实例上的 web 应用程序提供的默认配置文件，每个 webapp 都可以使用独有的 context.xml，通常放置于 webapp 目录的 META-INF 子目录中，常用于定义会话管理器，Realm 和 JDBC 等
logging.properties： 定义日志相关的配置信息，如日志级别、文件路径等
server.xml： Tomcat 核心配置文件，包含 Service、Connector、Engine、Realm、Valve、Hosts 主组件的相关配置信息
tomcat-users.xml： 包含 Realm 认证时用到的相关角色、用户和密码等信息；Tomcat 自带的 manager 默认情况下会用到此文件；在 Tomcat 中添加火删除用户，为用户指定角色等将通过编辑此文件实现
web.xml： 为部署与 Tomcat 实例上的所有 web 应用程序提供部署描述符，通常用于为 webapp 提供默认的 servlet 定义和基本的 MUIME 映射表
5. WEB 应用部署目录结构
部署应用程序时，一般会将其打包成一个 war 包，然后放到 Tomcat 的应用程序部署目录 webapps 中。而 web 应用程序有特定的组织格式，是一种层次型目录结构，通常包含了 servlet 代码文件、HTML、JSP 页面文件、类文件、部署描述符文件等等，相关说明如下：

/： 表示 web 应用程序的根目录，可以存放 HTML、JSP 页面以及其他客户端浏览器必须可见的其他文件（如 JS、CSS、图像文件）。在较大的应用程序中，还可以选择将这些文件划分为子目录层次结构

/WEB-INF： 表示 web 应用程序的所有私有资源目录，用户浏览器不可能访问到的，通常 web.xml 和 context.xml 均放置于此目录。

/WEB-INF/web.xml： 表示 web 应用程序的私有的部署描述符，描述组成应用程序的 servlet 和其他组件（如 filter），以及相关初始化参数和容器管理的安全性约束。

/WEB-INF/classes： 表示 web 应用程序自有的 Java 程序类文件及相关资源存放目录。

/WEB-INF/lib： 表示 web 应用程序自有的 JAR 文件，其中包含应用程序所需的 Java 类文件及相关资源（如第三方类库或 JDBC 驱动程序）

6. 部署静态页面
什么是静态页面？

静态页面就是内容始终固定的页面，即使用户不同、时间不同、输入的参数不同，页面内容也不会发生变化。除非网站的开发人员修改源代码，否则页面的内容始终不变。

6.1 部署单个 HTML
可以将自己写好的单个 HTML 部署到 Tomcat 中，方法如下：

先创建好 html 文件（hello.html）

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>将 HTML 部署到 Tomcat 上</title>
</head>
<body>
    <div>hello</div>
</body>
</html>
1
2
3
4
5
6
7
8
9
10
11
12
将创建好的 html 文件拷贝到 Tomcat 的 webapps/Root 目录中



启动 Tomcat，在浏览器的网址栏输入 127.0.0.1:8080/hello.html 就可以访问到刚刚部署的 HTML



6.2 部署带有 CSS、JavaScript、图片的 HTML
实际开发中，HTML 可能不仅仅是单一文件，还会依赖一些其它资源，如 CSS、JavaScript、图片等等。

可以将自己写好的带有 CSS、JavaScript 或图片的 HTML 部署到 Tomcat 中，方法如下：

创建好 html 文件（hello.html）

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>将 HTML 部署到 Tomcat 上</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <img src="弥豆子.jpg" alt="弥豆子">
    <script src="app.js"></script>
</body>
</html>
1
2
3
4
5
6
7
8
9
10
11
12
13
14
创建好 css 文件（style.css）

img {
    width: 500px;
    height: 300px;
}
1
2
3
4
创建好 js 文件（app.js）

console.log("hello");
1
准备好图片（弥豆子.jpg）



将以上几个文件都拷贝到 Tomcat 的 webapps/ROOT 目录中



启动服务器，在浏览器的网址栏输入 127.0.0.1:8080/hello.html 就可以访问到刚刚部署的 HTML



6.3 部署 HTML 到单独的目录中
实际开发中，HTML 文件可能不止一个，依赖的资源也比较多，很杂乱。因此直接全部拷贝到 webapps/ROOT 目录中就不太合适。

可以创建一个单独的目录，和 ROOT 目录并列，来存放我们要部署到 Tomcat 中的内容，方法如下：

在 webapps 目录中创建一个新的文件夹（这里为 HelloApp），并在该文件中，将 HTML 的依赖再通过创建新的目录来进行合理的分类（如 css、js、img 等）

将 6.2 中的那些文件全部拷贝到该文件中，并按照新建的目录进行分类（注意：可能要调整一些资源的路径）



启动 Tomcat，在浏览器的网址栏输入 127.0.0.1:8080/HelloApp/hello.html 就可以访问到刚刚部署的 HTML



6.4 部署博客系统页面
我在之前编写过一个博客系统的前端页面（源码及注释都写的很详细，大家有需要可以看看《个人简单博客系统页面搭建（附源码）》，这里就以这个博客系统的页面为例，将其部署到 Tomcat 上

首先在 webapps 目录下创建一个目录（这里为 project.myBlog.blogStudy）

将写的博客系统页面的文件拷贝进来



启动 Tomcat，在浏览器的网址栏输入 127.0.0.1:8080/project.myBlog.blogStudy/blog_list.html 就可以访问到刚刚部署的博客系统



6.5 部署博客系统到云服务器
上面已经介绍了如何将 HTML 文件等 web 程序部署到本地的 Tomcat 上，但是仅仅如此，其他人是无法访问到这些页面的。

因此，可以将 web 程序部署到云服务器的 Tomcat 的 webapps 目录中，这样你部署的程序其他人也可以看到。方式如下：

首先将你要部署的程序的目录打一个 zip 压缩包（这里以上述博客系统页面为例）



通过 xshell 连接上云服务器，并切换到 Tomcat 的 webapps 目录中



将 project.myBlog.blogStudy.zip 拖拽到 xshell 中（也可以使用其它方式，如使用 xftp）



拖拽成功后，将 project.myBlog.blogStudy.zip 进行解压缩

unzip. project.myBlog.blogStudy.zip
1


最后在浏览器中的网址栏搜索 http://:8080/project.myBlog.blogStudy/blog_list.html 就可以访问到该页面，大家也可以访问到的！

————————————————
版权声明：本文为CSDN博主「吞吞吐吐大魔王」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://project.myBlog.blogStudy.csdn.net/weixin_51367845/article/details/123429050