package MyThread.threadMethod5;

import MyThread.threadMethod5.MyThread;

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        MyThread mythread1 = new MyThread();
        mythread1.setName("飞机");
        mythread1.start();

        //插入线程 放在mythread1线程与当前线程main中间。
        mythread1.join();

        for (int i = 0; i < 100; i++) {
            System.out.println("main 线程 @"+i);
        }

        MyThread mythread2 = new MyThread();
        mythread2.setName("坦克");
        mythread2.start();

        //插入线程 放在main线程后。
        mythread2.join();


    }
}
