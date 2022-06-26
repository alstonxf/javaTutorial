package test.reflectExercise;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class review {

    public review() throws NoSuchMethodException {
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Person.class;
        Constructor PersonCon = clazz.getDeclaredConstructor();
//        PersonCon.newInstance();//注意：抽象类不能实例化,所以会报错！！，如果改为非抽象类是可以的。
//        Person p1 = (Person) PersonCon.newInstance("a",100);
//        System.out.println(p1.getName());

        Class clazz2 = Student.class;
        Constructor StudentCon = clazz2.getDeclaredConstructor(Integer.class);
        Object o1 = StudentCon.newInstance(20);
        Student s1 = (Student)o1;
        s1.setStudentId("cn000000");
        Method getStudentId = s1.getClass().getMethod("getStudentId");
        String id = (String) getStudentId.invoke(s1);
        System.out.println("id="+id);
     }
}
