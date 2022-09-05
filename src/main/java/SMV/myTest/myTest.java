package SMV.myTest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

public class myTest {
//    然后创建 Logger写入器：
    private static Logger logger = Logger.getLogger(myTest.class.getName());

    public myTest() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        logger.info("info：启动test1成功");
        logger.debug("debug：启动test1成功");
        logger.error("error：启动test1成功");

//        1. Properties加载我们mybatis全局配置文件
//        2. 创建我们SqlSessionFactoryBuilder对象
//        3. SqlSessionFactoryBuilder的builder方法里面会解析mybatis全局配置文件
//        4. 最后得到我们configuration所有的配置信息
//        5. 接着就可以实例化我们的sqlsessionFactory对象
//        6.  sqlsessionfactory对象中里面有这个事务管理器，来控制我们sql的事务的
//        7. 接着创建executor执行器，来执行我们crud的操作的，
//        8. 然后检查crud是否执行成功，如果执行失败就回到我们事务管理器回滚，
//        9.  如果执行成功，就提交事务

        //1.读取配置文件
        String resource = "mybatis-config-mytest.xml";// 1. Properties加载我们mybatis全局配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder Builder = new SqlSessionFactoryBuilder();//2. 创建我们SqlSessionFactoryBuilder对象
        SqlSessionFactory sqlSessionFactory = Builder.build(inputStream);//3. SqlSessionFactoryBuilder的builder方法里面会解析mybatis全局配置文件

        //3.使用工厂生产SqlSession对象
        //这里的sqlSession对象就相当于jdbc中的Connection对象
        //返回sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4.使用SqlSession创建Dao接口的代理对象
        //关联上接口，然后就可以调用接口方法了。
        UserDao UserDao1 = sqlSession.getMapper(UserDao.class);
        LocationDao LocationDao1 = sqlSession.getMapper(LocationDao.class);

        //5.使用代理对象执行方法
        //调用一个bean Location
        //使用LocationMapper.xml 调用LocationDao的方法
        List<Location> locationList = LocationDao1.getLocationList();
        System.out.println("使用LocationMapper.xml 调用LocationDao的方法");
        for (Location l:locationList){
            System.out.println(l.toString());
        }

        //调用bean USER,用的mapper2
        //使用UserMapper2调用UserDao接口的getUserList2方法查询
        System.out.println("根据ID查询用户 获取到 getUserById：");
        System.out.println(UserDao1.getUserList2());


        //调用相同bean USER,用的mapper1
        System.out.println("获取到 getUserList：");
        System.out.println(UserDao1.getUserList());

//        //调用相同bean USER,用的mapper1
//        System.out.println("根据ID查询用户 获取到 getUserListPassword：");
//        System.out.println(UserDao1.getUserListPassword());
//
//        //根据ID查询用户
//        System.out.println("根据ID查询用户 获取到 getUserById：");
//        System.out.println(UserDao1.getUserById(new User(1,null,null)));
//
//        //根据用户名字查询所有user
//        System.out.println("根据用户名字查询所有user");
//        HashMap<String, Object> n1 = new HashMap<>();
//        n1.put("n","张三");
//        System.out.println(UserDao1.getUserByName(n1));
//
//        //根据用户名字或id查询所有user
//        System.out.println("根据用户名字或id查询所有user");
//        HashMap<String,Object> idNameMap1 = new HashMap<>();
//        idNameMap1.put("idOrName","id");
//        idNameMap1.put("v","1");
//        System.out.println(UserDao1.getUserByNameOrId(idNameMap1));
//
//        HashMap<String,Object> idNameMap2 = new HashMap<>();
//        idNameMap2.put("idOrName","name");
//        idNameMap2.put("v","李四");
//        System.out.println(UserDao1.getUserByNameOrId(idNameMap2));

        //分页查询
        //我们通过mybatis自带的插件RowBounds对象进行分页
        System.out.println("测试分页查询：方法一");
        RowBounds rowBounds = new RowBounds(0,2);
        List<User> user = sqlSession.selectList("SMV.myTest.UserDao.getUserList", null, rowBounds);
        for (User user1 : user) {
            System.out.println(user1);
        }
        //分页查询方法2 使用limit
        System.out.println("测试分页查询：方法二");
        UserDao UserDao2 = sqlSession.getMapper(UserDao.class);
        List<User> users2 =  UserDao2.queryUserListPage2();
        for (User u : users2) {
            System.out.println(u);
        }

        //使用注解开发，用注解替代了xml文件。不过注解只能用于简单的sql。
        System.out.println("使用注解开发");
        UserDao UserDao3 = sqlSession.getMapper(UserDao.class);
        List<User> users3 =  UserDao3.queryUserListPage2();
        for (User u : users3) {
            System.out.println(u);
        }
        //6.释放资源
        sqlSession.close();
        inputStream.close();
    }

}
