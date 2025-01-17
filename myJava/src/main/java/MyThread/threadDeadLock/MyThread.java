package MyThread.threadDeadLock;

public class MyThread extends Thread {

    static Object objA = new Object();
    static Object objB = new Object();

    @Override
    public void run() {
        System.out.println("开始循环");
        while (true) {
            if ("线程A".equals(getName())) {
                synchronized (objA) {
                    System.out.println("线程A拿到了A锁，准备拿B锁");
                    synchronized (objB) {
                        System.out.println("线程B拿到了B锁,顺利执行完一轮");
                    }
                }
            } else if ("线程B".equals(getName())) {
                if ("线程B".equals(getName())) {
                    synchronized (objB) {
                        System.out.println("线程B拿到了B锁，准备拿A锁");
                        synchronized (objA) {
                            System.out.println("线程B拿到了A锁,顺利执行完一轮");
                        }
                    }
                }
            }
        }
    }
}
