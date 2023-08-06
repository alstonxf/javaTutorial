package MyThread.exercise6;

public class ThreadDemo {
    public static void main(String[] args) {
        Thread thread1 = new MyThread();
        Thread thread2 = new MyThread();

        thread1.setName("抽奖箱1");
        thread2.setName("抽奖箱2");

        thread1.start();
        thread2.start();

    }
}
