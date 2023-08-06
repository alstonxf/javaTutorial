package test.anotationExercise;

import java.lang.annotation.*;

//当一个注解被标记为@Inherited时，它会被继承到子类中。这意味着，如果一个类被注解了一个带有@Inherited注解的注解，那么当这个类的子类被创建时，子类也会继承该注解。
//注意：是对类做的注解才能继承，对属性field做的注解不能继承。

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String value();
}

@MyAnnotation("父类注解")
class ParentClass {}

class ChildClass extends ParentClass {}

public class testInherited {
    public static void main(String[] args) {
        MyAnnotation parentAnnotation = ParentClass.class.getAnnotation(MyAnnotation.class);
        System.out.println("父类注解值：" + parentAnnotation.value());

        MyAnnotation childAnnotation = ChildClass.class.getAnnotation(MyAnnotation.class);
        System.out.println("子类注解值：" + childAnnotation.value());
    }
}
