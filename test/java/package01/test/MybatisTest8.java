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

import java.io.IOException;
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
    //    ### 2.2 一级缓存注意事项
//    一级缓存是 SqlSession 范围的缓存，当调用SqISession 的修改，添加，删除，commit(), close()等方法时，就会清空一级缓存。
//   **示例代码1：  虽然我们查询了两次，但最后只执行了一次数据库操作，这就是 Mybatis 提供给我们的一级缓存在起作用了。因为一级缓存的存在，导致第二次查询 id 为 41 的记录时，并没有发出 sql 语句从数据库中查询数据，而是从一级缓存中查询。
    @Test
    public void testFirstLevelCache_1() {
        User user1 = userDao.findById(41);
        System.out.println(user1);
        User user2 = userDao.findById(41);
        System.out.println(user2);
        System.out.println(user1==user2);//打印true
    }
    //    **示例代码2：**  当清空缓存之后，Mybatis 会重新发出 sql 语句进行数据库的查询。
    @Test
    public void testFirstLevelCache_2() {
        User user1 = userDao.findById(41);
        System.out.println(user1);
        // sqlSession.close();
        // sqlSession = factory.openSession();

        sqlSession.clearCache();//此方法也可以清空缓存
        userDao = sqlSession.getMapper(IUserDao.class);

        User user2 = userDao.findById(41);
        System.out.println(user2);
        System.out.println(user1==user2);
    }


    @Test
    public void testClearCache(){
        //1. 查询
        User user1 = userDao.findById(41);
        System.out.println(user1);

        //2. 更新
        user1.setUsername("update clear cache user");
        user1.setAddress("河北省秦皇岛市");
        userDao.updateUser(user1);

        //3. 再次查询
        User user2 = userDao.findById(41);
        System.out.println(user2);
        System.out.println(user1==user2);
    }

//    ### 3.1 二级缓存概述
//    它指的是Mybatis中 SqlSessionFactory 对象的缓存。由同一个 SqlSessionFactory 对象创建的 SqlSession 共享其缓存。
    @Test
    public void testFirstLevelCache() throws IOException {
        //1.读取配置文件
        in = Resources.getResourceAsStream("com/SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

        SqlSession sqlSession1 = factory.openSession();
        IUserDao dao1 = sqlSession1.getMapper(IUserDao.class);
        User user1 = dao1.findById(41);
        System.out.println(user1);
        sqlSession1.close();//一级缓存消失，

        SqlSession sqlSession2 = factory.openSession();
        IUserDao dao2 = sqlSession2.getMapper(IUserDao.class);
        User user2 = dao2.findById(41);
        System.out.println(user2);
        sqlSession2.close();

        //System.out.println(sqlSession1==sqlSession2);
        System.out.println(user1 == user2);//false
        // 因为二级缓存存放的内容是数据，而不是对象。将第一次查询得到对象的数据保存到缓存中，第二次查询时，将数据封装到新的对象user2里面.
        // 虽然是false,但是只发起了一次查询，证明二级缓存开启成功
    }



}
