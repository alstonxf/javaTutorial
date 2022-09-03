## **设置IntelliJ IDEA 的文件编码的方式**

 这里介绍两种设置IntelliJ IDEA 的文件编码的方式，针对的是IntelliJ IDEA2017版本

 

File ->Settings ->Editor ->File Encodings 这种方式修改的文件编码方式只对当前 project 起作用，每次新建了一个工程后还需要重新设置编码方式。

File ->Other Settings ->Default Settings ->Editor ->File Encodings ，这儿设置的是默认的文件编码方式，所有新建的工程使用的都是默认的文件编码方式。

一个针对当前，一个针对于所有

 

二、步骤

点击File ->Settings ->Editor ->File Encodings

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps1.jpg" alt="img" style="zoom: 33%;" /> 

点击File ->Other Settings ->Default Settings ->Editor ->File Encodings

 

 

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps2.jpg" alt="img" style="zoom:33%;" /> 

## [**IDEA修改默认文件换行格式为LF**](https://www.cnblogs.com/mahoshojo/p/12622634.html)

File -> Settings -> Editor -> Code Style -> Line separator

在window下开发有一个大坑，就是换行默认是CRLF，也就是回车换行，但是Linux下只有换行LF，这样代码提交后，会出现编译问题，所以最好的办法是在IntelliJ下设置默认为LF。首先我们先介绍CRLF，LF和CR这三种东西，CR是MAC老版本的做法，就是回车，但是后来的MAC系统统一换成LF了，LF是Linux下的做法，就是换行，这个做法比较自然，为什么要回车换行呢，是吧。微软采用的是CRLF，看上去好像是兼容了CR和LF，但是实际完全不是那么回事，就是回车并换行，好鸡肋啊，微软一直保持这种做法，开发人员大多在Linux下，所以对于开发人员来说还是比较坑的。下面介绍设置详解：

第一步：File->Settings…

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps3.jpg" alt="img" style="zoom: 33%;" /> 

 

第二步：Editor->Code Style

  可以看到，默认是System-Dependent，这个其实还是很牛叉的，根据系统自动配置，但是你是windows系统，默认是CRLF，服务器是Linux，你就得自己换了。

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps4.jpg" alt="img" style="zoom: 33%;" /> 

 

  我们设置成下面这样，保存就好了

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps5.jpg" alt="img" style="zoom: 33%;" /> 

 

  创建文件时，就能看到默认是LF了

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps6.jpg" alt="img" style="zoom: 33%;" /> 

##  **Intellij IDEA 导入maven项目图文解析**

### **导入的前提是本机已经安装了maven、tomcat等必要的开发工具，并且项目已经down到了本地。**



------



1、启动IDEA，选择 ***import Project\***（前提项目已经down到本地了，如果本地没有的话，就需要选第四项，点击，选择是svn还是git还是其他方式拉取代码）

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps7.jpg" alt="img" style="zoom:50%;" /> 



------



2、选中要导入的项目后，选择 ***Maven，\***点击 ***Next，\***进入到下一页面<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps8.jpg" alt="img" style="zoom:33%;" />

 



------



3、继续点击Next

 

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps9.jpg" alt="img" style="zoom: 50%;" /> 



------



4、点击加号，添加***JDK\***

 



------



5、点击***Next\***

 



------



6、填写项目名称、选择项目路径（一般默认就行），点击***Finish\***

 



------



7、此刻会弹出一个信息框，点击 ***Yes\***

 



------



8、等待一段时间后（这段时间是在加载项目），就会看到项目菜单了

 



------



#### **项目就导入完毕了，接下来是配置信息，让项目能够运行**

9、选择 ***File ---Settings\***

 



------



10、选中 ***Build,Execution,Deployment ---> Build Tools --->Maven\***，按图中所示，配置maven，默认进来时候，划红线的第二、第三个是不能选的，将后面的两个选项框勾上以后，就能进行选择了，配置完以后，选择 ***Apply按钮\***

 



------



11、关闭掉上面的Settings弹框，在主界面选择 ***View ---> Tool Windows ---> Maven Projects\***

 



------



12、屏幕的右侧（一般默认的就是在右侧）会出现下面这个信息，此刻，需要加载项目需要的包，如果是首次加载Maven项目的话，需要点击下载那个图标，这一步会有点慢；如果本地已经有项目所需的包，那点击刷新按钮就行，有时候，需要多点几次刷新，才能全部加载完毕。（项目所需包没加载完，项目下会有红线提示）

 



------



13、配置Tomcat，点击图中所示的图标（如果勾选了View--->ToolBar，该图标是在左侧），点击***Edit Configurations\***

 



------



14、点击加号

 



------



15、选择Tomcat

 



------



16、如图所示，点击 ***Configrue...\*** 如果把 ***After launch\*** 勾选中，并选择好某个浏览器后，每次运行完毕会立即打开页面；端口号默认是8080，可以根据自己的需要来改

 



------



17、点击加号，选择Tomcat路径，点击OK

 

 



------



18、配置好以后，根据下图所示进行操作

 

 



------



19、当点击完***Fix\***按钮以后，会跳到***Deployment\***页签（如果不是点击***Fix\***按钮，而是通过点击***加号\***手动选择，那么选完以后，手动切换到***Deployment\***页签来进行下一步操作）；根据自己的项目实际来填写***Application context\*** ;全部配置好以后，点击***Apply\***按钮

 

 



------



20、回到主界面，选择 ***Build---> Build Artifacts\***

 



------



20、Build自己的项目

 



------



#### **21、至此，所有配置均已完成，启动Tomcat，大功告成！或者点击 Run--->Debug Tomcat7**

 

## **IDEA 中把普通项目改为maven项目**

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps10.jpg" alt="img" style="zoom:33%;" /><img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps11.jpg" alt="img" style="zoom:33%;" /> 

## [Idea使用技巧11--IDEA 修改工程名](https://www.cnblogs.com/luckyplj/p/13924311.html)

1.Project Settings下

　　1.1.更改project的Project name和Project compiler output。

file->project structure

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps12.jpg" alt="img" style="zoom:33%;" /> 

 

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps13.jpg" alt="img" style="zoom:33%;" /> 

　　1.2.更改Modules的Name

file->project structure

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps14.jpg) 

 

　　1.3.删除Artifacts下的两个打包配置（稍后会再自动生成）

或者修改

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps15.jpg" alt="img" style="zoom:33%;" /> 

2.更改pom.xml的artifactId‘![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps16.jpg)’

3.退出idea，找到项目路径，更改项目文件名

然后idea再open项目

如果是web项目，还需要修改tomcat的Deployment

 

 

**1.修改Project名称**

**3.关闭项目，去本地文件夹，修改项目所在文件夹的名称（****非常关键****）**

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps17.jpg) 

## **idea创建工程java不是蓝色source**

在java文件夹右键，然后mark directory as => root source即可

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps18.jpg) 

# **在Idea控制台调整日志等级**

1.导入包

import org.apache.log4j._

2.设置日志级别（DEBUG、INFO、WARN、ERROR）

Logger.getLogger("org").setLevel(Level.ERROR)

# **idea 项目能运行，但是代码冒红-解决措施**

 

 

1.清除idea缓存

 

 

 

2、更改编码格式

 

 

 

要确保代码编码格式为 UTF-8 

 

3、更改Project SDK

 

 

 

 

 

 

 

 4、更改idea 默认的JDK版本 

 

idea 默认导入 JDK 11，我们可以根据项目更改，一定要更具项目更改。

 

 

 

5、安装 lombok插件

 

 如果发现项目能运行，但是bean类的get()、set()方法都找不到了，直接安装lombok插件即可。

 

 

 

6、特殊原因

 

我是java初学者，遇到过一些无法解决的事情。网上找了很多方法都无法解决，我都是重新编译项目，编译成功，但还是冒红，然后重启项目然后就成功了。

 

 

# **idea出现decompiled .class file 解决方案**

## **问题**

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps19.jpg" alt="img" style="zoom:33%;" /> 

## **原因**

idea借助[反编译](https://so.csdn.net/so/search?q=反编译&spm=1001.2101.3001.7020)插件从.class文件中获取的源码， 并不会携带javadoc。

## **解决方案**

File->Project structure 选择本地jdk路径即可
<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps20.jpg" alt="img" style="zoom:33%;" />

Error:java: Compilation failed: internal java compiler error 解决办法

 

程序新视界

 

于 2018-08-27 22:01:46 发布

 

220752

 收藏 302

分类专栏： Intellij IDEA Intellij IDEA日常使用 文章标签： idea jdk 版本

版权

 

Intellij IDEA

同时被 2 个专栏收录

38 篇文章3 订阅

订阅专栏

 

Intellij IDEA日常使用

40 篇文章55 订阅

订阅专栏

错误现象

使用Idea导入新项目或升级idea或新建项目时会出现以下异常信息：

 

Error:java: Compilation failed: internal java compiler error 

1

 

 

错误原因

导致这个错误的原因主要是因为jdk版本问题，此处有两个原因，一个是编译版本不匹配，一个是当前项目jdk版本不支持。

 

查看项目的jdk

File ->Project Structure->Project Settings ->Project或使用快捷键Ctrl+Alt+shift+S打开项目的jdk配置：

 

查看此两处是否与目标jdk一致。

 

查看工程的jdk

点击上图中Modules查看对应jdk版本：

 

 

查看java编译器版本

 

导入java项目时此处处问题的概率比较多。

 

针对此问题，重新打开或修改pom文件（maven项目）中的内容很可能导致jdk版本重新变为1.5。如果是maven项目，可在pom文件中指定jdk相关信息：

 

<build>

​    <plugins>

​      <plugin>

​        <groupId>org.apache.maven.plugins</groupId>

​        <artifactId>maven-compiler-plugin</artifactId>

​        <configuration>

​          <source>1.7</source>

​          <target>1.7</target>

​        </configuration>

​      </plugin>

​    </plugins>

  </build>

## **idea提⽰Cannotfinddeclarationtogoto的解决⽅法**

今天遇到⼀个很奇怪的问题，某个类明明存在，且没有任何异常，但是引⽤该类的地⽅都提⽰错误，此类同包下的其他⽂件却没有没有类似

问题，尝试ctrl+⿏标左键进⼊该类，提⽰Cannot find declaration to go to。maven clean然后重新install⽆效、rebuild⽆效、代码删

了重下重新导⼊竟然还是⽆效。

后来发现是⽤idea切换git分⽀导致idea的缓存乱了，下⾯附上解决⽅法

很简单 File --> Invalidate Caches / Restart 重启idea搞定。

其他解决⽅法：

1、如果是.iml的问题，可以尝试删掉项⽬根⽬录下的.iml⽂件或者.idea⽂件夹，重新打开项⽬idea会⾃动⽣成正确的⽂件

2、File-->close project 退到⼩窗界⾯，选择Import Project的⽅式导⼊项⽬

3、选中项⽬⿏标右键，mark Directory as --> Sources Root，等待⼀会。。

# **IDEA中多行注释及取消注释快捷键**

 

1、一次性添加多行注释的快捷键

首先选中要注释区域，然后

ctrl+/ : 这个是多行代码分行注释，每行一个注释符号

ctrl+shift+/ : 这个是多行代码注释在一个块里，只在开头和结尾有注释符号

2、取消多行注释快捷键

怎样添加快捷键的，用相同方法取消，

如 ctrl+/ : 添加注释，则ctrl+/取消注释

ctrl+shift+/ : 添加注释，则ctrl+shift+/取消注释

# **IDEA中提示：java: -source 1.5已过时, 将在未来所有发行版中删除**

 

## ** ****方法一:**

\1. 打开【File】—【Project Structure】，找到以下两个地方：

Project Structure->Project里Project sdk以及project language level
Project Structure->Modules里Sources里的Language level

因为我的电脑上安装的Java的版本是1.8，所以在这两个地方位置的设置如下：

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps21.png) 

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps22.jpg) 

这几个地方要对应

\2. 当这两个地方配置好后，再次运行项目，出现如下的提示：

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps23.jpg) 

说明我们还有地方没有配置好。打开【File】—【Settings】，找到【Java Compiler】，会发现这个问题：

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps24.jpg) 

发现两个地方的版本对应不上，我们需要将下边的5或者1.5修改为8或者1.8。
修改完后，点击Apply—OK之后，程序就能正常运行了。

## **方法二:**

 要是还不行的话，试试改配置文件

1.修改Maven的Settings.xml文件添加如下内容

 



apache

<profile>

 <id>jdk-1.8</id>

 <activation>

  <activeByDefault>true</activeByDefault>

  <jdk>1.8</jdk>

 </activation>

 <properties>

  <maven.compiler.source>1.8</maven.compiler.source>

  <maven.compiler.target>1.8</maven.compiler.target>

  <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>

 </properties></profile>



2.在项目的pom.xml文件中添加：

vim

<properties>

 <maven.compiler.source>1.8</maven.compiler.source>

 <maven.compiler.target>1.8</maven.compiler.target>

</properties>

3.打开项目配置，设置Modules的Language Level为”8”

4.最后按”Ctrl+Alt+S”打开设置，搜索”Java Compiler”，将默认jdk和当前modual的jdk版本切换为1.8即可

 

 

# **程序包com.sun.tools.javac.util不存在问题的两种解决方法**

 

在项目开发中遇到“程序包com.sun.tools.javac.util不存在”的错误

 

 

以下配置经过验证可行，打开File-->Project Structure窗口：

\00001. 

1、Project Settings-->Project SDK-->选择1.8版本

\00002. 

 

\00001. 

2、Platform Settings-->SDKs-->选择1.8-->ClassPath-->点击加号并选择tools.jar，保存，重新编译即可。

\00002. 

 

# [Maven编译时找不到sun.jvm.hotspot](http://cn.voidcc.com/question/p-rinnlngn-ou.html)

 

添加$JAVA_HOME/lib/sa-jdi.jar到CLASSPATH。

或者

File-》Project Structure-》Libraries

 

点击“+”，选择java，找到本地JDK下的lib\sa-jdi.jar文件，添加到相应模块，点击ok，最后点击Apply即可。

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps25.jpg) 

 

# ***\*IDEA 在同一目录创建多个项目\****

以往的Eclipse、NetBeans等开发工具不同，IDEA的Project相当与Eclipse的Workspace，而Module相当于Project。

下边就给出Eclipse与IDEA的概念的对应关系：

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps26.jpg) 

我们的目标是：创建工作空间，然后在它下面建立2个项目，即 工作空间 project下，包含projectA与 projectB两个项目，然后在 项目projectA中创建两个模块module1和module2。

## ***\*一、同一目录建立两个项目\**** 

首先建立一个空目录，然后用idea打开（此时不要创建任何项目）

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps27.jpg) 

### ***\*在空目录project创建项目A\****

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps28.jpg" alt="img" style="zoom:33%;" /> 

下图是用于选择maven模板，如果有需要可以自行选择，我这里创建的是基本的java的maven目录结构。

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps29.jpg" alt="img" style="zoom:33%;" /> 

 <img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps30.jpg" alt="img" style="zoom:33%;" />

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps31.jpg" alt="img" style="zoom:33%;" /> 

projectA 创建好了

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps32.jpg) 

### ***\*在project创建项目B\****

在project上右键建立Module

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps33.jpg) 

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps34.jpg) 

下图标注的none的地方在创建项目时，应该是none；当是创建模块是，应该是父级模块的坐标

　　![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps35.jpg)

 ![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps36.jpg)

此时在project目录下建立的两个项目已经完成

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps37.jpg) 

## ***\*二、在projectA中建立多个模块\****

在projectA上右键创建module

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps38.jpg) 

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps39.jpg) 

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps40.jpg) 

点击下一步

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps41.jpg) 

 模块1创建完成

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps42.jpg) 

同理创建模块2

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps43.jpg) 

 目前为止我们在project目录中创建了两个项目projectA和projectB，为projectA创建了两个模块module-1和module-2。 project目录和projectAprojectB 在项目上没有必然的联系，只是用于存放项目。projectA和projectB也没有任何的联系。

module-1和module-2和project有强关联关系，是projectA的两个子模块。

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps44.jpg) 

## ***\*三、idea 导入同一目录下多个项目\****

创建project2目录

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps45.jpg) 

将project项目中的项目复制到project2中

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps46.jpg) 

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps47.jpg) 

打开后如下图

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps48.jpg) 

 在项目的pom.xml文件上右键，然后选择Add as Maven Project 

 <img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps49.jpg" alt="img" style="zoom:33%;" />

此时项目B 已经成功导入，成功导入后项目的目录会有不同的图标，可以对比projectA和projectB，此时projectB已导入，projectA未成功导入。

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps50.jpg" alt="img" style="zoom:33%;" /> 

同理projectA也做上面的操作，projectA也成功导入

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps51.jpg" alt="img" style="zoom:33%;" /> 

至此，项目已经成功从project2目录导入到IDEA中

# **删除代码中的空行**

 

ctrl+r，然后点亮后面的魔法图标启用正则表达式，输入^\s*\n，然后选择替换全部，空白换行就取消了

<img src="/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps52.jpg" alt="img" style="zoom:33%;" /> 

# **取消双击shift弹窗**

1.control+shift+A；

\2. 输入registry；

\3. 找到ide.suppress.double.click.handler,点对勾。

\4. close点击

 

# **git 设置**

![img](/Users/lixiaofeng/Library/Mobile%20Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/java/%E5%85%B6%E4%BB%96%E6%96%87%E6%A1%A3/IntelliJ%20IDEA.assets/wps53.jpg) 