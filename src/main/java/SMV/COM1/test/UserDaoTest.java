package SMV.COM1.test;

import SMV.COM1.dao.UserDao;
import SMV.COM1.pojo.User;
import SMV.COM1.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {

    @Test
    public void test(){
        //我们通过工程类创建SqlSession操作数据库的对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //然后通过sqlsession对象获取我们dao层的接口
        //方式一
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        //方式二(不推荐使用)
//        List<UserDao> UserDao = sqlSession.selectList("com.yunduo.dao.UserDao.class");

        //返回的对象执行自己的接口
        List<User> userList = mapper.getUserList();
        //如果这里打印出错，那么就是没有配置myabtis-config.xml中mappers中注册到mybatis中，以及要配置maven的pom.xml中build设置过滤
        //输出
        for (User user : userList) {
            System.out.println(user);
        }
        //关闭数据库对象
        sqlSession.close();

    }
}


