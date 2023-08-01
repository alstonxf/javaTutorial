package MyThread.exercise7;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<ArrayList<Integer>> arrayListFutureTask1 = new FutureTask<>(new MyThread());
        Thread thread1 = new Thread(arrayListFutureTask1);
        FutureTask<ArrayList<Integer>> arrayListFutureTask2 = new FutureTask<>(new MyThread());
        Thread thread2 = new Thread(arrayListFutureTask2);
        FutureTask<ArrayList<Integer>> arrayListFutureTask3 = new FutureTask<>(new MyThread());
        Thread thread3 = new Thread(arrayListFutureTask3);

        thread1.setName("抽奖箱1");
        thread2.setName("抽奖箱2");
        thread2.setName("抽奖箱3");

        thread1.start();
        thread2.start();
        thread3.start();

        ArrayList<Integer> result1 = arrayListFutureTask1.get();
        ArrayList<Integer> result2 = arrayListFutureTask2.get();
        ArrayList<Integer> result3 = arrayListFutureTask3.get();

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);

        HashMap<String, ArrayList<Integer>> stringArrayListHashMap = new HashMap<>();
        stringArrayListHashMap.put(thread1.getName(),result1);
        stringArrayListHashMap.put(thread2.getName(),result2);
        stringArrayListHashMap.put(thread3.getName(),result3);

        ArrayList<Object> re = getMaxMap(stringArrayListHashMap);


        System.out.println("在此抽奖过程中，"+re.get(0)+"中产生了最大奖项，该奖项金额为"+re.get(1)+"元");

    }

    // 定义一个方法来找到ArrayList中的最大值
    public static int getMaxValue(ArrayList<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("ArrayList为空或没有元素");
        }

        int maxValue = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            int currentValue = list.get(i);
            if (currentValue > maxValue) {
                maxValue = currentValue;
            }
        }
        return maxValue;
    }

    public static ArrayList<Object> getMaxMap(HashMap<String, ArrayList<Integer>> h){
        ArrayList<Object> maxList = new ArrayList<>();
        Integer maxValue = 0;
        for (Map.Entry<String, ArrayList<Integer>> stringArrayListEntry : h.entrySet()) {
            if (getMaxValue(stringArrayListEntry.getValue()) > maxValue){
                maxValue = getMaxValue(stringArrayListEntry.getValue());
                maxList.add(0,stringArrayListEntry.getKey());
                maxList.add(1,maxValue);
            }

        }
        return maxList;
    }
}
