package SpringTest.Sample3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author
 * Spring 入门测试使用xml
 * @desc 测试类
 */
public class TestHelloSpring {
    public static void main(String[] args) {
        //传统方式：new 对象() 紧密耦合
        System.out.println("传统方式：new 对象() 紧密耦合");
        HelloSpring helloSpring = new HelloSpringImpl();
        helloSpring.sayHello();

        //Spring方式：XML解析+反射+工厂模式
        System.out.println("Spring方式：XML解析+反射+工厂模式");
        //因为这里是测试使用，所以需要初始化Spring容器，并且加载配置文件，实际开发中这一步不需要。
        //1.初始化Spring容器，加载配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("sample3.xml");
        //2.通过容器获取helloSpring实例，getBean()方法中的参数是bean标签中的id
        HelloSpring helloSpring1 = (HelloSpring) applicationContext.getBean("helloSpring");
        //3.调用实例中的方法
        helloSpring1.sayHello();
    }
}