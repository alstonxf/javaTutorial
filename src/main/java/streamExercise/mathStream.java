package streamExercise;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class mathStream {
    public static void main(String[] args) {
        //如果这个流为空，会返回一个空的Optimal对象。当没有任何匹配时，可以使用默认值，比如设为""

        Stream<String> randoms = Stream.generate(Math::random).limit(10).map(w->w.toString());
//        randoms.forEach(System.out::print);//注意，如果这一行执行了，就把流关闭掉了，所以打印都是放在最后一行。
        Optional<String> largest = randoms.max(String::compareToIgnoreCase);
        System.out.println("largest "+largest.orElse(""));

        Stream<Integer> randoms2 = Stream.generate(Math::random).limit(10).flatMap(w -> f1(w));
        Optional<Integer> samllest = randoms2.peek(w-> System.out.println("random2 value="+w)).min(Integer::compareTo);
        System.out.println("samllest="+ samllest.orElse(0));
    }

    public static Stream<Integer>  f1(Double w){
        return Stream.of(Integer.valueOf((int) (w*100)));
    }

}

