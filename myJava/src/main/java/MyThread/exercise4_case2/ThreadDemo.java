package MyThread.exercise4_case2;

public class ThreadDemo {
    public static void main(String[] args) {
        Thread thread1 = new MyThread();
        Thread thread2 = new MyThread();
        Thread thread3 = new MyThread();
        Thread thread4 = new MyThread();
        Thread thread5 = new MyThread();

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}
