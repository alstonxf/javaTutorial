package test.streamExercise;

import java.util.Comparator;
import java.util.stream.Stream;

public class SubStream {
    public static void main(String[] args) {
        //limit   抽取子流，限制前100个元素。
        Stream<Double> randoms = Stream.generate(Math::random).limit(100);
        randoms.forEach(System.out::println);

        long randoms1 = Stream.generate(Math::random).limit(100).count();
        System.out.println(randoms1);

        //skip   当前流中除了前n个之外的所有元素。
        System.out.println(Stream.generate(Math::random).limit(100).skip(10).count());

        //takewhile 是java9才有的方法。是当前流中所有满足谓语条件的元素
        Stream<String> ta1 = new CountLongWords().codePoints("12wesdf13498dfwf");
//        ta1.takeWhile(s->"0123456789".contains(s));
        //dropwhile 是java9才有的方法。是当前流中所有不满足谓语条件的元素
//        ta1.dropWhile(s->"0123456789".contains(s));

        //concate 两流按顺序合并
        Stream<String> combined = Stream.concat(Stream.of("a","c"),Stream.of("A","B"));
        combined.forEach(System.out::println);

        //distinct 产生一个流，包含当前流中所有不同的元素。
        System.out.println("distinct 产生一个流，包含当前流中所有不同的元素。");
        Stream.of("a","b","c","A","a","A","c","d","e","E").distinct().forEach(System.out::println);

        //sorted 流中的元素按顺序排列
        System.out.println("sorted 流中的元素按顺序排列");
        Stream.of("e","1000","1","ab","dec").sorted().forEach(System.out::println);
        System.out.println("按长度排序");
        Stream.of("e","1000","1","ab","dec").sorted(Comparator.comparing(String::length)).forEach(System.out::println);

        //peek 捕捉每一条元素，作为input，但返回是void。
        Stream.of("a,c,c,d").peek(e -> System.out.println("Fetching1 "+e)).forEach(System.out::println);
        Stream.iterate(1.0,p->p*2).peek(e -> System.out.println("Fetching2 "+e)).limit(20).forEach(System.out::println);
//        Stream.of("a,c,c,d").peek(e -> e+"test").forEach(System.out::println);//peek方法中的e值不能改变，因为返回的是void。

    }

}
