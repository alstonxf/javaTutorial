# javaweb7复杂分页查询
## 业务逻辑分析

<img src="https://img-blog.csdnimg.cn/20200113171358413.bmp?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 数据库代码查询语句：

SELECT * FROM USER WHERE NAME LIKE ‘%李%’ AND address LIKE ‘%北京%’ LIMT 0,5;

从上图分析可知到：

从客户端输入的是一个map集合 |name|李 | |adress|北京| | Email | “”/null |

服务器 输出 PageBean对象 int totalCount; select count(*) from user where name like ? and address like ?; List list; select * from user where name like ? and address like ? limt ?,?;

根据map中的value值动态生成sql 1定义初始化sql=select count(*) from user where 1=1 2遍历map,判断其value是否有值 sb.append("and key like ?)

## 前端代码

```
    <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post">
            
                <label for="exampleInputName2">姓名</label>
                <input type="text" name="name" value="${condition.name[0]}" class="form-control" id="exampleInputName2" >
            
            
                <label for="exampleInputName3">籍贯</label>
                <input type="text" name="address" value="${condition.address[0]}" class="form-control" id="exampleInputName3" >
            

            
                <label for="exampleInputEmail2">邮箱</label>
                <input type="text" name="email" value="${condition.email[0]}" class="form-control" id="exampleInputEmail2"  >
            
            <button type="submit" class="btn btn-default">查询</button>
        </form>

```

## 后端代码

1修改FindUserByPageServlet中的代码 //获取调间查询的参数 Map<String, String[]> condition = request.getParameterMap();

```
 @WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {<!-- -->
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        request.setCharacterEncoding("utf-8");
        //1获取参数
        String currentPage = request.getParameter("currentPage");//当前页码
        String rows = request.getParameter("rows");//每页显示条数
        if (currentPage == null || "".equals(currentPage)){<!-- -->
            currentPage ="1";

        }

        if (rows ==null || "".equals(rows)){<!-- -->
            rows="5";
        }
        //获取调间查询的参数
        Map<String, String[]> condition = request.getParameterMap();


        //2调用service查询
        UserServiceImpl service = new UserServiceImpl();
        PageBean<User> pb=service.findUserByPage(currentPage,rows,condition);

        //3将pageBdean cun  request
        request.setAttribute("pb",pb);
        System.out.println(pb);
        request.setAttribute("condition",condition);

        //4转发到listjsp
        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
     this.doPost(request,response);
    }
}

```

修改UserService 接口中的findUserByPage方法；

```
PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);

```

```
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

    void addUser(User user);

    void deleteUser(String id);

    User findbyid(String id);

    void updateUser(User user);

    void deleSelectedUser(String[] ids);

    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}


```

修改UserServiceImpl 实现类中的findUserByPage方法

//使用dao查询总记录数 int totalCount=dao.findTotalCount(condition); //使用dao查询list int start=(currentPage-1)*rows; List list=dao.findByPage(start,rows,condition);

```
public class UserServiceImpl implements UserService{<!-- -->

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

    @Override
    public void addUser(User user) {<!-- -->
        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {<!-- -->
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findbyid(String id) {<!-- -->
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {<!-- -->
        dao.update(user);
    }

    @Override
    public void deleSelectedUser(String[] ids) {<!-- -->
        for (String id : ids) {<!-- -->
            dao.delete(Integer.parseInt(id));
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {<!-- -->
        int rows = Integer.parseInt(_rows);
        int currentPage = Integer.parseInt(_currentPage);
        if (currentPage<=0){<!-- -->
            currentPage=1;
        }
        //1创建空对象
        PageBean<User> pb = new PageBean<User>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //使用dao查询总记录数
        int totalCount=dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //使用dao查询list
        int start=(currentPage-1)*rows;
        List<User> list=dao.findByPage(start,rows,condition);
        pb.setList(list);

        //5计算总页码
        int totalPage=(totalCount %rows)==0? totalCount/rows:(totalCount/rows)+1;
        pb.setTotalPage(totalPage);
        return pb;
    }


}


```

## 修改UserDao接口中的findByPage方法和findTotalCount方法；
1. List findByPage(int start, int rows, Map<String, String[]> condition);1. int findTotalCount(Map<String, String[]> condition);
```
public interface UserDao {<!-- -->


    public List<User> findAll();

    User findUserByUsernameAndPassword(String username, String password);

    void add(User user);

    void delete(int id);

    User findById(int parseInt);

    void update(User user);

    int findTotalCount(Map<String, String[]> condition);

    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}

```

修改UserDaoImpl 实现类中的findTotalCount方法和findByPage方法；

```
 1. public int findTotalCount(Map<String, String[]> condition) {<!-- -->
        String sql="select count(*) from user where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {<!-- -->
            //排除分页条件
            if ("currentPage".equals(key)|| "rows".equals(key)) {<!-- -->
                continue;
            }
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value !=null &amp;&amp; !"".equals(value)){<!-- -->
                //有值
                sb.append(" and "+key+" like ? ");//一定要空行否则会报错
                params.add("%"+value+"%");//加参数条件的值
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);

        return template.queryForObject(sb.toString(),Integer.class,params.toArray());

    }

```

```
   2. public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {<!-- -->
        String sql = "select * from user  where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {<!-- -->

            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key)){<!-- -->
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null &amp;&amp; !"".equals(value)){<!-- -->
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }

        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);
        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);

        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }
}


```

```
public class UserDaoImpl implements UserDao {<!-- -->

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    


    @Override
    public User findById(int id) {<!-- -->
        String sql="select * from user where id = ?";

        return template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),id );
    }

    @Override
    public void update(User user) {<!-- -->
        String sql="update user  set name = ?,gender = ?, age = ?, address = ?, qq = ? , email = ? where id = ?";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {<!-- -->
        String sql="select count(*) from user where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {<!-- -->
            //排除分页条件
            if ("currentPage".equals(key)|| "rows".equals(key)) {<!-- -->
                continue;
            }
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value !=null &amp;&amp; !"".equals(value)){<!-- -->
                //有值
                sb.append(" and "+key+" like ? ");//一定要空行否则会报错
                params.add("%"+value+"%");//加参数条件的值
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);

        return template.queryForObject(sb.toString(),Integer.class,params.toArray());

    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {<!-- -->
        String sql = "select * from user  where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {<!-- -->

            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key)){<!-- -->
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null &amp;&amp; !"".equals(value)){<!-- -->
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }

        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);
        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);

        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }
}


```

结束！！！
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/103961233