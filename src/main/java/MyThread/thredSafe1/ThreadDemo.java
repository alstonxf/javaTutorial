package MyThread.thredSafe1;

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        MyThread mythread1 = new MyThread();
        MyThread mythread2 = new MyThread();
        MyThread mythread3 = new MyThread();

        mythread1.setName("售票窗口1");
        mythread2.setName("售票窗口2");
        mythread3.setName("售票窗口3");

        mythread1.start();
        mythread2.start();
        mythread3.start();

    }
}
