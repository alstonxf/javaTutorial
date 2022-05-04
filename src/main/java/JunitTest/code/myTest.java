package JunitTest.code;

import JunitTest.code.Calculator;

/**
 *
 * 然后我们再去编写测试类，创建对象
 */

//反面教材，这样测试完还要注释掉，所以最好使用JunitTest
public class myTest {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        //测试加法
        System.out.println(calculator.add(10, 10));		//20		正确

        //测试减法
        System.out.println(calculator.cut(10, 10));			//0		正确
    }
}
