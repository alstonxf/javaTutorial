package MyThread.threadMethod2;

public class ThreadDemo {
    public static void main(String[] args) {
        //创建线程要执行的参数对象
        MyRunnable myRunnable = new MyRunnable();

        //创建线程对象
        Thread mythread1 = new Thread(myRunnable, "线程1");
        Thread mythread2 = new Thread(myRunnable, "线程2");

        //设置优先级1-10，默认5,注意：优先级越高，抢到资源的概率就越大，但不是绝对的。
        mythread1.setPriority(1);
        mythread2.setPriority(10);
        //获取优先级
        System.out.println(mythread1.getPriority());
        System.out.println(mythread2.getPriority());

        mythread1.start();
        mythread2.start();


    }
}
