# SpringMVC 从入门到精通系列 02——请求参数的绑定
 # 文章目录
一、请求参数的绑定说明
1.1 绑定机制
1.2 支持的数据类型
二、基本数据类型和字符串类型
三、实体类型（JavaBean）
四、给集合属性数据封装
五、请求参数中文乱码的解决
六、自定义类型转换器
七、在控制器中使用原生的 ServletAPI 对象

---


## 一、请求参数的绑定说明

### 1.1 绑定机制
1. 表单提交的数据都是 <mark>k=v</mark> 格式的 username=haha&amp;password=1231. SpringMVC的参数绑定过程是把表单提交的请求参数，作为控制器中方法的参数进行绑定的1. 要求：提交表单的 name 和参数的名称是相同的
### 1.2 支持的数据类型
1. 基本数据类型和字符串类型1. 实体类型（JavaBean）1. 集合数据类型（List、map集合等）
---


## 二、基本数据类型和字符串类型
1.  提交表单的name和参数的名称是相同的 <li> **区分大小写** <pre><code class="prism language-html"><a href="params/testParams01?username=xiaowang&amp;password=1234">点击一下</a>
</code></pre> <pre><code class="prism language-java">@Controller
@RequestMapping(path = "/params")
public class ParamsController {

	@RequestMapping(path = "/testParams01")
	public String testParams01(String username, String password){
	   System.out.println("testParams方法执行了");
	   System.out.println(username);
	   System.out.println(password);
	   return "success";
	}
}
</code></pre> 测试结果： <img src="https://img-blog.csdnimg.cn/2021060309020758.png#pic_left" alt="在这里插入图片描述" width="400"/> </li>
---


## 三、实体类型（JavaBean）
1.  提交表单的 name 和 JavaBean 中的属性名称需要一致 <li> 如果一个 JavaBean 类中包含其他的引用类型，那么表单的 name 属性需要编写成：<mark>对象.属性</mark> 例如：address.name **jsp：** <pre><code class="prism language-html"><form action="params/saveAccount" method="post">
    姓名：<input type="text" name="username"><br>
    密码：<input type="text" name="password"><br>
    金额：<input type="text" name="money"><br>
    用户姓名：<input type="text" name="user.username"><br>
    用户年龄：<input type="text" name="user.age"><br>
    <input type="submit" value="提交" />
</form>
</code></pre> **Controller：** <pre><code class="prism language-java">@Controller
@RequestMapping(path = "/params")
public class ParamsController {

    @RequestMapping(path = "/saveAccount")
    public String testParams02(Account account){
        System.out.println(account);
        System.out.println("javaBean对象封装成功！！");
        return "success";
    }
}
</code></pre> **页面效果：** <img src="https://img-blog.csdnimg.cn/20210603091655349.png" alt="在这里插入图片描述"/> **测试结果：** <img src="https://img-blog.csdnimg.cn/20210603091740487.png#pic_left" alt="在这里插入图片描述" width="800"/> <font color="red">注意：引用对象要有 set 与 get 方法的生成。</font> </li>
---


## 四、给集合属性数据封装

JSP页面编写方式：list[0].属性、map[‘value’].属性

**jsp：**

```html
<form action="params/saveAccount" method="post">
	姓名：<input type="text" name="username"><br>
	密码：<input type="text" name="password"><br>
	金额：<input type="text" name="money"><br>
	<br>
	用户1姓名：<input type="text" name="users[0].username"><br>
	用户1年龄：<input type="text" name="users[0].age"><br>
	用户2姓名：<input type="text" name="users[1].username"><br>
	用户2年龄：<input type="text" name="users[1].age"><br>
	<br>
	用户姓名：<input type="text" name="map['one'].username"><br>
	用户年龄：<input type="text" name="map['one'].age"><br>
	<input type="submit" value="提交" />
</form>

```

**实体类：** <img src="https://img-blog.csdnimg.cn/20210603093204206.png#pic_left" alt="在这里插入图片描述" width="350"/> **测试结果：** <img src="https://img-blog.csdnimg.cn/20210603093458791.png?#pic_left" alt="在这里插入图片描述" width="330"/>

<img src="https://img-blog.csdnimg.cn/20210603093825541.png" alt="在这里插入图片描述"/>

---


## 五、请求参数中文乱码的解决

**在 web.xml 中配置：**

```java
<!--配置解决中文乱码的过滤器-->
<filter>
	<filter-name>characterEncodingFilter</filter-name>
	<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
	<init-param>
		<param-name>encoding</param-name>
		<param-value>UTF-8</param-value>
	</init-param>
</filter>
<filter-mapping>
	<filter-name>characterEncodingFilter</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>

```

**测试结果：** <img src="https://img-blog.csdnimg.cn/20210603092316518.png" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/20210603092336478.png#pic_left" alt="在这里插入图片描述" width="800"/>

---


## 六、自定义类型转换器
1. 表单提交的任何数据类型全部都是字符串类型，但是后台定义Integer类型，数据也可以封装上，说明Spring框架内部会默认进行数据类型转换。1. 如果想自定义数据类型转换，可以实现Converter的接口
**自定义类型转换器：**

```html
import org.springframework.core.convert.converter.Converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 把字符串转换日期
 */
public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        if(s == null){
            throw new RuntimeException("请输入参数");
        }
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        try {
            return df.parse(s);
        } catch (Exception e) {
            throw new RuntimeException("数据类型转换出现问题！！");
        }
    }
}

```

**注册自定义类型转换器，在springmvc.xml配置文件中编写配置：**

```java
<!--配置自定义类型的数据转换器-->
<bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
   <property name="converters">
       <set>
           <bean class="com.itheima.utils.StringToDateConverter"></bean>
       </set>
   </property>
</bean>

<!--开启springMvc框架注解的支持-->
<mvc:annotation-driven conversion-service="conversionService"/>

```

**jsp：**

```html
<form action="params/saveUser" method="post">
    姓名：<input type="text" name="username"><br>
    年龄：<input type="text" name="age"><br>
    生日：<input type="text" name="date"><br>
    <input type="submit" value="提交" />
</form><br>

```

**Controller：**

```xml
@Controller
@RequestMapping(path = "/params")
public class ParamsController {

    @RequestMapping(path = "/saveUser")
    public String testParams03(User user){
        System.out.println(user);
        System.out.println("自定义javaBean对象封装成功！！");
        return "success";
    }
}

```

**测试结果：** <img src="https://img-blog.csdnimg.cn/20210603095300931.png" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/20210603095315460.png" alt="在这里插入图片描述"/>

---


## 七、在控制器中使用原生的 ServletAPI 对象

```java
<a href="params/testGetServlet">获取到servlet</a>

```

```xml
@Controller
@RequestMapping(path = "/params")
public class ParamsController {
	@RequestMapping(path = "/testGetServlet")
	public String testParams04(HttpServletRequest request, HttpServletResponse response){
		System.out.println("获取Servlet成功！！");
		System.out.println(request);
		System.out.println(request.getSession());
		System.out.println(request.getSession().getSessionContext());
		System.out.println(response);
		return "success";
	}
}

```
# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117493927