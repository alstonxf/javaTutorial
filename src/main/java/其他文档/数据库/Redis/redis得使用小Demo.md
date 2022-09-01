# redis得使用小Demo
**

## 1前期准备

1建立一个web项目 2打开数据库 3打开redis服务器

**

## 建立web项目如下

建立如下文件包 <img src="https://img-blog.csdnimg.cn/20200112103522743.png" alt="在这里插入图片描述"/> 2详细的文件内如下

<img src="https://img-blog.csdnimg.cn/2020011210364356.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 导入下述的lib文件 <img src="https://img-blog.csdnimg.cn/20200112105223676.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 这些资源包可以从百度云盘获取 链接：https://pan.baidu.com/s/16FwmfIZ9Zw7B-dG_GZ6bcA 提取码：9dp3 1）首先是在dao文件下建立一个ProvinceDao的接口和ProvinceDaoImp实现类，具体代码如下；

```
public interface ProvinceDao {<!-- -->
    public List<Province> findAll();
}
``

```java
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

在domain下做一个province类

```
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

service包如下：

```
public interface ProvinceService {<!-- -->
    public List<Province> findAll();
    public String findAllJson();
}

``

```java
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

util包是两个工具类；JDBCUtils和JedisPoolUtils

```
public class JDBCUtils {<!-- -->

    private static DataSource ds ;

    static {<!-- -->

        try {<!-- -->
            //1.加载配置文件
            Properties pro = new Properties();
            //使用ClassLoader加载配置文件，获取字节输入流
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            pro.load(is);

            //2.初始化连接池对象
            ds = DruidDataSourceFactory.createDataSource(pro);

        } catch (IOException e) {<!-- -->
            e.printStackTrace();
        } catch (Exception e) {<!-- -->
            e.printStackTrace();
        }
    }

    /**
     * 获取连接池对象
     */
    public static DataSource getDataSource(){<!-- -->
        return ds;
    }


    /**
     * 获取连接Connection对象
     */
    public static Connection getConnection() throws SQLException {<!-- -->
        return  ds.getConnection();
    }
}


```

```
public class JedisPoolUtils {<!-- -->

    private static JedisPool jedisPool;

    static{<!-- -->
        //读取配置文件
        InputStream is = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
        //创建Properties对象
        Properties pro = new Properties();
        //关联文件
        try {<!-- -->
            pro.load(is);
        } catch (IOException e) {<!-- -->
            e.printStackTrace();
        }
        //获取数据，设置到JedisPoolConfig中
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));

        //初始化JedisPool
        jedisPool = new JedisPool(config,pro.getProperty("host"),Integer.parseInt(pro.getProperty("port")));



    }


    /**
     * 获取连接方法
     */
    public static Jedis getJedis(){<!-- -->
        return jedisPool.getResource();
    }
}


```

在web文件下建立一个servlet文件夹，存放ProvinceServlet类

```
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

最后还有两个配置文件druid.properties和jedies.properties;

```
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/day23
username=root
password=123456
initialSize=5
maxActive=10
maxWait=3000

```

```
host=127.0.0.1
port=6379
maxTotal=50
maxIdle=10
``

`剩下就是index.htmll

```javascript
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

按照上述步骤操作，一个小的案例就可以使用了。
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/103943613