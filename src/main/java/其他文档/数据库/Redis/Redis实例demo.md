# Redis实例demo
# redis

<img src="https://img-blog.csdnimg.cn/20200225162624109.bmp?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 这就是redis得一个实例逻辑图。 首先查询redis里面是否有数据，没有，则查询数据库，并且将得到的数据存入到redis中。有，则直接查询redis.

# 项目的准备

### 导入相关的资源包

<img src="https://img-blog.csdnimg.cn/20200225163721747.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 导入相关联的数据库连接工具类和配置

<img src="https://img-blog.csdnimg.cn/20200225164044388.png" alt="在这里插入图片描述"/>

## druid.properties

```
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/day23
username=root
password=123456
initialSize=5
maxActive=10
maxWait=3000

```

## jedis.properies

```
host=127.0.0.1
port=6379
maxTotal=50
maxIdle=10

```

## 文件目录

<img src="https://img-blog.csdnimg.cn/20200225164441533.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## domain包下是实体类

```
package cn.itcast.domain;

public class Province {<!-- -->
    private int id;
    private String name;

    public int getId() {<!-- -->
        return id;
    }

    public void setId(int id) {<!-- -->
        this.id = id;
    }

    public String getName() {<!-- -->
        return name;
    }

    public void setName(String name) {<!-- -->
        this.name = name;
    }
}

```

## 在web文件夹中有ProvinceServlet ,代码如下：

```
package cn.itcast.web.servlet;

import cn.itcast.domain.Province;
import cn.itcast.service.ProvinceService;
import cn.itcast.service.impl.ProvinceServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/provinceServlet")
public class ProvinceServlet extends HttpServlet {<!-- -->
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
     //调用service
        response.setContentType("application/json;charset=utf-8");
        ProvinceService service = new ProvinceServiceImp();

        //List<Province> list = service.findAll();
        String json1 = service.findAllJson();
        System.out.println(json1);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(json1);
//        System.out.println(json);


        response.getWriter().write(json1);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
     this.doPost(request,response);
    }
}


```

## 在service包中有ProvinceService接口类和ProvinceServiceImp实现类：

ProvinceService实现类中有两个方法

```
package cn.itcast.service;

import cn.itcast.domain.Province;

import java.util.List;

public interface ProvinceService {<!-- -->
    public List<Province> findAll();
    public String findAllJson();
}

```

## ProvinceServiceImp实现类：

```
package cn.itcast.service.impl;

import cn.itcast.dao.ProvinceDao;
import cn.itcast.dao.impl.ProvinceDaoImp;
import cn.itcast.domain.Province;
import cn.itcast.jedis.util.JedisPoolUtils;
import cn.itcast.service.ProvinceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

public class ProvinceServiceImp implements ProvinceService {<!-- -->

    private ProvinceDao dao =new ProvinceDaoImp();
    @Override
    public List<Province> findAll() {<!-- -->
        List<Province> all = dao.findAll();
        return all;
    }

    @Override
    public String findAllJson() {<!-- -->

        //1从redis中查询数据
        Jedis jedis = JedisPoolUtils.getJedis();
        String province_json = jedis.get("province");
        if (province_json==null ||province_json.length()==0) {<!-- -->
            System.out.println("redis没有数据");
            List<Province> ps = dao.findAll();
            ObjectMapper mapper = new ObjectMapper();
            try {<!-- -->
                 province_json = mapper.writeValueAsString(ps);
            } catch (JsonProcessingException e) {<!-- -->
                e.printStackTrace();
            }
            jedis.set("province",province_json);
            jedis.close();
        }else{<!-- -->
            System.out.println("redis有数据");

        }
        return province_json;
    }

}

```

## 在dao包中有ProvinceDao和ProvinceDaoImp两个实现类：

### provinceDao类

```
package cn.itcast.dao;

import cn.itcast.domain.Province;

import java.util.List;

public interface ProvinceDao {<!-- -->
    public List<Province> findAll();
}


```

### ProvinceDaoImp类

```
package cn.itcast.dao.impl;

import cn.itcast.dao.ProvinceDao;
import cn.itcast.domain.Province;

import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProvinceDaoImp implements ProvinceDao {<!-- -->
     //声明成员变量 jdbctemplement
    //private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
     private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Province> findAll() {<!-- -->
       //1定义sql
        String sql = "select * from province ";
        //2执行sal
        List<Province> list = template.query(sql, new BeanPropertyRowMapper<Province>(Province.class));
        return list;
    }
}


```

前端页面

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script>
        $(function () {<!-- -->
            $.get("provinceServlet",{<!-- -->},function (data) {<!-- -->
             //1获取select[{"id":1,"name":"北京"},{"id":2,"name":"上海"},{"id":3,"name":"广州"},{"id":4,"name":"陕西"}]
                var province = $("#province");

                // 2遍历json数组
              $(data).each(function () {<!-- -->
                  var option = "<option name='"+this.id+"'>"+this.name+"</option>";

                  //4.调用select的append追加option
                  province.append(option);
              });

            });
        });
    </script>
</head>
<body>

    <select id="province">
    <option>--请选择省份--</option>

    </select>
</body>
</html>

```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/104499858