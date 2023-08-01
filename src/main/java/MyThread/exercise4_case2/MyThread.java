package MyThread.exercise4_case2;

public class MyThread extends Thread{

    static int round = 0;//要让每个线程不共享这个变量。

//    static int[] all = new int[]{10,30,60};
    static double totalMoneySent = 0;//已经发出去的红包总金额

    double money = 0;//每次发的红包金额

    int round2 = 0; //跳出循环判断，让每个线程只运行一次。

    static double totalMoney = 100;//总的要发的红包金额
    static int packetNum = 3;//总红包数量

    public MyThread() {
    }


    @Override
    public void run() {
        while (true){
            synchronized (MyThread.class){
                if (round2 < 1){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    
                    if(round < packetNum-1){
                        money = Math.abs(Math.random()*(totalMoney));
                        totalMoney = totalMoney - money;
                        System.out.println(Thread.currentThread().getName()+"抢到了"+money+"元");
                    } else if (round == packetNum-1) {
                        System.out.println(Thread.currentThread().getName()+"最后一个抢到了"+totalMoney+"元");
                    } else {
                        System.out.println(Thread.currentThread().getName()+"没抢到");
                    }

                    round += 1;
                    round2 += 1;
                }else {
                    break;
                }

            }
        }
    }

}
