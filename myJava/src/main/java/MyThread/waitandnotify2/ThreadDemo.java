package MyThread.waitandnotify2;

import java.util.concurrent.ArrayBlockingQueue;

public class ThreadDemo {
    public static void main(String[] args) {
        /**
         *    需求：利用阻塞队列完成生产者和消费者（等待唤醒机制）的代码
         *    细节：生产者与消费者必须使用同一阻塞队列
         *
         */

        // 1、创建阻塞对象
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);

        // 2、创建线程对象,并将阻塞对象传递过去
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        producer.setName("producer");
        consumer.setName("consumer");

        //3、开启线程
        producer.start();
        consumer.start();
    }
}
