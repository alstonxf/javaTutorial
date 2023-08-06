package MyThread.thredSafe1;

public class MyThread extends Thread{
    //声明静态 所有这个类的方法共享同一对象
    static int ticket = 0;

    //创建锁对象
//    static Object ob = new Object();
    Class<MyThread> ob = MyThread.class;//或用本身对象肯定就是唯一的。

    @Override
    public void run() {
        while (true){
            synchronized (ob){//注意synchronized要写在循环里面，而且锁对象要是唯一的。否则不同的线程面对不同的锁，就起不到锁的作用了，比如把这里的ob改为this就有问题了。
                if(ticket<100){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    ticket+=1;
                    System.out.println(getName()+"正在售卖第"+ticket+"张票！！！");
                }else{
                    break;
                }

            }
        }
    }
}


