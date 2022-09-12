# javahweb1登陆案例
# 今日内容

```
1. 综合练习
	1. 简单功能
		1. 列表查询
		2. 登录
		3. 添加
		4. 删除
		5. 修改
		
	2. 复杂功能
		1. 删除选中
		2. 分页查询
			* 好处：
				1. 减轻服务器内存的开销
				2. 提升用户体验
		3. 复杂条件查询

```

<img src="https://img-blog.csdnimg.cn/20200311104729757.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/20200311104753881.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 2. 登录

```
1. 调整页面，加入验证码功能
2. 代码实现

```

## 业务逻辑流程
1. 设置编码1. 获取数据1. 验证码校验1. 封装user对象1. 调用service查询1. 判断是否登陆成功
## LoginServlet

```
package cn.itcast.web.servlet;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {<!-- -->
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        //1.设置编码
        request.setCharacterEncoding("utf-8");

        //2.获取数据
        //2.1获取用户填写验证码
        String verifycode = request.getParameter("verifycode");

        //3.验证码校验
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//确保验证码一次性
        if(!checkcode_server.equalsIgnoreCase(verifycode)){<!-- -->
            //验证码不正确
            //提示信息
            request.setAttribute("login_msg","验证码错误！");
            //跳转登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);

            return;
        }

        Map<String, String[]> map = request.getParameterMap();
        //4.封装User对象
        User user = new User();
        try {<!-- -->
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {<!-- -->
            e.printStackTrace();
        } catch (InvocationTargetException e) {<!-- -->
            e.printStackTrace();
        }


        //5.调用Service查询
        UserService service = new UserServiceImpl();
        User loginUser = service.login(user);
        //6.判断是否登录成功
        if(loginUser != null){<!-- -->
            //登录成功
            //将用户存入session
            session.setAttribute("user",loginUser);
            //跳转页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else{<!-- -->
            //登录失败
            //提示信息
            request.setAttribute("login_msg","用户名或密码错误！");
            //跳转登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);

        }




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        this.doPost(request, response);
    }
}


```

## service层

## UserService抽象接口

```

/**
 * 用户管理的业务接口
 */
public interface UserService {<!-- -->

    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();

    /**
     * 登录方法
     * @param user
     * @return
     */
    User login(User user);


}

```

## UserServiceImpl 实现service中UserService

```
public class UserServiceImpl implements UserService {<!-- -->
    private UserDao dao = new UserDaoImpl();

    @Override
    public List<User> findAll() {<!-- -->
        //调用Dao完成查询
        return dao.findAll();
    }

    @Override
    public User login(User user) {<!-- -->
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

```

## dao层

UserDaoImpl实现UserDao

## UserDao

```
/**
 * 用户操作的DAO
 */
public interface UserDao {<!-- -->


    public List<User> findAll();

    User findUserByUsernameAndPassword(String username, String password);


}

```

## UserDaoImpl

```


public class UserDaoImpl implements UserDao {<!-- -->

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {<!-- -->
        //使用JDBC操作数据库...
        //1.定义sql
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));

        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {<!-- -->
        try {<!-- -->
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {<!-- -->
            e.printStackTrace();
            return null;
        }

    }
   }

```

## 前端页面login.jsp

```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>管理员登录</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //切换验证码
        function refreshCode(){<!-- -->
            //1.获取验证码图片对象
            var vcode = document.getElementById("vcode");

            //2.设置其src属性，加时间戳
            vcode.src = "${pageContext.request.contextPath}/checkCodeServlet?time="+new Date().getTime();
        }
    </script>
</head>
<body>

    <h3 style="text-align: center;">管理员登录</h3>
    <form action="${pageContext.request.contextPath}/loginServlet" method="post">
        
            <label for="user">用户名：</label>
            <input type="text" name="username" class="form-control" id="user" placeholder="请输入用户名"/>
        

        
            <label for="password">密码：</label>
            <input type="password" name="password" class="form-control" id="password" placeholder="请输入密码"/>
        

        
            <label for="vcode">验证码：</label>
            <input type="text" name="verifycode" class="form-control" id="verifycode" placeholder="请输入验证码" style="width: 120px;"/>
            <a href="javascript:refreshCode();">
                <img src="${pageContext.request.contextPath}/checkCodeServlet" title="看不清点击刷新" id="vcode"/>
            </a>
        
        <hr/>
        
            <input class="btn btn btn-primary" type="submit" value="登录">
        
    </form>

    <!-- 出错显示的信息框 -->
    
        <button type="button" class="close" data-dismiss="alert" >
            &amp;times;
        </button>
        **${<!-- -->login_msg}**
    

</body>
</html>

```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/104791115