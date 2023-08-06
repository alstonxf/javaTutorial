import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class worker {
    public static void main(String[] args) {
        String[] b = new String[]{"a", "c"};
        List c = Arrays.asList(b);
        ArrayList<String> d = new ArrayList<>(c);
    }

    @Test
    public void a () {
        String a = "1";
        String b = "2";
        String c = b==a?"yes":"no";
        System.out.println("c="+c);
    }

    @Test
    public void b(){
        ArrayList<String> ab = new ArrayList(Arrays.asList(new String[]{"a","b"}));

        int a1b1 = (int) (Math.random() * 100) % 3;
        System.out.println(a1b1);
        switch (a1b1){
            case 0:{
                System.out.println("aaa!");
                break;
            }
            case 1:{
                System.out.println("bbb!");
                break;
            }
            default : {
                System.out.println("ddd!");
            }
        }
    }


}
