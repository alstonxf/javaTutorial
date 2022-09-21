package package01.test;

import com.itheima.dao.IUserDao;
import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;


public class MybatisTest8 {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before//单元测试之前执行
    public void init() throws Exception{
        //1.读取配置文件
        in = Resources.getResourceAsStream("com/SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession(true);//参数为true，设置为自动提交commit
        //4.获取dao的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After//单元测试之后执行
    public void destroy() throws Exception {
//        //提交事务,如果设置了自动提交，可以注释掉。
//        sqlSession.commit();
        //释放资源
        sqlSession.close();
        in.close();
    }


    /**
     * 测试条件查询
     */
    @Test
    public void testFindByCondition(){
        User u = new User();
        u.setUsername("老王");
        u.setId(43);
        List<User> users = userDao.findUserIfLabelByCondition(u);
        for (User user:users){
            System.out.println(user);
        }
    }

    /**
     * 测试条件查询
     */
    @Test
    public void testFindUserWhereLabelByCondition(){
        User u = new User();
        u.setUsername("老王");
        u.setId(43);
        List<User> users = userDao.findUserWhereLabelByCondition(u);
        for (User user:users){
            System.out.println(user);
        }
    }

    @Test
    public void testFindInIdsByList(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(41);
        list.add(42);
        list.add(47);

        List<User> users = userDao.findUserInIdsByList(list);
        for (User user:users){
            System.out.println(user);
        }
    }

    @Test
    public void testFindUserByBindLabel(){
        User u = new User();
        u.setUsername("王");

        List<User> users = userDao.findUserByBindLabel(u);
        for (User user:users){
            System.out.println(user);
        }
    }


//    ## 动态 SQL 的 bind 标签
//    bind 标签可以使用 OGNL 表达式创建一个变量井将其绑定到上下文中，==其主要用于模糊查询，防止 SQL 注入。==

    @Test
    public void testFindInIds(){
        QueryVo vo = new QueryVo();
        List<Integer> list = new ArrayList<Integer>();
        list.add(41);
        list.add(42);
        list.add(47);
        vo.setIds(list);
        List<User> users = userDao.findUserInIdsByQueryVo(vo);
        for (User user:users){
            System.out.println(user);
        }
    }

    //动态 SQL 的 set 标签
    @Test
    public void testUpdateUserBySetLabel(){
        User user = new User();
        user.setId(46);
        user.setUsername("mybatis upodateuser");
        user.setAddress("更新的地址");
        user.setSex("女");
        user.setBirthday(new Date());

        //执行保存方法
        userDao.updateUserBySetLabel(user);
    }

    //动态 SQL 的 trim 标签
    @Test
    public void testFindUserByTrimLabel(){
        User user = new User();
        user.setUsername("admin1");
        user.setSex("f");
        user.setBirthday(new Date());

        //执行保存方法
        List<User> u = userDao.findUserByTrimLabel(user);
    }

    /**
     * 测试使用SQL片段
     */
    @Test
    public void testFindAll2(){
        List<User> u = userDao.findAll2();
        System.out.println(u);
    }
    @Test
    public void testFindById2(){
        User u = userDao .findById2(2);
        System.out.println(u);
    }

}
