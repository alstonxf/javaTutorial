package SpringTest.AOP1;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPTest {

    //创建ApplicationContext对象
    private ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-AOP1.xml");

    @Test
    public void testAOP(){
        System.out.println("//创建ApplicationContext对象");
        // 1.从IOC容器中获取接口类型的对象
        IUserService userService = ac.getBean(IUserService.class);

        // 2.调用方法查看是否应用了切面中的通知
        userService.addUser("张三",20);
    }
}
