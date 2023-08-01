package MyThread.waitandnotify;

public class ThreadDemo {
    public static void main(String[] args) {
        /**
         *    需求：完成生产者和消费者（等待唤醒机制）的代码
         *         实现线程轮流交替执行的效果
         *
         */

        // 创建线程对象
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        producer.setName("producer");
        consumer.setName("consumer");

        producer.start();
        consumer.start();
    }
}
