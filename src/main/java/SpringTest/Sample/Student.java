package SpringTest.Sample;


public class Student {

    private String name2;
    private int id;
    private int age;
    private Dog dog;

    public Collection habit;

    public Student(String name, int id, int age, Dog dog, Collection collect) {
        this.name2 = name;
        this.id = id;
        this.age = age;
        this.dog = dog;
        this.habit = collect;
        for (String i:this.habit.myList){
            System.out.println("初始化传入参数"+i);
        }
        System.out.print("Student 初始化完成!!");
    }

    private void init2() {
        System.out.println("\n指定类中的初始化方法名称"+Student.class.getName()+" init...");
    }

    private void destroy2() {
        System.out.println("\n指定类中销毁方法名称"+Student.class.getName()+" destory...");
    }

    void studentInfo(){
        System.out.println("id="+this.id);
        System.out.println("name2="+this.name2);
        System.out.println("age="+this.age);
        System.out.println("dog="+this.dog.getName());
        System.out.println("habit="+this.habit.myList.toString());
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
