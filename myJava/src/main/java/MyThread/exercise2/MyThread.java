package MyThread.exercise2;

public class MyThread extends Thread{

    static int gift = 1;

    @Override
    public void run() {
        while (true){
            synchronized (MyThread.class){
                if (gift <= 90){
                    System.out.println("----------------------------------");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    int giftLeft = 100-gift;
                    System.out.println(Thread.currentThread().getName()+"售出第"+gift+"个礼物，还剩"+ giftLeft +"个礼物");
                    gift ++;
                } else {
                    break;
                }

            }

        }
    }

}
