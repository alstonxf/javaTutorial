package DEMO.E2;

import java.util.Scanner;

public class exercise6 {
    /**
     * 题目：输入两个正整数m和n，求其最大公约数和最小公倍数。
     */

    public static void main(String[] args) {
        System.out.println("请输入正整数m");
        Scanner scn = new Scanner(System.in);
        String num1 = scn.nextLine();
        System.out.println("请输入正整数n");
        String num2 = scn.nextLine();
        int numMax = Math.max(Integer.parseInt(num1), Integer.parseInt(num2));
        int numMin = Math.min(Integer.parseInt(num1), Integer.parseInt(num2));
        System.out.println("numMax="+numMax+" numMin="+numMin);
        int zdgy = 1;
        for(int i=numMin ; i>1; i--){
//            System.out.println(i);
            if (numMin%i==0 && numMax%i==0){
                zdgy = i;
                break;
            }
        }
        System.out.println("最大公约数是"+zdgy);

        int zxgb = numMin*numMax;
        for(int i=numMax;i<numMin*numMax;i++){
//            System.out.println(i);
            if (i%numMin==0 && i%numMax==0){
                zxgb = i;
                break;
            }
        }
        System.out.println("最小公倍数是"+zxgb);
        System.out.println(num1+num2);
    }
}
