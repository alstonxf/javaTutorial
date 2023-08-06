package MyThread.exercise6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MyThread extends Thread{

    static ArrayList<Integer> money = new ArrayList<Integer>(Arrays.asList(10,5,20,50,100,200,500,800,2,80,300,700));

    static ArrayList<Integer>  result1 = new ArrayList<Integer>();
    static ArrayList<Integer>  result2 = new ArrayList<Integer>();

    public MyThread() {
    }

    @Override
    public void run() {
        while (true){
            synchronized (MyThread.class){
                if (money.size() > 0){
                    int randomPickIndex = new Random().nextInt(money.size());
//                    System.out.println(Thread.currentThread().getName()+"又产生了一个"+money.get(randomPickIndex)+"元大奖");
                    if(getName()=="抽奖箱1"){
                        result1.add(money.get(randomPickIndex));
                    }else{
                        result2.add(money.get(randomPickIndex));
                    }

                    money.remove(randomPickIndex);

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

        int result1max = 0;
        int result1sum = 0;
        for (int i = 0; i < result1.size(); i++) {
            if(result1.get(i)>result1max){
                result1max = result1.get(i);
            }
            result1sum += result1.get(i);
        }

        int result2max = 0;
        int result2sum = 0;
        for (int i = 0; i < result2.size(); i++) {
            if(result2.get(i)>result2max){
                result2max = result2.get(i);
            }
            result2sum += result2.get(i);
        }
        System.out.println(
                "抽奖箱1总共产生了"+result1.size()+"个奖项"+"\n" +
                        " 分别是："+Arrays.toString(result1.toArray())+" 最高奖项为"+result1max+"总计额为"+result1sum+"元"
        );

        System.out.println(
                "抽奖箱2总共产生了"+result2.size()+"个奖项"+"\n" +
                        " 分别是："+Arrays.toString(result2.toArray())+" 最高奖项为"+result2max+"总计额为"+result2sum+"元"
        );

    }

}
