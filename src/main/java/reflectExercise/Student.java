package reflectExercise;

import java.util.Date;

public class Student extends Person{
    private String studentId;

    public Student(){};

    public Student(Integer age){
        super.setAge(age);
    }

    public Student(Date d){
    }
    public Student(String Name,Integer age,String studentId){
        super();
        super.setName(Name);
        super.setAge(age);
        this.studentId = studentId;
    }

    @Override
    public void sport() {
        System.out.println("sport0");
    }
    @Override
    public void sport(String a) {
        System.out.println("sport"+a);
    }

    @Override
    public void sport(String... args) {

    }
//    @Override
//    public void sport(String... args) {
//        for(String s:args){
//            System.out.println("Sport:"+s);
//        }
//    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }



}
