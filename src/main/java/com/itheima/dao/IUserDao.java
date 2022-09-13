package com.itheima.dao;

import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 用户的持久层  接口
 */

public interface IUserDao {
    /**
     * 查询操作
     */
    //改用注解，第二步：删除dao的xml文件，改为直接在注解写sql。 如果不删除dao的xml 文件，会报错的。因为机器不知道要执行注解还是xml文件的sql。
//    @Select("select * from user;")
    List<User> findAll();

    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(Integer userId);

    /**
     *在持久层接口中添加查询单个方法：
     * 根据 id 查询用户信息
     * @param userId
     * @return
     */
    User findById(Integer userId);

    /**
     * 根据姓名模糊查询
     * @return
     */
    List<User> findByName(String name);

    /**
     * 分页查询
     * @return
     */
    List<User> findByPage(@Param("start")int start, @Param("rows")int rows);

    /**
     * 在持久层接口中添加查询总的记录条数：
     * 查询总用户数
     * @return
     */
    int findTotal();


    /**
     * 在持久层接口中添加根据QueryVo中的条件查询用户的方法：
     * 根据QueryVo中的条件查询用户
     * @param
     * @return
     */

    List<User> findUserByVo(QueryVo vo);


}

