package SpringTest.SpringDemo3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Core:核心容器提供Spring框架的基本功能，其主要组件BeanFactory是工厂模式的实现。它通过控制反转（IOC）机制，将应用程序配置和依赖性规范与实际的程序代码分离开。
 * Spring Context:想Spring框架提供上下文信息，包括企业服务。
 * Spring DAO：JDBC DAO抽象层提供了有用的一场层次结构，用来管理异常处理和不同数据库供应商抛出的错误信息。
 */
public class App {
    public static void main( String[] args ) {
        //获取ApplicationContext对象
//        ApplicationContext application=new ClassPathXmlApplicationContext("ApplicationContext3.xml");
        ApplicationContext application=new ClassPathXmlApplicationContext("SpringTest/SpringDemo3/ApplicationContext3.xml");
        //通过ApplicationContext获得TestHello对象
        //getBean（）方法中的参数即为配置文件中Bean的id的值
        TestHello testHello=(TestHello) application.getBean("TestHello");
        System.out.println(testHello.getMessage());
    }
}