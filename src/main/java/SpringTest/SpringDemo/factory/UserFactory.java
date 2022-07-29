package SpringTest.SpringDemo.factory;
import SpringTest.SpringDemo.entity.User;

public class UserFactory {
    public static User createUser(){
        return new User();
    }
}
