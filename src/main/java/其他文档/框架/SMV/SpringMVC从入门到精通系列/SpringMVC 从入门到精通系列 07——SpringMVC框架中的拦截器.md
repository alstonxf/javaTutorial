# SpringMVC 从入门到精通系列 07——SpringMVC框架中的拦截器
 # 文章目录
1. 拦截器概述
2. 自定义拦截器步骤
3. HandlerInterceptor接口中的方法
4. 配置多个拦截器

---


## 1. 拦截器概述
1. SpringMVC框架中的拦截器用于对处理器进行 <mark>预处理</mark> 和 <mark>后处理</mark> 的技术。1. 可以定义拦截器链，连接器链就是将拦截器按着一定的顺序结成一条链，在访问被拦截的方法时，拦截器链中的拦截器会按着定义的顺序执行。<li>拦截器和过滤器的功能比较类似，有如下区别： 
  <ol>1. 过滤器是Servlet规范的一部分，任何框架都可以使用过滤器技术。1. 拦截器是 SpringMVC 框架独有的。1. 过滤器配置了 /*，可以拦截任何资源。1. 拦截器只会对控制器中的方法进行拦截。
---


## 2. 自定义拦截器步骤
<li> **创建类，实现HandlerInterceptor接口，重写需要的方法。** 
  <ol>1. return true 放行，执行下一个拦截器。如果没有，执行controller中的方法1. return false 不放行。
```java
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 预处理，在controller方法执行前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor执行了。。");
        //成功放行
        return true;
        //不放行
        //request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
       	//return false;
    }
}

```

**在springmvc.xml中配置拦截器类。**

```xml
<!--配置拦截器-->
<mvc:interceptors>
    <!--配置第一个拦截器-->
    <mvc:interceptor>
        <!--要拦截的方法-->
        <mvc:mapping path="/user/*"/>
        <!--不要拦截的方法-->
        <!--<mvc:exclude-mapping path=""/>-->
        <!--配置拦截器对象-->
        <bean class="com.itheima.interceptor.MyInterceptor"></bean>
    </mvc:interceptor>
</mvc:interceptors>

```

---


<font size="5">演示如下：</font>

**工程目录：** <img src="https://img-blog.csdnimg.cn/img_convert/a1999e2ad46583335f153fa3521cae46.png#pic_left" alt="在这里插入图片描述" width="300"/>

**Controller：**

```java
@Controller
@RequestMapping(path = "/user")
public class  UserController {

    @RequestMapping(path = "/testInterceptor")
    public String testInterceptor(){
        System.out.println("testInterceptor方法执行了");

        return "success";
    }
}

```

**演示页面：**

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>拦截器</h3>

<a href="user/testInterceptor">拦截器</a>
</body>
</html>

```

**成功页面：**

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>执行成功</h3>

</body>
</html>

```

**演示效果：** <img src="https://img-blog.csdnimg.cn/img_convert/34107035b2c4af2fbe08ed6d197f3741.png#pic_left" alt="在这里插入图片描述" width="250"/> <img src="https://img-blog.csdnimg.cn/img_convert/95ab196a94279e2c27c4f0c5df9fddac.png" alt="在这里插入图片描述"/>

---


## 3. HandlerInterceptor接口中的方法
<li>**preHandle** 方法是controller方法 <mark>执行前</mark>拦截的方法 
  <ol>1. 可以使用 request 或者 response 跳转到指定的页面1. return true放行，执行下一个拦截器，如果没有拦截器，执行controller中的方法。1. return false不放行，不会执行controller中的方法。1. 可以使用request或者response跳转到指定的页面1. 如果指定了跳转的页面，那么controller方法跳转的页面将不会显示。1. request 或者 response不能再跳转页面了
**代码演示：**

```java
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 预处理，在controller方法执行前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor执行了。。。前");
        return true;
    }

    /**
     * 后处理方法，controller方法执行后，success.jsp执行之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor执行了。。。后");
    }

    /**
     * success.jsp执行后，该方法会执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyInterceptor执行了。。。最后");
    }
}

```

**成功页面输出信息：**

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>执行成功</h3>
<%System.out.println("success.jsp");%>
</body>
</html>

```

**演示结果：** <img src="https://img-blog.csdnimg.cn/img_convert/fe37302437ed469001f4ddefa69ad341.png#pic_left" alt="在这里插入图片描述" width="270"/>

---


## 4. 配置多个拦截器

```xml
<!--配置拦截器-->
<mvc:interceptors>
    <!--配置第一个拦截器-->
    <mvc:interceptor>
        <!--要拦截的方法-->
        <mvc:mapping path="/user/*"/>
        <!--不要拦截的方法-->
        <!--<mvc:exclude-mapping path=""/>-->
        <!--配置拦截器对象-->
        <bean class="com.itheima.interceptor.MyInterceptor"></bean>
    </mvc:interceptor>

    <!--配置第二个拦截器-->
    <mvc:interceptor>
        <!--要拦截的方法-->
        <mvc:mapping path="/user/*"/>
        <!--不要拦截的方法-->
        <!--<mvc:exclude-mapping path=""/>-->
        <!--配置拦截器对象-->
        <bean class="com.itheima.interceptor.MyInterceptor2"></bean>
    </mvc:interceptor>
</mvc:interceptors>

```

拦截顺序是 <mark>根据配置里面的拦截顺序进行拦截</mark>，放行也是从最深处向外放行，具体图示如下：

<img src="https://img-blog.csdnimg.cn/img_convert/66eec0d2c13941f7299dead83bf289b3.png" alt="在这里插入图片描述"/>

演示效果如下： <img src="https://img-blog.csdnimg.cn/img_convert/8b7e6db8be07f654fb93b5ffd16b5a86.png#pic_left" alt="在这里插入图片描述" width="320"/>
# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/118945069