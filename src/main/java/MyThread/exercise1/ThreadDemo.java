package MyThread.exercise1;

public class ThreadDemo {
    public static void main(String[] args) {

        Thread thread1 = new MyThread();
        Thread thread2 = new MyThread();

        thread1.start();
        thread2.start();
    }
}
