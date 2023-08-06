package SpringTest.AOP1.service;

import SpringTest.AOP1.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * 目标类，代理对象实现类，会被动态代理
 */
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public void addUser(String userName, Integer age) {
        System.out.println(userName+":"+age);
    }
}