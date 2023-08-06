package MyThread.threadMethod4;

import MyThread.threadMethod4.MyThread;

import static java.lang.Boolean.TRUE;

public class ThreadDemo {
    public static void main(String[] args) {
        MyThread mythread1 = new MyThread();
        MyThread mythread2 = new MyThread();

        mythread1.setName("飞机");
        mythread2.setName("坦克");

        mythread1.start();
        mythread2.start();

    }
}
