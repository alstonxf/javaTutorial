package com.itheima.dao;

import com.itheima.domain.Account;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAccountDaoAnnotation {

    /**
     * 查询所有账户，并且获取每个账户所属的用户信息
     */
    @Select("select * from account ")
    @Results(id="accountMap", value={
            @Result(id=true, property = "id", column = "id"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "money", column = "money"),
            @Result(property = "user", column = "uid",
                    one = @One(select = "com.itheima.dao.IUserDao.findById", fetchType = FetchType.EAGER))
    })
    List<Account> findAll();


    /**
     * 根据用户id查询账户信息
     */
    @Select("select * from account where uid = #{uid}")
    List<Account> findAccountByUid(Integer userId);

}

