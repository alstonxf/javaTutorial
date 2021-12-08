package E1;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

public class exercise7 {
    private static Object SimpleDateFormat;

    /**
     *
     7.计算并输出21世纪的闰年，计算程序的执行时间。
     */
    public static void main(String[] args) {
        Date date1 = new Date();
        System.out.println(date1);
        for(int y=2000;y<2100;y++){
            if (y%4==0){
                System.out.println("闰年："+y);
            }
        }
        Date date2 = new Date();
        System.out.println(date2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        System.out.println("耗时："+ String.valueOf(date2.getTime()-date1.getTime())+"ms");

        System.out.println(System.currentTimeMillis());
    }

}
