package MyThread.thredSafe2;

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {

        MyRunnable runnable = new MyRunnable();

        Thread mythread1 = new Thread(runnable);
        Thread mythread2 = new Thread(runnable);
        Thread mythread3 = new Thread(runnable);

        mythread1.setName("售票窗口1");
        mythread2.setName("售票窗口2");
        mythread3.setName("售票窗口3");

        mythread1.start();
        mythread2.start();
        mythread3.start();

    }
}
