package SpringTest.Sample;
import SpringTest.Sample.Collection;



public class Student {

    private String name;
    private int id;
    private int age;
    private Dog dog;

    private String[] array1;
    private Collection Collection1;
    private Collection Collection2;
    private Collection Collection3;
    private Collection Collection4;

    public Student(String name, int id, int age, Dog dog) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.dog = dog;
        System.out.print("Student 初始化完成");
    }


    private void destroy2() {
        System.out.println(Student.class.getName()+" destory...");
    }

    private void init2() {
        System.out.println(Student.class.getName()+" init...");
    }
}
