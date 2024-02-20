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
//        ApplicationContext ac = new ClassPathXmlApplicationContext("com/bean.xml");
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("com/bean.xml");//可以调用ac.close()手动关闭容器

            //2.根据id获取bean对象
            //两种方式获取对象
        IAccountService accountService = (IAccountService) ac.getBean("accountService");
        IAccountService accountService0 = (IAccountService) ac.getBean("accountService0");
        IAccountService accountService2 = (IAccountService) ac.getBean("accountService2");
        IAccountService accountService3 = (IAccountService) ac.getBean("accountService3");
        IAccountService accountService4 = (IAccountService) ac.getBean("accountService4");
        IAccountService accountService5 = (IAccountService) ac.getBean("accountService5");
        IAccountService accountService6 = (IAccountService) ac.getBean("accountService6");
        IAccountService accountService7 = (IAccountService) ac.getBean("accountService7");
        IAccountDao accountDao = ac.getBean("accountDao", IAccountDao.class);

        //打印对象
        System.out.println("accountService的值是" + accountService);
        System.out.println("accountService的值是" + accountService+" 两个实例地址相同，默认是单例");
        System.out.println(accountDao);

//        accountService.InsertAccount();
        accountService0.InsertAccount();


//      关闭容器 调用destroy方法1 注册钩子关闭容器  在容器未关闭之前，提前设置好回调函数，让JVM在退出之前回调此函数来关闭容器
        ac.registerShutdownHook();
//      关闭容器 调用destroy方法2 close()是在调用close()的时候关闭
//        如果采用 ApplicationContext ac = new ClassPathXmlApplicationContext(“bean.xml”) 的方式获取核心容器，那么容器就没有手动关闭容器 close() 的方法。
//        ac.close();


    }
}

