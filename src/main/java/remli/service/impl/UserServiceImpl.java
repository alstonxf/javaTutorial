package remli.service.impl;

import remli.dao.impl.UserDao;

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

