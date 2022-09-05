# SpringMVC 从入门到精通系列 05——Ajax的实现方式总结
 # 文章目录
一、$.ajax() 方式
二、$.post() 方式
三、补充：不返回 json 对象的二种方式
3.1 打印流方式实现
3.2 返回布尔类型方式实现

---


后文的 java 后台代码，省略了以下代码，使用这种二级目录结构方便管理一些… 废话不多说，马上就开始！

```java
@Controller
@RequestMapping("/testAjax")
public class AjaxController {
	...
}

```

---


## 一、$.ajax() 方式

前端：

```javascript
$('#btn1').click(function (){
    var param = {username:"xiuYan", password:"123456", age: 21};
    $.ajax({
        url:"testAjax/formAjax1",
        contentType:"application/json;charset=UTF-8",
        data: JSON.stringify(param),
        dataType:"json",
        type:"post",
        success:function (data) {
            console.log(data)
        }
    })
})

```

后端：

```java
@RequestMapping("/formAjax1")
public @ResponseBody User testAjax1(@RequestBody User user){
    System.out.println("测试方法1");
    System.out.println(user);
    return user;
}

```

后台打印： <img src="https://img-blog.csdnimg.cn/20210617225707300.png#pic_left" alt="在这里插入图片描述" width="500"/> 前台响应数据： <img src="https://img-blog.csdnimg.cn/2021061723013355.png#pic_left" alt="在这里插入图片描述" width="500"/>

**注意：**
1. 如果使用 @RequestBody 注解，ajax需要传 **json 字符串**，因此需要 JSON.stringify(param) 进行转换。1. 也可以使用 ‘{“username”:“xiuYan”,“password”:“123456”,“age”:21}’，但是更推荐上一种。1. <font color="red">@RequestBody 注解的作用是把 json 的字符串转换成 JavaBean 的对象</font>1. $.ajax() 方式实现，需要和 @RequestBody 注解配合使用，其作用是把客户的 json 对象转换为 javaBean 对象。(使用该注解前提：contentType 设置为：application/json)
---


## 二、$.post() 方式

前端：

```javascript
$('#btn2').click(function (){
    $.post("testAjax/formAjax2",{
        username: "xiuYan2",
        password: "123456",
        age: 22
    }, function (data){
        console.log(data)
    })
})

```

后端：

```java
@RequestMapping("/formAjax2")
public @ResponseBody User testAjax2(User user){
    System.out.println("测试方法2");
    System.out.println(user);
    return user;
}

```

后台打印： <img src="https://img-blog.csdnimg.cn/20210617231741659.png#pic_left" alt="在这里插入图片描述" width="500"/>

前台响应： <img src="https://img-blog.csdnimg.cn/2021061723165964.png#pic_left" alt="在这里插入图片描述" width="500"/>

**注意：**
1. json数据使用，json的头带双引号也能解析成功。如"username": “xiuYan2”…1. **这里后台不需要用 @RequestBody 注解，这样会报 415错误。**
$.get() 方法就不一一赘述了，和 $.post() 用法是一样的。

---


## 三、补充：不返回 json 对象的二种方式

  在有些情况下，我们不需要返回一个对象，只需要返回一种状态，根据这种状态来进行后续动作，那么这种方式怎么实现呢？这里提供打印流以及返回布尔的两种方式供读者参考…

---


### 3.1 打印流方式实现

前端：

```javascript
$('#btn3').click(function (){
    $.post("testAjax/formAjax3",{
        username: "xiuYan3",
        password: "123456",
        age: 23
    }, function (data){
        if(data == "yes"){
            console.log("成功处理！");
        }else{
            console.log("失败处理！");
        }
    })
})

```

后端：

```java
@RequestMapping("/formAjax3")
public void testAjax3(User user, HttpServletResponse response) throws IOException {
    System.out.println("测试方法3");
    System.out.println(user);
    //模拟业务层操作
    Integer count = 1;
    PrintWriter writer = response.getWriter();
    if(count == 1){
        writer.write("yes");
    }else{
        writer.write("no");
    }
}

```

后台打印： <img src="https://img-blog.csdnimg.cn/20210629102511651.png#pic_left" alt="在这里插入图片描述" width="480"/> 前台响应： <img src="https://img-blog.csdnimg.cn/20210617233647413.png" alt="在这里插入图片描述"/>

---


### 3.2 返回布尔类型方式实现

前端：

```js
//测试方法4
$('#btn4').click(function (){
    $.post("testAjax/formAjax4",{
        username: "xiuYan4",
        password: "123456",
        age: 24
    }, function (data){
        if(data == true){
            console.log("成功处理！");
        }else{
            console.log("失败处理！");
        }
    })
})

```

后端：

```java
@RequestMapping("/formAjax4")
public @ResponseBody boolean testAjax4(User user, HttpServletResponse response) throws IOException {
    System.out.println("测试方法4");
    System.out.println(user);
    //模拟业务层操作
    Integer count = 1;

    if(count == 1){
        return true;
    }else{
        return false;
    }
}

```

后台打印： <img src="https://img-blog.csdnimg.cn/20210629102732137.png#pic_left" alt="在这里插入图片描述" width="480"/> 前台响应： <img src="https://img-blog.csdnimg.cn/20210629102758632.png#pic_left" alt="在这里插入图片描述" width="400"/>

---


  这里我推荐使用的方式是第二种 $.post() 方式，较为简洁，但是有的 jquery 版本不支持这个方法，调换相应版本即可。我这里使用的版本是 jquery-3.3.1.js，$.ajax() 方法与 $.post() 方法都支持。如果大家对文章内容还存在一些疑问，欢迎大家在评论区留言哦~
# **文章地址： **    https://blog.csdn.net/weixin_43819566/article/details/118003770