package package01.test2;

import com.itheima.dao.IUserDao;
import com.itheima.dao.IUserDaoAnnotation;
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

public class AnnotationCRUDTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private IUserDaoAnnotation userDao;

    @Before
    public void Init() throws IOException {
        in = Resources.getResourceAsStream("com/SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(IUserDaoAnnotation.class);
    }

    @After
    public void destroy() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        for (User user: users){
            System.out.println(user);
        }
    }

    @Test
    public void testInsert(){
        User user =  new User();
        user.setUsername("annotation_user01");
        user.setBirthday(new Date());
        user.setAddress("山西省太原市");
        //插入之前
        System.out.println(user);
        userDao.insertUser(user);
        //插入之后
        System.out.println(user);
    }

    @Test
    public void testUpdate(){
        User user =  new User();
        user.setId(77);
        user.setUsername("update_user");
        user.setAddress("北京市");
        user.setBirthday(new Date());
        userDao.updateUser(user);
    }

    @Test
    public void testDelete(){
        userDao.deleteUser(77);
    }

    @Test
    public void testFindById(){
        User user = userDao.findById(41);
        System.out.println(user);
    }

    @Test
    public void testFindUserByNameMethod1(){
        String name = "%王%";
        List<User> users = userDao.findUserByNameMethod1(name);
        for(User user: users){
            System.out.println(user);
        }
    }

    @Test
    public void testFindUserByNameMethod2(){
        List<User> users = userDao.findUserByNameMethod2("王");
        for(User user: users){
            System.out.println(user);
        }
    }

    @Test
    public void testFindTotalUser(){
        int totalUser = userDao.findTotalUser();
        System.out.println(totalUser);
    }


    @Test
    public void testFindByName(){
        List<User> users = userDao.findUserByName("%王%");
        for (User user: users){
            System.out.println(user);
        }
    }


}

