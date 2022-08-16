**MyBatis****手册**

 

**1、简介**

**1.1 什么是Mybatis**

MyBatis 是一款优秀的持久层框架;

它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

**1.2 持久化**

数据持久化

持久化就是将程序的数据在持久状态和瞬时状态转化的过程内存：断电即失数据库（Jdbc）,io文件持久化。为什么要持久化？

有一些对象，不能让他丢掉内存太贵**1.3 持久层**

Dao层、Service层、Controller层

完成持久化工作的代码块

层界限十分明显

**1.4 为什么需要MyBatis**

帮助程序员将数据存入到数据库中

方便

传统的JDBC代码太复杂了，简化，框架，自动化

不用MyBatis也可以，技术没有高低之分

优点：

简单易学灵活sql和代码的分离，提高了可维护性。提供映射标签，支持对象与数据库的orm字段关系映射提供对象关系映射标签，支持对象关系组建维护提供xml标签，支持编写动态sql**2、第一个Mybatis程序**

思路：搭建环境 --> 导入MyBatis --> 编写代码 --> 测试

**2.1 搭建环境**

新建项目

创建一个普通的maven项目删除src目录 （就可以把此工程当做父工程了，然后创建子工程）导入maven依赖

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps1.png) 

4.创建一个Module

**2.2 创建一个模块**

编写mybatis的核心配置文件

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps2.png) 

编写mybatis工具类

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps3.png) 

**2.3 编写代码**

实体类Dao接口public interface UserDao {

public List<User> getUserList();

}

接口实现类 （由原来的UserDaoImpl转变为一个Mapper配置文件）

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps4.png) 

测试

注意点：

org.apache.ibatis.binding.BindingException: Type interface com.kuang.dao.UserDao is not known to the MapperRegistry.

MapperRegistry是什么?

核心配置文件中注册mappers

junit测试

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps5.png) 

可能会遇到的问题：

配置文件没有注册

绑定接口错误

方法名不对

返回类型不对

Maven导出资源问题

**3、CURD**

**1. namespace**

namespace中的包名要和Dao/Mapper接口的包名一致

**2. select**

选择，查询语句；

id：就是对应的namespace中的方法名；resultType : Sql语句执行的返回值；parameterType : 参数类型；编写接口

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps6.png) 

编写对应的mapper中的sql语句

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps7.png) 

测试

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps8.png) 

**注意：增删改查一定要提交事务：**

sqlSession.commit();

1

**3. Insert**

**4. update**

**5. Delete**

**6. 万能Map**

假设，我们的实体类，或者数据库中的表，字段或者参数过多，我们应该考虑使用Map!

UserMapper接口//用万能Map插入用户

public void addUser2(Map<String,Object> map);

UserMapper.xml<!--对象中的属性可以直接取出来 传递map的key-->

<insert id="addUser2" parameterType="map">

insert into user (id,name,password) values (#{userid},#{username},#{userpassword})

</insert>

测试

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps9.png) 

Map传递参数，直接在sql中取出key即可！ 【parameter=“map”】

对象传递参数，直接在sql中取出对象的属性即可！ 【parameter=“Object”】

只有一个基本类型参数的情况下，可以直接在sql中取到

多个参数用Map , **或者注解！**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps10.png) 

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps11.png) 

上图Map负责把参数对应好，例如id对应userid，name对应username，pwd对应userpassword，传入的时候可以不传完全

例如下图只传了两个参数，则name会为null

**7. 模糊查询**

模糊查询这么写？

Java代码执行的时候，传递通配符% %List<User> userList = mapper.getUserLike("%李%");

2.在sql拼接中使用通配符

select * from user where name like "%"#{value}"%"

**4、配置解析**

**1. 核心配置文件**

mybatis-config.xmlMybatis的配置文件包含了会深深影响MyBatis行为的设置和属性信息。

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps12.png) 

**2. 环境配置 environments**

MyBatis 可以配置成适应多种环境

不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境

学会使用配置多套运行环境！

MyBatis默认的事务管理器就是JDBC ，连接池：POOLED

**3. 属性 properties**

我们可以通过properties属性来实现引用配置文件

这些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置。【db.poperties】

编写一个配置文件

db.properties

driver=com.mysql.cj.jdbc.Driver

url=jdbc:mysql://localhost:3306/mybatis?userSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC

username=root

password=root

在核心配置文件中引入

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps13.png) 

可以直接引入外部文件可以在其中增加一些属性配置如果两个文件有同一个字段，优先使用外部配置文件的**4. 类型别名 typeAliases**

类型别名可为 Java 类型设置一个缩写名字。 它仅用于 XML 配置.意在降低冗余的全限定类名书写。

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps14.png) 

也可以指定一个包，每一个在包 domain.blog 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如 domain.blog.Author 的别名为 author,；若有注解，则别名为其注解值。见下面的例子：

<typeAliases>

<package name="com.kuang.pojo"/>

</typeAliases>

在实体类比较少的时候，使用第一种方式。

如果实体类十分多，建议用第二种扫描包的方式。

第一种可以DIY别名，第二种不行，如果非要改，需要在实体上增加注解。

@Alias("author")

public class Author {

...

}

**5. 设置 Settings**

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps15.png) 

**6. 其他配置**

typeHandlers（类型处理器）

objectFactory（对象工厂）

plugins 插件

mybatis-generator-core

mybatis-plus

通用mapper

**7. 映射器 mappers**

MapperRegistry：注册绑定我们的Mapper文件；

方式一：【推荐使用】

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps16.png) 

方式二：使用class文件绑定注册

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps17.png) 

**注意点：**

接口和他的Mapper配置文件必须同名接口和他的Mapper配置文件必须在同一个包下方式三：使用包扫描进行注入

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps18.png) 

**8. 作用域和生命周期**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps19.png) 

声明周期和作用域是至关重要的，因为错误的使用会导致非常严重的并发问题。

SqlSessionFactoryBuilder:

一旦创建了SqlSessionFactory，就不再需要它了局部变量SqlSessionFactory:

说白了就可以想象为：数据库连接池SqlSessionFactory一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建一个实例。因此SqlSessionFactory的最佳作用域是应用作用域（ApplocationContext）。最简单的就是使用单例模式或静态单例模式。SqlSession：

连接到连接池的一个请求SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。用完之后需要赶紧关闭，否则资源被占用！**5、解决属性名和字段名不一致的问题**

**1. 问题**

数据库中的字段

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps20.png) 

新建一个项目，拷贝之前的，测试实体类字段不一致的情况

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps21.png) 

测试出现问题

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps22.png) 

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps23.png) 

解决方法：

起别名<select id="getUserById" resultType="com.kuang.pojo.User">

select id,name,pwd as password from USER where id = #{id}

</select>

**2. resultMap**

结果集映射

id name pwd

id name password

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps24.png) 

resultMap 元素是 MyBatis 中最重要最强大的元素。ResultMap 的设计思想是，对简单的语句做到零配置，对于复杂一点的语句，只需要描述语句之间的关系就行了。ResultMap 的优秀之处——你完全可以不用显式地配置它们。如果这个世界总是这么简单就好了。**6、日志**

**6.1 日志工厂**

如果一个数据库操作，出现了异常，我们需要排错，日志就是最好的助手！

曾经：sout、debug

现在：日志工厂

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps25.png) 

SLF4JLOG4J 【掌握】LOG4J2JDK_LOGGINGCOMMONS_LOGGINGSTDOUT_LOGGING 【掌握】NO_LOGGING在MyBatis中具体使用哪一个日志实现，在设置中设定

**STDOUT_LOGGING**<settings>

<setting name="logImpl" value="STDOUT_LOGGING"/>

</settings>

**6.2 Log4j**

什么是Log4j？

Log4j是Apache的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是控制台、文件、GUI组件；我们也可以控制每一条日志的输出格式；通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程；最令人感兴趣的就是，这些可以通过一个配置文件来灵活地进行配置，而不需要修改应用的代码。先导入log4j的包

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps26.png) 

log4j.properties

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps27.png) 

配置settings为log4j实现

测试运行

**Log4j简单使用**

在要使用Log4j的类中，导入包 import org.apache.log4j.Logger;日志对象，参数为当前类的class对象Logger logger = Logger.getLogger(UserDaoTest.class);

日志级别

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps28.png) 

infodebugerror**7、分页**

**思考：为什么分页？**

减少数据的处理量**7.1 使用Limit分页**

SELECT * from user limit startIndex,pageSize

**使用MyBatis实现分页，核心SQL**

接口//分页

List<User> getUserByLimit(Map<String,Integer> map);

2.Mapper.xml

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps29.png) 

3.测试

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps30.png) 

**7.2 RowBounds分页**

不再使用SQL实现分页

接口//分页2

List<User> getUserByRowBounds();

2.mapper.xml

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps31.png) 

3.测试

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps32.png) 

**7.3 分页插件**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps33.png) 

**8、使用注解开发**

**8.1 面向接口开发**

大家之前都学过面向对象编程，也学习过接口，但在真正的开发中，很多时候我们会选择面向接口编程根本原因 ：解耦,可拓展,提高复用,分层开发中,上层不用管具体的实现,大家都遵守共同的标准,使得开发变得容易,规范性更好 。

在一个面向对象的系统中，系统的各种功能是由许许多多的不同对象协作完成的。在这种情况下，各个对象内部是如何实现自己的，对系统设计人员来讲就不那么重要了;而各个对象之间的协作关系则成为系统设计的关键。小到不同类之间的通信，大到各模块之间的交互，在系统设计之初都是要着重考虑的，这也是系统设计的主要工作内容。面向接口编程就是指按照这种思想来编程。

关于接口的理解：

接口从更深层次的理解，应是定义(规范，约束)与实现(名实分离的原则)的分离。接口的本身反映了系统设计人员对系统的抽象理解。接口应有两类:. 第一类是对一个个体的抽象，它可对应为一个抽象体(抽象类 abstract class);

. 第二类是对一个个体某方面的抽象， 即形成一个抽象面(接口 interface) ;

.个体有可能有多个抽象面。抽象体与抽象面是有区别的。

三个面向区别

面向对象是指，我们考虑问题时，以对象为单位，考虑它的属性和方法；面向过程是指，我们考虑问题时，以一个具体的流程（事务过程）为单位，考虑它的实现；接口设计与非接口设计是针对复用技术而言的，与面向对象（过程）不是一个问题，更多的体现就是对系统整体的架构；**8.2 使用注解开发**

注解在接口上实现

@Select("select * from user")

List<User> getUsers();

需要在核心配置文件中绑定接口

<mappers>

<mapper class="com.kuang.dao.UserMapper"/>

</mappers>

测试

本质：反射机制实现

底层：动态代理

**MyBatis详细执行流程**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps34.png) 

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps35.png) 

**8.3 注解CURD**

//方法存在多个参数，所有的参数前面必须加上@Param("id")注解

@Delete("delete from user where id = ${uid}")

int deleteUser(@Param("uid") int id);

编写接口，增加注解

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps36.png) 

测试类

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps37.png) 

注意：我们必须要将接口注册绑定到我们的核心配置文件中！

关于@Param( )注解

基本类型的参数或者String类型，需要加上引用类型不需要加如果只有一个基本类型的话，可以忽略，但是建议大家都加上我们在SQL中引用的就是我们这里的@Param( )中设定的属性名面试题：#{} 和 ${}使用的区别？

首先建议：能使用 #{} 就不要使用 ${}~~~

mybatis 中使用 Mapper.xml 里面的配置进行 sql 查询，经常需要动态传递参数，例如我们需要根据用户的姓名来筛选用户时，sql 如下：

select * from user where name = "AAA";

上述 sql 中，我们希望 name 后的参数 “AAA” 是动态可变的，即不同的时刻根据不同的姓名来查询用户。在 Mapper.xml 文件中使用如下的 sql 可以实现动态传递参数 name：

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps38.png) 

动态 SQL 是 mybatis 的强大特性之一，也是它优于其他 ORM 框架的一个重要原因。mybatis 在对 sql 语句进行预编译之前，会对 sql 进行动态解析，解析为一个 BoundSql 对象，也是在此处对动态 SQL 进行处理的。在动态 SQL 解析阶段， #{ } 和 ${ } 会有不同的表现。

比如：我想让name的值为AAA，上面那两句代码解析下来的结果都是一样的：

select * from user where name = "AAA";

但是，其解析过程中处理手段是有所不同的！

\#{ }在预处理时，会把参数部分用一个占位符 ? 代替，变成如下的 sql 语句：

select * from user where name = ?;

${ } 仅仅为一个纯碎的 string 替换，在动态 SQL 解析阶段将会进行变量替换。

select * from user where name = "";

综上所得， ${ } 变量的替换阶段是在动态 SQL 解析阶段，而 #{ }变量的替换是在 DBMS 中。

总结：

\#{ }可以防止Sql 注入，它会将所有传入的参数作为一个字符串来处理。

$ {} 则将传入的参数拼接到Sql上去执行，一般用于表名和字段名参数，$ 所对应的参数应该由服务器端提供，前端可以用参数进行选择，避免Sql 注入的风险。

**9、Lombok**

Lombok项目是一个Java库，它会自动插入编辑器和构建工具中，Lombok提供了一组有用的注释，用来消除Java类中的大量样板代码。仅五个字符(@Data)就可以替换数百行代码从而产生干净，简洁且易于维护的Java类。

使用步骤：

在IDEA中安装Lombok插件在项目中导入lombok的jar包

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps39.png) 

3.在程序上加注解

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps40.png) 

说明：

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps41.png) 

示例图：

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps42.png) 

**10、多对一处理**

多个学生一个老师；站在学生的角度，就是多个学生 **关联**一个老师；站在老师的角度，就是一个老师 **集合**了很多学生。

alter table student ADD CONSTRAINT fk_tid foreign key (tid) references teacher(id)

**1. 测试环境搭建**

导入lombok新建实体类Teacher,Student建立Mapper接口建立Mapper.xml文件在核心配置文件中绑定注册我们的Mapper接口或者文件 【方式很多，随心选】测试查询是否能够成功**2. 按照查询嵌套处理**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps43.png) 

**3.按照结果嵌套处理**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps44.png) 

回顾Mysql多对一查询方式:

子查询 （按照查询嵌套）联表查询 （按照结果嵌套）**11、一对多处理**

**1. 环境搭建**

**实体类**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps45.png) 

**2. 按照结果嵌套处理**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps46.png) 

**3.按照查询嵌套处理**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps47.png) 

**小结**

关联 - association 【多对一】

集合 - collection 【一对多】

javaType & ofType

JavaType用来指定实体类中的类型

ofType用来指定映射到List或者集合中的pojo类型，泛型中的约束类型

注意点：

保证SQL的可读性，尽量保证通俗易懂注意一对多和多对一，属性名和字段的问题如果问题不好排查错误，可以使用日志，建议使用Log4j面试高频问题

Mysql引擎InnoDB底层原理索引索引优化**12、动态SQL**

什么是动态SQL：动态SQL就是根据不同的条件生成不同的SQL语句

所谓的动态SQL，本质上还是SQL语句，只是我们可以在SQL层面，去执行一个逻辑代码

动态 SQL 是 MyBatis 的强大特性之一。如果你使用过 JDBC 或其它类似的框架，你应该能理解根据不同条件拼接 SQL 语句有多痛苦，例如拼接时要确保不能忘记添加必要的空格，还要注意去掉列表最后一个列名的逗号。利用动态 SQL，可以彻底摆脱这种痛苦。

if

choose (when, otherwise)

trim (where, set)

foreach

**搭建环境**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps48.png) 

创建一个基础工程

导包编写配置文件编写实体类

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps49.png) 

4.编写实体类对应Mapper接口和Mapper.xml文件

**IF**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps50.png) 

这条语句提供了可选的查找文本功能。如果不传入 “title”，那么所有处于 “ACTIVE” 状态的 BLOG 都会返回；如果传入了 “title” 参数，那么就会对 “title” 一列进行模糊查找并返回对应的 BLOG 结果（细心的读者可能会发现，“title” 的参数值需要包含查找掩码或通配符字符）。

测试代码：

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps51.png) 

**choose (when, otherwise)**

有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps52.png) 

**trim（where，set）**

where 元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。而且，若子句的开头为 “AND” 或 “OR”，where 元素也会将它们去除。

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps53.png) 

set 元素会动态地在行首插入 SET 关键字，并会删掉额外的逗号（这些逗号是在使用条件语句给列赋值时引入的）

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps54.png) 

所谓的动态SQL，本质还是SQL语句，只是我们可以在SQL层面执行一个逻辑代码！

**Foreach**

foreach 元素的功能非常强大，它允许你指定一个集合，声明可以在元素体内使用的集合项（item）和索引（index）变量。它也允许你指定开头与结尾的字符串以及集合项迭代之间的分隔符。这个元素也不会错误地添加多余的分隔符，看它多智能！

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps55.png) 

**SQL片段**

有的时候，我们可能会将一些功能的部分抽取出来，方便服用！

使用SQL标签抽取公共部分可

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps56.png) 

2.在需要使用的地方使用Include标签引用即可

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps57.png) 

注意事项：

最好基于单标来定义SQL片段不要存在where标签动态SQL就是在拼接SQL语句，我们只要保证SQL的正确性，按照SQL的格式，去排列组合就可以了

建议：

先在Mysql中写出完整的SQL，再对应的去修改成我们的动态SQL实现通用即可。

**13、缓存**

**13.1 简介**

查询 ： 需要连接数据库，耗资源

一次查询的结果，给他暂存一个可以直接取到的地方 --> 内存：那么这些数据就叫做 缓存

我们再次查询的相同数据的时候，直接走缓存，不走数据库了

什么是缓存[Cache]？存在内存中的临时数据。

将用户经常查询的数据放在缓存（内存）中，用户去查询数据就不用从磁盘上（关系型数据库文件）查询，从缓存中查询，从而提高查询效率，解决了高并发系统的性能问题。

为什么使用缓存？减少和数据库的交互次数，减少系统开销，提高系统效率

什么样的数据可以使用缓存？经常查询并且不经常改变的数据 【可以使用缓存】

**13.2 MyBatis缓存**

MyBatis包含一个非常强大的查询缓存特性，它可以非常方便的定制和配置缓存，缓存可以极大的提高查询效率。MyBatis系统中默认定义了两级缓存：一级缓存 和 二级缓存

默认情况下，只有一级缓存开启（SqlSession级别的缓存，也称为本地缓存）

二级缓存需要手动开启和配置，他是基于namespace级别的缓存。

为了提高可扩展性，MyBatis定义了缓存接口Cache。我们可以通过实现Cache接口来定义二级缓存。

**13.3 一级缓存**

一级缓存也叫本地缓存：SqlSession

与数据库同一次会话期间查询到的数据会放在本地缓存中

以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库

测试步骤：

开启日志测试在一个Session中查询两次记录

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps58.png) 

3.查看日志输出

说明缓存一样 ~~

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps59.png) 

**缓存失效的情况：**

查询不同的东西增删改操作，可能会改变原来的数据，所以必定会刷新缓存查询不同的Mapper.xml手动清理缓存sqlSession.clearCache();

**13.4 二级缓存**

二级缓存也叫全局缓存，一级缓存作用域太低了，所以诞生了二级缓存基于namespace级别的缓存，一个名称空间，对应一个二级缓存工作机制一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中

如果会话关闭了，这个会员对应的一级缓存就没了；但是我们想要的是，会话关闭了，一级缓存中的数据被保存到二级缓存中

新的会话查询信息，就可以从二级缓存中获取内容

不同的mapper查询出的数据会放在自己对应的缓存（map）中

一级缓存开启（SqlSession级别的缓存，也称为本地缓存）

二级缓存需要手动开启和配置，他是基于namespace级别的缓存。

为了提高可扩展性，MyBatis定义了缓存接口Cache。我们可以通过实现Cache接口来定义二级缓存。

步骤：

开启全局缓存

<!--显示的开启全局缓存-->

<setting name="cacheEnabled" value="true"/>

在Mapper.xml中使用二级缓存，可以自定义参数

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps60.png) 

测试

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps61.png) 

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps62.png) 

使用二级缓存后

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps63.png) 

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps64.png) 

注意：我们需要将实体类序列化，否则就会报错**小结：**

只要开启了二级缓存，在同一个Mapper下就有效所有的数据都会放在一级缓存中只有当前会话提交，或者关闭的时候，才会提交到二级缓存中**13.5 缓存原理**

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps65.png) 

**注意：**

只有查询才有缓存，根据数据是否需要缓存（修改是否频繁选择是否开启）useCache=“true”

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps66.png) 

**13.6 自定义缓存-ehcache**

Ehcache是一种广泛使用的开源Java分布式缓存。主要面向通用缓存

导包

![img](file:////private/var/folders/ff/x_g33zbx1h3d9ntbb1g2_c040000gn/T/com.kingsoft.wpsoffice.mac/wps-lixiaofeng/ksohtml//wps67.png) 

在mapper中指定使用我们的ehcache缓存实现

<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>

 

 