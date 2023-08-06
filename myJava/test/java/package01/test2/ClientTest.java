package package01.test2;

import com.itheima.dao.IGoodsDao;
import com.itheima.dao.IOrdersDao;
import com.itheima.domain.Orders;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class ClientTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IOrdersDao ordersDao;
    private IGoodsDao goodsDao;

    @Before//单元测试之前执行
    public void init() throws Exception{
        //1.读取配置文件
        in = Resources.getResourceAsStream("com/SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象
        ordersDao = sqlSession.getMapper(IOrdersDao.class);
        goodsDao = sqlSession.getMapper(IGoodsDao.class);
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
     * 测试嵌套查询：根据订单Id查询当前订单所对应的商品信息
     */
    @Test
    public void testNestedQuery(){
        Orders orders = ordersDao.findOrdersNestedQueryByOrdersId(1);
        System.out.println("------------------订单编号为 1 的订单以及商品信息如下：----------------");
        System.out.println(orders);
        System.out.println(orders.getGoodsList());
    }

    /**
     *  测试嵌套结果：根据订单Id查询当前订单所对应的商品信息
     */
    @Test
    public void testNestedResult(){
        Orders orders = ordersDao.findOrdersNestedResultsByOrdersId(1);
        System.out.println("------------------订单编号为 1 的订单以及商品信息如下：----------------");
        System.out.println(orders);
        System.out.println(orders.getGoodsList());
    }


}

