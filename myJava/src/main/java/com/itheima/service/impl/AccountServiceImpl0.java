package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.service.IAccountService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 业务层实现类
 */
//@Component("accountService")
//@Service("accountService")
//@Scope("prototype")//**作用**：用于指定bean的作用范围 常用取值：singleton, prototype
public class AccountServiceImpl0 implements IAccountService {
    public AccountServiceImpl0() {
        System.out.println("accountService 对象创建了");
    }

    private IAccountDao accountDao;

    public void setAccountDao0(AccountDaoImpl accountDao){
        this.accountDao = accountDao;
    }
    //１. 业务层 service
    // 方法1：没有使用spring 直接创建，耦合高
//    IAccountService accountService1 = new AccountServiceImpl();
    // 方法2：没有使用spring，而是引入bean.properties, 使用自己创建的BeanFactory类来实例化，降低了耦合度
//    IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");
    // 方法3：使用spring，引入bean.xml

    @Override
    public void InsertAccount() {
        accountDao.insertAccount();
        System.out.println("service中的InsertAccount方法执行了");
    }

    public void init(){
        System.out.println("对象初始化了。。。。");
    }

    public void destroy(){
        System.out.println("对象销毁了。。。。");
    }


}