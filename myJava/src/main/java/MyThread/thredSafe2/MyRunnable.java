package MyThread.thredSafe2;

public class MyRunnable implements Runnable{
    //所有这个类的方法共享同一对象，因为都统一实现Runnable，所以不必像实现Thread方法一样声明静态
    int ticket = 0;

    @Override
    public void run() {
        //1.循环
        //2.同步代码块（同步方法）
        //3.判断共享数据是否到了末尾，如果到了末尾
        //4.判断共享数据是否到了末尾，如果没有到了末尾

        //1.循环
        while (true){
            //2.同步代码块（同步方法）
            if (method()) break;
        }
    }

    public synchronized boolean method(){
        //3.判断共享数据是否到了末尾，如果到了末尾
        if(ticket == 100){
            return true;
        }else{
            //4.判断共享数据是否到了末尾，如果没有到了末尾
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ticket++;
            System.out.println(Thread.currentThread().getName()+"正在售卖第"+ticket+"张票！！！");
        }
        return false;

    }

}


