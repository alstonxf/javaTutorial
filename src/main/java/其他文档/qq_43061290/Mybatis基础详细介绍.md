# Mybatis基础详细介绍
1什么是框架 半成品软件,项目开发中的解决方法,不同的框架解决不同的问题 使用框架的好处就是封装了很多的细节,使得开发者可以使用既简单的方式来解决开发,大大的提高了开发的效率. 2.三层架构 1.表现层:展现数据 SpringMvc 2.业务层:业务需求 3.持久层:是和数据库进行交互 mybatis Spring 不属于任何一层但是无可替代

3.持久层的技术解决方案 JDBC技术: Connection PrepareStatment ResultSet SpringTemplate的JdbcTemplate

```
以上都不是框架
JDBC是框架
Spring JDBClate是工具类


```

Mybatis 1.概念:是一个优秀的基于java持久层的框架,他内部封装了jdbc,使开发者只需要关注sql语句本身,而不需要花费力气来处理加载驱动,创建连接等复杂的过程. 通过xml来配置好各种参数.采用ORM思想来解决实体和数据库的映射问题,对jdbc进行封装,屏蔽了jdbc api 底层的访问细节,使我们不必和api打交道就可以直接完成对数据库的持久化操作

2.ORM object relational mapping 简单来说:就是把数据库和实体类及实体类的属性对应起来 让我们可以操作实体类来实现操作数据库表

```
封装 
user      User
id       userId
user_name userName


```

3.mybatis的入门

## 1.环境搭建

1.创建maven工程并导入坐标 2.创建实体类和dao 的接口 3.创建mybatis的主配置文件:SqlMapConfig.xml 4.创建映射配置文件:UserDao.xml

## 2.环境搭建的注意事项

```
第一个：创建IUserDao.xml 和 IUserDao.java时名称是为了和我们之前的知识保持一致。
			在Mybatis中它把持久层的操作接口名称和映射文件也叫做：Mapper
			所以：IUserDao 和 IUserMapper是一样的
		第二个：在idea中创建目录的时候，它和包是不一样的
			包在创建时：com.itheima.dao它是三级结构
			目录在创建时：com.itheima.dao是一级目录
		第三个：mybatis的映射配置文件位置必须和dao接口的包结构相同
		第四个：映射配置文件的mapper标签namespace属性的取值必须是dao接口的全限定类名
		第五个：映射配置文件的操作配置（select），id属性的取值必须是dao接口的方法名

		当我们遵从了第三，四，五点之后，我们在开发中就无须再写dao的实现类。
	mybatis的入门案例
		第一步：读取配置文件
		第二步：创建SqlSessionFactory工厂
		第三步：创建SqlSession
		第四步：创建Dao接口的代理对象
		第五步：执行dao中的方法
		第六步：释放资源

		注意事项：
			不要忘记在映射配置中告知mybatis要封装到哪个实体类中
			配置的方式：指定实体类的全限定类名
		
		mybatis基于注解的入门案例：
			把IUserDao.xml移除，在dao接口的方法上使用@Select注解，并且指定SQL语句
			同时需要在SqlMapConfig.xml中的mapper配置时，使用class属性指定dao接口的全限定类名。
	明确：
		我们在实际开发中，都是越简便越好，所以都是采用不写dao实现类的方式。
		不管使用XML还是注解配置。
		但是Mybatis它是支持写dao实现类的。


```

```
	当我们遵从了345点后,我们就不需要去写dao 的实现类,只需要写接口
3.mybatis是支持写dao实现类的,但是原则上讲我们使用框架是为了简单方便所以一般来讲就不写dao实现类
光靠ID是没办法定义到sql语句的,我们应该利用namespace 属性加上ID来一起定义
3.自定义Mybatis的分析:
	mybatis在使用代理dao 的方式实现增删改查时做了什么事?
		1.创建代理对象
		2.在代理对象中使用selectList()
4.主配置文件的内容分析,mybatis原理
	1.DataSource里面
		<property>里面放置了连接数据库的信息
		用来创建connector相当于jdbc过程中的创建连接的对象
	2.mapper里面
		mapper里面resource或者class配置指定了接口的位置获取到sql语句,这里对应着prestatement调用执行语句过程.
	3.namespace 里面有个全类名
		这个用在后面查询到结果以后的封装对象,通过反射的技术可以得到类对象.Class.forName("全类名").newInstance(); 前面需要强转一下,因为得到的是object对象.每个实体类中的属性和表的属性是一样的因此可以把表的列明看作是实体类的属性的名称,就可以使用反射的方法根据属性的名称获取到每个属性并把值放进去.想让以上的方法执行,我们需要提供两个信息:1.连接信息,知道了上面的四个属性,我们可以构建连接池2.映射信息,他包含两个部分.第一:执行的SQL语句,第二封装结果的实体类的全类名.把这两个部分封装成一个对象Mapper.


```

可能有很多个所以我们需要用一个map类型来封装,key 就是String类型的内容为namespace 加上ID也就是方法.中间用点分割,值设置为得到的mapper对象Mpper里面包含两个信息,一个是String domainClassPath和String sql

工厂模式 把繁琐的细节封装起来,如果我在这里new了一个 工厂模式的优点就是解耦 构建者模式就是将创建的细节,使用者直接调用方法即可 代理模式就是不修改源码的基础上对已有的方法进行增强

```
1

```

## 注解入门

package xiaojun.cai.dao;

import org.apache.ibatis.annotations.Select; import xiaojun.cai.domain.User;

import java.util.List;

public interface UserDao {<!-- --> @Select(“select * from user”) List findAll(); } //注解配置 //在主配置文件里面配置时mapper class=“全类名

```

```java
package xiaojun.cai.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import xiaojun.cai.dao.UserDao;
import xiaojun.cai.domain.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {<!-- -->
    @Test
    public void test() throws IOException {<!-- -->
        //1.读取配置文件
        //InputStream in = Resources.getResourceAsStream("src/main/resources/SqlMapConfig.xml");
        InputStream in = new FileInputStream("src/main/resources/SqlMapConfig.xml");

        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        UserDao userDao = session.getMapper(UserDao.class);
        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();
        for(User user : users){<!-- -->
            System.out.println(user);
        }
        //6.释放资源
        session.close();
        in.close();
    }

}



```

```
	4.使用SqlSession创建Dao接口的代理对象
	UserDao userDao = session.getMapper(UserDao.class)
	public<T> getMapper(Class<I>daoInterdace){<!-- -->
		//类加载器和被代理对象使用相同的类加载器
		//代理对象要实现的接口和被代理对象实现的接口相同
		//如何代理:他就是增强的方法,这个需要我们自己来实现,此处就是一个invocation的接口,我们需要提供该接口的实现类实现类中调用selectList方法
		Proxy.newProxyInstance(类加载器,代理对象要实现的接口,如何代理).


```

## 自定义mybatis的编码

```
重新写通过入门案例可以看到的类
Class Resource 
Class SqlSessionFactoryBuilder
Interface SqlSessionFactory
Interface  SqlSession
一般来讲短的是接口长的就是实现类
删除mybatis的pom文件然后


```

## mybatis的增删改查

```
select last_insert_id();查询刚刚操作的ID值


```

## Mybatis 的参数

```
2.1.2简单类型
OGNL表达式:Object Graphic Navigation Language
对象图导航语言
通过对象中的取值方法来获取到数据,在写法上面把get省略了.
比如:在我们在获取用户的名称的时候
	在类中的方法是:user.getUsername()
	在OGNL表达式中的写法是user.username
mybatis中为什么可以直接写username而不用写user.呢,因为在paramterType中已经提供了属性所属的类,所以此时不需要写对象名


2.2对于数据库中的信息和java程序的类名字不匹配的问题解决办法
	1.取别名,这样就保证了类里面的成员变量一致性.
	2.如果不取别名,可以通过在映射文件中配置该方法
		<retultMap id = "userMap",type="com.demo.domain.User">
		.....
		.....
		.....
		</resultMap>
		
		这个执行操作需要执行第二次解析xml文件,效率比较低,但是他编写起来很方便.


```

```
可以通过自己编写Dao来实现功能,但是这样多可很多不必要的代码,一般来讲我们不采用这样的方法来实现.


```

## 自己编写DAO的原理

## 分析mybatis的映射文件的SQL 深入

```
查询条件的设定,有的时候有查询条件有的时候没,我们应该怎么来设定SQL呢
//查询的条件,可能有姓名可能有地址也可能有,使用<if>标签来解决这个问题,判断成立的时候加上if标签里面的内容到sql语句里面去.
<where>标签的使用 where标签相当于替换掉了1=1这个操作.单纯的加上where标签就可以了.
但是我们将学习到的内容是什么呢,是日以继夜的努力和每天奋斗的精神.


```

## 连接池

```
1.连接池:
	我们在实际开发的过程中都会使用连接池,因为这样可以减少连接的次数.
	连接池就是用于存储连接的容器,容器就是集合,该集合必须是线程安全的,不能两个线程拿到同一个连接
	该线程还是要实现队列的特性,先进先出.
2.mybatis中的连接池
	提供了三种方式的配置.
		配置的位置:
			主配置文件SQLMapConfig.xml中的DataSource标签,type属性就是表示采用何种连接池方式.
			type:POOLED,采用传统的javax.sql.DataSource规范中的连接池,mybatis中有针对规范实现
                UNPOOLED,采用传统的获取连接的方式,虽然也实现了javax.sql.DataSource接口,但是没有使用池的思想,每次使用都要获取.
                JNDI:采用服务器提供的JNDI技术实现,来获取到DataSource对象,不同的服务器拿到的是不一样的.如果不是web或者maven的war是不能使用的.
                我们课程中使用的是tomCat服务器,采用的连接池技术是dbcp
                
      


```

## 连接池的工作原理

```
     POOLED 和UNPOOLED 区别
     POOLED原理没有使用c3p0也没有用dbcp,而是使用javax.sql.DataSource接口实现了一套自己的规范.
     
     
     
     连接池从池子中获取到连接,结束之后将连接归还而不是关闭.不用连接池的话连接结束之后将连接关闭
     UNPOOLED观察源码发现每次都是注册驱动,返回connection对象.
     POOLED里面有同步代码快保证每一个连接拿到的是不同的连接.
     连接池里面使用集合来放置连接对象.有两个池子,一个空闲池,一个活动池
      protected final List<PooledConnection> idleConnections = new ArrayList<PooledConnection>();
      如果空闲的连接还有那么我们建立新的连接,将头部的第一个连接给删除掉
       conn = state.idleConnections.remove(0);
       否则如果空闲池没有空闲,就跳转到活动池.如果活动的连接小于设定的最大连接那么,我们可以建立一个新的连接.
       如果活动池子也满了,那么判断一下哪个是最早的连接,将最早的连接交给新来的,也就是说这个连接池没有拒绝策略.


```

源码

```
  private PooledConnection popConnection(String username, String password) throws SQLException {<!-- -->
    boolean countedWait = false;
    PooledConnection conn = null;
    long t = System.currentTimeMillis();
    int localBadConnectionCount = 0;

    while (conn == null) {<!-- -->
      synchronized (state) {<!-- -->
        if (!state.idleConnections.isEmpty()) {<!-- -->
          // Pool has available connection
          conn = state.idleConnections.remove(0);
          if (log.isDebugEnabled()) {<!-- -->
            log.debug("Checked out connection " + conn.getRealHashCode() + " from pool.");
          }
        } else {<!-- -->
          // Pool does not have available connection
          if (state.activeConnections.size() < poolMaximumActiveConnections) {<!-- -->
            // Can create new connection
            conn = new PooledConnection(dataSource.getConnection(), this);
            if (log.isDebugEnabled()) {<!-- -->
              log.debug("Created connection " + conn.getRealHashCode() + ".");
            }
          } else {<!-- -->
            // Cannot create new connection
            PooledConnection oldestActiveConnection = state.activeConnections.get(0);
            long longestCheckoutTime = oldestActiveConnection.getCheckoutTime();
            if (longestCheckoutTime > poolMaximumCheckoutTime) {<!-- -->
              // Can claim overdue connection
              state.claimedOverdueConnectionCount++;
              state.accumulatedCheckoutTimeOfOverdueConnections += longestCheckoutTime;
              state.accumulatedCheckoutTime += longestCheckoutTime;
              state.activeConnections.remove(oldestActiveConnection);
              if (!oldestActiveConnection.getRealConnection().getAutoCommit()) {<!-- -->
                try {<!-- -->
                  oldestActiveConnection.getRealConnection().rollback();
                } catch (SQLException e) {<!-- -->
                  /*
                     Just log a message for debug and continue to execute the following
                     statement like nothing happend.
                     Wrap the bad connection with a new PooledConnection, this will help
                     to not intterupt current executing thread and give current thread a
                     chance to join the next competion for another valid/good database
                     connection. At the end of this loop, bad {@link @conn} will be set as null.
                   */
                  log.debug("Bad connection. Could not roll back");
                }  
              }
              conn = new PooledConnection(oldestActiveConnection.getRealConnection(), this);
              conn.setCreatedTimestamp(oldestActiveConnection.getCreatedTimestamp());
              conn.setLastUsedTimestamp(oldestActiveConnection.getLastUsedTimestamp());
              oldestActiveConnection.invalidate();
              if (log.isDebugEnabled()) {<!-- -->
                log.debug("Claimed overdue connection " + conn.getRealHashCode() + ".");
              }
            } else {<!-- -->
              // Must wait
              try {<!-- -->
                if (!countedWait) {<!-- -->
                  state.hadToWaitCount++;
                  countedWait = true;
                }
                if (log.isDebugEnabled()) {<!-- -->
                  log.debug("Waiting as long as " + poolTimeToWait + " milliseconds for connection.");
                }
                long wt = System.currentTimeMillis();
                state.wait(poolTimeToWait);
                state.accumulatedWaitTime += System.currentTimeMillis() - wt;
              } catch (InterruptedException e) {<!-- -->
                break;
              }
            }
          }
        }
        if (conn != null) {<!-- -->
          // ping to server and check the connection is valid or not
          if (conn.isValid()) {<!-- -->
            if (!conn.getRealConnection().getAutoCommit()) {<!-- -->
              conn.getRealConnection().rollback();
            }
            conn.setConnectionTypeCode(assembleConnectionTypeCode(dataSource.getUrl(), username, password));
            conn.setCheckoutTimestamp(System.currentTimeMillis());
            conn.setLastUsedTimestamp(System.currentTimeMillis());
            state.activeConnections.add(conn);
            state.requestCount++;
            state.accumulatedRequestTime += System.currentTimeMillis() - t;
          } else {<!-- -->
            if (log.isDebugEnabled()) {<!-- -->
              log.debug("A bad connection (" + conn.getRealHashCode() + ") was returned from the pool, getting another connection.");
            }
            state.badConnectionCount++;
            localBadConnectionCount++;
            conn = null;
            if (localBadConnectionCount > (poolMaximumIdleConnections + poolMaximumLocalBadConnectionTolerance)) {<!-- -->
              if (log.isDebugEnabled()) {<!-- -->
                log.debug("PooledDataSource: Could not get a good connection to the database.");
              }
              throw new SQLException("PooledDataSource: Could not get a good connection to the database.");
            }
          }
        }
      }

    }

    if (conn == null) {<!-- -->
      if (log.isDebugEnabled()) {<!-- -->
        log.debug("PooledDataSource: Unknown severe error condition.  The connection pool returned a null connection.");
      }
      throw new SQLException("PooledDataSource: Unknown severe error condition.  The connection pool returned a null connection.");
    }

    return conn;
  }

```

## 事务

```
1.什么是事务
	事务的四大特性ACID
	不考虑隔离性会产生的三个问题
	解决办法,四种隔离级别
	
	事务是通过SqlSession对象的commit来提交,通过rollback方法来实现回滚
	SqlSession  session = factory.openSession(boolean autoCommit)如果在这里将其设置为true的话我们将获得到自动的事务提交.


```

## select * from user where id in list

```
forEach 标签
<forEach collection="ids" open="and id in(" close=")" item="id" separtor=",">
#{<!-- -->id}
</forEach>
collection是需要遍历的集合.open表示开始,close标水结束,item表示插入到中间的元素,separtor 表示分隔符号.
#里面的取值决定于item的值.


```

## mybatis的多表查询

```
1.表的几种机制:
	1.一对多 一个用户定义多个订单
	2.多对一
	3.一对一 身份证号和人
	4.多对多 一个学生可以被多个老师教过,一个老师可以交多个学生
	
特例:如果拿出每一个订单他都只能属于同一个用户.所以mybatis就把多对一看成了一对一.这个是什么意思.

2.mybatis中的多表查询:
	实例:用户和账户
		一个用户可以有多个账户
		一个账户只能属于同一个用户(多个账户也可以使用同一个账户)
	步骤:
		1.先建立两张表
			让用户表 和账户表之间具备一对多的关系,需要使用外键在账户表中添加.
		2.建立两个实体类,用户实体类和账户实体类.
			让用户实体类和账户实体类可以提现一对多的关系.
			在其中的一个表中添加另一个对象的引用.
			从表实体中应该包含一个主表实体的对象应用.
		3.建立两个配置文件
			用户配置文件和账户的配置文件
		4.实现配置
			当我们查询用户时,可以同时得到用户信息下面所包含的账户信息
			当我们查询账户时,可以同时得到账户所属用户信息.
			
		5.sql中的语句应该怎么写呢
			SELECT U.*,a.id AS aid,a.`MONEY`,a.`UID` FROM USER u,account a WHERE a.`UID`=u.id;
		6.在mybatis中怎么实现:
			1.可以写一个AccountUse类,将两个类的字段全都写到对象里面去,然后通过上面的查询的语句查询之后封装到该组合好的对象里面去.但是这个方法是不常用的.
			2.在其中的一个表中添加另一个对象的引用.从表实体中应该包含一个主表实体的对象应用.在Account中添加User的引用.
	


```

## 一对一查询

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.itheima.dao.IAccountDao">

    <!-- 定义封装account和user的resultMap -->
    <resultMap id="accountUserMap" type="account">
        <id property="id" column="aid"></id>
        <result property="uid" column="uid"></result>
        <result property="money" column="money"></result>
        <!-- 一对一的关系映射：配置封装user的内容-->
        <association property="user" column="uid" javaType="user">
            <id property="id" column="id"></id>
            <result column="username" property="username"></result>
            <result column="address" property="address"></result>
            <result column="sex" property="sex"></result>
            <result column="birthday" property="birthday"></result>
        </association>
    </resultMap>

    <!-- 查询所有 -->
    <select id="findAll" resultMap="accountUserMap">
        select u.*,a.id as aid,a.uid,a.money from account a , user u where u.id = a.uid;
    </select>

    <!--查询所有账户同时包含用户名和地址信息-->
    <select id="findAllAccount" resultType="accountuser">
        select a.*,u.username,u.address from account a , user u where u.id = a.uid;
    </select>

</mapper>

```

IAccountDao接口

```
package com.itheima.dao;

import com.itheima.domain.Account;
import com.itheima.domain.AccountUser;

import java.util.List;

public interface IAccountDao {<!-- -->
/**
*@Description查询所有的同时要获取到当前账户的所属用户信息,这里应该是一对一,因为一个账户只对应一个用户.
*@Author caixiaocai
*@Date 2020/3/12
*/
    List<Account> findAll();

    /**
     * 查询所有的用户,并且带有用户名和地址信息
     */
    List<AccountUser> findAllAccount();
}


```

## 一对多查询

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.itheima.dao.IUserDao">

    <!-- 配置 查询结果的列名和实体类的属性名的对应关系 -->
<resultMap id="userAccountMap" type="user">
    <id property="id" column="id"></id>
    <result property="username" column="username"></result>
    <result property="sex"  column="sex"></result>
    <result property="birthday" column="birthday"></result>

<!--    配置user对象中accounts集合的映射-->
    <collection property="accounts" ofType="account">
        <id property="id" column="aid"></id>
        <result property="uid" column="uid"></result>
        <result property="money" column="money"></result>

    </collection>
</resultMap>


    <!-- 查询所有 -->
    <select id="findAll" resultMap="userAccountMap" >
      SELECT * FROM USER u LEFT OUTER JOIN account a ON u.`id`= a.`UID`
    </select>

    <!-- 根据id查询用户 -->
    <select id="findById" parameterType="INT" resultType="user">
        select * from user where id = #{<!-- -->uid}
    </select>


</mapper>

```

```
package com.itheima.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public class User implements Serializable {<!-- -->

    private Integer id;
    private String username;
    private String address;
    private String sex;
    private Date birthday;

    //一对多的关系映射,主表实体应该包含从表实体的集合引用
    private List<Account> accounts;

    public List<Account> getAccounts() {<!-- -->
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {<!-- -->
        this.accounts = accounts;
    }

    @Override
    public String toString() {<!-- -->
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    public Integer getId() {<!-- -->
        return id;
    }

    public void setId(Integer id) {<!-- -->
        this.id = id;
    }

    public String getUsername() {<!-- -->
        return username;
    }

    public void setUsername(String username) {<!-- -->
        this.username = username;
    }

    public String getAddress() {<!-- -->
        return address;
    }

    public void setAddress(String address) {<!-- -->
        this.address = address;
    }

    public String getSex() {<!-- -->
        return sex;
    }

    public void setSex(String sex) {<!-- -->
        this.sex = sex;
    }

    public Date getBirthday() {<!-- -->
        return birthday;
    }

    public void setBirthday(Date birthday) {<!-- -->
        this.birthday = birthday;
    }
}



```

## 多对多查询

```
实例:用户和角色
	每个人有多个角色,每个角色也可以对应多个人.
什么是主键和外键:
	主键就是当一张表中唯一的不可以重复的列,可以是一列或者几列
	外键:一个字段同时存在于a表和b表中但是他是a表的主键,我们说这个字段是b表的外键.
步骤:
	1.建立两张表,用户表,角色表
		让用户和角色具有多对多的关系,需要使用中间表,中间表包含各自的主键..在中间表中是外键.
	2.建立两个实体
		用户实体和角色实体可以提现多对多的问题.各自包含一个对方的集合引用.
	3.建立两个配置文件
		用户的配置文件
		角色的配置文件
	4.实现配置
		当我们查用户时,可以得到用户信息下面包含的角色信息
		当我们查询角色的时候,可以得到角色的所赋予的用户信息.
			就是只有获取角色然后顺便获取到用户的信息,如果没有角色的信息是不会被查询到的.


```

## 通过查找角色,并且查找到角色对应的信息

```
三张表user , role ,user_role

先通过查找role然后查中间表,然后在用查到的结果来查user表得到信息

select * from role r left outer join user_role ur on r.id=ur.rid;
这里表明查找角色的所以数据并且查出来中间表rid和r.id相等的值.
然后通过这个结果来继续查询出啦用户表的信息
select * from role r left outer join user_role ur on r.id=ur.rid left outer join user u on u.id = ur.uid;
都是通过左外连接相当于如果左边的信息含有右边的信息则追加,否则的话就不添加.
上面还存在一个问题就是有两个表的字段是重名的,我们通过一个表的取别名来解决
select u.*,r.id as rid,r.role_name,r.role_desc......后面都是一样的操作.



```

## 通过角色查找xml配置

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IRoleDao">
<!--    查询所以-->
<!--    定义roleMap-->
    <resultMap id="roleMap" type="role">
        <id property="roleId" column="rid"></id>
        <result property="roleName" column="role_name"></result>
        <result property="roleDesc" column="role_desc"></result>
        <collection property="users" ofType="user">
            <id property="id" column="id"></id>
            <result property="username" column="username"></result>
            <result property="sex" column="sex"></result>
            <result property="address" column="address"></result>
            <result property="birthday" column="birthday"></result>
        </collection>

<!--        配置一对多映射-->


    </resultMap>
    <select id="findAll"    resultMap="roleMap">
        SELECT U.*,r.`ID` AS rid,r.`ROLE_NAME`,r.`ROLE_DESC` FROM
        role r LEFT OUTER JOIN user_role ur ON
        r.id=ur.rid LEFT OUTER JOIN USER u ON ur.`UID`= u.`id`
    </select>
<!--    这-->

</mapper>

```

## 通过查找user表来获取到他的角色

```
其实就是个前面的思路上面先查找user然后左外连接中间表,然后查询到的结果再来查询role表这样子就得到了结果里面的值.
SELECT * FROM USER u LEFT OUTER JOIN user_role ur ON u.`id`=ur.uid LEFT OUTER JOIN role r ON ur.`RID`=r.`ID`
sql语句就是上面的这个.


```

## 通过用户查找

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.itheima.dao.IUserDao">

    <!-- 配置 查询结果的列名和实体类的属性名的对应关系 -->
<resultMap id="userMap" type="user">
    <id property="id" column="id"></id>
    <result property="username" column="username"></result>
    <result property="sex"  column="sex"></result>
    <result property="birthday" column="birthday"></result>

    <collection property="roles" ofType="Role">
        <id property="roleId" column="rid"></id>
        <result property="roleDesc" column="role_desc"></result>
        <result property="roleName" column="role_name"></result>
    </collection>

<!--    配置user对象中accounts集合的映射-->

</resultMap>


    <!-- 查询所有 -->
    <select id="findAll" resultMap="userMap" >
         SELECT u.*,r.id AS rid,r.`ROLE_DESC`,r.`ROLE_NAME`FROM USER u LEFT OUTER JOIN
          user_role ur ON u.`id`=ur.uid LEFT OUTER JOIN role r ON ur.`RID`=r.`ID`

    </select>

    <!-- 根据id查询用户 -->
    <select id="findById" parameterType="INT" resultType="user">
        select * from user where id = #{<!-- -->uid}
    </select>


</mapper>

```

## JNDI 概述和原理

```
1.概述:
	java naming and directory interface java命名和目录接口,是标准的java命名系统接口
	模仿Windows的注册表,注册表是map结构,有key和value的值,jndi的value是对象.
	
	当tomCat服务器启动时也为我们提供一个map集合,key是字符串,value是object对象.key里面directory是规定的,name是指定的,value里面对象类型是通过配置文件的方式来指定的


```

## Mybatis 的延迟加载

```
1.问题:在一对多中,当我们有一个用户,他有100多个账户.
	在查询用户的时候,要不要帮他把账户都查出来.
	在查询账户时,需不需要把用户也查出来.
	在查询用户时,用户下的账户信息应该是什么时候什么时候使用查询.否则会给内存带来极大的压力
	在查询账户时,应该将用户查出来.

延迟加载:就是上面的使用数据才发起查询的,叫做按需加载,也叫作懒在家
立即加载:不管用不用,只要调用方法立马就加载

一对多,多对多:延迟加载,符合逻辑,减少内存空间的使用.
多对一,一对一:立即加载



```

## 按需加载

```
1.完成配置
	在主配置文件里面
	    <!--    配置参数-->
    <settings>
        <!--        开启mybatis全局加载的开关-->
        <setting name="lazyLoadingEnabled" value="true"/>
        <!--        当开启的时候会立即加载,否则按需加载-->
        <setting name="aggressiveLazyLoading" value="false"/>
    </settings>
    
    在Mapping映射文件中修改语句
     <association property="user" column="uid" javaType="user" select="com.itheima.dao.IUserDao.findById"></association>
     其中的column不可以省略他代表着findById的参数,这个select属性其实是调用了另一个方法查询.



```

## 缓存

```
1.什么是缓存
	存在于内存中的临时数据
2.为什么使用缓存
	减少和数据库的交互次数,提高执行效率
3.什么情况下使用缓存
	经常查询并且不经常改变
	数据的正确与否对最终的结果影响不大.
4.不适用于缓存的数据
	经常改变的数据
	数据的正确对于最终的结果影响很大的.例如银行的汇率,股市的牌价



```

## mybatis中的缓存

```
1.一级缓存
	指的是mybatis中SQLSession对象的缓存.当我们执行了查询之后,查询的结果会同时存到SQLSession为我们提供的一块区域中,该区域的结构是一个map集合当我们再次查询同样的数据,mybatis会先从SQLSession中查看是否有,如果有的话就同时拿出来使用
	当SQLSession消失时,一级缓存就消失了接下来
	mybatis默认是直接有一级缓存的,调用SqlSession.close之后缓存就消失了.
	SQLSession.clearCache 也会清除掉缓存.
2.更新了信息之后,缓存是如何进行更新,保证信息的正确性的呢.
	当Session调用了修改,添加,删除,commit,close等方法时会清空一级缓存.保证数据的安全性
	
3.二级缓存
	SQLSessionFactory的缓存,由同一个SQLSessionFactory对象创建的SQLSession共享其缓存.
	二级缓存的使用步骤:
		1.让mybatis框架支持二级缓存(在SqlMapConfig.xml中配置
			<settings>
				<setting name="cacheEnabled" value="true"/>
			</settings>
		2.让当前的映射文件支持二级缓存(在IuserDao.xml文件中配置
			<cache/>添加在文件里面就可以了
		3.让当前的操作支持二级缓存.(在select标签中配置.
			<select id="findAll" useCache="true">
				.....
			</select>
			
			然后就支持二级缓存了.
4.二级缓存存放的是数据不是对象.
	存的是散装数据.类似于json数据,键值对的.,,当发起查询时,二级缓存会创建一个新的对象将本来的这些散装的数据封装为新的对象,因此,前后两次查询到的对象的地址值不是同一个.


```

## mybatis的注解开发

```
1.使用Results注解来解决字段和数据库不对应的问题
	@Results(id="map" value={<!-- -->@Result(id="flase",column=..))} id boolean 值表示是否为主键.
	后面需要使用这个的时候直接使用Results(value={<!-- -->'map'})



```

```
    <!-- 指定带有注解的dao接口所在位置 -->
    <!-- 配置 dao 接口的位置，它有两种方式
      第一种：使用 mapper 标签配置 class 属性
        <mapper class="com.itheima.dao.IUserDao"></mapper>
         <mapper class="com.itheima.dao.IAccountDao"></mapper>

      第二种：使用 package 标签，直接指定 mapper 接口所在的包-->
<!--    <package name="com.itheima.dao"></package>-->

```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/105740320