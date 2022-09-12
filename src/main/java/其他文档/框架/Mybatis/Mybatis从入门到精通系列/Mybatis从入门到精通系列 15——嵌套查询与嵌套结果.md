# Mybatis从入门到精通系列 15——嵌套查询与嵌套结果
Mybatis 在映射文件中加载关联关系对象主要通过两种方式：嵌套查询与嵌套结果。
1. **嵌套查询是指通过执行另外一条 SQL 映射语句来返回预期的复杂类型；**1. **嵌套结果是使用嵌套结果映射来处理重复的联合结果的子集。**
本文我们针对 Mybatis 的多对多的嵌套查询与嵌套结果进行详细分析。

<img src="https://img-blog.csdnimg.cn/20210519115233329.png#pic_center" alt="在这里插入图片描述" width="700"/>

---


 # 文章目录
一、环境准备
1.1 建立数据库
1.2 项目工程初始化
二、多对多实现嵌套查询
三、多对多实现嵌套结果

---


## 一、环境准备

### 1.1 建立数据库

下面拿商品表和订单表举例说明如何实现嵌套查询与嵌套结果。一个订单包含多种商品，一个商品也可以属于多个订单。具体关系如下：

<img src="https://img-blog.csdnimg.cn/20210526231751827.png?#pic_center" alt="在这里插入图片描述" width="750"/>

---

下面在 mysql 中建立 t_goods 表、t_orders 表以及 t_goods_orders 表，并在 t_goods_orders 设立外键。 <img src="https://img-blog.csdnimg.cn/20210526232135891.png?#pic_left" alt="在这里插入图片描述" width="650"/> <img src="https://img-blog.csdnimg.cn/20210526232230812.png?#pic_left" alt="在这里插入图片描述" width="650"/> <img src="https://img-blog.csdnimg.cn/20210526232414498.png?#pic_left" alt="在这里插入图片描述" width="650"/> <img src="https://img-blog.csdnimg.cn/20210526232519308.png?#pic_left" alt="在这里插入图片描述" width="650"/> 

**注意：t_goods** 表中的 **customer_id** 属性，以及 **t_goods_orders** 中的 **number** 在本文案例中只是一个正常的属性，没有特殊含义。

---


**添加数据：**

​	<img src="https://img-blog.csdnimg.cn/20210527133233708.png?#pic_left" alt="在这里插入图片描述" width="400"/> <img src="https://img-blog.csdnimg.cn/20210527133259909.png?#pic_left" alt="在这里插入图片描述" width="400"/>

<img src="https://img-blog.csdnimg.cn/20210527133315239.png?#pic_left" alt="在这里插入图片描述" width="400"/>



---


### 1.2 项目工程初始化

**工程目录：** <img src="https://img-blog.csdnimg.cn/20210526233148540.png#pic_left" alt="在这里插入图片描述" width="280"/>

---


**导入依赖：**

在pom文件中带入以下依赖：

```xml
<dependencies>
 	<dependency>
	     <groupId>org.mybatis</groupId>
	     <artifactId>mybatis</artifactId>
	     <version>3.4.5</version>
	 </dependency>
	 <dependency>
	     <groupId>mysql</groupId>
	     <artifactId>mysql-connector-java</artifactId>
	     <version>5.1.6</version>
	 </dependency>
	 <dependency>
	     <groupId>log4j</groupId>
	     <artifactId>log4j</artifactId>
	     <version>1.2.12</version>
	 </dependency>
	 <dependency>
	     <groupId>junit</groupId>
	     <artifactId>junit</artifactId>
	     <version>4.10</version>
	 </dependency>
</dependencies>

```

注：数据库我使用的版本是 5.7.27，读者可根据自己数据库的版本导入相应的依赖。

---


**主配置文件：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <!--引入外部配置文件-->
    <properties resource="jdbcConfig.properties" />

    <!--配置开启二级缓存-->
    <settings>
        <setting name="cacheEnabled" value="true" />
    </settings>

    <!--使用typeAliases配置别名，他只能配置domain中类的别名-->
    <typeAliases>
        <package name="com.itheima.domain" />
    </typeAliases>

    <!--配置环境-->
    <environments default="mysql">
        <!--配置mysql的环境-->
        <environment id="mysql">
            <!--配置事务-->
            <transactionManager type="JDBC" />
            <!--配置连接池-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!--配置映射文件的信息-->
    <mappers>
      <package name="com.itheima.dao" />
    </mappers>
</configuration>

```

---


**数据库外部配置文件：**

```xml
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatishomework?useUnicode=true&amp;characterEncoding=UTF-8
jdbc.username=root
jdbc.password=000000

```

---


**log4j.properties：**

``` 
</code>
# Set root category priority to INFO and its only appender to CONSOLE.
#log4j.rootCategory=INFO, CONSOLE            debug   info   warn error fatal
log4j.rootCategory=debug, CONSOLE, LOGFILE

# Set the enterprise logger category to FATAL and its only appender to CONSOLE.
log4j.logger.org.apache.axis.enterprise=FATAL, CONSOLE

# CONSOLE is set to be a ConsoleAppender using a PatternLayout.
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=%d{ISO8601} %-6r [%15.15t] %-5p %30.30c %x - %m\n

# LOGFILE is set to be a File appender using a PatternLayout.
log4j.appender.LOGFILE=org.apache.log4j.FileAppender
log4j.appender.LOGFILE.File=d:\axis.log
log4j.appender.LOGFILE.Append=true
log4j.appender.LOGFILE.layout=org.apache.log4j.PatternLayout
log4j.appender.LOGFILE.layout.ConversionPattern=%d{ISO8601} %-6r [%15.15t] %-5p %30.30c %x - %m\n

```

---


**商品和订单实体类：**

```java
public class Goods implements Serializable {

    private Integer id;
    private String goodName;
    private Float price;

    //一对多映射
    private List<Orders> ordersList;

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodName='" + goodName + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}

```

```java
public class Orders implements Serializable {
    private Integer id;
    private Integer customerId;
    private Float totalPrice;

    //一对多映射
    private List<Goods> goodsList;

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

```

---


## 二、多对多实现嵌套查询

**需求：** 实现查询一个订单，将其所包含的商品也查询出来。

**分析：**   查询订单我们需要用到 t_goods 表，但订单分配的商品的信息我们并不能直接找到商品信息，而是要通过中间表( t_goods_order 表)才能关联到商品信息。

---


分别在 IOrdersDao 和 IGoodsDao 添加以下方法：

**IOrdersDao .java：**

```java
public interface IOrdersDao {

    /**
     * 嵌套查询方式：根据订单Id查询当前订单所对应的商品信息
     */
    public Orders findOrdersNestedQueryByOrdersId(Integer id);

}

```

**IGoodsDao.java：**

```java
public interface IGoodsDao {

    /**
     * 根据 id 查询商品
     */
    public Goods findGoodsById(Integer id);

}

```

---


分别在 IOrdersDao.xml 和 IGoodDao.xml 中编写如下代码：

**IOrdersDao.xml：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IOrdersDao">

    <!--嵌套查询的 resultMap-->
    <resultMap id="OrdersMap2" type="orders">
        <id property="id" column="id" />
        <result property="customerId" column="customer_id" />
        <result property="totalPrice" column="totalPrice" />
        <collection property="goodsList" column="id" ofType="goods"
                    select="com.itheima.dao.IGoodsDao.findGoodsById">
        </collection>
    </resultMap>

    <select id="findOrdersNestedQueryByOrdersId" parameterType="Integer" resultMap="OrdersMap2">
        select * from t_orders where id = #{id}
    </select>

</mapper>

```

  在 <resultMap> 中使用了 <collection> 元素来映射多对多关联关系，其中 property 属性表示订单持久化类的商品属性，ofType 属性表示集合中的数据为商品类型，而 column 的属性值会作为参数执行 IGoodDao 中定义的 id 为 findGoodsById 的执行语句来查询订单中的商品信息。

**IGoodsDao.xml：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IGoodsDao">

    <select id="findGoodsById" parameterType="Integer" resultType="Goods">
        select * from t_goods where id in(
            select good_id from t_goods_orders where order_id = #{id}
        )
    </select>

</mapper>

```

在 IGoodsDao 中 id 为 findGoodsById 的执行语句中，该语句的 SQL 会根据订单的 id 查询与该订单所关联的商品信息，当然这里是借助了中间表查询的商品信息。

---


**编写测试类：**

```java
public class Client {
    private InputStream in;
    private SqlSession sqlSession;
    private IOrdersDao ordersDao;
    private IGoodsDao goodsDao;

    @Before//单元测试之前执行
    public void init() throws Exception{
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象
        ordersDao = sqlSession.getMapper(IOrdersDao.class);
        goodsDao = sqlSession.getMapper(IGoodsDao.class);
    }

    @After//单元测试之后执行
    public void destroy() throws Exception {
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试嵌套查询：根据订单Id查询当前订单所对应的商品信息
     */
    @Test
    public void testNestedQuery(){
        Orders orders = ordersDao.findOrdersNestedQueryByOrdersId(1);
        System.out.println("------------------订单编号为 1 的订单以及商品信息如下：----------------");
        System.out.println(orders);
        System.out.println(orders.getGoodsList());
    }

}

```

**测试结果：**

 <img src="https://img-blog.csdnimg.cn/20210527000117928.png#pic_left" alt="在这里插入图片描述" width="800"/>

---


## 三、多对多实现嵌套结果

在 ICustomerDao 中添加以下方法：

**IOrdersDao.java：**

```java
public interface IOrdersDao {

    /**
     * 嵌套结果：根据订单Id查询当前订单所对应的商品信息
     */
    public Orders findOrdersNestedResultsByOrdersId(Integer id);
    
}

```

---


**IOrdersDao.xml：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.IOrdersDao">

    <!--嵌套结果的 resultMap-->
    <resultMap id="OrdersMap1" type="orders">
        <id property="id" column="id" />
        <result property="customerId" column="customer_id" />
        <result property="totalPrice" column="totalPrice" />
        <collection property="goodsList" ofType="goods">
            <id property="id" column="gid" />
            <result property="goodName" column="goodName" />
            <result property="price" column="price" />
        </collection>
    </resultMap>

    <!--嵌套结果-->
    <select id="findOrdersNestedResultsByOrdersId" resultMap="OrdersMap1" parameterType="Integer">
        select o.*, g.id as gid, g.goodName, g.price
        from t_orders o, t_goods g, t_goods_orders og
        where og.good_id = g.id
          and og.order_id = o.id
          and o.id=#{id}
    </select>
 
</mapper>

```

---


**添加测试方法：**

```java
/**
 *  测试嵌套结果：根据订单Id查询当前订单所对应的商品信息
 */
@Test
public void testNestedResult(){
    Orders orders = ordersDao.findOrdersNestedResultsByOrdersId(1);
    System.out.println("------------------订单编号为 1 的订单以及商品信息如下：----------------");
    System.out.println(orders);
    System.out.println(orders.getGoodsList());
}

```

**测试结果：**

 <img src="https://img-blog.csdnimg.cn/20210527072802645.png#pic_left" alt="在这里插入图片描述" width="800"/>

# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117022226