package MyThread.exercise4;

public class ThreadDemo {
    public static void main(String[] args) {
        MyThread myThread = new MyThread(100, 3);

        Thread thread1 = new Thread(myThread);
        Thread thread2 = new Thread(myThread);
        Thread thread3 = new Thread(myThread);
        Thread thread4 = new Thread(myThread);
        Thread thread5 = new Thread(myThread);


//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
        thread5.start();
    }
}
