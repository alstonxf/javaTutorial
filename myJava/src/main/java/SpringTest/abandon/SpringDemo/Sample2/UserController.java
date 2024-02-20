package SpringTest.abandon.SpringDemo.Sample2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//3.创建控制器类
@Controller
public class UserController {
    @Autowired // 注意：此处使用了自动装配的注解
    private UserMapper userMapper;
    public void reg() {
        System.out.println("UserController.reg() >> 控制器即将执行用户注册……");
        userMapper.insert();
    }
}