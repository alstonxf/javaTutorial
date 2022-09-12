# Spring 从入门到精通系列 02——程序的耦合问题及工厂模式解耦
本文针程序的耦合问题及工厂模式解耦进行分析，并着重强调单例对象与多例对象的进行区分。

<img src="https://img-blog.csdnimg.cn/20210531194346342.png#pic_center" alt="在这里插入图片描述" width="380"/>

---


 # 文章目录
程序的耦合问题及工厂模式解耦
一、程序的耦合
二、工厂模式解耦
三、分析工厂模式中的问题并改造

---


## 程序的耦合问题及工厂模式解耦

### 一、程序的耦合

**耦合**：程序间的依赖关系包括，以下两个方面：

1. 类之间的依赖
1. 方法间的依赖

**解耦**：降低程序间的依赖关系

1. 实际开发中：<font color="red">应该做到，编译期不依赖，运行时才依赖。</font>

**解耦的思路**：

1. **第一步：使用反射来创建对象，而避免使用new关键字。**
1. **第二步：通过读取配置文件来获取要创建的对象全限定类名。**
   <font color="red">**注意**：我们应该明确的是，不能彻底的解决依赖的问题，只能通过方式方法来降低依赖。</font>

那么我们下面通过例子说明什么是编译期依赖，以及解耦的思路：

---


创建一个空项目并写一个类，通过该类连接数据库进行查询操作。

**pom文件中导入坐标：**

```xml
<dependencies>
	<dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.6</version>
    </dependency>
</dependencies>

```

**编写测试 jdbc 的类：**

```java
public class jdbcDemo {
    public static void main(String[] args) throws Exception {
        //1.注册驱动
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        //2.获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springdb", "root", "000000");
        //3.获取操作数据库的预处理对象
        PreparedStatement pstm = conn.prepareStatement("select * from account");
        //4.执行sql，得到结果集
        ResultSet rs = pstm.executeQuery();
        //5.遍历结果集
        while(rs.next()){
            System.out.println(rs.getString("name"));
        }
        //6.释放资源
        rs.close();
        pstm.close();
        conn.close();
    }
}

```

**测试结果：**

 <img src="https://img-blog.csdnimg.cn/20210531203157152.png?pic_left" alt="在这里插入图片描述" width="300"/> 

但是如果对于以上案例，如果缺失数据库驱动（pom 没有引入驱动 jar 包或者引入版本有问题等），那么将导致以下现象： <img src="https://img-blog.csdnimg.cn/2021053120391730.png?pic_left" alt="在这里插入图片描述" width="800"/> 

以上由于缺少驱动，导致无法进行编译，那么这就是所谓**编译期的依赖**。

那么在正常 jdbc 连接数据库的时候，我们通常采用 class.forName(com.mysql.jdbc.Driver) 的形式来注册驱动。 <img src="https://img-blog.csdnimg.cn/20210531205015918.png??pic_left" alt="在这里插入图片描述" width="500"/> 

那么这样的操作方式，就使得程序不再依赖某个具体的驱动类。

  这样的程序虽说在没有数据库驱动 jar 包的情况下依然运行不了，但是在编译器至少不报错，这也就做到了我们上文所说的：<font color="red">编译期不依赖，运行时才依赖。</font>

因此我们采用以下方式来创建对象：
1. **在创建对象的时候，使用反射来创建对象，避免使用 "new"** 但是也存在问题。如： com.mysql.jdbc.Driver 这个字符串是固定的，如果在日后采用其他数据库，还需要修改驱动，因此还需要以下步骤。
1. **通过读取配置文件来获取要创建的对象全限定类名**
---


### 二、工厂模式解耦

为了模拟日常开发中我们所面临的耦合问题，我们创建一个新的工程，工程目录如下：

 <img src="https://img-blog.csdnimg.cn/20210531211312833.png?#pic_left" alt="在这里插入图片描述"/> 

**具体代码如下：**

```java
/**
 * 持久层接口
 */
public interface IAccountDao {
    void insertAccount();
}

/**
 * 账户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao {
    public void insertAccount() {
        System.out.println("保存了账户");
    }
}

/**
 * 业务层的接口
 */
public interface IAccountService {

    /**
     * 模拟保存账户
     */
    void InsertAccount();
}

/**
 * 业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao = new AccountDaoImpl();

    public void InsertAccount() {
        accountDao.insertAccount();
    }
}

/**
 * 模拟表现层，用于调用业务层
 */
public class Client {
    public static void main(String[] args) {
        IAccountService accountService = new AccountServiceImpl();
        accountService.InsertAccount();
    }
}

```

**测试结果：** 

<img src="https://img-blog.csdnimg.cn/20210531220713706.png#pic_left" alt="在这里插入图片描述" width="300"/> 

以上显示正常输出，但是也存在以下两个依赖的问题： <img src="https://img-blog.csdnimg.cn/20210531221426798.png#pic_left" alt="在这里插入图片描述"/> 下面我们通过应用 **工厂模式** 解决以上问题。

---


  在实际开发中我们可以把三层的对象都使用配置文件配置起来，当启动服务器应用加载的时候，让一个类中的方法通过读取配置文件，把这些对象创建出来并存起来。在接下来的使用的时候，直接拿过来用就好了。那么，这个读取配置文件、创建和获取三层对象的类就是工厂。

**那么如何创建 Bean 对象的工厂呢？** 首先我们应明白以下概念：
- Bean：在计算机英语中，有可重用组件的含义。
- javaBean： 用 java 语言编写的可重用组件。（ 范围：javaBean > 实体类）
<font color="red">创建 Bean工厂的对象就是创建我们的 service 和 dao 对象的。</font>

创建步骤如下：

1、需要配置文件，来配置service和dao
      配置的内容：唯一标志 = 全限定类名（key=value）
2、通过读取配置文件中配置的内容，反射创建对象
我的配置文件可以是 xml也可以是properties
其中，==配置文件可以选择 properties 也可以选择 xml，本文采用 properties 的方式。==

**BeanFactory 代码如下：**

```java
public class BeanFactory {
    //定义一个Properties对象
    private static Properties props;

    //使用静态代码块为Properties对象赋值
    static {
        try {
            //实例化对象
            props = new Properties();
            //获取properties文件流对象
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            props.load(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化properties失败");
        }
    }

    /**
     * 根据 bean名称获取 bean单例对象
     */
    public static Object getBean(String beanName){
        Object bean = null;
        try{
            String beanPath = props.getProperty(beanName);
            bean = Class.forName(beanPath).newInstance();//反射方式创建对象
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}

```

**bean.properties：**

```properties
accountService = com.itheima.service.impl.AccountServiceImpl
accountDao = com.itheima.dao.impl.AccountDaoImpl

```

==**注意：** 文件流对象 BeanFactory 的文件流不能采用 new FileInputStream() 的形式来获取，应采用类加载器的方式获取。因为 new 形式创建需要路径作为参数，而无论是什么路径在项目部署之后都会消失，而在 resources 下的文件，部署后会成为类根目录下的文件，直接可采用类加载器的方式加载即可。==

**解决业务层以及表现层的依赖：**

```java
//１. 业务层 service
private IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");
//1. 持久层 dao
private IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDao");

```

在控制台输出 Service 和 Dao 层对象，并打印 InsertAccount() 方法的结果如下： <img src="https://img-blog.csdnimg.cn/20210531225730929.png#pic_left" alt="在这里插入图片描述" width="400"/> 

好的我们继续，当删除其中一个实现类，如删除 AccountDaoImpl，目录如下：

 <img src="https://img-blog.csdnimg.cn/20210531230118386.png?x#pic_left" alt="在这里插入图片描述" width="250"/>

 但是我们依旧能运行 Client，与我们上文所说的：<font color="red">编译期不依赖，运行时才依赖。</font>相呼应，这就是所谓的工厂模式进行解耦！ <img src="https://img-blog.csdnimg.cn/20210531230532111.png#pic_left" alt="在这里插入图片描述" width="600"/>

---


### 三、分析工厂模式中的问题并改造

当我们循环打印五次 service 层对象的时候，我们发现每次都需要创建新的对象，即 **多例对象**。 <img src="https://img-blog.csdnimg.cn/20210531231715666.png?#pic_left" alt="在这里插入图片描述" width="400"/>   

多例模式与单例模式相比，**区别在于对于类成员的操作**，比如：在执行 AccountDaoImpl 存在类成员 i，并且每次执行InsertAccount 都让其加一，即修改类成员。那么两种模式对于此类操作就会展示出不同的效果。

**AccountDaoImpl.java：**

```java
/**
 * 账户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao {
    private int i = 0;
    public void insertAccount() {
        System.out.println("保存了账户");
        System.out.println(i);
        i++;
    }
}

```

  单例对象指挥创建一次，从而类中的成员指挥初始化一次，因为单例对象存在 **线程问题**，那么每次我们执行 AccountDaoImpl 就会看到 i 在一次次的累加。（这也就是 Servlet 不要定义类成员的原因）

  但是，多例由于被创建多次，执行效率肯定不如单例对象高。但在日常开发中，由于我们很少定义在业务层 service 和持久层 dao 的可修改的类成员，因此也就 **不存在上文所说的线程问题**，通常的方法也就是让 i 定义在方法里面，即： <img src="https://img-blog.csdnimg.cn/20210531233811189.png#pic_left" alt="在这里插入图片描述" width="250"/> 

由于我们在实例化 service 对象的时候，在 BeanFactory 对象中，每次都会调用以下代码进行构造，即每次都会重新生成 bean： <img src="https://img-blog.csdnimg.cn/20210531234219125.png#pic_left" alt="在这里插入图片描述" width="500"/> 

因此我们应该用到 map 容器 key、value 的方式去创建 bean，那么就可以解决了多例对象的问题。

<font color="red">注：我们最终使用的就是单例对象，或者说单例对象更加常用。</font>

---

**修改 BeanFactory 代码如下：**

```java
public class BeanFactory {
    //定义一个Properties对象
    private static Properties props;

    //定义一个map，用于存放我们要创建的对象，即容器。
    private static Map<String, Object> beans;

    //使用静态代码块为Properties对象赋值
    static {
        try {
            //实例化对象
            props = new Properties();
            //获取properties文件流对象
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            props.load(in);
            //实例化容器
            beans = new HashMap<String, Object>();
            //取出配置文件中所有的key
            Enumeration keys = props.keys();
            //遍历枚举
            while(keys.hasMoreElements()){
                //取出每个key
                String key = keys.nextElement().toString();
                //根据key获取value
                String beanPath = props.getProperty(key);
                //反射创建对象
                Object value = Class.forName(beanPath).newInstance();
                //把key和value存入容器
                beans.put(key, value);
            }

        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化properties失败");
        }
    }

    /**
     * 根据 bean名称获取 bean单例对象
     */
    /*public static Object getBean(String beanName){
        Object bean = null;
        try{
            String beanPath = props.getProperty(beanName);
            bean = Class.forName(beanPath).newInstance();//反射方式创建对象
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }*/
    public static Object getBean(String beanName){
        return beans.get(beanName);
    }
}

```

我们将上文分析到的问题避免去发生：

① 让成员在成员方法中 

② 修改多例对象成单例

**AccountDaoImpl.java:**

```java
public class AccountDaoImpl implements IAccountDao {

    public void insertAccount() {
        int i = 0;
        System.out.println("保存了账户");
        System.out.println(i);
        i++;
    }
}

```

**模拟客户端：**

```java
public class Client {
	public static void main(String[] args) {
	    for (int i = 0; i < 5; i ++){
	        IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");
	        System.out.println(accountService);
	        accountService.InsertAccount();
	    }
	}
}

```

**运行结果如下：** <img src="https://img-blog.csdnimg.cn/20210531235055840.png#pic_left" alt="在这里插入图片描述" width="350"/>

---


本文针对 Spring 程序的耦合及解耦进行了讲解，如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117432123