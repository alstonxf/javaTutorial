package E2;

import java.util.ArrayList;

/**
 * 【程序2】
 *  题目：判断101-200之间有多少个素数，并输出所有素数。
 *
 *  分析：什么是素数，只能被自己和1整除的数。
 */

public class exercise2 {
    public static void main(String[] args) {

        ArrayList<Object> sNum = new ArrayList<>();//有多少个素数
        sNum.add(12);
        sNum.add("test");
        System.out.println(sNum.get(0));
        System.out.println(sNum.size());
        System.out.println(sNum.remove("test"));
        System.out.println(sNum.remove(0));
        System.out.println(sNum.size());
        //输出所有素数。
        for (int i=101;i<200;i++){
            sNum.add(i);//添加素数

            for (int j=2;j<i;j++){
                if (i%j==0){
                    System.out.println("i="+i+"  j="+j+"  被整除，不是素数");
                    sNum.remove(sNum.size()-1);
                    break;
//                    continue;
                }
            }

        }

        System.out.println("素数个数："+sNum.size());
        int sig = 1;
        for(Object s: sNum){
            System.out.println("素数"+sig+"="+s);
            sig+=1;
        }
    }

}
