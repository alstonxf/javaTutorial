package MyThread.threadMethod;

public class MyThread extends Thread{
    public MyThread() {
    }

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try{
                Thread.sleep(1000);//线程休眠。
            } catch (InterruptedException e){
                System.out.println(e);
            }
            System.out.println(getName() +"@" +i);
        }
    }
}
