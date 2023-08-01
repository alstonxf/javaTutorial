package MyThread.case3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 1.创建一个类MyCallable实现Callable接口
         * 2.重写call（是有返回值的，表示多线程运行的结果）
         *
         * 3.创建MyCallable的对象（表示多线程要执行的任务）
         * 4.创建FutureTask的对象（作用管理多线程运行的结果）
         * 5.创建Thread类的对象，并启动（表示线程）
         *
         */

        //创建MyCallable的对象（表示多线程要执行的任务）
        MyCallable myCallable = new MyCallable();
        //创建FutureTask的对象（作用管理多线程运行的结果）
        FutureTask<Integer> integerFutureTask = new FutureTask<>(myCallable);
        //创建Thread类的对象，并启动（表示线程）
        Thread thread1 = new Thread(integerFutureTask);
        Thread thread2 = new Thread(integerFutureTask);
        thread1.start();
        thread2.start();
        //获取多线程运行的结果
        Integer result = integerFutureTask.get();
        System.out.println(result);
    }
}
