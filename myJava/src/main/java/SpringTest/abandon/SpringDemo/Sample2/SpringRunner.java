package SpringTest.abandon.SpringDemo.Sample2;

//4.创建测试类

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserController userController = ac.getBean("userController",UserController.class);
        userController.reg();
        ac.close();
    }
}