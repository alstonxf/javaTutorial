package MyThread.thredSafe3.mytest;

public class MyThread implements Runnable{

    static int ticket = 0;

    @Override
    public void run() {

        while (true){
            if (method() == true){
                break;
            }
        }

    }

    public synchronized boolean method(){
        if(ticket==100){
            return true;
        }else {
            try{
                ticket++;
                Thread.sleep(10);
                System.out.println(Thread.currentThread().getName()+" 正在卖第"+ticket+"张票");
            }catch (Exception e){
                System.out.println(e);
            }
        }

        return false;
    }

}
