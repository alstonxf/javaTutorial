package SpringTest.SpringDemo;

import SpringTest.SpringDemo.entity.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestDemo {
    @Test
    public void test1(){
//        实现类ClassPathXmlApplicationContext是加载类路径下的spring配置文件
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//        FileSystemXmlApplicationContext是加载本地磁盘下spring的配置文件
//        ApplicationContext ac = new FileSystemXmlApplicationContext("applicationContext.xml");
//        User user = (User) ac.getBean("user");
//        结果显示，将user对象交给spring容器管理成功
//        System.out.println(user);

    }
}