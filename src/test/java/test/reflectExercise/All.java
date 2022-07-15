package test.reflectExercise;

import java.lang.reflect.*;
import java.util.Arrays;

public class All {
    private Student cs1;

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
//        Student s1 = new Student();
        Student s1 = new Student("n2",2,"002");
        Student s3 = new Student("n3",3,"003");
        Student s4 = new Student("n4",4,"004");

        Class cl1 = s1.getClass();
        Class cl2 = Student.class;
        Class cl3 = Class.forName("test.reflectExercise.Student");

        Constructor[] con1 = cl1.getDeclaredConstructors();
        Constructor[] con2 = cl1.getConstructors();
        Method[] m1 = cl1.getMethods();
        Method[] m2 = cl1.getDeclaredMethods();
        Field[] f1 = cl1.getFields();
        Field[] f2 = cl1.getDeclaredFields();

        System.out.println("ob1="+ Arrays.toString(con1));
        System.out.println("ob2="+ Arrays.toString(con2));
        System.out.println("m1="+ Arrays.toString(m1));
        System.out.println("m2="+ Arrays.toString(m2));
        System.out.println("f1="+ Arrays.toString(f1));
        System.out.println("f2="+ Arrays.toString(f2));

        System.out.println("con1[0].getName()"+con1[0].getName());
        System.out.println("con1[1].getName()"+con1[1].getName());
        System.out.println("modifier"+con1[0].getModifiers());

//        System.out.println(con1.toString());
//        Constructor cs = cl1.getConstructor(String.class, Integer.class, String.class);
//        Method ms = cl1.getDeclaredMethod("sport");
//        System.out.println(ms);

//        Student cs1 = (Student)cs.newInstance("n5",5,"005");
//        System.out.println(cs1.getCountry()+"  "+ cs1.getName()+"  age "+cs1.getAge()+"  "+ cs1.getStudentId());
//        Field[] fs = cl1.getFields();
//        System.out.println("fs="+ Arrays.toString(fs));
//        cs1.sport("basketball");

//        String nameCS1 =

        System.out.println("modifier"+ Modifier.toString(4));

        Student s9 = new Student("n9",9,"000");
        Class cl6 =  s9.getClass();
        Constructor c6 = cl6.getConstructor(String.class,Integer.class,String.class);
        Student o6 = (Student)cl6.newInstance();
        System.out.println(Arrays.asList(cl6.getDeclaredMethods()));
        Method m6 = cl6.getDeclaredMethod("sport");
        m6.invoke(s9);
        Method m9 = cl6.getDeclaredMethod("sport",String.class);
        m9.invoke(s9,"fdasf");




    }
}
