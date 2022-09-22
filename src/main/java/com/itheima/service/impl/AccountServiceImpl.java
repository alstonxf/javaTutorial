package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.impl.AccountDaoImpl;
import com.itheima.factory.BeanFactory;
import com.itheima.service.IAccountService;

/**
 * 业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao = new AccountDaoImpl();
    //１. 业务层 service
    private IAccountService accountService = (IAccountService) BeanFactory.getBean("accountService");

    public void InsertAccount() {
        accountDao.insertAccount();
    }
}