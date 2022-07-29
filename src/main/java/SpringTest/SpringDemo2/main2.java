package SpringTest.SpringDemo2;

import SpringTest.SpringDemo3.remli.dao.impl.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class main2 {
    public static void main(String[] args) {
//        五、SpringAPI
//        1、ApplicationContext的继承
//        applicationContext:接口类型，代表应用上下文，可通过其实例获得Spring容器中的Bean对象。
//        1
//        2、ApplicationContext的实现类
//        1.ClassPathXmlApplicationContext：类 路径 文件类型 应用 上下文——resource下文件(相对)
//        它是从类的根路径下加载配置文件（常用）
//        1
//        /*获取文件流*/
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext2.xml");

        /*接口*/                      /*接口实现*/

//        2.FileSystemXmlApplicationContext：文件 系统 文件类型 应用 上下文——磁盘绝对路径
//        它是从磁盘路径上加载配置文件，配置文件可以在磁盘的任何位置。

        ApplicationContext appp = new FileSystemXmlApplicationContext("/Users/lixiaofeng/Library/Mobile Documents/com~apple~CloudDocs/Documents/myGItProject/myJava/src/main/resources/ApplicationContext3.xml");

//        3.AnnotationConfigApplicationContext：注解 配置 应用 上下文——注解开发
//        当使用注解配置容器对象时，需要使用此类来创建Spring容器。它用来读取注解。
//        1
//        3、getBean()方法的使用
//        1.强转类型
//        一个是通过id来获取：某一个类型可存在多个，获取后需要强转。
//        1
        UserDao u111= (UserDao) appp.getBean("userDao222");


//        2.直接获取类对象：类文件直接指定
//        一个通过返回值来获取：一种类型只能有一个，获取后不需要强转。

        UserDao userDao = app.getBean(UserDao.class);

//        3.通过java对象获取
        Object d111 = appp.getBean("userDao");
    }
}
