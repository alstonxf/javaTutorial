package test.testAnonymousClasses;

public class testAnonymousClasses {
    public static void main(String[] args) {
        //使用一般类实现接口
        class myclass implements myInterface{

            @Override
            public void myMethod() {
                System.out.println("使用一般类实现接口...");
            }
        };
        //有类名
        new myclass().myMethod();

        //使用匿名内部类直接实现接口
        myInterface myInterface1 = new myInterface() {
            @Override
            public void myMethod() {
                System.out.println("使用匿名内部类直接实现接口....");
            }
        };
        //没有类名，只有接口名
        myInterface1.myMethod();
    }
}
