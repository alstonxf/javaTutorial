package MyThread.threadMethod4;

public class MyThread extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getName() +"@" +i);
            //测试出让线程，上一个线程执行完后出让cpu资源给另外线程使用,并非绝对，使用的少，只是让结果看起来均匀一点分布。
            Thread.yield();
        }
    }
}
