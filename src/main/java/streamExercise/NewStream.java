package streamExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class NewStream {
    public static void main(String[] args) {
        Stream<Integer> s1 = Stream.of(1,23,4,5,98);

        Integer[] i1 = new Integer[]{1,2,4};
//        ArrayList<Integer> il1= (ArrayList<Integer>) Arrays.asList(i1);
        Stream<Integer> s2 = Arrays.stream(i1);

        Stream<Integer> s3 = Stream.empty();

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

    }
}
