package MyThread.exercise1;

public class MyThread extends Thread{

    static int ticket = 1;

    @Override
    public void run() {
        while (true){
            synchronized (MyThread.class){
                if (ticket <= 1000){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+"售出第"+ticket+"张票，还剩"+(1000-ticket)+"张");
                    ticket++;
                }else {
                    break;
                }

            }
        }
    }

}
