package SpringTest.SpringDemo2;

import SpringTest.SpringDemo3.remli.dao.impl.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class main {
    public static void main(String[] args) {
        /*解析application.xml配置文件获取Bean实例(对象)*/
        ApplicationContext app = new ClassPathXmlApplicationContext("SpringTest/SpringDemo/applicationContext2.xml");
        /*对象获取并强制转换类型*/
        UserDao userDao = (UserDao) app.getBean("userDao");
        /*对象调用方法*/
        userDao.save();

        /*接口*/      /*接口实现*/
        UserDao ud = (UserDao) app.getBean("userDao11");
        /*获取对象*/  /*强转对象*/
        Object ud1 = app.getBean("userDao11");
        ud.save();

        /*接口*/      /*接口实现*/
        UserDao u2= (UserDao) app.getBean("userDao222");
        /*获取对象*/  /*强转对象*/
        Object d2 = app.getBean("userDao222");
        u2.save();


    }
}
//    /*获取文件流*/
//    ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext2.xml");
//    /*接口*/                      /*接口实现*/
//
//    /*获取对象*/                /*强转对象*/
//    UserService userService = (UserService) app.getBean("userService");
//
//    userService.save();
