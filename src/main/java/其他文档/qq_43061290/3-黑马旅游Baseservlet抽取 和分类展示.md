# 3-黑马旅游Baseservlet抽取 和分类展示
## 优化Servlet

减少Servlet的数量，现在是一个功能一个Servlet，将其优化为一个模块一个Servlet，相当于在数据库中一张表对应一个Servlet，在Servlet中提供不同的方法，完成用户的请求。

<img src="https://img-blog.csdnimg.cn/20200318120857280.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> Idea控制台中文乱码解决：-Dfile.encoding=gb2312 <img src="https://img-blog.csdnimg.cn/20200318120920207.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## BaseServlet

```
package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {<!-- -->


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {<!-- -->
        //System.out.println("baseServlet的service方法被执行了...");

        //完成方法分发
        //1.获取请求路径
        String uri = req.getRequestURI(); //   /travel/user/add
        System.out.println("请求uri:"+uri);//  /travel/user/add
        //2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println("方法名称："+methodName);
        //3.获取方法对象Method
        //谁调用我？我代表谁
        System.out.println(this);//UserServlet的对象cn.itcast.travel.web.servlet.UserServlet@4903d97e
        try {<!-- -->
            //获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {<!-- -->
            e.printStackTrace();
        } catch (IllegalAccessException e) {<!-- -->
            e.printStackTrace();
        } catch (InvocationTargetException e) {<!-- -->
            e.printStackTrace();
        }


    }

    /**
     * 直接将传入的对象序列化为json，并且写回客户端
     * @param obj
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {<!-- -->
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将传入的对象序列化为json，返回
     * @param obj
     * @return
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {<!-- -->
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}


```

## UserServlet

```
package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*") // /user/add /user/find
public class UserServlet extends BaseServlet {<!-- -->

    //声明UserService业务对象
    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->

        //验证校验
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        //比较
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){<!-- -->
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        User user = new User();
        try {<!-- -->
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {<!-- -->
            e.printStackTrace();
        } catch (InvocationTargetException e) {<!-- -->
            e.printStackTrace();
        }

        //3.调用service完成注册
        //UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if(flag){<!-- -->
            //注册成功
            info.setFlag(true);
        }else{<!-- -->
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }

        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        //1.获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User();
        try {<!-- -->
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {<!-- -->
            e.printStackTrace();
        } catch (InvocationTargetException e) {<!-- -->
            e.printStackTrace();
        }

        //3.调用Service查询
       // UserService service = new UserServiceImpl();
        User u  = service.login(user);

        ResultInfo info = new ResultInfo();

        //4.判断用户对象是否为null
        if(u == null){<!-- -->
            //用户名密码或错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码或错误");
        }
        //5.判断用户是否激活
        if(u != null &amp;&amp; !"Y".equals(u.getStatus())){<!-- -->
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活");
        }
        //6.判断登录成功
        if(u != null &amp;&amp; "Y".equals(u.getStatus())){<!-- -->
            request.getSession().setAttribute("user",u);//登录成功标记

            //登录成功
            info.setFlag(true);
        }

        //响应数据
        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    /**
     * 查询单个对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");
        //将user写回客户端

       /* ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
       writeValue(user,response);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        //1.销毁session
        request.getSession().invalidate();

        //2.跳转登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        //1.获取激活码
        String code = request.getParameter("code");
        if(code != null){<!-- -->
            //2.调用service完成激活
            //UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            //3.判断标记
            String msg = null;
            if(flag){<!-- -->
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            }else{<!-- -->
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}


```

## 页面路径改写

## register.html

<img src="https://img-blog.csdnimg.cn/20200318145351596.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## login.html

<img src="https://img-blog.csdnimg.cn/20200318145418921.png" alt="在这里插入图片描述"/>

## header.html

<img src="https://img-blog.csdnimg.cn/20200318145437939.png" alt="在这里插入图片描述"/>

# 分类数据展示

## 效果

<img src="https://img-blog.csdnimg.cn/2020031814552747.png" alt="在这里插入图片描述"/>

## 分析

<img src="https://img-blog.csdnimg.cn/20200412165047464.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 后台代码

## CategoryServlet

```
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {<!-- -->

    private CategoryService service = new CategoryServiceImpl();

    /**
     * 查询所有
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        //1.调用service查询所有
        List<Category> cs = service.findAll();
        //2.序列化json返回
       /* ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),cs);*/
       writeValue(cs,response);

    }

}

```

## CategoryService

```
public class CategoryServiceImpl implements CategoryService {<!-- -->

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {<!-- -->
        return categoryDao.findAll();
    }
}

```

## CategoryDao

```
public class CategoryDaoImpl implements CategoryDao {<!-- -->

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {<!-- -->
        String sql = "select * from tab_category ";
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}

```

## 在BaseServlet中封装了序列化json的方法

```
/**
 * 直接将传入的对象序列化为json，并且写回客户端
 * @param obj
 */
public void writeValue(Object obj,HttpServletResponse response) throws IOException {<!-- -->
    ObjectMapper mapper = new ObjectMapper();
    response.setContentType("application/json;charset=utf-8");
    mapper.writeValue(response.getOutputStream(),obj);
}

/**
 * 将传入的对象序列化为json，返回
 * @param obj
 * @return
 */
public String writeValueAsString(Object obj) throws JsonProcessingException {<!-- -->
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
}

```

## 前台代码

## hader.html加载后，发送ajax请求，请求category/findAll

```
/查询分类数据
 $.get("category/findAll",{<!-- -->},function (data) {<!-- -->
     //[{cid:1,cname:国内游},{},{}]
     var lis = '- [首页](index.html)';
     //遍历数组,拼接字符串(<li>)
     for (var i = 0; i < data.length; i++) {<!-- -->
         var li = '- ['+data[i].cname+'](route_list.html)';

         lis += li;
         
     }
     //拼接收藏排行榜的li,- [收藏排行榜](favoriterank.html)
     
     lis+= '- [收藏排行榜](favoriterank.html)';

     //将lis字符串，设置到ul的html内容中
     $("#category").html(lis);
 });

```

```
<!-- 头部 start -->

<script src="js/getParameter.js"></script>
<script>
    $(function () {<!-- -->
        //查询用户信息
        $.get("user/findOne",{<!-- -->},function (data) {<!-- -->
           //{uid:1,name:'李四'}
           var msg = "欢迎回来，"+data.name;
           $("#span_username").html(msg);

       });

       //查询分类数据
        $.get("category/findAll",{<!-- -->},function (data) {<!-- -->
            //[{cid:1,cname:国内游},{},{}]
            var lis = '- [首页](index.html)';
            //遍历数组,拼接字符串(<li>)
            for (var i = 0; i < data.length; i++) {<!-- -->
                var li = '- ['+data[i].cname+'](route_list.html?cid='+data[i].cid+')';

                lis += li;
                
            }
            

            //拼接收藏排行榜的li,- [收藏排行榜](favoriterank.html)
            
            lis+= '- [收藏排行榜](favoriterank.html)';

            //将lis字符串，设置到ul的html内容中
            $("#category").html(lis);
        });


        //给搜索按钮绑定单击事件，获取搜索输入框的内容
        $("#search-button").click(function () {<!-- -->
            //线路名称
            var rname = $("#search_input").val();

            var cid = getParameter("cid");
            // 跳转路径 http://localhost/travel/route_list.html?cid=5，拼接上rname=xxx
            location.href="http://localhost/travel/route_list.html?cid="+cid+"&amp;rname="+rname;
        });
    });

</script>

<header id="header">
        
            <img src="images/top_banner.jpg" alt="">
        
        
            <!-- 未登录状态  -->
            
                [登录](login.html)
                [注册](register.html)
            
            <!-- 登录状态  -->
            
            	
                
                [我的收藏](myfavorite.html)
                [退出](javascript:location.href='exitServlet';)
            
        
        
            
                
                    [<img src="images/logo.jpg" alt="">](/)
                
                
                    <input name="" id="search_input"  type="text" placeholder="请输入路线名称" class="search_input" autocomplete="off">
                    [搜索](javascript:;)
                
                
                    
                        <img src="images/hot_tel.jpg" alt="">
                    
                    
                        <p class="hot_time">客服热线(9:00-6:00)</p>
                        <p class="hot_num">400-618-9090</p>
                    
                
            
        
    </header>
    <!-- 头部 end -->
     <!-- 首页导航 -->
    
        <ul id="category" class="nav">
           <!-- - [首页](index.html)
            - [门票](route_list.html)
            - [酒店](route_list.html)
            - [香港车票](route_list.html)
            - [出境游](route_list.html)
            - [国内游](route_list.html)
            - [港澳游](route_list.html)
            - [抱团定制](route_list.html)
            - [全球自由行](route_list.html)
            - [收藏排行榜](favoriterank.html)-->
        </ul>
    
    

```

## 对分类数据进行缓存优化

分析发现，分类的数据在每一次页面加载后都会重新请求数据库来加载，对数据库的压力比较大，而且分类的数据不会经常产生变化，所有可以使用redis来缓存这个数据。 分析： <img src="https://img-blog.csdnimg.cn/2020041216513145.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

```
package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {<!-- -->

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {<!-- -->
        //1.从redis中查询
        //1.1获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可使用sortedset排序查询
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3查询sortedset中的分数(cid)和值(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cs = null;
        //2.判断查询的集合是否为空
        if (categorys == null || categorys.size() == 0) {<!-- -->

            System.out.println("从数据库查询....");
            //3.如果为空,需要从数据库查询,在将数据存入redis
            //3.1 从数据库查询
            cs = categoryDao.findAll();
            //3.2 将集合数据存储到redis中的 category的key
            for (int i = 0; i < cs.size(); i++) {<!-- -->

                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        } else {<!-- -->
            System.out.println("从redis中查询.....");

            //4.如果不为空,将set的数据存入list
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {<!-- -->
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);

            }
        }


        return cs;
    }
}


```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/104941717