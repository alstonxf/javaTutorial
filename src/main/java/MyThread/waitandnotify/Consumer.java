package MyThread.waitandnotify;

public class Consumer extends Thread{

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
                if (Cache.count == 0) {
                    break;
                }else {
                    //先判断有没有消息在缓存中
                    if(Cache.threadRunFlag == 0){
                        //如果没有，就等待
                        try {
                            Cache.lock.wait();//让当前线程和锁进行绑定。
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        Cache.lock.notifyAll();
                    }else{
                        //把缓存中的总数-1
                        Cache.count--;
                        //如果有，就消费
                        System.out.println("正在消费，还能继续消费"+Cache.count+"条！！！");
                        //消费之后，唤醒生产者继续生产
                        Cache.lock.notifyAll();
                        //修改缓存中的状态
                        Cache.threadRunFlag = 0;
                    }


                }
            }
        }

    }
}
