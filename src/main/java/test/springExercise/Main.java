package test.springExercise;

//package test.springExercise;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class TestCase {
//    public void run() {
//        public abstract boolean doTest();
//
//        if (doTest()) {
//            System.out.println("Test succeed.");
//        } else {
//            System.out.println("Test failed.");
//        }
//
//    }
//}
//
//public class JunitApplication {
//    private static final List testCases = new ArrayList<>();
//    public static void register(TestCase testCase) {
//        testCases.add(testCase);
//    }
//    public static final void main(String[] args) {
//        for (TestCase case: testCases) {
//            case.run();
//        }
//    }
//}
//
//
//
//public class UserServiceTest extends TestCase {
//    @Override
//    public boolean doTest() {
//        // 注册操作还可以通过配置的方式来实现，不需要程序员显示调用register()
//        JunitApplication.register(new UserServiceTest());
//    }
//}
class Animal {
    public void makeSound() {
        System.out.println("动物发出声音");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("汪汪汪");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog animal = new Dog();
        animal.makeSound(); // 输出 "汪汪汪"
    }
}