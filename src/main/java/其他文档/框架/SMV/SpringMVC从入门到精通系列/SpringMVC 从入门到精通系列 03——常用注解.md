# SpringMVC 从入门到精通系列 03——常用注解
 # 文章目录
1. RequestParam 注解
2. RequestBody 注解
3. PathVariable 注解
4. RequestHeader 注解（了解）
5. CookieValue 注解（了解）
6. ModelAttribute 注解
7. SessionAttributes 注解

---


## 1. RequestParam 注解
1.  **作用：** <mark>把请求中的指定名称的参数传递给控制器中的形参赋值</mark> <li> **属性：** 
  <ol>1. **value：** 请求参数中的名称1. **required：** 请求参数中是否必须提供此参数，默认值是true，<mark>必须提供</mark>（如果不提供会报错）1. **代码如下：**
**jsp:**

```html
<a href="anno/testRequestParam?username=xiuyan">测试RequestParam</a>

```

**Controller：**

```java
@Controller
@RequestMapping(path = "/anno")
public class AnnoController {

    @RequestMapping(path = "/testRequestParam")
    public String testRequestParam(@RequestParam(value = "username") String name){
        System.out.println("测试RequestParam注解");
        System.out.println(name);
        return "success";
    }
}

```

**测试结果：** <img src="https://img-blog.csdnimg.cn/20210603100842846.png" alt="在这里插入图片描述"/>

---


## 2. RequestBody 注解
1.  **作用：用于获取请求体的内容**<font color="red">（注意：get方法不可以）</font> <li> **属性：** 
  <ol>1. required：是否必须有请求体，默认值是true
**代码如下：**

**jsp：**

```html
<form action="anno/testRequestBody" method="post">
    姓名：<input type="text" name="uname"><br>
    年龄：<input type="text" name="age"><br>
    <input type="submit" value="提交" />
</form>

```

**Controller：**

```java
@Controller
@RequestMapping(path = "/anno")
public class AnnoController {

    @RequestMapping(path = "/testRequestBody")
    public String testRequestBody(@RequestBody String body){
        System.out.println("测试RequestBody注解");
        System.out.println(body);
        return "success";
    }
}

```

**测试结果：** <img src="https://img-blog.csdnimg.cn/20210603101515974.png" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/20210603101549498.png" alt="在这里插入图片描述"/>

---


## 3. PathVariable 注解
1. **作用：拥有绑定url中的占位符的。** <mark>例如：url中有/delete/{id}，{id}就是占位符</mark><li>**属性：** 
  <ol>1. value：指定url中的占位符名称1. 请求路径一样，可以根据不同的请求方式去执行后台的不同方法<li>restful风格的URL优点 
    <ol>1. 结构清晰1. 符合标准1. 易于理解1. 扩展方便
**jsp：**

```html
<a href="anno/testPathVariable/111">测试PathVariable</a>

```

**Controller：**

```java
@Controller
@RequestMapping(path = "/anno")
public class AnnoController {

    @RequestMapping(path = "/testPathVariable/{sid}")
    public String testPathVariable(@PathVariable(name="sid") String id){
        System.out.println("测试PathVariable注解");
        System.out.println(id);
        return "success";
    }
}

```

<img src="https://img-blog.csdnimg.cn/20210603102336226.png" alt="在这里插入图片描述"/>

---


## 4. RequestHeader 注解（了解）
1.  **作用：获取指定请求头的值** <li> **属性：** 
  <ol>1. value：请求头的名称
代码如下

```java
@RequestMapping(path="/hello")
public String sayHello(@RequestHeader(value="Accept") String header) {
	System.out.println(header);
	return "success";
}

```

---


## 5. CookieValue 注解（了解）
1.  **作用：用于获取指定cookie的名称的值** <li> **属性：** 
  <ol>1. value：cookie的名称
**代码：**

```java
@RequestMapping(path="/hello")
public String sayHello(@CookieValue(value="JSESSIONID") String cookieValue) {
	System.out.println(cookieValue);
	return "success";
}

```

---


## 6. ModelAttribute 注解
<li>**作用：** 
  <ol>1. 出现在方法上：表示当前方法会在控制器方法执行前先执行。1. 出现在参数上：获取指定的数据给参数赋值。1. 当提交表单数据不是完整的实体数据时，保证没有提交的字段使用数据库原来的数据。
**有返回值：**

**jsp：**

```html
<form action="anno/testModuleAttribute" method="post">
	姓名：<input type="text" name="username"><br>
	年龄：<input type="text" name="age"><br>
	<input type="submit" value="提交" />
</form>

```

**Controller：**

```java
@RequestMapping(path="/testModuleAttribute")
public String testModuleAttribute( User user){
    System.out.println("showUser方法执行了");
    System.out.println(user);
    return "success";
}

/**
 * 该方法会先执行（有返回值）
*/
@ModelAttribute
public User showUser(String username){
    System.out.println("测试ModuleAttribute注解");
    User user = new User();
    user.setUsername("xiuyan");
    user.setAge(22);
    user.setDate(new Date());
    return user;
}

```

**测试结果：** <img src="https://img-blog.csdnimg.cn/20210603104838286.png" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/20210603104911334.png" alt="在这里插入图片描述"/>

  结果显示，由于先执行 showUser 方法，在方法里面封装了 User 对象并返回，从而再执行 testModuleAttribute 方法时，不会因为传入表单没有传入日期属性，而导致日期属性为空。

---


**无返回值：**

```java
@RequestMapping(path="/testModuleAttribute")
public String testModuleAttribute(@ModelAttribute("abc") User user){
    System.out.println("showUser方法执行了");
    System.out.println(user);
    return "success";
}

/**
 * 无返回值
 */
@ModelAttribute
public void showUser(String username, Map<String, User> map){
    System.out.println("测试ModuleAttribute注解");
    User user = new User();
    user.setUsername("xiuyan");
    user.setAge(22);
    user.setDate(new Date());
    map.put("abc", user);
}

```

**测试结果：** <img src="https://img-blog.csdnimg.cn/20210603105424521.png" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/20210603105442433.png" alt="在这里插入图片描述"/>

---


## 7. SessionAttributes 注解
1. **作用：用于多次执行控制器方法间的参数共享**<li>**属性：** 
  <ol>1. value：指定存入属性的名称
**jsp：**

```html
<a href="anno/testSessionAttributes">测试SessionAttributes</a><br>
<a href="anno/getSessionAttributes">测试getSessionAttributes</a><br>
<a href="anno/delSessionAttributes">测试delSessionAttributes</a><br>

```

```java
@Controller
@RequestMapping(path = "/anno")
@SessionAttributes(value = {"msg"})
public class AnnoController {

    @RequestMapping(path="/testSessionAttributes")
    public String testSessionAttributes(Model model){
        System.out.println("testSessionAttributes方法执行了");
        //默认会将 msg 存入 request 域，当开启@SessionAttributes(value = {"msg"})，也会将 msg 存入 session
        model.addAttribute("msg", "haha");
        return "success";
    }
    @RequestMapping(path="/getSessionAttributes")
    public String getSessionAttributes(ModelMap model){
        System.out.println("getSessionAttributes方法执行了");
        String msg = (String) model.get("msg");
        System.out.println(msg);
        return "success";
    }
    @RequestMapping(path="/delSessionAttributes")
    public String delSessionAttributes(SessionStatus status){
        System.out.println("delSessionAttributes方法执行了");
        status.setComplete();
        return "success";
    }

}

```

**成功页面：**

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h3>成功页面</h3>

    ${requestScope.msg}
    ${sessionScope.msg}
</body>
</html>


```

<img src="https://img-blog.csdnimg.cn/20210603111029690.png" alt="在这里插入图片描述"/>
# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/117511435