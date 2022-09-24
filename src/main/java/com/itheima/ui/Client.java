package com.itheima.ui;

import com.itheima.dao.IAccountDao;
import com.itheima.factory.BeanFactory;
import com.itheima.service.IAccountService;
import com.itheima.service.impl.AccountServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 模拟表现层，用于调用业务层
 */
public class Client {
    @PostConstruct //作用：用于指定初始化方法
    public void init(){
        System.out.println("初始化");
    }
    @PreDestroy //作用：用于指定销毁方法
    public void destroy(){
        System.out.println("销毁方法Service");//这里单例对象需要手动关闭才会执行该方法
    }
    /**
     * 获取spring的ioc核心容器，并根据id获取对象
     */
    public static void main(String[] args) {

        //实例化方法1：不使用spring，直接new
        //IAccountService accountService = new AccountServiceImpl(); 可以用从bean获取替代
        //实例化方法2：不使用spring，使用factory
//        IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");
        //实例化方法3：使用spring
            //1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("com/bean.xml");

            //2.根据id获取bean对象
            //两种方式获取对象
        IAccountService accountService = (IAccountService) ac.getBean("accountService");
        IAccountService accountService2 = (IAccountService) ac.getBean("accountService2");
        IAccountService accountService3 = (IAccountService) ac.getBean("accountService3");
        IAccountService accountService4 = (IAccountService) ac.getBean("accountService4");
        IAccountService accountService5 = (IAccountService) ac.getBean("accountService5");
        IAccountService accountService6 = (IAccountService) ac.getBean("accountService6");
        IAccountService accountService7 = (IAccountService) ac.getBean("accountService7");
        IAccountDao accountDao = ac.getBean("accountDao", IAccountDao.class);

        //打印对象
        System.out.println("accountService的值是" + accountService);
        System.out.println(accountDao);

        accountService.InsertAccount();

        //手动关闭容器
//        如果采用 ApplicationContext ac = new ClassPathXmlApplicationContext(“bean.xml”) 的方式获取核心容器，那么容器就没有手动关闭容器 close() 的方法。
//        ac.close();


    }
}

