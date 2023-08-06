package test.anotationExercise;

public class FruitRun {
    public static void main(String[] args) {
        System.out.println("父类");
        FruitInfoUtil.getFruitInfo(Apple.class);
        System.out.println("@Inherited：@Inherited是一个标记注解，表明某个被标注的类 型是被继承的。如果有一个使用了@Inherited修饰的Annotation被用于一 个Class，则这个注解将被用于该Class的子类。");
        FruitInfoUtil.getFruitInfo(AppleSon.class);
    }
}