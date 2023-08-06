package MyThread.case2;

public class MyThread implements Runnable{
    //书写线程要执行的代码

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            //可以获取到当前线程的兑现
            System.out.println(Thread.currentThread().getName()+"  "+i);
        }
    }
}
