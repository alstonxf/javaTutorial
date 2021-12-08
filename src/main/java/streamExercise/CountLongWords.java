package streamExercise;

import java.util.ArrayList;

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
        Words.stream().filter(w->w.length()>l).count();
        System.out.println(count);

        //filter
        Words.parallelStream().filter(w->w.length()>l).count();
        System.out.println(count);


    }
}
