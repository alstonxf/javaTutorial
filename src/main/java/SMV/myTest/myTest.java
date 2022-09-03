package SMV.myTest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class myTest {

    public myTest() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        //使用mybatis第一步，获取SqlSessionFactory对象
        String resource = "mybatis-config-mytest.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //这里的sqlSession对象就相当于jdbc中的Connection对象
        //返回sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao UserDao1 = sqlSession.getMapper(UserDao.class);

        //根据ID查询用户
        System.out.println("根据ID查询用户 获取到 getUserById：");
        System.out.println(UserDao1.getUserById(new User(1,null,null)));


        //根据用户名字查询所有user
        System.out.println("根据用户名字查询所有user");
        HashMap<String, Object> n1 = new HashMap<>();
        n1.put("n","张三");
        System.out.println(UserDao1.getUserByName(n1));

        //根据用户名字或id查询所有user
        System.out.println("根据用户名字或id查询所有user");
        HashMap<String,Object> idNameMap1 = new HashMap<>();
        idNameMap1.put("idOrName","id");
        idNameMap1.put("v","1");
        System.out.println(UserDao1.getUserByNameOrId(idNameMap1));

        HashMap<String,Object> idNameMap2 = new HashMap<>();
        idNameMap2.put("idOrName","name");
        idNameMap2.put("v","李四");
        System.out.println(UserDao1.getUserByNameOrId(idNameMap2));

        //关闭连接
        sqlSession.close();
    }


}
