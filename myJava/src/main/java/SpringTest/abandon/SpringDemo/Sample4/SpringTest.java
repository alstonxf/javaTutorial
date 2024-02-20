package SpringTest.abandon.SpringDemo.Sample4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
    public static void main(String[] args) {
        //1.初始化Spring容器，加载配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringTest/Sample4/sample4.xml","SpringTest/Sample4/sample4_2.xml");
        //2.通过容器获取实例，getBean()方法中的参数是bean标签中的id
        User user =  applicationContext.getBean("user2", User.class);
        //3.调用实例中的属性
        System.out.println("List集合："+user.getLists());
        System.out.println("Set集合："+user.getSets());
        System.out.println("Map集合："+user.getMaps());
        System.out.println("Properties："+user.getProperties());
        System.out.println("数组:");
        String[] array = user.getArray();
        for (String s : array) {
            System.out.println(s);
        }
    }
}