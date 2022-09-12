# SpringMVC 从入门到精通系列 04——返回值分类和转发和重定向的实现
 # 文章目录
一、 返回值分类
1.1 返回字符串
1.2 返回值是 void
1.3 返回值是ModelAndView对象
二、SpringMVC框架提供的转发和重定向
1.1 forward 请求转发
2.2 redirect 重定向
三、响应json数据之过滤静态资源

---


## 一、 返回值分类

### 1.1 返回字符串
 1、Controller 方法返回字符串可以指定逻辑视图的名称，根据视图解析器为物理视图的地址。 

```java
@RequestMapping(value="/hello")
public String sayHello() {
	System.out.println("Hello SpringMVC!!");
	// 跳转到 success.js p页面
	return "success";
}

```

2、具体的应用场景

**jsp**：

```html
<a href="user/testString">testString</a>
```

**Controller**：

```java
@Controller
@RequestMapping(path = "/user")
public class UserController {

    /**
     * 返回字符串
     * @param model
     * @return
     */
    @RequestMapping("/testString")
    public String testString(Model model){
        System.out.println("testString方法执行了");
        //模拟从数据库中查询出user对象
        User user = new User();
        user.setUsername("admin");
        user.setPassword("111");
        user.setAge(13);
        model.addAttribute("user", user);
        return "success";
    }
}

```
**成功页面**：

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>	
	<h3>成功页面</h3>	
	${user.username}
	${user.password}
	${user.age}
</body>
</html>

```


	<h3>成功页面</h3>	
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210603112504672.png)


### 1.2 返回值是 void
1、如果控制器的方法返回值编写成 void，执行程序报404的异常（也有可能是空白页面），默认查找JSP页面没有找到。
原因：默认会跳转到 @RequestMapping(value="/initUpdate") initUpdate的页面。

2、可以使用请求转发或者重定向跳转到指定的页面

3、代码如下：

**jsp**：

```
<a href="user/testVoid">testVoid</a>
```

**Controller**：

```java
@RequestMapping("/testVoid")
public void testVoid(HttpServletRequest request, HttpServletResponse response)throws Exception{
    System.out.println("testVoid方法执行了");
    System.out.println(request.getContextPath());
    
    //跳转到成功页面
    //请求转发
    //request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request, response);
    //重定向不能访问wen-inf下面的文件

    //跳转到其他界面
    request.getRequestDispatcher("/index.jsp").forward(request, response);
    response.sendRedirect(request.getContextPath()+"/index.jsp");

    //设置中文乱码
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");//浏览器解析

    //直接会进行响应
    response.getWriter().print("你好");
    return;
}

```


<font color="red"> 注意：用转发和重定向用不了视图解析器，因此需要常规处理。</font>

---


### 1.3 返回值是ModelAndView对象
1.  **ModelAndView对象是Spring提供的一个对象，可以用来调整具体的JSP视图** 

2. **具体的代码如下**

    **jsp：**

   ```
   <a href="user/testModelAndView">testModelAndView</a>
   
   ```

   **Controller:**

   ```java
   @RequestMapping("/testModelAndView")
   public ModelAndView testModelAndView(){
       System.out.println("testModelAndView方法执行了");
       //创建 ModelAndView 对象
       ModelAndView mv = new ModelAndView();
       User user = new User();
       user.setUsername("xiuyan");
       user.setPassword("222222");
       user.setAge(13);
   
       //把user对象存储到mv对象中，也会把user对象存入到request对象中
       mv.addObject(user);
   
       //跳转
       mv.setViewName("success");
       return mv;
   }
   
   ```

    **测试结果：** 
    <img src="https://img-blog.csdnimg.cn/20210603115514585.png" alt="在这里插入图片描述"/>
---


## 二、SpringMVC框架提供的转发和重定向

### 1.1 forward 请求转发

controller 方法返回 String类型，想进行请求转发也可以编写成：

```java
/**
 * "forward:转发的JSP路径"，不走视图解析器了，所以需要编写完整的路径
 */
@RequestMapping("/testForward")
public String testForward(){
	System.out.println("testForwardOrRedirect方法执行了");
	
	//请求转发
	//转发到成功页面
	//return "forward:/WEB-INF/pages/success.jsp";
	//转到其他页面
	//return "forward:/index.jsp";
	//其他请求
	return "forward:/user/testString";
}

```

### 2.2 redirect 重定向

```java
@RequestMapping("/testRedirect")
public String testRedirect(){
    System.out.println("testForwardOrRedirect方法执行了");

    //重定向
    return "redirect:/user/testString";
    //return "redirect:/index.jsp";
}

```

---


## 三、响应json数据之过滤静态资源

DispatcherServlet会拦截到所有的资源，导致一个问题就是静态资源（img、css、js）也会被拦截到，从而不能被使用。解决问题就是需要配置静态资源不进行拦截，在 springmvc.xml 配置文件添加如下配置

1、mvc:resources标签配置不过滤

​	1、location 元素表示 webapp 目录下的包下的所有文件
​	2、mapping 元素表示以 /static 开头的所有请求路径，如 /static/a 或者/static/a/b

```html
<!--告诉前端控制器，哪些资源不拦截-->
<mvc:resources location="/css/" mapping="/css/**"/> <!-- 样式 -->
<mvc:resources location="/images/" mapping="/images/**"/> <!-- 图片 -->
<mvc:resources location="/js/" mapping="/js/**" /><!--javascript-->

```



# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117515190