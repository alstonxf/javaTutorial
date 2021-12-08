package E1;

import java.util.Scanner;

public class exercise2 {

    //2.给出一个随机字符串，判断有多少字母？多少数字？
    public static void main(String[] args) {

        System.out.println("请输入一个随机字符串");
        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();
        //方法1
        int abc = 0;
        int num = 0;
        for (int i=0; i<input.length(); i++){
            if (String.valueOf(input.charAt(i)).matches("^[a-zA-Z\u4e00-\u9fa5]+$")){
                abc+=1;
            }
            else if(String.valueOf(input.charAt(i)).matches("^[0-9\u4e00-\u9fa5]+$")){
                num+=1;
            }
            else{

            }
        }
        System.out.println("该字符串共有 字母数量："+abc+"  数字数量："+num);

        //方法2
        int n = input.length();
        num = 0;
        int let = 0;
        for (int i = 0; i < n; i++) {
            if (Character.isDigit(input.charAt(i))) {
                num++;
            }
            if (Character.isLetter(input.charAt(i))) {
                let++;
            }
        }
        System.out.println("字母：" + let + '\n' + "数字：" + num);
    }

}
