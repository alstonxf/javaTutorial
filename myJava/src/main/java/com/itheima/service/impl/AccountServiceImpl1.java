package com.itheima.service.impl;

import com.itheima.service.IAccountService;

import java.util.Date;

public class AccountServiceImpl1 implements IAccountService {

    //如果是经常变化的数据，并不适用于注入的方式
    private String name;
    private Integer age;
    private Date birthday;

    public AccountServiceImpl1(String UserName, Integer age, Date birthday){
        this.name = UserName;
        this.age = age;
        this.birthday = birthday;
    }

    @Override
    public void InsertAccount() {

    }
}

