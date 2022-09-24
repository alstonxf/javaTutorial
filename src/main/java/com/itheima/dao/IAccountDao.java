package com.itheima.dao;

import com.itheima.domain.Account;
import com.itheima.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.List;

public interface IAccountDao {

    /**
     * 查询所有账户
     */
    List<Account> findAll();
    List<Account> findAll2();

    //1. 持久层 dao
    IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDao");

    /**
     * 持久层接口
     */
    void insertAccount();

//    二、用于注入数据的 <font color="red">以上三个注入只能诸如其他的bean类型的数据，而基本类型和String类型无法使用上述注解实现。 另外，集合类型的注入只能通过XML来实现。</font>
    @Autowired //**细节**：在使用 Autowired注解注入时，set 方法就不是必须的了。
    @Qualifier("accountDao1")
    IAccountDao accountDao2 = null;

    @Resource(name = "accountDao1")
    IAccountDao accountDao3 = null ;


}

