package com.remli.dao.impl;


import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.remli.domain.User;

public class UserDaoImpl implements UserDao {
    /*实现接口的类的方法*/
//    public void save() {
//        System.out.println("程序启动");
//    }

    /*重写无参构造——修改默认构造方法——对应接口*/
    public UserDaoImpl(){
        System.out.println("无参构造修改：UserDaoImpl对象创建...");
    }


    private void destroy() {
        System.out.println("UserDaoImpl destory...");
    }

    private void init() {
        System.out.println("UserDaoImpl init...");
    }

    private String username;
    private int age;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }


    /*定义变量、构造方法*/
    private List<String> strList;
    private Map<String , User> userMap;
    private Properties properties;

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }


    /*实现接口的类的方法*/
    public void save() {
        System.out.println("程序启动2");
        System.out.println(strList);
        System.out.println(userMap);
        System.out.println(properties);
    }

    private String name;
    private String addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }


}
