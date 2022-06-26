package test.JunitTest.code;

/**
 * 在这里我们忘掉单元测试，使用平时我们自己测试的方式来测试数据，看看它有什么缺点。
 *
 * 首先，我先创建在一个计算器类，在其中随便创建两个运算方法，供我们模拟测试。
 */

/**
 * 计算器
 */
public class Calculator {
    /**
     * 加法
     */
    public int add(int num1, int num2) {
        return num1 + num2;
    }

    /**
     * 减法
     */
    public int cut(int num1, int num2) {
        return num1 - num2;
    }
}
