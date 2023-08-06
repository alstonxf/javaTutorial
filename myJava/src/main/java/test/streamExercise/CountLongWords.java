package test.streamExercise;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CountLongWords {
    public static void main(String[] args) {
        ArrayList<String> Words = new ArrayList<String>();
        Words.add("dsfdas");
        Words.add("dsfdas1");
        Words.add("dsfdas12");
        Words.add("dsfdas123");
        int l = 5;

        int count = 0 ;

        for(String w:Words){
            if(w.length()>l){
                System.out.println(w);
                count++;
            }
        }
        System.out.println(count);

        //filter
        Stream<String> longWords = Words.stream().filter(w->w.length()>l);
        longWords.count();
        System.out.println(count);

        //filter
        Words.parallelStream().filter(w->w.length()>l).count();
        System.out.println(count);

        //map
        Stream<String> lowercaseWords = Words.stream().map(String::toLowerCase);
        Stream<String> uppercaseWords = Words.stream().map(String::toUpperCase);

        //flatmap
        lowercaseWords.flatMap(w->codePoints(w)).forEach(System.out::println);
        uppercaseWords.flatMap(w->codePoints(w)).filter(w->testFilter(w)).forEach(System.out::println);
    }

    public static Stream<String> codePoints(String w){
        ArrayList<String> result = new ArrayList<String>();
            int i = 0;
            while(i<w.length()){
                int j = w.offsetByCodePoints(i,1);
                result.add(w.substring(i,j));
                i=j;
            }
        return result.stream();

    }

    public static Boolean testFilter(String a){
        return a.equals("a");
    }

}
