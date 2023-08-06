package SMV.COM1.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * Mybatis工具类
 * 工厂类
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static{
        try{
            //使用mybatis第一步，获取SqlSessionFactory对象
            String resource = "SMV/COM1/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch (Exception exception){
            exception.getMessage();
        }
    }
    //这里的sqlSession对象就相当于jdbc中的Connection对象
    //返回sqlSession对象
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}

