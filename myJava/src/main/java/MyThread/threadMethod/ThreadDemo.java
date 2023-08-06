package MyThread.threadMethod;

public class ThreadDemo {
    public static void main(String[] args) {
        MyThread mythread1 = new MyThread();
        MyThread mythread2 = new MyThread("线程2");//设置线程的名称(Thread构造方法也可以设置,需要在MyThread写构造方法)

        //返回线程的名称
        String name = mythread1.getName();
        System.out.println();
        //设置线程的名称(Thread构造方法也可以设置,需要在MyThread写构造方法)
        mythread1.setName("线程1");

        mythread1.start();
        mythread2.start();


        //哪条线程执行到这个方法，此时获取的就是哪条线程的对象。
//        当JVM虚拟机启动后，会自动的启动多条线程，其中就有一条叫做main的线程，它的作用就是去调用main方法，并执行其中的代码，在以前，我们调用的都是这个线程
        Thread t = Thread.currentThread();
        System.out.println(t.getName());


    }
}
