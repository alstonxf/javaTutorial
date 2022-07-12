package com.remli.factory;

import com.remli.dao.impl.UserDao;
import com.remli.dao.impl.UserDaoImpl;

public class DynamicFactory {
    /*对象工厂*/
    public UserDao getUserDao() {
        /*返回对象*/
        return new UserDaoImpl();
    }
}
