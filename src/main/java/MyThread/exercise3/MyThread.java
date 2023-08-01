package MyThread.exercise3;

public class MyThread extends Thread{

    static int num = 1;

    @Override
    public void run() {
        while (true){
            synchronized (MyThread.class){
                if (num <= 100){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (num%2==1){
                        System.out.println(Thread.currentThread().getName()+"取到奇数"+num);
                    }
//                    System.out.println(Thread.currentThread().getName()+"取到数字"+num);
                    num ++;
                } else {
                    break;
                }

            }

        }
    }

}
