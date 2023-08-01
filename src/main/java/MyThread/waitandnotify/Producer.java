package MyThread.waitandnotify;

public class Producer extends Thread{

    @Override
    public void run() {
        /**
         * 1. 循环
         * 2. 同步代码块
         * 3. 判断共享数据是否到了末尾（到了末尾）
         * 3. 判断共享数据是否到了末尾（没有到了末尾，执行核心逻辑）
         */

        while (true){
            synchronized (Cache.lock){
                if(Cache.count==0){
                    break;
                }else{
                    //判断缓存中是否有消息要消费
                    if (Cache.threadRunFlag == 1) {
                        //如果有，就等待
                        try {
                            Cache.lock.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }else{
                        //如果没有，就生产消息
                        System.out.println("生产者生成了一条消息");
                        //修改缓存区的消息状态
                        Cache.threadRunFlag = 1;
                        //通知等待的消费者开始消费
                        Cache.lock.notifyAll();
                    }

                }
            }
        }

    }
}
