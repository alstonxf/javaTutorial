package test.streamExercise;

import java.util.stream.Stream;

public class printStream {

    public printStream(){

    }

    public static void prints(Stream s) throws NoSuchMethodException {
        System.out.println("--开始打印流");
        s.forEach(System.out::println);
        System.out.println("--结束打印流");
    }

    public static  void printp(Stream s) throws NoSuchMethodException{

    }

}
