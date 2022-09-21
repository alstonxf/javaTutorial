package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.*;
import org.junit.Test;

import java.util.List;

public interface IUserDaoAnnotation {

    /**
     * 查询所有
     */
    @Select("select *from user")
    List<User> findAll();

    /**
     * 添加用户
     */
    @Insert("insert into user(username, address, sex, birthday) values(#{username}, #{address}, #{sex}, #{birthday})")
    @SelectKey(keyColumn="id",keyProperty="id",resultType=Integer.class,before =
            false, statement = { "select last_insert_id()" })
    void insertUser(User user);

    /**
     * 更新
     */
    @Update("update user set username=#{username}, sex=#{sex}, address=#{address}, birthday=#{birthday} where id=#{id}")
    void updateUser(User user);

    /**
     * 删除用户
     */
    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);

    /**
     * 根据id查询用户
     */
    @Select("select * from user where id = #{uid}")
    User findById(Integer id);


    /**
     * 根据用户名称模糊查询实现1
     */
    @Select("select * from user where username like #{name}")
    List<User> findUserByNameMethod1(String name);

    /**
     * 根据用户名称模糊查询实现2
     */
    @Select("select * from user where username like '%${value}%'")  //${value}是固定写法
    List<User> findUserByNameMethod2(String username);

    /**
     * 查询总用户数
     */
    @Select("select count(*) from user")
    int findTotalUser();
    @Select("select * from user where username = #{name}")
    List<User> findUserByName(String s);

    /**
     * 使用 Results 注解
     */
    interface IUserDaoByResults {

        /**
         * 查询所有
         */
        @Select("select *from user")
        @Results(id="userMap",
                value= {
                        @Result(id=true,column="id",property="userId"),
                        @Result(column="username",property="userName"),
                        @Result(column="sex",property="userSex"),
                        @Result(column="address",property="userAddress"),
                        @Result(column="birthday",property="userBirthday")
                })
        List<User> findAll();

        /**
         * 根据id查询用户
         */
        @Select("select * from user where id = #{uid}")
        @ResultMap("userMap")
        User findById(Integer id);

        /**
         * 根据用户名称模糊查询实现1
         */
        @Select("select * from user where username like #{name}")
        @ResultMap("userMap")
        List<User> findUserByNameMethod1(String name);

    }


}

