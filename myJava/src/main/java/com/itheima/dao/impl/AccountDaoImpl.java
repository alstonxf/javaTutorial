package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;

import java.util.List;

/**
 * 账户的持久层实现类
 */
public class AccountDaoImpl implements IAccountDao {
    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public List<Account> findAll2() {
        return null;
    }

    public void insertAccount() {
        int i = 0;
        System.out.println("保存了账户");
        System.out.println(i);
        i++;
    }
}