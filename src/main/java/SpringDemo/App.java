package SpringDemo;

import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class App {
    public static void main( String[] args ) {
        //获取ApplicationContext对象
//        ApplicationContext application=new ClassPathXmlApplicationContext("ApplicationContext.xml");
        ApplicationContext application=new ClassPathXmlApplicationContext("ApplicationContest.xml");
        //通过ApplicationContext获得TestHello对象
        //getBean（）方法中的参数即为配置文件中Bean的id的值
        TestHello testHello=(TestHello) application.getBean("TestHello");
        System.out.println(testHello.getMessage());
    }
}