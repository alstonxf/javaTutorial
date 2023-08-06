package MyThread.waitandnotify2;

import java.util.concurrent.ArrayBlockingQueue;

public class Consumer extends Thread{
    ArrayBlockingQueue<String> queue;

    public Consumer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        /**
         * 1. 循环
         * 2. 同步代码块
         * 3. 判断共享数据是否到了末尾（到了末尾）
         * 3. 判断共享数据是否到了末尾（没有到了末尾，执行核心逻辑）
         */
        while (true){
            //不断的从阻塞队列当中获取消息
            try {
                String takeMessage = queue.take();//take方法已经是加过锁的，不用再手动加了
                System.out.println("消费者消费了队列中的消息"+takeMessage);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

}
