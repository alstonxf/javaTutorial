package test.anotationExercise;

import java.lang.reflect.Field;

public class FruitRun2 {
    public static void main(String[] args) {

        Field[] declaredFields = Apple.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println("遍历属性："+declaredField);
            if (declaredField.isAnnotationPresent(FruitProvider.class)){
                System.out.println("找到有FruitProvider注解的属性："+declaredField);
                FruitProvider annotation = declaredField.getAnnotation(FruitProvider.class);
                int id = annotation.id();
                String name = annotation.name();
                String address = annotation.address();
                System.out.println("供应商编号：" + id +
                        ",供应商名称：" + name+ ",供应商地址：" + address);
            }
        }
    }
}
