package SpringTest.SpringDemo3.remli.service.impl;

import SpringTest.SpringDemo3.remli.dao.impl.UserDao;

public class UserServiceImpl{
    private UserDao userDao;
    private int age;
    public UserServiceImpl(UserDao userDao, int age) {
        /*有参构造*/
        this.userDao = userDao;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save() {
        userDao.save();
    }


}


