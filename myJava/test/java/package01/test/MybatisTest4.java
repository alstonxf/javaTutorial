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
import java.util.Date;
import java.util.List;


public class MybatisTest4 {
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
        sqlSession = factory.openSession();
        //4.获取dao的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After//单元测试之后执行
    public void destroy() throws Exception {
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试保存操作
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("testInsertOne");
        user.setAddress("测试地址");
        user.setSex("男");
        user.setBirthday(new Date());
        System.out.println("保存之前:"+user);

        //执行保存方法
        userDao.saveUser(user);
        System.out.println("保存之后:"+user);
    }

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

    /**
     * 测试删除方法
     */
    @Test
    public void testDelete(){
        //执行删除方法
        userDao.deleteUser(75);
    }

    /**
     * 测试查询单个方法
     */
    @Test
    public void testFindOne(){
        //执行查询单个方法
        User user = userDao.findById(52);
        System.out.println(user);
    }

    /**
     * 测试模糊查询
     */
    @Test
    public void testFindByName(){

        //方式2
        List<User> users = userDao.findByName("%王%");
        for (User user:users){
            System.out.println(user);
        }
    }

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

    /**
     * 查询总记录数
     */
    @Test
    public void testFindTotal(){
        int total = userDao.findTotal();
        System.out.println(total);
    }

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


}

