package MyThread.case1;

/**
 * ### 03 多线程的实现方式
 *
 * #### 继承Thread类的方式进行实现
 *
 * #### 实现Runnable接口的方式进行实现
 *
 * #### 利用Callable接口和Future接口方式实现
 */
public class ThreadDemo {
    public static void main(String[] args) {
    /*
    多线程的第一种启动方式：
    1.自己定义一个类继承Thread
    2.重写run方法，就是线程要执行的代码
    3.创建子类的对象，并启动线程
     */

        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();

        //可以为线程取个名字，以区分。如果没有设置，默认是Thread-0,1,2,3...
        myThread1.setName("线程1");
        myThread2.setName("线程2");
        //开启线程
        myThread1.start();
        myThread2.start();
    }
}
