package test.JunitTest.test.My;

import org.junit.Test;

/**
 * package test.JunitTest.test.My;
 */
public class TestJunit {

    @Test
    public void test1() {
        System.out.println("---test1---");
    }

    @MyTest
    public void test2() {
        System.out.println("---test2---");
    }

    @MyTest
    public void test3() {
        System.out.println("---test3---");
    }

    public void test4() {
        System.out.println("---test4---");
    }
}
