package MyThread.waitandnotify;

public class Cache {
    /**
     * 作用：控制消费者和生产者的执行
     */
    //是否有消息 0：没有 1：已有消息待消费

    public static int threadRunFlag = 0;

    public static int count = 10;

    static Object lock = new Object();
}
