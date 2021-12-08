package E2;

import java.time.LocalDate;

public class tes {
    public static void main(String[] args) {
        String a = "100";
        System.out.printf(a);
        String b = a;
        System.out.println("b="+b);
        System.out.println("a="+a);
//        b = "200";
//        System.out.println("b="+b);
//        System.out.println("a="+a);
        a = "200";
        System.out.println("b="+b);
        System.out.println("a="+a);

        LocalDate d = LocalDate.now();
        System.out.println(d);
        System.out.println(d.getDayOfMonth());
        System.out.println(d.getDayOfYear());
        System.out.println(d.getDayOfWeek());

    }
}
