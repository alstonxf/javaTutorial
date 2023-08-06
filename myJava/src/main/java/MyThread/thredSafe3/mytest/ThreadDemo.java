package MyThread.thredSafe3.mytest;

public class ThreadDemo {
    public static void main(String[] args) {

        MyThread myThread = new MyThread();

        Thread mythread1 = new Thread(myThread);
        Thread mythread2 = new Thread(myThread);

        mythread1.start();
        mythread2.start();
    }
}
