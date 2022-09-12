# javaweb5删除选中
<img src="https://img-blog.csdnimg.cn/20200311210814599.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 使用form表单进行选中然后提交id数组，后台获取在所有都删除。

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

        window.onload = function(){<!-- -->
            //给删除选中按钮添加单击事件
            document.getElementById("delSelected").onclick = function(){<!-- -->
                if(confirm("您确定要删除选中条目吗？")){<!-- -->

                   var flag = false;
                    //判断是否有选中条目
                    var cbs = document.getElementsByName("uid");
                    for (var i = 0; i < cbs.length; i++) {<!-- -->
                        if(cbs[i].checked){<!-- -->
                            //有一个条目选中了
                            flag = true;
                            break;
                        }
                    }

                    if(flag){<!-- -->//有条目被选中
                        //表单提交
                        document.getElementById("form").submit();
                    }

                }

            }
            //1.获取第一个cb
            document.getElementById("firstCb").onclick = function(){<!-- -->
                //2.获取下边列表中所有的cb
                var cbs = document.getElementsByName("uid");
                //3.遍历
                for (var i = 0; i < cbs.length; i++) {<!-- -->
                    //4.设置这些cbs[i]的checked状态 = firstCb.checked
                    cbs[i].checked = this.checked;

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
        [删除选中](javascript:void(0);)

    
    <form id="form" action="${pageContext.request.contextPath}/delSelectedServlet" method="post">
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
                <td><input type="checkbox" name="uid" value="${user.id}"></td>
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


    </table>
    </form>
    
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${pb.currentPage == 1}">
                    <li class="disabled">
                </c:if>

                <c:if test="${pb.currentPage != 1}">
                    <li>
                </c:if>


                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage - 1}&amp;rows=5&amp;name=${condition.name[0]}&amp;address=${condition.address[0]}&amp;email=${condition.email[0]}" aria-label="Previous">
                        &amp;laquo;
                    </a>
                </li>


                <c:forEach begin="1" end="${pb.totalPage}" var="i" >


                    <c:if test="${pb.currentPage == i}">
                        - [${<!-- -->i}](${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&amp;rows=5&amp;name=${condition.name[0]}&amp;address=${condition.address[0]}&amp;email=${condition.email[0]})
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        - [${<!-- -->i}](${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&amp;rows=5&amp;name=${condition.name[0]}&amp;address=${condition.address[0]}&amp;email=${condition.email[0]})
                    </c:if>

                </c:forEach>


                <li>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage + 1}&amp;rows=5&amp;name=${condition.name[0]}&amp;address=${condition.address[0]}&amp;email=${condition.email[0]}" aria-label="Next">
                        &amp;raquo;
                    </a>
                </li>
                
                    共${<!-- -->pb.totalCount}条记录，共${<!-- -->pb.totalPage}页
                

            </ul>
        </nav>


    





</body>
</html>


```

## DelSelectedServlet

```
package cn.itcast.web.servlet;

import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delSelectedServlet")
public class DelSelectedServlet extends HttpServlet {<!-- -->
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        //1.获取所有id
        String[] ids = request.getParameterValues("uid");
        //2.调用service删除
        UserService service = new UserServiceImpl();
        service.delSelectedUser(ids);

        //3.跳转查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/userListServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {<!-- -->
        this.doPost(request, response);
    }
}


```

## service层

```
    /**
     * 批量删除用户
     * @param ids
     */
    void delSelectedUser(String[] ids);

```

```
    @Override
    public void delSelectedUser(String[] ids) {<!-- -->
        if(ids != null &amp;&amp; ids.length > 0){<!-- -->
            //1.遍历数组
            for (String id : ids) {<!-- -->
                //2.调用dao删除
                dao.delete(Integer.parseInt(id));
            }
        }

    }

```
<li/>
## dao层
<li/>
```
    @Override
    public void delete(int id) {<!-- -->
        //1.定义sql
        String sql = "delete from user where id = ?";
        //2.执行sql
        template.update(sql, id);
    }

```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/104805783