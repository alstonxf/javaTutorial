package DEMO.E1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class exercise4 {
    /**
     * 4.编写敏感词过滤程序
     *
     * 说明：在网络程序中，如聊天室、聊天软件等，经常需要对一些用户所提交的聊天内容中的敏感性词语进行过滤。
     *
     * 如“性”、“色情”、“爆炸”、“恐怖”、“枪”、“军火”等，这些都不可以在网上进行传播，需要过滤掉或者用其他词语替换掉。
     */
    public static void main(String[] args) {
        String input = "“性”、“色情”、“爆炸”、“恐怖”、“枪”、“军火”,这些都不可以在网上进行传播";
        System.out.println(filter(input));
    }

    public static String filter(String input){
        Map<String,String> filterMap = new HashMap<>();
        for (String word: Arrays.asList("性","色情","爆炸","恐怖","枪","军火")){
            filterMap.put("needFilterWords",word);
            input = input.replace(word,"*");
        }
        return input;
    }



}
