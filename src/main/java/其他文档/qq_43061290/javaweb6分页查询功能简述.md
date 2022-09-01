# javaweb6分页查询功能简述
分页查询功能图 <img src="https://img-blog.csdnimg.cn/2020011211132744.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/20200112111342888.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> PageBean 对象

```
int totalCount;   //总记录数

int totalPage;    //总页码 =总记录数%每页显示条数  == 0 ？总记录数/每页显示条数 ：总记录数/每页显示条数+1


List list;        //每页数据list集合


int currentPage;   //当前页码

int  rows;

```

totalCount=select count(*) from user; totalPage=每页显示条数给服务器：rows list=select * from user limit ? ?; 第一个？ ：开始查询的索引 第二 ？：rows每页显示的条数 currentPage=提供当前页码给服务器

开始查询的索引=（currentPage-1）*rows

展示的一个例子

1 0 5 2 5 5 3 10 5 4 15 5

可以看出输入的两个参数是 currrentPage和rows;

## 业务逻辑流程

| FindUserByPageServlet

```
		1接受请求参数 currentPage ,rows
		
		2调用Service查询PageBean
		
		3将PageBean 存入request
		
		4转发list.jsp展示 |  |

```

|- UserService

```
	 PageBean<User> findUserByPage(int currentPage,int rows){
	 
	 //1创建空的PageBean对象
	 
	 //2设置当前页面属性和rows属性
	 
	 //3调用dao查询totalCount总记录数
	  dao.findTotalCount();
	  
	 //4 start=(currentPage-1)*rows;
	 
	 //5调用dao查询list集合
	 dao.findByPage(int start,int rows)
	 //6计算总页码
	 //7返-|--|

```

| UserDao

```
	 //查询总记录数
	 int fingTotalCount()
	 
	 //分页查询List
	 
	 List findByPage（int start,int rows） |  |

```

## 开始写代码具体步骤

## 后端代码编写

（代码中的红色字体是没有导入包）

```
public class PageBean<T> {<!-- -->
    private int totalCount;//总记录数
    private int totalPage; //总页码
    private List<T> list;  //每页的数据
    private int currentPage;//当前页码
    private int rows;//每页显示的记录数

    public int getTotalCount() {<!-- -->
        return totalCount;
    }

    public void setTotalCount(int totalCount) {<!-- -->
        this.totalCount = totalCount;
    }

    public int getTotalPage() {<!-- -->
        return totalPage;
    }

    public void setTotalPage(int totalPage) {<!-- -->
        this.totalPage = totalPage;
    }

    public List<T> getList() {<!-- -->
        return list;
    }

    public void setList(List<T> list) {<!-- -->
        this.list = list;
    }

    public int getCurrentPage() {<!-- -->
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {<!-- -->
        this.currentPage = currentPage;
    }

    public int getRows() {<!-- -->
        return rows;
    }

    public void setRows(int rows) {<!-- -->
        this.rows = rows;
    }

    @Override
    public String toString() {<!-- -->
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                '}';
    }
}

```

建立FindUserByPageServlet

```

@WebServlet( "/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {<!-- -->
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
    //1获取参数
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");

        if (currentPage == null || "".equals(currentPage)){<!-- -->
            currentPage ="1";
        }
        if (rows ==null || "".equals(rows)){<!-- -->
            rows="5";
        }
    //2调用service查询

       UserService service=new UserServiceImpl();
       PageBean<User> pb=service.findUserByPage(int currentPage,int rows);
      //3将PageBean存入request
      request.setAttribute("pb",pb);

      //4转发到List.jsp
        request.getRequestDispatcher("/list.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
     this.doPost(request,response);
    }
}

```

建立，编写UserService接口和UserServiceImpl；

```
public interface UserService {<!-- -->
    public  PageBean<User> findUserByPage(int currentPage,int rows);
}


```

```
public class UserServiceImpl implements UserService {<!-- -->
    @Override
    public PageBean<User> findUserByPage(int currentPage, int rows) {<!-- -->
        int rows = Integer.parseInt(_rows);
        int currentPage = Integer.parseInt(_currentPage);
        if (currentPage<=0){<!-- -->
            currentPage=1;
        }
        //1创建空对象
        PageBean<User> pb = new PageBean<User>();
        //2设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //3使用dao查询总记录数
        int totalCount=dao.findTotalCount();
        pb.setTotalCount(totalCount);
        //4使用dao查询list
          //计算开始的记录索引
        int start=(currentPage-1)*rows;
        List<User> list=dao.findByPage(start,rows);
        pb.setList(list);

        //5计算总页码
        int totalPage=(totalCount %rows)==0? totalCount/rows:(totalCount/rows)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }
}


```

创建UserDao接口和UserDaoImpl实现类；

```
public interface UserDao {<!-- -->

     //查询总记录数
    int findTotalCount();
    //分页查询总记录i数
    List<User> findByPage(int start, int rows);

}

```

```
public class UserDaoimpl implements UserDao {<!-- -->
    @Override
    public int findTotalCount() {<!-- -->
       String sql="select count(*) from user";

        return template.queryForObject(sql,Integer.class);
    }

    @Override
    public List<User> findByPage(int start, int rows) {<!-- -->
        String sql="select * from user limit ?,?";
        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),start,rows);
    }
}

```

## 前端代码编写

```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {<!-- -->
            text-align: center;
        }
    </style>
    <script>
        function deleteUser(id){<!-- -->
            //用户安全提示
            if(confirm("您确定要删除吗？")){<!-- -->
                //访问路径
                location.href="${pageContext.request.contextPath}/delUserServlet?id="+id;
            }
        }
        window.onload=function () {<!-- -->
            //给删除选中添加单击时间
            document.getElementById("delSelected").onclick=function () {<!-- -->
                var flag=false;
                if(confirm("您确定要删除吗？")){<!-- -->
                    var cbs = document.getElementsByName("uid");
                    for(var i=0;i<cbs.length;i++){<!-- -->
                        if(cbs[i].checked){<!-- -->
                            flag=true;
                            break;
                        }

                    }
                    if (flag) {<!-- -->
                        //访问路径
                        document.getElementById("from").submit();
                    }


                }

            }
            //1获取第一个cb
            document.getElementById("firstCb").onclick=function () {<!-- -->
                //获取下边列表中 =得cb
                var cbs = document.getElementsByName("uid");
                for(var i=0;i<cbs.length;i++){<!-- -->
                    //设置这些cbs得checked状态=firstCb.check
                    cbs[i].checked=this.checked;
                }


            }
        }

    </script>
</head>
<body>

    <h3 style="text-align: center">用户信息列表</h3>
    

        <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post">
            
                <label for="exampleInputName2">姓名</label>
                <input type="text" name="name" value="${condition.name[0]}" class="form-control" id="exampleInputName2" >
            
            
                <label for="exampleInputName3">籍贯</label>
                <input type="text" name="address" value="${condition.address[0]}" class="form-control" id="exampleInputName3" >
            

            
                <label for="exampleInputEmail2">邮箱</label>
                <input type="text" name="email" value="${condition.email[0]}" class="form-control" id="exampleInputEmail2"  >
            
            <button type="submit" class="btn btn-default">查询</button>
        </form>

    
    
        [添加联系人](${pageContext.request.contextPath}/add.jsp)
        [删除选中](javascript:void (0);)
    
    <form id="from" action="${pageContext.request.contextPath}/delSelectedServlet" method="post">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" id="firstCb"></th>
                <th>编号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>籍贯</th>
                <th>QQ</th>
                <th>邮箱</th>
                <th>操作</th>
            </tr>

            <c:forEach items="${pb.list}" var="user" varStatus="s">
                <tr>
                    <th><input type="checkbox" name="uid" value="${user.id}"></th>
                    <td>${<!-- -->s.count}</td>
                    <td>${<!-- -->user.name}</td>
                    <td>${<!-- -->user.gender}</td>
                    <td>${<!-- -->user.age}</td>
                    <td>${<!-- -->user.address}</td>
                    <td>${<!-- -->user.qq}</td>
                    <td>${<!-- -->user.email}</td>
                    <td>[修改](${pageContext.request.contextPath}/findUserServlet?id=${user.id})&amp;nbsp;
                        [删除](javascript:deleteUser(${user.id});)</td>
                </tr>

            </c:forEach>


            <%--        <tr>--%>
            <%--            <td colspan="8" align="center">[添加联系人](add.html)</td>--%>
            <%--        </tr>--%>
        </table>
    </form>
    
        <nav aria-label="Page navigation">

<%--            第一页禁用--%>
            <ul class="pagination">
                <c:if test="${pb.currentPage==1}">
                    <li class="disabled">
                </c:if>

                <c:if test="${pb.currentPage!=1}">
                   <li >
                </c:if>
<%--                    当前页码减一页--%>
                <li>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage -1}&amp;rows=5" aria-label="Previous">
                        &amp;laquo;
                    </a>
                </li>
                <c:forEach begin="1" end="${pb.totalPage}" var="i">

                    <c:if test="${pb.currentPage ==i}">
                        - [${<!-- -->i}](${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&amp;rows=5)
                    </c:if>
                    <c:if test="${pb.currentPage !=i}">
                        - [${<!-- -->i}](${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&amp;rows=5)
                    </c:if>

                </c:forEach>

<%--                 当前页码加一页--%>
                <li>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage +1}&amp;rows=5" aria-label="Next">
                        &amp;raquo;
                    </a>
                </li>
                
                        共${<!-- -->pb.totalCount}条记录,共${<!-- -->pb.totalPage}页
                    
            </ul>
        </nav>
    

</body>
</html>


```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/103943972