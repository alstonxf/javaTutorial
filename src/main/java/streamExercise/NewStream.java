package streamExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class NewStream {
    public static void main(String[] args) throws NoSuchMethodException {
        Stream<Integer> s3 = Stream.empty();

        Stream<Integer> s1 = Stream.of(1,23,4,5,98);

        Integer[] i1 = new Integer[]{1,2,4};
//        ArrayList<Integer> il1= (ArrayList<Integer>) Arrays.asList(i1);
        Stream<Integer> s2 = Arrays.stream(i1);

        //产生随机数无限流
        Stream<Double> s4 = Stream.generate(Math::random).limit(5);

        printStream.prints(s1);
        printStream.prints(s2);
        printStream.prints(s3);
        printStream.prints(s4);
    }
}
