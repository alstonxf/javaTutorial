package E2;
import java.time.LocalDate;
import java.util.Date;

import static java.lang.System.*;
import java.time.LocalDate;

public class tes2 {
    static String x = "x";

    public static void main(String[] args) {
//        val a = "";
//        只有类才存在静态的变量 方法只能对静态变量的操作 不能在方法内试图定义静态变量
//                否则的话会抛出编译错误
//        静态变量的本意是为了让所有的对象共享这个变量,如果在方法里面定义静态变量的话就存在逻辑错误了,也达不到你想要目的. 因为在方法定义静态变量根本没有他的任何意义. 任何对象都有自己的方法,即使是静态方法,方法内的变量也是在方法调用时候才开始分配内存,所以想给成静态的在逻辑上存在问题
        System.out.println("x打印了"+x);
        System.out.println("main 打印了");
//        out.printf("-----  "+"%s %tc",x, LocalDate.now());
        out.printf("-----  "+"%s %tc%tD%ty%tc%td%te%ta%tA",x, new Date(),new Date(),new Date(),new Date(),new Date(),new Date(),new Date(),new Date());
        out.printf("-----  "+"%1$s %2$tc%2$tD%2$ty%2$tc%2$td%2$te%2$ta%2$tA",x, new Date());
    }
    static
    {
        System.out.println(x);
        String a = "test1";
        System.out.println(a);
    }

}
