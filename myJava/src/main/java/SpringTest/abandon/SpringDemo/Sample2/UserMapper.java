package SpringTest.abandon.SpringDemo.Sample2;

import org.springframework.stereotype.Repository;

//2.创建另一个组件类(用于数据的交互类)
@Repository
public class UserMapper {
    public void insert() {
        System.out.println("UserMapper.insert() >> 将用户数据写入到数据库 中……");
    }
}
