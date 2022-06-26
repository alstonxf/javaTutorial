package test.JunitTest.test.My;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * MyTest注解类
 *
 * 此自定义注解@MyTest只是作为需要单元测试的标记，不需要做默认值
 * @Retention注解表示给与@MyTest注解生命周期
 * 当前定义的注解可以保留到运行时，通过反射机制可以获取注解信息
 * 否则反射将对注解没有任何作用，失去了该意义和自定义单元测试的初衷
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface MyTest {
//    String value() default "";
}