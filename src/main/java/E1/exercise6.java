package E1;

import sun.tools.tree.AddExpression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class exercise6 {
    /**
     * 6.计算某年、某月、某日和某年、某月、某日之间的天数间隔和周数。
     * @param args
     */

    public static void main(String[] args) throws ParseException {
        System.out.println("请输入第一个年月日如20210101");
        String scn1 = String.valueOf(new Scanner(System.in).nextLine());
        System.out.println("请输入第二个年月日如20210102");
        String scn2 = String.valueOf(new Scanner(System.in).nextLine());
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        Date date1 = date.parse(scn1);
        Date date2 = date.parse(scn2);
        long res;
        if (date1.after(date2)){
            res = date1.getTime()-date2.getTime();
        }else {
            res = date2.getTime()-date1.getTime();
        }
        long days = res/1000/60/60/24;
        System.out.println("天数间隔："+ days);
        long weeks = days/7;
        System.out.println("周数间隔："+weeks);
    }
}
