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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MybatisTest5 {
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

//一、简单类型的参数
// 1.1 单个参数

    /**
     * 测试查询单个方法
     */
    @Test
    public void testFindOne(){
        //执行查询单个方法
        User user = userDao.findById(52);
        System.out.println(user);
    }

//1.2 多个参数
    /**
     * 测试分页查询
     */
    @Test
    public void testFindByPage(){
        //执行查询一个方法
        List<User> users = userDao.findByPage(0, 3);
        for (User user:users){
            System.out.println(user);
        }
    }

//    二、pojo 对象和 pojo 包装对象
//2.1 传递 pojo 对象
    /**
     * 更新操作
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(46);
        user.setUsername("mybatis upodateuser");
        user.setAddress("更新的地址");
        user.setSex("女");
        user.setBirthday(new Date());

        //执行保存方法
        userDao.updateUser(user);
    }

//2.2 传递 pojo 包装对象
    /**
     * 测试使用queryVo作为查询条件
     */
    @Test
    public void testFindUserByVo(){
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setUsername("%王%");
        queryVo.setUser(user);

        //执行查询一个方法
        List<User> users = userDao.findUserByVo(queryVo);
        /* List<User> users = userDao.findByName("王");*/
        for (User u:users){
            System.out.println(u);
        }
    }

//    三、传入参数类型是 Map
    @Test
    public void testGetBeanList(){
//        select * from user where id=#{MapKey1} and code = #{MapKey2}
        HashMap<Object,Object> h = new HashMap<>();
        h.put("MapKey1",1);
        h.put("MapKey2","admin1");
        List<User> users = userDao.getBeanList(h);
        System.out.println("id = "+users.get(0).getId());
        System.out.println("username = "+users.get(0).getUsername());
    }

//    四、传入参数类型是 List
    @Test
    public void testFindByList(){
        Integer[] ids = new Integer[]{1,2};
        List<Integer> sid = Arrays.asList(ids);
        List<User> users = userDao.findByList(sid);
        System.out.println("id = "+users.get(0).getId());
        System.out.println("username = "+users.get(0).getUsername());
    }
}

