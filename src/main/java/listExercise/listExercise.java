package listExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class listExercise {
    public static void main(String[] args) {
        //1:Array（数组）是基于索引(index)的数据结构，它使用索引在数组中搜索和读取数据是很快的。 Array获取数据的时间复杂度是O(1),但是要删除数据却是开销很大，因为这需要重排数组中的所有数据
        String[] a = new String[]{"1"};
        String[] b = new String[5];
        String[] c = {"1"};

        //2:List 继承Collection的一个接口,所以不能实例化,是一个有序的集合，可以包含重复的元素，提供了按索引访问的方式，
        //java.util public interface List<E> extends java.util.Collection<E>
        List<String> d = Arrays.asList("a","b");
        List<String> x = Arrays.asList(b);
        List<String> y = Arrays.asList(c);


        /** 比较
         * list 可以给自动扩容， 而数组， 你初始化的时候就应该给指定大小。 而且list的方法很多， 对里面的元素操作起来也方便。
         List是一个接口，而ArrayList是一个类。
         ArrayList继承并实现了List。
         所以List不能实例化，但可以为List创建一个引用，而ArrayList就可以被构造。
         List list;     //正确   list=null;
         List list=new List();    //   是错误的用法
         */

        //3:list 转为 arraylist（继承自list的类）
        //ArrayList 结构器1 参数是List
        ArrayList<String> d1 = new ArrayList<String>(d);

        //ArrayList 结构器2
        ArrayList<String> e = new ArrayList<String>();
        d1.add("2");

        //把数组元素拷贝一个数组里
        e.toArray(b);


    }
}
