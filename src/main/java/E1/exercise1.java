package E1;

import javax.xml.ws.soap.Addressing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class exercise1 {
    /**
     * 1.请根据控制台输入的特定日期格式拆分日期
     *
     * 如：请输入一个日期（格式如：**月**日****年）经过处理得到：****年**月**日
     * @param args
     */
    String args = "01月10日2021年";
    public static void main(String[] args) throws IOException {
        //1b. 接收键盘的输入
        System.out.println("请输入日期：（格式如：**月**日****年）");
        Scanner sc = new Scanner(System.in);
        String input =sc.nextLine();
//        String input = "01月10日2021年";
        System.out.println("input="+input);

        int indexM = input.indexOf("月");
        int indexY = input.indexOf("年");
        int indexD = input.indexOf("日");
        String res = input.substring(indexY-4,indexY)+"年"+input.substring(indexM-2,indexM)+"月"+input.substring(indexD-2,indexD)+"日";
        System.out.println(res);


        Scanner c = new Scanner(System.in);
        System.out.println("请输入日期：（格式如：**月**日****年）");
        String str = c.nextLine();
        int num = str.indexOf("日");
        String str1 = str.substring(num + 1);
        String str2 = str.substring(0, num + 1);
        System.out.println(str1 + str2);
    }
}
