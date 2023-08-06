package SpringTest.SpringDemo3.remli.factory;

import SpringTest.SpringDemo3.remli.dao.impl.UserDao;
import SpringTest.SpringDemo3.remli.dao.impl.UserDaoImpl;

public class DynamicFactory {
    /*对象工厂*/
    public UserDao getUserDao() {
        /*返回对象*/
        return new UserDaoImpl();
    }
}
