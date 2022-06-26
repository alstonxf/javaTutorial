package DEMO.E1;

import java.util.HashMap;
import java.util.Set;

public class exercise3 {
//    3.统计段落中出现某个词的次数
    public static void main(String[] args) {

        String input1 = "i love you love me are You kiddding me";
        String input2 = "我爱你爱我正如你爱我 我爱你爱我正如你爱我我爱你爱我正如你爱我hh哈哈哈哈哈哦额";
        String word = "爱你";
        count(input1,"o");
        count(input2,"哈哈哈");
    }

    public static Integer count(String input, String word){
        HashMap<String, Integer> countMap = new HashMap<>();
        int wordLength = word.length();
        for (int i=0;i<input.length()-wordLength;i++){
            String tempWord = input.substring(i,i+wordLength);
            Integer tempCount = countMap.getOrDefault(tempWord,0);
            countMap.put(tempWord,tempCount+1);
        }

        Set<String> keys = countMap.keySet();
        Object[] keysList = keys.toArray();
        for (int i = 0 ; i<countMap.size();i++){
            System.out.println("key="+ keysList[i] +" value="+countMap.get(keysList[i]));
        }

        System.out.println(word+"   在   "+input+"中的数量是"+countMap.get(word));
        return countMap.get(word);
    }

}
