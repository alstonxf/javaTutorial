package SpringTest.SpringDemo3;

public class Test
        /**
         * 当创建一个简单的Java项目的时候，在控制台要求输出“Hello world!”,如果要求输出“Hello Beer!”等等，那么就要不断地去修改程序源代码。如果项目庞大，程序中涉及这一输出地语句不止一处，有很多处，且分散在整个项目地源码（往往有成千上万）中，如此大动干戈地改动程序，对于一个软件系统地维护来说将是灾难性地。
         */
{
    public static void main( String[] args )
    {
        TestHello testHello=new TestHello();
        testHello.setMessage("Hello world!……");
        System.out.println(testHello.getMessage());
    }
}