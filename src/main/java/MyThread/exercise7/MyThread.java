package MyThread.exercise7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;

public class MyThread implements Callable<ArrayList<Integer>> {

    static ArrayList<Integer> money = new ArrayList<Integer>(Arrays.asList(10,5,20,50,100,200,500,800,2,80,300,700));

    public MyThread() {
    }

    @Override
    public ArrayList<Integer> call() throws Exception {
        ArrayList<Integer>  result = new ArrayList<Integer>();
        while (true){
            synchronized (MyThread.class){
                if (money.size() > 0){
                    int randomPickIndex = new Random().nextInt(money.size());
                    result.add(money.get(randomPickIndex));
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
        return result;
    }
}
