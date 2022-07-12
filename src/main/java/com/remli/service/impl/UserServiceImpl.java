package com.remli.service.impl;

import com.remli.dao.impl.UserDao;
import com.remli.dao.impl.UserDaoImpl;

public class UserServiceImpl{
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        /*有参构造*/
        this.userDao = userDao;
    }


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save() {
        userDao.save();
    }


}

