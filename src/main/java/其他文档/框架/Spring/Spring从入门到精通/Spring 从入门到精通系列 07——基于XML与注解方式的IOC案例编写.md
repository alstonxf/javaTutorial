# Spring 从入门到精通系列 07——基于XML与注解方式的IOC案例编写
本文使用 XML 方式和注解方式实现单表的 CRUD操作，连带巩固 IOC 配置过程。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
一、基于 XML 的 IOC 的案例
1.1 导入依赖
1.2 账户实体类
1.3 业务层接口及实现类
1.4 持久层接口及实现类
1.5 bean.xml
★
1.6 测试方法
二、基于注解配置的 IOC 案例
2.1 业务层接口实现类
2.2 持久层接口实现类
2.3 bean.xml
★

---


## 一、基于 XML 的 IOC 的案例

**工程目录：** <img src="https://img-blog.csdnimg.cn/20210601223328851.png#pic_left" alt="在这里插入图片描述" width="250"/>

---


### 1.1 导入依赖

```xml
<dependencies>
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-context</artifactId>
	    <version>5.0.2.RELEASE</version>
	</dependency>
	<dependency>
	    <groupId>commons-dbutils</groupId>
	    <artifactId>commons-dbutils</artifactId>
	    <version>1.4</version>
	</dependency>
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-test</artifactId>
	    <version>5.0.2.RELEASE</version>
	</dependency>
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>5.1.6</version>
	</dependency>
	<dependency>
	    <groupId>c3p0</groupId>
	    <artifactId>c3p0</artifactId>
	    <version>0.9.1.2</version>
	</dependency>
	<dependency>
	    <groupId>junit</groupId>
	    <artifactId>junit</artifactId>
	    <version>4.12</version>
	</dependency>
</dependencies>

```

---


### 1.2 账户实体类

```java
/**
 * 账户的实体类
 */
public class Account implements Serializable {
    private Integer id;
    private String name;
    private Float money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}

```

---


### 1.3 业务层接口及实现类

```java
/**
 * 账户的业务层接口
 */
public interface IAccountService {

    /**
     * 查询所有
     * @return
     */
    List<Account> findAllAccount();

    /**
     * 查询一个
     * @return
     */
    Account findAccountById(Integer id);

    /**
     * 添加账户
     * @param account
     */
    void insertAccount(Account account);

    /**
     * 更新账户
     * @param account
     */
    void updateAccount(Account account);

    /**
     * 删除
     * @param id
     */
    void deleteAccount(Integer id);
}

```

```java
/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    public Account findAccountById(Integer id) {
        return accountDao.findAccountById(id);
    }

    public void insertAccount(Account account) {
        accountDao.insertAccount(account);
    }

    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    public void deleteAccount(Integer id) {
        accountDao.deleteAccount(id);
    }
}

```

---


### 1.4 持久层接口及实现类

```java
/**
 * 账户的持久层接口
 */
public interface IAccountDao {

    /**
     * 查询所有
     * @return
     */
    List<Account> findAllAccount();

    /**
     * 查询一个
     * @return
     */
    Account findAccountById(Integer id);

    /**
     * 添加账户
     * @param account
     */
    void insertAccount(Account account);

    /**
     * 更新账户
     * @param account
     */
    void updateAccount(Account account);

    /**
     * 删除
     * @param id
     */
    void deleteAccount(Integer id);
}

```

```java
/**
 * 账户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao {

    private QueryRunner runner;

    public void setRunner(QueryRunner runner) {
        this.runner = runner;
    }

    public List<Account> findAllAccount() {
        try {
            return runner.query("select * from account", new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Account findAccountById(Integer id) {
        try {
            return runner.query("select * from account where id = ?", new BeanHandler<Account>(Account.class), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertAccount(Account account) {
        try {
            runner.update("insert into account(name, money) values(?,?)", account.getName(), account.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(Account account) {
        try {
            runner.update("update account set name = ?, money = ? where id = ?", account.getName(), account.getMoney(), account.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(Integer id) {
        try {
            runner.update("delete from account where id = ?", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

```

**注意：** 这里使用 dbutils 工具类的 QueryRunner 对象与数据库进行交互，也将对象的创建也交给 Spring，因此生成 get、set 方法。

---


### 1.5 bean.xml <font color="red">★</font>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--首先配置 service 业务层对象-->
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
        <!--注入 dao -->
        <property name="accountDao" ref="accountDao"></property>
    </bean>

    <!--配置 dao 对象-->
    <bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl">
        <!--注入 QueryRunner -->
        <property name="runner" ref="runner"></property>
    </bean>

    <!--配置 QueryRunner -->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
        <!--注入数据源-->
        <constructor-arg name="ds" ref="dataSource"></constructor-arg>
    </bean>

    <!--配置数据源-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--连接数据库的必备信息-->
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/springdb"></property>
        <property name="user" value="root"></property>
        <property name="password" value="000000"></property>
    </bean>

</beans>

```

**注意：**
1. 在配置对象间的依赖关系，首先配置 service 业务层对象，因为业务层对象中含有 dao 对象，所以就需要依赖注入的方式注入 dao 对象，这时候还没有 dao 对象所以还需要配置 dao 对象 … 以此类推。1. QueryRunner 的数据源是通过调用带参构造函数构造的。1. QueryRunner 默认是单例对象，为了防止线程干扰，可以使用 scope=“prototype” 让其变为多例对象。
---


### 1.6 测试方法

```java
public class AccountServiceTest {

    private ApplicationContext ac;
    private IAccountService as;

    @Before
    public void init(){
        ac = new ClassPathXmlApplicationContext("bean.xml");
        as = (IAccountService) ac.getBean("accountService");
    }


    @Test
    public void testFindAll(){
        List<Account> accounts = as.findAllAccount();
        for(Account account:accounts){
            System.out.println(account);
        }
    }

    @Test
    public void testFindOne(){
        Account account = as.findAccountById(2);
        System.out.println(account);
    }
    @Test
    public void testInsert(){
        Account account = new Account();
        account.setName("test");
        account.setMoney(5000f);
        as.insertAccount(account);
    }
    @Test
    public void testUpdate(){
        Account account = as.findAccountById(4);
        account.setName("test");
        account.setMoney(6000f);
        as.updateAccount(account);
    }
    @Test
    public void testDelete(){
        as.deleteAccount(4);
    }
}

```

---


## 二、基于注解配置的 IOC 案例

**工程目录：** <img src="https://img-blog.csdnimg.cn/20210601232305237.png#pic_left" alt="在这里插入图片描述" width="250"/> 除了以下内容与 xml 案例中的不同，其余部分都相同。

---


### 2.1 业务层接口实现类

```java
/**
 * 账户的业务层实现类
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    public Account findAccountById(Integer id) {
        return accountDao.findAccountById(id);
    }

    public void insertAccount(Account account) {
        accountDao.insertAccount(account);
    }

    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    public void deleteAccount(Integer id) {
        accountDao.deleteAccount(id);
    }
}

```

**注意：**
1. <font color="red">当使用 Autowired 自动按照类型注入时，set 方法也就不是必须的了。</font>1. @Component、@Controller、@Service、@Repository 四个注解的作用是相同的，但是为清晰，还是按照标准来。
---


### 2.2 持久层接口实现类

```java
/**
 * 账户的持久层实现类
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    private QueryRunner runner;

    public List<Account> findAllAccount() {
        try {
            return runner.query("select * from account", new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Account findAccountById(Integer id) {
        try {
            return runner.query("select * from account where id = ?", new BeanHandler<Account>(Account.class), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertAccount(Account account) {
        try {
            runner.update("insert into account(name, money) values(?,?)", account.getName(), account.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(Account account) {
        try {
            runner.update("update account set name = ?, money = ? where id = ?", account.getName(), account.getMoney(), account.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(Integer id) {
        try {
            runner.update("delete from account where id = ?", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

```

---


### 2.3 bean.xml <font color="red">★</font>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

	<!--开启注解扫描-->
    <context:component-scan base-package="com.itheima"></context:component-scan>

    <!--配置QueryRunner-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
        <!--注入数据源-->
        <constructor-arg  name="ds" ref="dataSource"></constructor-arg>
    </bean>

    <!--配置数据源-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--连接数据库的必备信息-->
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/springdb"></property>
        <property name="user" value="root"></property>
        <property name="password" value="000000"></property>
    </bean>

</beans>

```

**注意：** 由于 QueryRunner 是 dbutils 下的一个类，因此无法在 QueryRunner 类上加入注解，所以 QueryRunner 目前只能在 bean.xml 中配置。在下一篇文章中，会介绍如何用纯注解的方式编写 IOC。

---


本文使用 XML 方式和注解方式实现单表的 CRUD操作，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~
# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117455809