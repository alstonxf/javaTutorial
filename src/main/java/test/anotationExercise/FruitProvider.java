package test.anotationExercise;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface FruitProvider {

    public int id() default -1;

    public String name() default "默认名称";

    public String address() default "默认地址";

}
