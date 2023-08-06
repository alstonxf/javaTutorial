package MyThread.thredSafe3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread extends Thread{
    //声明静态 所有这个类的方法共享同一对象
    static int ticket = 0;

    //创建锁对象
    static Lock lock = new ReentrantLock();

//    @Override
//    public void run() { //1 //2 //3
//        while (true){
//            lock.lock();
//                if(ticket == 100){
//                    lock.unlock();
//                    break;
//                }else{
//                    try {
//                        Thread.sleep(100);//睡觉时，线程的执行权会交个其他线程
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    ticket++;
//                    System.out.println(getName()+"正在售卖第"+ticket+"张票！！！");
//                }
//            lock.unlock();
//        }
//    }
//    或者写在finally，这样只写一个lock.unlock（）是标准写法

    @Override
    public void run() { //1 //2 //3
        while (true){
            try{
                lock.lock();
                if(ticket == 100){
                    break;//把lock.unlock() 写在break外面的finally里，这样即使是break也会执行unlock。
                }else{
                    try {
                        Thread.sleep(100);//睡觉时，线程的执行权会交个其他线程
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    ticket++;
                    System.out.println(getName()+"正在售卖第"+ticket+"张票！！！");
                }
            } catch (Exception e){

            } finally {
                lock.unlock();
            }
        }
    }
}



