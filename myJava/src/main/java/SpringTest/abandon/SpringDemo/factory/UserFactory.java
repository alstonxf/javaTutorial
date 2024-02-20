package SpringTest.abandon.SpringDemo.factory;
import SpringTest.abandon.SpringDemo.entity.User;

public class UserFactory {
    public static User createUser(){
        return new User();
    }
}
