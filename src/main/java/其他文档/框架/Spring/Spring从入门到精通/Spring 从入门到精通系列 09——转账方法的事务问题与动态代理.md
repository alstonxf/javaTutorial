# Spring 从入门到精通系列 09——转账方法的事务问题与动态代理
本文由转账方法中存在的事务问题引出事务控制的方法，最后简述动态代理方法，实现统一维护。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
一、转账方法的事务问题（了解）
1.1 问题分析
1.2 创建 ConnectionUtils类
1.3 事务管理相关的工具类
1.4 更新事务控制
1.5 其他代码
二、动态代理

---


## 一、转账方法的事务问题（了解）

### 1.1 问题分析

为了说明事务问题，我们创建一个新的项目，并添加转账方法。

**账户的业务层接口及其实现类：**

```java
/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    /**
     * 转账
     * @param sourceName    转出账户名称
     * @param targetName    转入账户名称
     * @param money         转账金额
     */
    public void transfer(String sourceName, String targetName, Float money) {
	    //1. 根据名称查询转出账户
	    Account source = accountDao.findAccountByName(sourceName);
	    //2. 根据名称查询转入账户
	    Account target = accountDao.findAccountByName(targetName);
	    //3. 转出账户减钱
	    source.setMoney(source.getMoney()-money);
	    //4. 转入账户加钱
	    target.setMoney(target.getMoney()+money);
	    //5. 更新转出账户
	    accountDao.updateAccount(source);  	
	    //6. 更新转入账户
	    accountDao.updateAccount(target);  
	}
}

```

**测试类：测试 aaa 向 bbb 转账100块**

```java
/**
 * 使用junit单元测试：测试配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {

    @Autowired
    private IAccountService as = null;

    @Test
    public void testTransfer(){
        as.transfer("aaa", "bbb", 100f);
    }
}

```

正常情况下，aaa 会向 bbb 成功转帐。但是如果说在业务层方法更新转入账户时发现异常，更新收款方账户则会出错。如： <img src="https://img-blog.csdnimg.cn/20210602093155727.png?#pic_left" alt="在这里插入图片描述" width="600"/>

<font color="red">**解决方案：** 需要使用 ThreadLocal 对象把 Connection 和当前线程绑定，从而使一个线程中只有一个能控制事务的对象（要么都发生，要么都不发生）</font>

---


### 1.2 创建 ConnectionUtils类

```java
/**
 * 连接的工具类，它用于从数据源中获取一个链接，并且实现和线程的绑定
 */
public class ConnectionUtils {

    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取当前线程上的连接
     * @return
     */
    public Connection getThreadConnection(){
        try{
            //1. 先从ThreadLocal上获取
            Connection conn = tl.get();
            //2. 判断当前线程上是否有连接
            if(conn == null){
                //3. 从数据源中获取一个链接，并且存入ThreadLocal中
                conn = dataSource.getConnection();
                tl.set(conn);
            }
            //4. 返回当前线程上的连接
            return conn;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 把连接和线程解绑
     */
    public void removeConnection(){
        tl.remove();
    }
}

```

**注意：** 这里需要说明，连接和线程解绑的方法 removeConnection()

  连接是从连接池中获取的，在web工程中，启动 Tomcat 服务器，这里就已经在连接池中初始化了连接的数量，从而在后期与数据库交互的时候不在跟数据库获取连接，保证了 Connection 的执行效率。而服务器中也存在一个技术叫做线程池技术，它的工作原理与连接池类似，我们每次访问都是从线程池中拿到线程以供我们使用，那么<font color="red">线程调用 close() 方法不是真正的关闭，而是把他自己还回池中。</font>**但由于线程中是绑着连接的，当我们把线程还回池之前应该将线程与连接解绑，否则下次获取到该线程时，连接依旧有，但是已经不能用了。**

---


### 1.3 事务管理相关的工具类

控制事务需要把 Connection对象的手动提交改成被动提交，setAutoCommit(false)

```java
/**
 * 和事务管理相关的工具类，它包含了，开启事务、提交事务、回滚事务和释放连接
 */
public class TransactionManager {

    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    /**
     * 开启事务
     */
    public void beginTransaction(){
        try{
            connectionUtils.getThreadConnection().setAutoCommit(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 提交事务
     */
    public void commit(){
        try{
            connectionUtils.getThreadConnection().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 回滚事务
     */
    public void rollback(){
        try{
            connectionUtils.getThreadConnection().rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 释放连接
     */
    public void release(){
        try{
            connectionUtils.getThreadConnection().close();//还回连接池中
            connectionUtils.removeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

```

---


### 1.4 更新事务控制

```java
/**
 * 业务的业务层实现类
 *
 * 事务控制应该都在业务层
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;
    private TransactionManager txManager;

	// set 方法：提供依赖注入
    public void setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
    }

    /**
     * 转账
     * @param sourceName    转出账户名称
     * @param targetName    转入账户名称
     * @param money         转账金额
     */
    public void transfer(String sourceName, String targetName, Float money) {
        try{
            //1. 开启事务
            txManager.beginTransaction();
            //2. 执行操作
                    //2.1. 根据名称查询转出账户
                    Account source = accountDao.findAccountByName(sourceName); // 1. 第一次事务，提交
                    //2.2. 根据名称查询转入账户
                    Account target = accountDao.findAccountByName(targetName); //  2. 第二次事务提交
                    //2.3. 转出账户减钱
                    source.setMoney(source.getMoney()-money);
                    //2.4. 转入账户加钱
                    target.setMoney(target.getMoney()+money);
                    //2.5. 更新转出账户
                    accountDao.updateAccount(source);  //  3. 第三次事务提交

                    int i = 1/0;  //  4. 报异常

                    //6. 更新转入账户
                    accountDao.updateAccount(target);  // 5. 事务不执行
            //3. 提交事务
            txManager.commit();
        }catch (Exception e){
            //5. 回滚操作
            txManager.rollback();
            e.printStackTrace();
        } finally {
            //6.释放连接
            txManager.release();
        }
    }
}

```

**运行结果：** <img src="https://img-blog.csdnimg.cn/20210602100343896.png?#pic_left" alt="在这里插入图片描述" width="800"/>

---


### 1.5 其他代码

**bean.xml：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--首先配置service业务层对象-->
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
        <!--注入dao-->
        <property name="accountDao" ref="accountDao"></property>
        <!--事务管理器-->
        <property name="txManager" ref="txManager"></property>
    </bean>

    <!--配置dao对象-->
    <bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl">
        <!--注入QueryRunner-->
        <property name="runner" ref="runner"></property>
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>

    <!--配置QueryRunner-->
    <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype"></bean>

    <!--配置数据源-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!--连接数据库的必备信息-->
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/springdb"></property>
        <property name="user" value="root"></property>
        <property name="password" value="000000"></property>
    </bean>


    <!--配置Connection的工具类，Connection-->
    <bean id="connectionUtils" class="com.itheima.utils.ConnectionUtils">
        <!--注入数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>

    <!--配置事务管理器-->
    <bean name="txManager" class="com.itheima.utils.TransactionManager">
        <!--注入-->
        <property name="connectionUtils" ref="connectionUtils"></property>
    </bean>
</beans>

```

**账户的持久层类：**

```java
/**
 * 账户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao {

    private QueryRunner runner;
    private ConnectionUtils connectionUtils;

    public void setRunner(QueryRunner runner) {
        this.runner = runner;
    }

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public void updateAccount(Account account) {
        try {
            runner.update(connectionUtils.getThreadConnection(),"update account set name = ?, money = ? where id = ?", account.getName(), account.getMoney(), account.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Account findAccountByName(String accountName) {
        try {
            List<Account> accounts = runner.query(connectionUtils.getThreadConnection(),
                    "select * from account where name = ?", new BeanListHandler<Account>(Account.class), accountName);
            if(accounts==null || accounts.size()==0){
                return null;
            }
            if(accounts.size()>1){
                throw new RuntimeException("结果集不唯一，数据有问题");
            }
            return accounts.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

```

---


## 二、动态代理
1. **特点：** 字节码随用随创建，随用随加载1. **作用：** 不修改源码的基础上对方法增强<li>**分类：** 
  1. 基于接口的动态代理1. 基于子类的动态代理 </li>
---


动态代理就不在这过多赘述了，最终实现动态代理，以后只需要修改下面代码即可，不用修改所有源码，**相当于统一维护。**

```java
public class Beanfactory {
    private IAccountService accountService;
    private TransactionManager txManager;
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }
    public final void setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
    }
    public IAccountService getAccountService(){
        return (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                accountService.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object rtValue = null;
                        try{
                            txManager.beginTransaction();
                            rtValue = method.invoke(accountService, args);
                            txManager.commit();
                            return rtValue;
                        }catch (Exception e){
                            txManager.rollback();
                            throw new RuntimeException(e);
                        }finally {
                            txManager.release();
                        }
                    }
                });
    }
}

```

---


读者们想了解，可以点击右方链接查看：👉 https://www.cnblogs.com/yulinfeng/p/7811965.html</a>
# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117459286