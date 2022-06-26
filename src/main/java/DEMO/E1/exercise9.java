package DEMO.E1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class exercise9 {
    public static void main(String[] args) throws ParseException {
        /**
         * 9.编写程序，当以年-月-日的格式输入一个日期时，输出其该年是否为闰年，该月有几天，该日是星期几
         */
        System.out.println("请输入年月日例如 2010年01月02日");
        Scanner scan = new Scanner(System.in);
        SimpleDateFormat date = new SimpleDateFormat("yyyy年MM月dd日");
        Date date1 = date.parse(String.valueOf(scan.nextLine()));

    }
}
