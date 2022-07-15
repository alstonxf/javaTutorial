package test.listExercise;

import com.sun.tools.javac.util.List;
import java.util.Arrays;


class listExercise3 {
    public static void main(String[] args) {
        /*
        1.Arrays.asList()可以插入null，而List.of()不可以
        2.用List.of的List自然是不包含null，而用Arrays.asList的List包含null。上图结果也可得知。
        3.List.of生成的List不能修改，Arrays.asList生成的List能修改。
        由源码可知，底层的数组就是传入的数组，所以对原数组的修改会影响到用Arrays.asList方法生成的List。而且Objects.requireNonNull(array)检查的是整个数组是不是null，而非对每个元素进行检查是否为null。所以用Arrays.asList方法可以插入空值。
        也没有规定是final的，所以支持修改。
         */

        //List<Integer> ls1 = Arrays.asList(1, 2, null);
        List<Integer> ls2 = List.of(1,2);
        //System.out.println(ls1.contains(null));
        System.out.println(ls2.contains(null));
        List<Integer> ls3 = List.of(1,2,null);

        List<String> k = com.sun.tools.javac.util.List.of("a");
        java.util.List<String> d = Arrays.asList("a");
        java.util.List<String> d1 = Arrays.asList("a");
        System.out.println("k vs d = "+k.equals(d)); //返回TRUE
        System.out.println("k vs d = "+(k == d)); //返回FALSE
        System.out.println("d1 vs d = "+(d1 == d)); //返回FALSE

        List.of("1");

    }
}

/*结果
Exception in thread "main" java.lang.NullPointerException.....
*/