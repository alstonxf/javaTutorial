package MyThread.waitandnotify2;

import java.util.concurrent.ArrayBlockingQueue;

public class Producer extends Thread{

    ArrayBlockingQueue<String> queue;

    public Producer(ArrayBlockingQueue<String> queue) {
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
            //不断的把消息放到阻塞队列当中
            try {
                queue.put("这是消息");//put方法已经是加过锁的，不用再手动加了
                System.out.println("生产者放了一条消息到队列中");//将打印语句写在了锁的外面，对结果不会有任何影响，只是控制台打印时有时重复。
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
