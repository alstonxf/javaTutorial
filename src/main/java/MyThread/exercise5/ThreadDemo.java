package MyThread.exercise5;

public class ThreadDemo {
    public static void main(String[] args) {
        Thread thread1 = new MyThread();
        Thread thread2 = new MyThread();
        Thread thread3 = new MyThread();

        thread1.setName("抽奖箱1");
        thread2.setName("抽奖箱2");
        thread3.setName("抽奖箱3");

        thread1.start();
        thread2.start();
        thread3.start();

    }
}
