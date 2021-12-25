package streamExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class intStream {
    Random r1 = new Random();



//    r1.ints(10,100); ？？？
//    r1.longs(10);

    public static void main(String[] args) {
        IntStream s = IntStream.of(1, 2);
        int[] s1 = s.toArray();
        System.out.println(Arrays.toString(s1));

        Stream<Integer> s2 = Stream.of(1, 2);
        s2.toArray();

        LongStream.of(10);

    }


}
