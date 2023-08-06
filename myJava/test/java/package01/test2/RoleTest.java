package package01.test2;

import com.itheima.dao.IRoleDao;
import com.itheima.domain.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class RoleTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IRoleDao roleDao;

    @Before//单元测试之前执行
    public void init() throws Exception{
        //1.读取配置文件
        in = Resources.getResourceAsStream("com/SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象
        roleDao = sqlSession.getMapper(IRoleDao.class);
    }

    @After//单元测试之后执行
    public void destory() throws Exception {
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        List<Role> roles = roleDao.findAll();
        for (Role role:roles){
            System.out.println("-----每个角色的信息-----");
            System.out.println(role);
            System.out.println(role.getUsers());
        }
    }
}

