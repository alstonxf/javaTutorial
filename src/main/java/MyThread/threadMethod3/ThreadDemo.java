package MyThread.threadMethod3;

import static java.lang.Boolean.TRUE;

public class ThreadDemo {
    public static void main(String[] args) {
        MyThread mythread1 = new MyThread();
        MyThread2 mythread2 = new MyThread2();

        mythread2.setDaemon(TRUE);//设置守护进程，守护进程会在其他进程结束后，陆续停止。
        mythread1.start();
        mythread2.start();

    }
}
