package test.JunitTest.test;

import test.JunitTest.code.Calculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

public class CaculatorTest {
    private Calculator calculator;

    @BeforeEach
    public void testBefore(){
        System.out.println("测试开始");
        calculator = new Calculator();
    }
    @AfterEach
    public void testAfter(){
        System.out.println("测试结束");
    }

    @Test
    public void add(){
//        Calculator calculator = new Calculator();
        //测试加法 使用打印的方法是可以，但还要自己评判，比较麻烦、
//        int i = 10/0;
//        System.out.println(calculator.add(10, 10));		//20		正确
        //所以面对这个问题，我们在单元测试的时候，尽量不要去打印预期值，需要注重观察是绿色和红色比较好，它可以直观的反映程序的是否准确性和达到预期值。
        //改用需要引入一个对象的静态方法来断言是否为预期值。Assert.assertEquals(预期值, 结果);
        int result = calculator.add(10, 10);
        Assert.assertEquals(2,result);
    }

    @Test
    public void cut(){
        //测试减法
        int result = calculator.cut(10, 10);
        Assert.assertEquals(0,result);
    }

}
