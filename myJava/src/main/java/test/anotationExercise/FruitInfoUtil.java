package test.anotationExercise;

import java.lang.reflect.Field;

public class FruitInfoUtil {

    public static void getFruitInfo(Class<?> clazz){

        String strFruitProvider = "供应商信息：";

        //获取传入class的所有属性
        Field[] declaredFields = clazz.getDeclaredFields();

        //筛选有FruitProvider注解的属性
        for (Field declaredField : declaredFields) {
            //通过反射获取处理注解
            if (declaredField.isAnnotationPresent(FruitProvider.class)){
                FruitProvider annotation = declaredField.getAnnotation(FruitProvider.class);
                //处理注解信息
                strFruitProvider += strFruitProvider + "，供应商编号：" + annotation.id() +
                ",供应商名称：" + annotation.name() + ",供应商地址：" + annotation.address();

                System.out.println(strFruitProvider);
            }
        }

    }
}


