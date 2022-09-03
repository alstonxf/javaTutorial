package SMV.myTest;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    //查询所有用户
    List<User> getUserList();
    //根据ID查询用户
    User getUserById(User user);
    //新增用户
    int AddUser(User user);
    //根据ID修改用户
    int UpdateUserById(User user);
    //根据用户ID清除用户
    int DeleteUserById(User user);

    //根据ID查询用户
    User getUserById2(HashMap<String,Object> map);

    User getUserByName(HashMap<String,Object> map);

    User getUserByNameOrId(HashMap<String,Object> map);

}


