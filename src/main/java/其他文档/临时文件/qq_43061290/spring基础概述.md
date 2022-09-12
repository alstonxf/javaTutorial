# spring基础概述
```
1.Spring概述
	Spring是分层的java SE/EE应用 full-stack 轻量级开源框架,以IOC(Inverse of control)翻转控制和AOP(Aspect Oriented Programming:面向切面编程)为内核.
2.Spring两大核心
    IOC AOP

3.Spring发展历程和优势
	1.方便解耦便于来发
	2.AOP编程的支持
	3.声明式事务的支持
	5.方便程序的测试结合Junit
	6.方便集成优秀的框架
	7.降低对javaEEapi的使用难度 JDBC JAVAMAIL,
	8.源码的经典学习范例.消耗太多的时间.
	 
4.Spring体系结构
	core container 核心容器 所有的功能都需要这个的支持.
	data access 持久层支持

5.程序的解耦
	jdbc操作
		1.注册驱动
			DriverManger.registerDriver(new com.mysql.jdbc.Driver);
		2.获取连接
			 Connection connection =DriverManger.getConnection("jdbc:mysql://localhost:3306/eesy","root","root");
		3.获取数据库操作的预处理对象
			PrepareStatement pstm = conn.prepareStatement("sql");
		4.执行sql,获取结果集
		ResultSet rs = pstm.executeQuery();
		5.变了结果集合
			while(rs.next()){<!-- -->
			sout(rs.getString("name"))
			}
		6.释放资源
		re.close
		pstm.close
		conn.close
	
	如果没有驱动包那么我们这个程序会在编译器报错,这个就是耦合
	程序间的耦合:
		其实就是程序间的依赖关系
            1.类之间的依赖
            2.方法之间的依赖
     解耦:
     	降低程序间的依赖关系
     	实际开发中应该做到编译器不依赖,运行期才依赖
    解决办法,我们导入驱动的时候使用反射的技术.class.forName("com.mysql.jbdc.driver")
这样子的话编译器是不会报错的,就算没有驱动报的错也是运行期的FileNotFoundException

解耦的思路:
	1.使用反射来创建对象,而避免new关键字
	2.因为这样子把程序写死了,通过读取配置文件来获取要创建的对象的全限定类名
问题:
    三层架构:
        持久层
        表现层
        业务层
        他们之间的调用都是通过new来实现的,因此耦合性很差,如果删除掉其中的一个文件,我们在的程序就会不通过,并且这个问题是编译器异常,因为类直接就new不出来的.我们在实际的开发过程中应该解决这个问题.其实在编译器间报错对于目前来说可能是更容易判断错误一些.
       如何解决上面的依赖问题呢:
       	 bean在计算机英语中有可重用组件的意思.
       	 JavaBean:用java语言编写的可重用组件.
 解决三层架构耦合度多高的方法:	
 	通过读取配置文件的方法来反射创建出对象.
 	1.需要一个配置文件来配置我们的service和dao配置的内容是唯一标识=全限定类名(key=value
 	2.读取配置文件,反射创建对象.
 配置文件可以是properties和xml文件,我们可以使用破刃properties文件来作为配置文件
 	配置文件内容:
 		accountService=com.demo.service.impl.AccountServiceimpl
 		accountDao=com.demo.dao.impl.AccountDaoImpl
 	工厂类的代码
 	public class BeanFactory{<!-- -->
 		private  static Properties props;
 		//定义一个容器来存放创建的对象来解决多例问题
 		private static Map<String,Object> beans;
 		//使用静态代码块来为properties赋值
 		static{<!-- -->
 			props = new Properties();
 			//获取流对象
 			InputStream in = BeanFacroty.class.getClsaaLoder().getResourceStream("bean.properties");
 			props.load(in);//然后就可以读取到我们的配置文件的内容了.
 			//实例化容器
 				beans = new HashMap<String,Object>();
 			//取出配置文件中的key
 			Enumeration<String> keys = props.key();
 			//遍历枚举
 			while(keys.hasMoreElements){<!-- -->
 				String key = keys.nextElement().toString();
 				String path = props.getPropertu(key);
 				Object value = Class.forName(path).newInstance();
 				beans.put(key,value);
 			}
 		}
 		public static Object getBean(String beanName){<!-- -->
 			//根据bean的名称获取bean对象
 			String beanPath = props.getProerty(beanName);
 			bean = Class.forName(beanPath).newInstance();
 			//中间有异常可能要抛出.
 			return bean;
 			---------------------------------------
 			直接return beans.get(beanName);
 		}
 	}
对于上面的解耦的问题
	单例:始终只有一个,对象只有一个,对象的地址值都是一个,类的成员只有一个.效率较高但是会遇到安全问题.
	多例对象:没有线程安全问题,但是效率很低.
	
在service和dao中都没有定义成员变量或者改变成员变量因此我们不需要使用多例对象来保证线程安全.
bean= Class.forName().newInstance()//每次都会调用默认构造函数创建新的对象.
如果newInstance创建的对象每次都不存的话就会被垃圾回收机制清理掉,因此我们可以把他存在集合里面实现了单例
静态代码块每次都执行一次.


6.IOC的概念和Spring中的IOC 
	Inverse of control 
	new 和 forname的区别.
	程序本身本来是可以控制需要调用的是谁,但是主动将控制权交给了工厂或者框架,并且在编译器间不在确定是否得到了正确的资源,成了被动的地方,因此这种控制权反转就叫做控制反转.
	控制反转的作用就是削减计算机程序的耦合,降低代码之间的依赖关系注意我们是没办法来消除耦合的.

	1.搭建Spring基于xml的开发环境
	入门案例:
		    public static void main(String[] args) {<!-- -->
    //1.获取到核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2.根据id获取到bean对象
        IAccountService as = (IAccountService) ac.getBean("accountService");
        IAccountDao adao = ac.getBean("accountDao",IAccountDao.class);
        //as.saveAccount();
        System.out.println(as);
        System.out.println(adao);


7.依赖注入dependency injection DI


```

## ApplicationContext 的实现类

```
1.ClassPathXmlApplicationContext:加载类路径下的配置文件,要求配置文件必须在类路径下面,这个使用的比较多.
2.FileSystemXmlApplicationContext:他可以加载磁盘任意路径的
3.AnnotationConfigApplicationContext:他是读取注解创建.


```

## ApplicationContext 和BeanFactory

```
APPlicationContext:采用立即加载的策略,只要一读取立即加载对象.单例对象实际上使用这个更多.
BeanFactory:采用延迟加载的方法,需要的时候再创建方法.多例对象. 


```

## 三种创建Bean对象的方式.

```
Spring对bean的管理细节
1.使用默认构造函数创建
	在Spring的配置文件中使用Bean标签,配以id和class属性之后,且没有其他属性和标签时,采用的就是默认构造函数创建bean对象,此时如果类中没有默认的构造函数,无法创建.
	
2.使用普通工厂的方法创建对象(使用某个类中的方法创建对象,并存入Spring容器中.
	配置文件里面写的内容
	<bean id="instanceFactory" class="com.itheima.factory.InstanceFactory"></bean>
	<bean id ="accountService" factory-bean="instanceFactory" factory-method="getAccountService"></bean>
	
	
3.使用工厂中的静态方法创建对象并放入容器中
	<bean id = "accountService" class="com.itheima.factory.StaticFactory" factory-method="getAccountService"></bean>


```

## Bean 的作用范围

```
bean对象默认是单例的.
bean标签的scope属性.:作用是用来指定bean的作用范围.
取值:
	singleton:单例也是默认值
	prototype:多例
	request:作用于web应用的请求范围
	session:作用于web应用的会话范围
	global-session:全局会划范围,集群会话范围,如果不是集群环境就是session.集群之间的服务器负载均衡


```

## spring中bean对象的生命周期

```
应该是在需要类的时候创建,创建完所需要的类之后就killed
单例对象:
	出生:容器创建时出生
	活着:容器还在他就存在
	死亡:容器消失,他就消失
	<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl" scope="sington" init-method="init" destory-method="destory"></bean>
多例对象:
	出生:使用时Spring创建的
	活着:在使用的过程中一直活着
	死亡:当对象长时间不用且没有别的对象引用时,java的垃圾回收机制进行回收.


```

## 依赖注入

```
什么是依赖注入dependency injection
IOC的作用是降低程序间的依赖关系(耦合),程序之间必然会有一些依赖.依赖关系交由Spring来维护.在当前类中需要用到其他类的对象由Spring为我们提供我们只需要在配置文件中说明依赖关系.我们称之为依赖注入.
1.可以注入的数据类型有三类
    1.基本类型和String
    2.其他类型的Bean(在配置文件中或者注解配置过的bean
    3.复杂类型(集合类型)
2.注入的方式:
	第一种:使用构造函数提供
		如果经常变化的数据,不适合注入的方式.
		构造函数就是初始化函数,new方法的时候我们会先调用构造函数
		需要在配置文件中<bean>标签的内部增加标签constructor-arg>
			标签有几个睡醒
				1.type:用于指定要注入的数据类型,该数据类型也是构造函数中某个或者某些参数的类型
				2.index:用于指定要注入的数据给构造函数中的指定索引位置的参数赋值.参数的索引从0开始
				3.name::指定给构造函数中的指定名字的参数赋值o(╥﹏╥)o,这个用的最多
				4.value:注入的值如果是日期应该要怎么我们应该先配置一个bean对象然后用id来引用这个对象.ref=
				5.ref:value 是不可以引用的我们用ref属性来实现引用.bean类型值得就是Spring核心容器中出现过的对象.如果没有引用就不可以引用.
		优点:
        	在获取对象时,注入数据是必须的,否则对象无法创建成功
        缺点:
        	改变了bean对象的实例化方式,使我们在创建对象时,如果用不到这些数据也必须提供.
        	
   <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
        <constructor-arg name="name" value="泰斯特"></constructor-arg>
        <constructor-arg name="age" value="18"></constructor-arg>
        <constructor-arg name="birthday" ref="now"></constructor-arg>
    </bean>
		
 <!-- 配置一个日期对象 -->
    <bean id="now" class="java.util.Date"></bean>
    
	第二种:使用set方法提供 更加常用的是set方法注入
	
		设计到的标签:property
		出现的位置:bean标签的内部
		标签的属性:
			name:用于指定set方法后面的名字比如setUsernme 那么这个属性对应的值应该是name
			value:值
			ref:引用,同上
		优点:
			创建对象时没明确的限制,可以直接使用默认的构造函数
		缺点n:
		如果某个成员必须要赋值的话,set注入不能够保证一定会注入.
		
	复杂类型的注入:数组 list set集合 map集合 properties
	Arrays.toString()这样子才可以将数组里面的内容打印出来.
		<array>  <list> <map> 里面用value标签赋值,一个标签对应一个元素.
		单列结合的标签:list array set
		map集合properties :map ,properties
		结构相同可以互换. 


 <bean id="accountService2" class="com.itheima.service.impl.AccountServiceImpl2">
        <property name="name" value="TEST" ></property>
        <property name="age" value="21"></property>
        <property name="birthday" ref="now"></property>
  </bean>


	第三种:使用注解提供
	
总结:我们的代码应该要解决依赖问题,SpringIOC就觉得是依赖的问题,然后通过xml文件来实现IOC,还有通过标签来配置注解问题.



```

## 常用注解按照作用的分类

```
注解配置和xml配置的功能都是一样的.
1.用于创建对象的
2.用于注入对象的
3.用于改变作用范围的
4.和生命周期相关的.

* 曾经XML的配置：
 *  <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"
 *        scope=""  init-method="" destroy-method="">
 *      <property name=""  value="" | ref=""></property>
 *  </bean>
 *
 * 1.用于创建对象的
 *      他们的作用就和在XML配置文件中编写一个<bean>标签实现的功能是一样的他们的作用
 *      @Component:
 *          作用：用于把当前类对象存入spring容器中
 *          属性：
 *              value：用于指定bean的id。当我们不写时，它的默认值是当前类名，且首字母改小写。
 *      @Controller：一般用在表现层
 			作用和上面的内容一模一样的,为我们提供三层架构使用的注解
 *      @Service：一般用在业务层
 *      @Repository：一般用在持久层
 *      以上三个注解他们的作用和属性与Component是一模一样。
 *      他们三个是spring框架为我们提供明确的三层使用的注解，使我们的三层对象更加清晰
 *
 *
 * 2.用于注入数据的
 *      他们的作用就和在xml配置文件中的bean标签中写一个<property>标签的作用是一样的
 *      @Autowired:
 *          作用：自动按照类型注入。只要容器中有唯一的一个bean对象类型和要注入的变量类型匹配，就可以注入成功
 *                如果ioc容器中没有任何bean的类型和要注入的变量类型匹配，则报错。
 *                如果Ioc容器中有多个类型匹配时：
 如果出现了两个,那么先按照类型来查找如果发现有不止一个那么继续按照变量名来查找.如果有一样的就成功,没有就报错.
 *          出现位置：
 *              可以是变量上，也可以是方法上
 *          细节：
 *              在使用注解注入时，set方法就不是必须的了。
 *      @Qualifier:
 *          作用：在按照类中注入的基础之上再按照名称注入。它在给类成员注入时不能单独使用。但是在给方法参数注入时可以（稍后我们讲）
 *          属性：
 *              value：用于指定注入bean的id。
 *      @Resource
 *          作用：直接按照bean的id注入。它可以独立使用
 *          属性：
 *              name：用于指定bean的id。
 *      以上三个注入都只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现。
 *      另外，集合类型的注入只能通过XML来实现。
 *
 *      @Value
 *          作用：用于注入基本类型和String类型的数据
 *          属性：
 *              value：用于指定数据的值。它可以使用spring中SpEL(也就是spring的el表达式）
 *                      SpEL的写法：${<!-- -->表达式}jsp的表达式.看写在什么位置上代表是什么的el表达式.
 *
 * 3.用于改变作用范围的
 *      他们的作用就和在bean标签中使用scope属性实现的功能是一样的
 *      Scope
 *          作用：用于指定bean的作用范围
 *          属性：默认的就是singleton.
 			这个值在@service等注解下面使用
 *              value：指定范围的取值。常用取值：singleton prototype
 *
 * 4.和生命周期相关 了解
 *      他们的作用就和在bean标签中使用init-method和destroy-methode的作用是一样的
 *      @PreDestroy
 *          作用：用于指定销毁方法
 *      @PostConstruct
 *          作用：用于指定初始化方法
 
 正式瓜皮我们都是在社会主义的制度下进行合理的资本主义经营


```

## 使用注解时更改约束条件，添加扫描的包

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--告知spring在创建容器时要扫描的包，配置所需要的标签不是在beans的约束中，而是一个名称为
    context名称空间和约束中-->
    <context:component-scan base-package="com.itheima"></context:component-scan>
</beans>

```

## xml IOC案例

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">




    <!--配置service对象-->
    <bean id="accountService" class="com.demo.service.impl.AccountServiceImpl">
        <!--    注入dao对象-->
        <property name="accountDao" ref="accountDao"></property>
    </bean>

    <bean id="accountDao" class="com.demo.dao.impl.AccountDaoImpl">
        <property name="runner" ref="runner"></property>
    </bean>
    <!-- -多例对象的配置-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
<!--        注入数据源-->
        <constructor-arg name="ds" ref="dataSource"></constructor-arg>
    </bean>
<!--    配置数据源-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
<!--        连接数据库的必备信息-->
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl"    value="jdbc:mysql://localhost:3306/essy"></property>
        <property name="user" value="root"></property>
        <property name="password"  value="root"></property>
    </bean>
<!--    -->
</beans>




```

```
//测试代码

public class AccountServiceTest {<!-- -->
    @Test
    public void testFindAll(){<!-- -->
        //1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //得到业务层的对象
        AccountService accountService = ac.getBean("accountService", AccountService.class);
        List<Account> allAccount = accountService.findAllAccount();
        for (Account account : allAccount) {<!-- -->
            System.out.println(account);

        }

    }

```

```
package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.service.IAccountService;

import java.util.List;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService{<!-- -->

    private IAccountDao accountDao;

    public void setAccountDao(IAccountDao accountDao) {<!-- -->
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAllAccount() {<!-- -->
        return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Integer accountId) {<!-- -->
        return accountDao.findAccountById(accountId);
    }

    @Override
    public void saveAccount(Account account) {<!-- -->
        accountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {<!-- -->
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Integer acccountId) {<!-- -->
        accountDao.deleteAccount(acccountId);
    }
}



```

```
package com.demo.dao.impl;

import com.demo.dao.AccountDao;
import com.demo.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 账户的持久层实现类
 */

public class AccountDaoImpl implements AccountDao {<!-- -->
    private QueryRunner runner;

    public void setRunner(QueryRunner runner) {<!-- -->
        this.runner = runner;
    }

    @Override
    public List<Account> findAllAccount() {<!-- -->
        List<Account> query = null;
        try {<!-- -->
            query = runner.query("select * from account", new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {<!-- -->
            System.out.println("没查询到数据");
        }
        return query;
    }

    @Override
    public Account findAccountById(Integer id) {<!-- -->
        Account query = null;
        try {<!-- -->
            query = runner.query("select * from account", new BeanHandler<Account>(Account.class));
        } catch (SQLException e) {<!-- -->
            System.out.println("没查询到数据");
        }
        return query;
    }

    @Override
    public void saveAccount(Account account) {<!-- -->

        try {<!-- -->
         runner.update("insert into account(name,money) values(?,?)",account.getName(),account.getMoney());
        } catch (SQLException e) {<!-- -->
            System.out.println("没保存");

        }
    }

    @Override
    public void update(Account account) {<!-- -->
            try {<!-- -->
                runner.update("update account set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
            } catch (SQLException e) {<!-- -->
                System.out.println("没跟新");
            }
            }

    @Override
    public void delete(Integer id) {<!-- -->
        try {<!-- -->
            runner.update("delet from account where id=",id);
        } catch (SQLException e) {<!-- -->
            System.out.println("删除失败");
        }

    }
}


```

## 注解案例

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 告知spring在创建容器时要扫描的包 -->
    <context:component-scan base-package="com.itheima"></context:component-scan>
    <!--配置QueryRunner-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
        <!--注入数据源-->
        <constructor-arg name="ds" ref="dataSource"></constructor-arg>
    </bean>

    <!-- 配置数据源 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--连接数据库的必备信息-->
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/eesy"></property>
        <property name="user" value="root"></property>
        <property name="password" value="1234"></property>
    </bean>
</beans>

```

```
package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户的业务层实现类
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService{<!-- -->

    @Autowired
    private IAccountDao accountDao;

    @Override
    public List<Account> findAllAccount() {<!-- -->
        return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Integer accountId) {<!-- -->
        return accountDao.findAccountById(accountId);
    }

    @Override
    public void saveAccount(Account account) {<!-- -->
        accountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {<!-- -->
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Integer acccountId) {<!-- -->
        accountDao.deleteAccount(acccountId);
    }
}


```

```
package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户的持久层实现类
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {<!-- -->

    @Autowired
    private QueryRunner runner;



    @Override
    public List<Account> findAllAccount() {<!-- -->
        try{<!-- -->
            return runner.query("select * from account",new BeanListHandler<Account>(Account.class));
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountById(Integer accountId) {<!-- -->
        try{<!-- -->
            return runner.query("select * from account where id = ? ",new BeanHandler<Account>(Account.class),accountId);
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAccount(Account account) {<!-- -->
        try{<!-- -->
            runner.update("insert into account(name,money)values(?,?)",account.getName(),account.getMoney());
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {<!-- -->
        try{<!-- -->
            runner.update("update account set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Integer accountId) {<!-- -->
        try{<!-- -->
            runner.update("delete from account where id=?",accountId);
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }
}


```

## 注解配置

我们可能用有注解有xml的配置方法可能更简单一点.原则是这样子

```
 <cotext:component-scan base-package="com.demo"></cotext:component-scan>
 注解配置需要在里面写上这个bean.xml
 基于注解的配置怎么将上面的这个包扫描配置和在jar包中没办法添加注解的配置去掉呢
 	1.增加一个配置类
 		@Configration指定当前类是一个配置类
 		@ComponetnScans(basePackage="com.demo")表明要扫描的包 value 属性和 basePackage作用是一样的都是表明使用注解要扫描的包.
 		@Bean 将当前方法的返回值当做bean对象存入到Spring容器中.注解的属性就是name:默认值为当前方法的名称.这个值作为key.
 		使用注解配置时,如果方法有参数,Spring去容器中查找是否有容器,他和autowei的作用是一样的.如果没有匹配是会报错.
 		@import:导入其他的配置类
 		当我们引入import注解之后,含有import注解的类就是主配置类.
 		@popertySource(classpath:jdbcConfig.properties)用于指定properties文件的位置:value:指定文件的名称和路径 .classpath:/com/config表示文件的路径类路径下面的包名可以写着
 		
 		


```

## 将bean.xml转换成注解配置

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 告知spring在创建容器时要扫描的包 -->
    <context:component-scan base-package="com.itheima"></context:component-scan>
    <!--配置QueryRunner-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
        <!--注入数据源-->
        <constructor-arg name="ds" ref="dataSource"></constructor-arg>
    </bean>

    <!-- 配置数据源 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--连接数据库的必备信息-->
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/eesy"></property>
        <property name="user" value="root"></property>
        <property name="password" value="1234"></property>
    </bean>
</beans>

```

```
/**
 * 该类是一个配置类，它的作用和bean.xml是一样的
 * spring中的新注解
 * Configuration
 *     作用：指定当前类是一个配置类
 *     细节：当配置类作为AnnotationConfigApplicationContext对象创建的参数时，该注解可以不写。
 * ComponentScan
 *      作用：用于通过注解指定spring在创建容器时要扫描的包
 *      属性：
 *          value：它和basePackages的作用是一样的，都是用于指定创建容器时要扫描的包。
 *                 我们使用此注解就等同于在xml中配置了:
 *                      <context:component-scan base-package="com.itheima"></context:component-scan>
 *  Bean
 *      作用：用于把当前方法的返回值作为bean对象存入spring的ioc容器中
 *      属性:
 *          name:用于指定bean的id。当不写时，默认值是当前方法的名称
 *      细节：
 *          当我们使用注解配置方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象。
 *          查找的方式和Autowired注解的作用是一样的
 *  Import
 *      作用：用于导入其他的配置类
 *      属性：
 *          value：用于指定其他配置类的字节码。
 *                  当我们使用Import的注解之后，有Import注解的类就父配置类，而导入的都是子配置类
 *  PropertySource
 *      作用：用于指定properties文件的位置
 *      属性：
 *          value：指定文件的名称和路径。
 *                  关键字：classpath，表示类路径下
 */
@Configuration
@ComponentScan("com.itheima")
public class SpringConfiguration {<!-- -->
    @Bean(name="runner")
    public QueryRunner createQueryRunner(DataSource dataSource){<!-- -->
        return new QueryRunner(dataSource);
    }

    /**
     * 创建数据源对象
     * @return
     */
    @Bean(name="dataSource")
    public DataSource createDataSource(){<!-- -->
        try {<!-- -->
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        }catch (Exception e){<!-- -->
            throw new RuntimeException(e);
        }
    }

}

```

## 注解配置的加载类AnnoationConfigApplicationContext

```
参数是测试类的类字节码文件.
ApplicationContext ac = new AnnoationConfigApplicationContext(SpringConfiguration.class);
其中我们是在SpringConfiuration中配置的注解.
当配置类作为ApplicationConfigApplicationContext对象创建的参数时@Configuration注解可以不写.


```

## Spring 继承Junit

```
1.Junit继承了一个main方法
	该方法或判断当前测试类的哪些方法使用了@Test注解,如果有则让这个方法执行.
2.Junit不会管我们是否使用了Spring框架
	所以不会为我们读取配置文件/配置类创建Spring的核心容器
3.当我们在执行测试类方法时,没有IOC容器,就算写了autoweid也会报空指针异常.无法实现注入.

将不能创建容器的main方法换成可以创建容器的主方法
步骤:
	1.导入jar包.depedency
		<Spring-test>
	2.在测试类的上面添加注解@RunWith(SpringJunit4ClassRunner.class)
	3.告知Spring的运行期,Spring的IOC创建基于xml还是注解并且注明位置
		@ContextConfiguration()
			location:指定xml文件加上classpath关键字,表示在类路径下classes,指定注解类所在的位置
			
			
			
			package com.demo.test;

import com.demo.domain.Account;
import com.demo.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用Junit进行单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {<!-- -->
    @Autowired
    private AccountService accountService;

    @Test
    public void testFindAll(){<!-- -->
        //1.获取容器

        //得到业务层的对象

        List<Account> allAccount = accountService.findAllAccount();
        for (Account account : allAccount) {<!-- -->
            System.out.println(account);

        }

    }
    @Test
    public void testFindById(){<!-- -->

    }
}


```

```
1.模板岗位
2.实习经历
3.


```

## 事务分析

```
每个连接产生一个事务,所以有的事务成功了,有的事务失败了,所以会导致转账失败后信息错误
解决办法:需要使用ThreadLocal对象把connection和当前对象绑定,使得一个线程里面只有一个事务的对象.
事务的控制应该都在业务层不是在持久层.
ThreadLocal是用空间换安全的,他维护了一个map每个线程都有一个map保证了线程之间的安全问题.


```

## 动态代理

```
电脑的问题,代理商来买电脑.代理的出现解决了代理的问题.代理实际上增加了原来的功能,比如增加了售后.


```

代码实现动态代理

```
package com.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 模拟一个消费者
 */
public class Client {<!-- -->
    public static void main(String[] args) {<!-- -->
        /*动态代理;
        *   特点:字节码随用谁创建,随用随加载
        * 作用:不修改源码的基础上对方法进行修改
        * 分类
        *   1.基于接口的动态代理
        *   2.基于子类的动态代理
        * 基于接口的动态代理:
        *   设计的类Proxy,提供者是JDK官方
        * 如何创建代理对象,使用Proy.newProxyInstance(
        * 创建代理对象的要求:
        *   被代理类至少实现一个接口,如果没有实现接口则不可以使用
        * newProxyInstance
        *   ClassLoader:加载代理对象字节码文件的.写的是被代理对象的类加载器.代理对象和被代理对象使用相同的类加载器.
        *   class[]:字节码数组,为了让代理对象和被代理对象拥有相同的方法,让他们两实现相同的接口.
        *   InvocationHandler:用于提供增强的代码,我们一般是写一个该接口的实现类,通常情况线下都是匿名内部类.但不是不必须的.
        * */
        final Producer producer = new ProducerImpl();//当匿名内部类访问外部成员时,该成员需要用final来修饰.
        Producer proxyProducer = (Producer) Proxy.newProxyInstance(producer.getClass().getClassLoader(), producer.getClass().getInterfaces(),
                new InvocationHandler() {<!-- -->

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {<!-- -->
               /**
                *
                * 功能描述:作用:代理对象实现任何方法都要经过该方法
                * @param:Objects args: 方法所需的参数
                * @param:Method method 当前执行的方法
                * @param: java.lang.Object Proxy代理对象的引用
                * @return: java.lang.Object
                * @auther: caixiaocai
                */
               //提供增强的代码
                //获取到参数
                Object returnValue=null;
                Float money = (Float) args[0];
                if("saleProduct".equals(method.getName())){<!-- -->
                    returnValue = method.invoke(producer,money*0.8f);
                }
                return returnValue;
            }
        });
        proxyProducer.saleProduct(1000);



    }

}


```

## 基于子类的动态代理(没有借口的时候)

```
package com.demo.cglic;

import com.demo.proxy.Producer;
import com.demo.proxy.ProducerImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 模拟一个消费者
 */
public class Client {<!-- -->
    public static void main(String[] args) {<!-- -->
        /*动态代理;
         *   特点:字节码随用谁创建,随用随加载
         * 作用:不修改源码的基础上对方法进行修改
         * 分类
         *   1.基于接口的动态代理
         *   2.基于子类的动态代理
         * 基于子类的动态代理:
         *   设计的类Enhance,提供者第三方
         * 如何创建代理对象,使用Enhancer.create(
         * 创建代理对象的要求:
         *   被代理类不能是最终类,不可以被final修饰
         * create
         *
         *   class:字节码,被代理对象的class
         *   callBack:用于提供增强的代码,我们一般是写一个该接口的实现类,通常情况线下都是匿名内部类.但不是不必须的.
         *              我们一般写该接口的子接口的实现类,methodInterceptor 他是callBack的子接口
         *
         *
         * */
        final Producer producer = new ProducerImpl();
        Producer cglibProducer = (Producer)Enhancer.create(producer.getClass(), new MethodInterceptor() {<!-- -->
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {<!-- -->
                //代理对象的方法都要经过这个方法
                /**
                 *
                 * 功能描述:
                 * @methodProxy:当前方法的代理对象
                 * @args:方法的参数
                 * @param:method
                 * @param: proxy
                 * @return: java.lang.Object
                 * @auther: caixiaocai
                 * @date: 2020/3/16 13:16
                 */
                //提供增强的代码
                //获取到参数
                Object returnValue=null;
                Float money = (Float) args[0];
                if("saleProduct".equals(method.getName())){<!-- -->
                    returnValue = method.invoke(producer,money*0.8f);
                }
                return returnValue;

            }
        });
        cglibProducer.saleProduct(1000);

    }
}


```

## AOP(面向切面编程)

```
1.在软件业,aop值得是通过预编译的方式和运行期动态代理实现程序功能的统一维护的一种技术.函数式编程规范的一种衍生泛型.可以利用AOP来实现业务逻辑的各个部分的隔离,从而使得业务各部分之间的耦合度的降低,提高程序的可重用性,同时提高了开发的效率

2.AOP作用和优势:
	1.作用:
		在程序的运行期间,不修改源码对己方的方法进行增强
	2.优点:
		减少代码的重复开发
		提高维护效率
		维护方便
3.AOP 的实现方式.
	使用动态代理的方法
	
4.AOP的具体应用
	Spring中的aop是通过配置的方式来实现AOP
	
	
	


```

## Spring中的AOP

```
根据是否实现了接口来选择是基于接口的动态代理还是基于子类的动态代理
基于接口的动态代理必须要实现一个接口但是对子类的动态代理只需要他不是最终类,因此基于子类的还是基于接口的是可以选择的.
1.AOP的相关术语
	1.JoinPoint:所谓的连接点值得是name被拦截到的点,在Spring中指的是方法,因为在Spring中只有方法类型支持连接点.
	2.PointCut:被增强的方法称为切入点,所有的方法都是连接点.我们可以按照方法名来指定哪些方法被增强.
	3.Advice:增强通知:指的是拦截到连接点后所作的事情.
		.通知类型:
        	1.前置通知:invoke方法之前的操作
        	2.后置通知:invoke之后的操作
        	3.异常通知:异常语句里面的
        	4.最终通知:finally语句里面的操作
        	5.环绕通知:整个invoke方法执行就是环绕通知.
       4.Introduction:引介是一种特殊的通知在不改变代码的前提下,Introduction可以再运行期为类动态的添加一些方法或字段.
      5.WAVing:织入 是指把增强应用到目标对象来创建的代理对象额过程.
      Spring采用动态代理织入,而Aspectj采用编译器织入和类装载期织入
      6.Proxy:代理 一个类被AOP织入增强后产生一个结果代理类
      7Aspect切面:是切入点和通知(引介)的结合.
      8.target:被代理对象.
	
	


```

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">
<!--    配置Spring的IOC.把service对象配置进来-->
    <bean id="accountService" class="com.demo.service.impl.AccountServiceImpl"></bean>
<!--    Spring中基于xml的aop配置
        1.把通知的bean也交给Spring管理
        2.使用aop:config标签李艾表明开始aop的配置
        3.使用aop:aspect表示配置切面
            id:是给切面提供一个唯一的标识
            ref:是指定通知类的bean的id
        4.在aop:aspect标签的内部来配置通知的类型.
                我们现在是让logger是在切入点方法之前进行执行,因此我们配置的是前置通知.
                aop:before: 表示配置前置通知
                    method:用于指定logger类中的哪个方法作为前置通知.
                    pointcut:用于指定对于业务层中的哪些方法进行增强
                切入点表达式的写法:
                    访问修饰符 返回值 包名.包名....类名.方法名(参数列表)
                    public void com.demo.service.impl.AccountServiceImpl.saveAccount();
                    访问修饰符可以省略
                    返回值可以使用通配符表示任意的返回值
                    包名可以使用 *.*
                    *..*表示包及其子包
                    类名和方法名都可以通过*来通配
                    参数:
                        可以直接写数据类型
                            基础类型写名称
                            引用类型写包名.类名
                            *表示有一个任意参数
                            ..表示有无参数均可.有的话可以是任意的参数
                      实际开发中切入点的表达式切到业务层下面的.
                      com.demo.service.impl.*.*(..)
                    全通配写法:
                        * *..*.*(..)
	5.配置切入点表达式:
	<aop:pointcut id="pt1" expression="....">
	然后将其他的来引用这个.他可以写在aspect里面供该切点引用.写在外面的时候一定要写在aspect标签的前面供所有的切点使用. 
	6.环绕通知
	<aop:around id >
	当我们配置了环绕通知之后,切入点方法没有执行而通知方法执行了
通过对比动态代理中的环绕通知代码,发现动态代理中的环绕通知有明确的切入点调用.我们的代码中没有所以会出现不执行的现象.
解决:Spring框架为我们提供了一个接口,ProceedingJoinPoint,该接口有一个方法proceed,该方法相当于明确调用切入点的方法.该接口可作为环绕通知的方法参数,在程序执行时,Spring框架会为我们提供该接口的实现类供我们使用
        -->

<!--    配置logger类-->
    <bean id="logger" class="com.demo.utils.Logger"></bean>
    <!--配置aop-->
    <aop:config>
        <!--    配置切面-->
        <aop:aspect id="logAdvice" ref="logger">
            <aop:before method="printLog" pointcut="execution(public void com.demo.service.impl.AccountServiceImpl.saveAccount())"></aop:before>
      
        </aop:aspect>
    </aop:config>


    </beans>

```

## 环绕通知

```
环绕通知可以通过编码的方式来实现什么时候执行通知

package com.itheima.utils;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 用于记录日志的工具类，它里面提供了公共的代码
 */
public class Logger {<!-- -->

    /**
     * 前置通知
     */
    public  void beforePrintLog(){<!-- -->
        System.out.println("前置通知Logger类中的beforePrintLog方法开始记录日志了。。。");
    }

    /**
     * 后置通知
     */
    public  void afterReturningPrintLog(){<!-- -->
        System.out.println("后置通知Logger类中的afterReturningPrintLog方法开始记录日志了。。。");
    }
    /**
     * 异常通知
     */
    public  void afterThrowingPrintLog(){<!-- -->
        System.out.println("异常通知Logger类中的afterThrowingPrintLog方法开始记录日志了。。。");
    }

    /**
     * 最终通知
     */
    public  void afterPrintLog(){<!-- -->
        System.out.println("最终通知Logger类中的afterPrintLog方法开始记录日志了。。。");
    }

    /**
     * 环绕通知
     * 问题：
     *      当我们配置了环绕通知之后，切入点方法没有执行，而通知方法执行了。
     * 分析：
     *      通过对比动态代理中的环绕通知代码，发现动态代理的环绕通知有明确的切入点方法调用，而我们的代码中没有。
     * 解决：
     *      Spring框架为我们提供了一个接口：ProceedingJoinPoint。该接口有一个方法proceed()，此方法就相当于明确调用切入点方法。
     *      该接口可以作为环绕通知的方法参数，在程序执行时，spring框架会为我们提供该接口的实现类供我们使用。
     *
     * spring中的环绕通知：
     *      它是spring框架为我们提供的一种可以在代码中手动控制增强方法何时执行的方式。
     */
    public Object aroundPringLog(ProceedingJoinPoint pjp){<!-- -->
        Object rtValue = null;
        try{<!-- -->
            Object[] args = pjp.getArgs();//得到方法执行所需的参数

            System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。前置");

            rtValue = pjp.proceed(args);//明确调用业务层方法（切入点方法）

            System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。后置");

            return rtValue;
        }catch (Throwable t){<!-- -->
            System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。异常");
            throw new RuntimeException(t);
        }finally {<!-- -->
            System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。最终");
        }
    }
}


```

## 基于注解配置的案例

```
package com.itheima.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 用于记录日志的工具类，它里面提供了公共的代码
 */
@Component("logger")
@Aspect//表明当前就是一个通知类
public class Logger {<!-- -->

    /**
     * 前置通知
     */
    @Before("pt1()")
    public  void beforePrintLog(){<!-- -->
        System.out.println("前置通知Logger类中的beforePrintLog方法开始记录日志了。。。");
    }

    /**
     * 后置通知
     */
    @AfterReturning("pt1()")
    public  void afterReturningPrintLog(){<!-- -->
        System.out.println("后置通知Logger类中的afterReturningPrintLog方法开始记录日志了。。。");
    }
    /**
     * 异常通知
     * **/
    @AfterThrowing("pt1()")
    public  void afterThrowingPrintLog(){<!-- -->
        System.out.println("异常通知Logger类中的afterThrowingPrintLog方法开始记录日志了。。。");
    }

    /**
     * 最终通知
     */
    @After("pt1()")
    public  void afterPrintLog(){<!-- -->
        System.out.println("最终通知Logger类中的afterPrintLog方法开始记录日志了。。。");
    }

    /**
     * 环绕通知
     * 问题：
     *      当我们配置了环绕通知之后，切入点方法没有执行，而通知方法执行了。
     * 分析：
     *      通过对比动态代理中的环绕通知代码，发现动态代理的环绕通知有明确的切入点方法调用，而我们的代码中没有。
     * 解决：
     *      Spring框架为我们提供了一个接口：ProceedingJoinPoint。该接口有一个方法proceed()，此方法就相当于明确调用切入点方法。
     *      该接口可以作为环绕通知的方法参数，在程序执行时，spring框架会为我们提供该接口的实现类供我们使用。
     *
     * spring中的环绕通知：
     *      它是spring框架为我们提供的一种可以在代码中手动控制增强方法何时执行的方式。
     */
    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt1(){<!-- -->}
    @Around("pt1()")
    public Object aroundPringLog(ProceedingJoinPoint pjp){<!-- -->
        Object rtValue = null;
        try{<!-- -->
            Object[] args = pjp.getArgs();//得到方法执行所需的参数

            System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。前置");

            rtValue = pjp.proceed(args);//明确调用业务层方法（切入点方法）

            System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。后置");

            return rtValue;
        }catch (Throwable t){<!-- -->
            System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。异常");
            throw new RuntimeException(t);
        }finally {<!-- -->
            System.out.println("Logger类中的aroundPringLog方法开始记录日志了。。。最终");
        }
    }
}


```

## bean.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

<!--    配置Spring创建容器要扫描的包-->
    <context:component-scan base-package="com.itheima"></context:component-scan>

<!--    &amp;lt;!&amp;ndash; 配置srping的Ioc,把service对象配置进来&amp;ndash;&amp;gt;
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"></bean>-->


<!--    &amp;lt;!&amp;ndash; 配置Logger类 &amp;ndash;&amp;gt;
    <bean id="logger" class="com.itheima.utils.Logger"></bean>-->

  <!--  &amp;lt;!&amp;ndash;配置AOP&amp;ndash;&amp;gt;
    <aop:config>
        &amp;lt;!&amp;ndash; 配置切入点表达式 id属性用于指定表达式的唯一标识。expression属性用于指定表达式内容
              此标签写在aop:aspect标签内部只能当前切面使用。
              它还可以写在aop:aspect外面，此时就变成了所有切面可用
          &amp;ndash;&amp;gt;
        <aop:pointcut id="pt1" expression="execution(* com.itheima.service.impl.*.*(..))"></aop:pointcut>
        &amp;lt;!&amp;ndash;配置切面 &amp;ndash;&amp;gt;
        <aop:aspect id="logAdvice" ref="logger">
            &amp;lt;!&amp;ndash; 配置前置通知：在切入点方法执行之前执行
            <aop:before method="beforePrintLog" pointcut-ref="pt1" ></aop:before>&amp;ndash;&amp;gt;

            &amp;lt;!&amp;ndash; 配置后置通知：在切入点方法正常执行之后值。它和异常通知永远只能执行一个
            <aop:after-returning method="afterReturningPrintLog" pointcut-ref="pt1"></aop:after-returning>&amp;ndash;&amp;gt;

            &amp;lt;!&amp;ndash; 配置异常通知：在切入点方法执行产生异常之后执行。它和后置通知永远只能执行一个
            <aop:after-throwing method="afterThrowingPrintLog" pointcut-ref="pt1"></aop:after-throwing>&amp;ndash;&amp;gt;

            &amp;lt;!&amp;ndash; 配置最终通知：无论切入点方法是否正常执行它都会在其后面执行
            <aop:after method="afterPrintLog" pointcut-ref="pt1"></aop:after>&amp;ndash;&amp;gt;

            &amp;lt;!&amp;ndash; 配置环绕通知 详细的注释请看Logger类中&amp;ndash;&amp;gt;
            <aop:around method="aroundPringLog" pointcut-ref="pt1"></aop:around>
        </aop:aspect>
    </aop:config>
-->
<!--    开启注解开启注解配置的支持-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>

```

## `测试类`

```
package com.itheima;

import com.itheima.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试AOP的配置
 */
public class AOPTest {<!-- -->

    public static void main(String[] args) {<!-- -->
        //1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        //2.获取对象
        IAccountService as = (IAccountService)ac.getBean("accountService");
        //3.执行方法
        as.saveAccount();
    }
}



```

## SPring中的JDBC

```
问题:每个Dao都需要在开始的new一个JDBCtemplate的类,因此造成了代码的重复
解决方法:
	写一个类JdbcDaoSupport来提取重复的代码然后设置get方法然后让其他的dao来继承这个类来获取到其对象.
	这个方法在Spring里面是有的,但是继承了Spring里面的类以后用注解配置就有问题了


```

## 转账的注解配置

```
package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户的业务层实现类
 *
 * 事务控制应该都是在业务层
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService{<!-- -->
    @Autowired
    private IAccountDao accountDao;

    public void setAccountDao(IAccountDao accountDao) {<!-- -->
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAllAccount() {<!-- -->
       return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Integer accountId) {<!-- -->
        return accountDao.findAccountById(accountId);

    }

    @Override
    public void saveAccount(Account account) {<!-- -->
        accountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {<!-- -->
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Integer acccountId) {<!-- -->
        accountDao.deleteAccount(acccountId);
    }

    @Override
    public void transfer(String sourceName, String targetName, Float money) {<!-- -->
        System.out.println("transfer....");
            //2.1根据名称查询转出账户
            Account source = accountDao.findAccountByName(sourceName);
            //2.2根据名称查询转入账户
            Account target = accountDao.findAccountByName(targetName);
            //2.3转出账户减钱
            source.setMoney(source.getMoney()-money);
            //2.4转入账户加钱
            target.setMoney(target.getMoney()+money);
            //2.5更新转出账户
            accountDao.updateAccount(source);

            int i=1/0;

            //2.6更新转入账户
            accountDao.updateAccount(target);
    }
}


```

```
package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.utils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户的持久层实现类
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {<!-- -->
    @Autowired
    private QueryRunner runner;
    @Autowired
    private ConnectionUtils connectionUtils;
//对于注解配置不需要
/*    public void setRunner(QueryRunner runner) {
        this.runner = runner;
    }

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }*/

    @Override
    public List<Account> findAllAccount() {<!-- -->
        try{<!-- -->
            return runner.query(connectionUtils.getThreadConnection(),"select * from account",new BeanListHandler<Account>(Account.class));
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountById(Integer accountId) {<!-- -->
        try{<!-- -->
            return runner.query(connectionUtils.getThreadConnection(),"select * from account where id = ? ",new BeanHandler<Account>(Account.class),accountId);
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAccount(Account account) {<!-- -->
        try{<!-- -->
            runner.update(connectionUtils.getThreadConnection(),"insert into account(name,money)values(?,?)",account.getName(),account.getMoney());
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {<!-- -->
        try{<!-- -->
            runner.update(connectionUtils.getThreadConnection(),"update account set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Integer accountId) {<!-- -->
        try{<!-- -->
            runner.update(connectionUtils.getThreadConnection(),"delete from account where id=?",accountId);
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountByName(String accountName) {<!-- -->
        try{<!-- -->
            List<Account> accounts = runner.query(connectionUtils.getThreadConnection(),"select * from account where name = ? ",new BeanListHandler<Account>(Account.class),accountName);
            if(accounts == null || accounts.size() == 0){<!-- -->
                return null;
            }
            if(accounts.size() > 1){<!-- -->
                throw new RuntimeException("结果集不唯一，数据有问题");
            }
            return accounts.get(0);
        }catch (Exception e) {<!-- -->
            throw new RuntimeException(e);
        }
    }
}


```

```
package com.itheima.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 连接的工具类，它用于从数据源中获取一个连接，并且实现和线程的绑定
 */
@Component("connectionUtils")
public class ConnectionUtils {<!-- -->

    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
    @Autowired
    private DataSource dataSource;

   /* public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    /**
     * 获取当前线程上的连接
     * @return
     */
    public Connection getThreadConnection() {<!-- -->
        try{<!-- -->
            //1.先从ThreadLocal上获取
            Connection conn = tl.get();
            //2.判断当前线程上是否有连接
            if (conn == null) {<!-- -->
                //3.从数据源中获取一个连接，并且存入ThreadLocal中
                conn = dataSource.getConnection();
                tl.set(conn);
            }
            //4.返回当前线程上的连接
            return conn;
        }catch (Exception e){<!-- -->
            throw new RuntimeException(e);
        }
    }

    /**
     * 把连接和线程解绑
     */
    public void removeConnection(){<!-- -->
        tl.remove();
    }
}


```

```
package com.itheima.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 和事务管理相关的工具类，它包含了，开启事务，提交事务，回滚事务和释放连接
 */
@Aspect
@Component("txManager")
public class TransactionManager {<!-- -->
    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt1(){<!-- -->}
    @Autowired
    private ConnectionUtils connectionUtils;

    /*public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }*/

    /**
     * 开启事务
     */

    public  void beginTransaction(){<!-- -->
        try {<!-- -->
            connectionUtils.getThreadConnection().setAutoCommit(false);
            System.out.println("设置了事务自动关闭");
        }catch (Exception e){<!-- -->
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */

    public  void commit(){<!-- -->
        try {<!-- -->
            System.out.println("开启事务");
            connectionUtils.getThreadConnection().commit();
            System.out.println("开启事务成功");

        }catch (Exception e){<!-- -->
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */

    public  void rollback(){<!-- -->
        try {<!-- -->
            connectionUtils.getThreadConnection().rollback();
        }catch (Exception e){<!-- -->
            e.printStackTrace();
        }
    }


    /**
     * 释放连接
     */

    public  void release(){<!-- -->
        try {<!-- -->
            connectionUtils.getThreadConnection().close();//还回连接池中
            connectionUtils.removeConnection();
        }catch (Exception e){<!-- -->
            e.printStackTrace();
        }
    }

    @Around("pt1()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) {<!-- -->
        Object result = null;
        try {<!-- -->
            Object[] args = pjp.getArgs();
            this.beginTransaction();
            result = pjp.proceed(args);
            this.commit();
        } catch (Throwable e) {<!-- -->
            this.rollback();
            e.printStackTrace();
        } finally {<!-- -->
            this.release();
            return result;
        }



    }}





```

```
package com.itheima.test;

import com.itheima.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用Junit单元测试：测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {<!-- -->

    @Autowired
    @Qualifier("accountService")
    private  IAccountService as;

    @Test
    public  void testTransfer(){<!-- -->
        as.transfer("aaa","bbb",100f);
    }

}


```

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

<!--    &amp;lt;!&amp;ndash;配置代理的service&amp;ndash;&amp;gt;-->
<!--    <bean id="proxyAccountService" factory-bean="beanFactory" factory-method="getAccountService"></bean>-->

<!--    &amp;lt;!&amp;ndash;配置beanfactory&amp;ndash;&amp;gt;-->
<!--    <bean id="beanFactory" class="com.itheima.factory.BeanFactory">-->
<!--        &amp;lt;!&amp;ndash; 注入service &amp;ndash;&amp;gt;-->
<!--        <property name="accountService" ref="accountService"></property>-->
<!--        &amp;lt;!&amp;ndash; 注入事务管理器 &amp;ndash;&amp;gt;-->
<!--        <property name="txManager" ref="txManager"></property>-->
<!--    </bean>-->
<!--    配置臊面的区域注解-->
    <context:component-scan base-package="com.itheima"></context:component-scan>

<!--     &amp;lt;!&amp;ndash; 配置Service &amp;ndash;&amp;gt;
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
        &amp;lt;!&amp;ndash; 注入dao &amp;ndash;&amp;gt;
        <property name="accountDao" ref="accountDao"></property>
    </bean>-->

    <!--配置Dao对象-->
<!--    <bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl">
        &amp;lt;!&amp;ndash; 注入QueryRunner &amp;ndash;&amp;gt;
        <property name="runner" ref="runner"></property>
        &amp;lt;!&amp;ndash; 注入ConnectionUtils &amp;ndash;&amp;gt;
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>-->

    <!--配置QueryRunner-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype"></bean>

    <!-- 配置数据源 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--连接数据库的必备信息-->
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/eesy"></property>
        <property name="user" value="root"></property>
        <property name="password" value="root"></property>
    </bean>

    <!-- 配置Connection的工具类 ConnectionUtils -->
<!--
    <bean id="connectionUtils" class="com.itheima.utils.ConnectionUtils">
        &amp;lt;!&amp;ndash; 注入数据源&amp;ndash;&amp;gt;
        <property name="dataSource" ref="dataSource"></property>
    </bean>
-->

<!--    &amp;lt;!&amp;ndash; 配置事务管理器&amp;ndash;&amp;gt;
    <bean id="txManager" class="com.itheima.utils.TransactionManager">
        &amp;lt;!&amp;ndash; 注入ConnectionUtils &amp;ndash;&amp;gt;
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>-->

    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>

```

## Spring控制事务的一组API

```
 1.PlatformTransactionManager接口,里面有commit和rollback方法
 2.DataSourceTransactionMangers:真正管理事务的对象
 3.TransactionDefinition:事务的定义信息对象.
 	隔离级别:四个
 	事务的传播行为:增删白必须要有事务,查询可以没有事务
 	事务的超时时间:可以配置永远斗不过期 默认值为-1表示没有超时时间,如果有单位是秒
 	事务是否只是可读,建议查询设置为只读
 4.TransactionStatus
 	该接口描述了某个时间点上面对事务对象进行的状态信息,包含6个具体的操作
 		刷新事务:void flush
 		获取是否存在存储点:boolean hashSavePoint 按部提交,按部为操作的单位.
 		获取事务是否为新事物:boolean isCompleted
 		获取事务是否回滚:boolean isRollBack
 		设置回滚:void setRollbackOnly()


```

## Spring 基于事务控制基于xml的配置

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 配置账户的持久层-->
    <bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl">

        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
        <property name="accountDao" ref="accountDao"></property>
    </bean>


    <!-- 配置数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
        <property name="url" value="jdbc:mysql://localhost:3306/eesy"></property>
        <property name="username" value="root"></property>
        <property name="password" value="root"></property>
    </bean>

<!--    spring基于xml的生声明事务配置的步骤
            1.配置事务管理器
            2.配置事务的通知
                导入事务的约束 tx名称空间和约束,同时也需要aop的
                使用tx:advice标签来配置事务的通知
                    属性:
                        id:给事务通知起一个唯一标志
                        transaction-manger给事务通知提供一个事务管理器引用
             3.配置aop中的通用切入点表达式.
             4.建立事务通知和切入点表达式的关系
             5.配置事务的属性
                在事务的通知tx:advice标签的内部-->


<!--    1.配置事务管理器-->
    <bean id="transactionManger" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>

<!--    配置事务的通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManger">
<!--        配置事务的属性
                1.isolation:指定事务的隔离级别默认是default,表示数据库的默认隔离级别
                2.no-rollback: 用于指定一个异常,产生该异常值,不回滚,产生其他异常时,事务回滚.
                3.propagation:表示指定事务的传播行为,默认值为REQUIED,表示一定会有事务.增删改的选择.查询可以选择SUPPORTS
                4.read-only: 用于指定是否设置为只读,只有查询方法可以设置为true默认值为false
                5.rollback-for:用于指定一个异常,产生该异常时事务回滚,其他异常不会滚,默认值无,表示任何异常都会回滚.
                6.timeout:用于指定事务的超时时间,默认永不超时-1 &amp;ndash;&amp;gt;-->
        <tx:attributes>
            <tx:method name="*" propagation="REQUIRED" read-only="false"/>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"></tx:method>
<!--            -->
        </tx:attributes>
    </tx:advice>

<!--    配置aop -->
    <aop:config>
<!--        配置切入点-->
        <aop:pointcut id="pt1" expression="execution(* com.itheima.service.impl.*.*(..))"></aop:pointcut>
<!--        建立切入点表达式和事务管理之间的关系-->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"></aop:advisor>
    </aop:config>
</beans>

```

## Spring 基于事务控制基于注解的配置

```
package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
@Transactional(..)//在这里面会实现属性的配置.
public class AccountServiceImpl  implements AccountService {<!-- -->
    @Autowired
    private IAccountDao accountDao;





    @Override
    public Account findAccountById(Integer accountId) {<!-- -->
        return accountDao.findAccountById(accountId);

    }

    @Override
    public void transfer(String sourceName, String targetName, float money) {<!-- -->
        System.out.println("transfer....");
        //2.1根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        //2.2根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        //2.3转出账户减钱
        source.setMoney(source.getMoney()-money);
        //2.4转入账户加钱
        target.setMoney(target.getMoney()+money);
        //2.5更新转出账户
        accountDao.updateAccount(source);

//            int i=1/0;

        //2.6更新转入账户
        accountDao.updateAccount(target);
    }


}


```

```
package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户的持久层实现类
 */
@Repository("accountDao")
public class AccountDaoImpl  implements IAccountDao {<!-- -->
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account findAccountById(Integer accountId) {<!-- -->
        List<Account> accounts = jdbcTemplate.query("select * from account where id = ?",new BeanPropertyRowMapper<Account>(Account.class),accountId);
        return accounts.isEmpty()?null:accounts.get(0);
    }

    @Override
    public Account findAccountByName(String accountName) {<!-- -->
        List<Account> accounts = jdbcTemplate.query("select * from account where name = ?",new BeanPropertyRowMapper<Account>(Account.class),accountName);
        if(accounts.isEmpty()){<!-- -->
            return null;
        }
        if(accounts.size()>1){<!-- -->
            throw new RuntimeException("结果集不唯一");
        }
        return accounts.get(0);
    }

    @Override
    public void updateAccount(Account account) {<!-- -->
        jdbcTemplate.update("update account set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
    }
}


```

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 配置账户的持久层-->
<!--    <bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl">-->

<!--        <property name="dataSource" ref="dataSource"></property>-->
<!--    </bean>-->
<!--
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
        <property name="accountDao" ref="accountDao"></property>
    </bean>
-->
<!--    创建容器时需要扫描的包-->
    <context:component-scan base-package="com.itheima"></context:component-scan>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>
    </bean>


    <!-- 配置数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
        <property name="url" value="jdbc:mysql://localhost:3306/eesy"></property>
        <property name="username" value="root"></property>
        <property name="password" value="root"></property>
    </bean>

<!--    spring基于xml的生声明事务配置的步骤
            1.配置事务管理器
             2.开启Spring对注解配置的支持
             3.需要事务支持的地方使用@Transactional-->


<!--    1.配置事务管理器-->
    <bean id="transactionManger" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
<!--    2.开启Spring对注解配置事务的支持-->
    <tx:annotation-driven transaction-manager="transactionManger"></tx:annotation-driven>

<!--&amp;lt;!&amp;ndash;    配置事务的通知&amp;ndash;&amp;gt;
    <tx:advice id="txAdvice" transaction-manager="transactionManger">
&amp;lt;!&amp;ndash;        配置事务的属性
                1.isolation:指定事务的隔离级别默认是default,表示数据库的默认隔离级别
                2.no-rollback: 用于指定一个异常,产生该异常值,不回滚,产生其他异常时,事务回滚.
                3.propagation:表示指定事务的传播行为,默认值为REQUIED,表示一定会有事务.增删改的选择.查询可以选择SUPPORTS
                4.read-only: 用于指定是否设置为只读,只有查询方法可以设置为true默认值为false
                5.rollback-for:用于指定一个异常,产生该异常时事务回滚,其他异常不会滚,默认值无,表示任何异常都会回滚.
                6.timeout:用于指定事务的超时时间,默认永不超时-1 &amp;ndash;&amp;gt;&amp;ndash;&amp;gt;
        <tx:attributes>
            <tx:method name="*" propagation="REQUIRED" read-only="false"/>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"></tx:method>
&amp;lt;!&amp;ndash;            &amp;ndash;&amp;gt;
        </tx:attributes>
    </tx:advice>

&amp;lt;!&amp;ndash;    配置aop &amp;ndash;&amp;gt;
    <aop:config>
&amp;lt;!&amp;ndash;        配置切入点&amp;ndash;&amp;gt;
        <aop:pointcut id="pt1" expression="execution(* com.itheima.service.impl.*.*(..))"></aop:pointcut>
&amp;lt;!&amp;ndash;        建立切入点表达式和事务管理之间的关系&amp;ndash;&amp;gt;
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"></aop:advisor>
    </aop:config>-->
</beans>

```

```
package com.itheima.test;

import com.itheima.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用Junit单元测试：测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {<!-- -->

    @Autowired
    @Qualifier("accountService")
    private AccountService as;

    @Test
    public  void testTransfer(){<!-- -->
        as.transfer("aaa","bbb",100f);

    }

}


```

## Spring完全基于注解的配置

```
package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * spring的配置类 相当于bean.xml 主配置文件
 */
@Configuration
@ComponentScan("com.itheima")//创建容器需要扫描的包
@Import({<!-- -->JdbcConfig.class,TransactionConfig.class})//引入其他配置类
@PropertySource("jdbcConfig.properties")
@EnableTransactionManagement//开启注解的支持
public class SpringConfiguration {<!-- -->
}


```

```
package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * 和连接数据库相关的配置类
 */
public class JdbcConfig {<!-- -->
    @Value("${jdbc.driver}")
    private  String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Bean("jdbcTemplate")
    public JdbcTemplate createJdbcTemplate(DataSource dataSource){<!-- -->
        return new JdbcTemplate(dataSource);
    }
    @Bean("dataSource")
    public DataSource createDataSource(){<!-- -->
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

}


```

```
package config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 和事务相关的配置类
 */
public class TransactionConfig {<!-- -->
    /**
     * 用于创建事务管理器对象
     * @param dataSource
     * @return
     */
    @Bean("transactionManger")
    public PlatformTransactionManager createTransactionManger(DataSource dataSource){<!-- -->
        return new DataSourceTransactionManager(dataSource);
    }
}


```

test

```
package com.itheima.test;

import config.SpringConfiguration;
import com.itheima.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用Junit单元测试：测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest {<!-- -->

    @Autowired
    @Qualifier("accountService")
    private AccountService as;

    @Test
    public  void testTransfer(){<!-- -->
        as.transfer("aaa","bbb",100f);

    }

}


```

```
package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
@Transactional
public class AccountServiceImpl  implements AccountService {<!-- -->
    @Autowired
    private IAccountDao accountDao;





    @Override
    public Account findAccountById(Integer accountId) {<!-- -->
        return accountDao.findAccountById(accountId);

    }

    @Override
    public void transfer(String sourceName, String targetName, float money) {<!-- -->
        System.out.println("transfer....");
        //2.1根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        //2.2根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        //2.3转出账户减钱
        source.setMoney(source.getMoney()-money);
        //2.4转入账户加钱
        target.setMoney(target.getMoney()+money);
        //2.5更新转出账户
        accountDao.updateAccount(source);

//            int i=1/0;

        //2.6更新转入账户
        accountDao.updateAccount(target);
    }


}


```

## spring5新特性

```
响应式非阻塞的框架
1.8和1.7之间反射的速度差别非常大.


```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/105768252