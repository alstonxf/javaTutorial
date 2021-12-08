package reflectExercise;

import sun.jvm.hotspot.jdi.MethodImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.PublicKey;
import java.sql.SQLOutput;
import java.util.Arrays;

public class All2 {
    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clz = Student.class;

        Constructor[] cons = clz.getConstructors();
        Constructor[] consd = clz.getDeclaredConstructors();
        Constructor con = clz.getConstructor(String.class,Integer.class,String.class);

        Method[] methods = clz.getMethods();
        Method[] methodsd = clz.getDeclaredMethods();
        Method method = clz.getMethod("sport",String.class);

        Field[] fields = clz.getFields();
        Field[] fieldsd = clz.getDeclaredFields();
        Field field = clz.getDeclaredField("studentId");

        System.out.println(Arrays.toString(cons));
        System.out.println(Arrays.toString(consd));
        System.out.println(Arrays.toString(methods));
        System.out.println(Arrays.toString(methodsd));
        System.out.println(Arrays.toString(fields));
        System.out.println(Arrays.toString(fieldsd));

        for(Field f:fieldsd){
            System.out.println("得到field："+f.getName());
        }
        method.invoke(clz.getConstructor(Integer.class).newInstance(19),"fssafads");
    }
}
