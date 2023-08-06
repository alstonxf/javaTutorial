package MyThread.case2;

public class ThreadDemo {

    /**
     *
     * @param args
     * 1.自己定义一个类实现Runnable接口
     * 2.重写里面的run方法
     * 3.创建自己的类的对象
     * 4.创建一个Thread类的对象，并开启线程
     */

    public static void main(String[] args) {
        //创建MyRun的对象
        //表示多线程要执行的任务
        MyThread myThread = new MyThread();

        //创建线程对象
        Thread thread1 = new Thread(myThread);
        thread1.setName("线程1");

        //给线程设置名字
        Thread thread2 = new Thread(myThread);
        thread1.setName("线程2");

        //开启线程
        thread1.start();
        thread2.start();

    }
}
