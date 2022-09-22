package com.itheima.dao;

import com.itheima.domain.Account;
import com.itheima.factory.BeanFactory;

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
}

