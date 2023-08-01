package MyThread.exercise5;

import java.util.*;

public class MyThread extends Thread{

    static ArrayList<Integer> money = new ArrayList<Integer>(Arrays.asList(10,5,20,50,100,200,500,800,2,80,300,700));

    int moneyLength = money.size();

    public MyThread() {
    }

    @Override
    public void run() {
        while (true){
            synchronized (MyThread.class){
                if (money.size() > 0){
                    int randomPickIndex = new Random().nextInt(money.size());
//                    System.out.println("抽到第"+randomPickIndex+"个");
                    System.out.println(Thread.currentThread().getName()+"又产生了一个"+money.get(randomPickIndex)+"元大奖");
                    money.remove(randomPickIndex);

                    //另一种方法，将集合打乱。
//                    Collections.shuffle(money);
//                    int prize = money.remove(0);//会将被删除的值返回

                }else {
                    break;
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
