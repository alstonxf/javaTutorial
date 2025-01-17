package SpringTest.SpringDemo3.remli.factory;

import SpringTest.SpringDemo3.remli.dao.impl.UserDao;
import SpringTest.SpringDemo3.remli.dao.impl.UserDaoImpl;

public class StaticFactory {
    /*对象工厂——对象创建——返回对象*/
    public static UserDao getUserDao() {
        /*返回对象*/
        return new UserDaoImpl();
    }
}
