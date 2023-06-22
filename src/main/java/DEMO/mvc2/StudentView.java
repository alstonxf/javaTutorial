package DEMO.mvc2;

import DEMO.mvc2.model.StudentEntityBean;

public class StudentView {

    public void displayStudentSuccess(int id, String name){
        System.out.println("更新成功 学生id：" + id + " 姓名：" + name);
    }

    public void displayStudentFail(int id, String name){
        System.out.println("更新失败 学生id：" + id + " 姓名：" + name);
    }


    public void displayStudent2(StudentEntityBean studentEntityBean){
        int id = studentEntityBean.getId();
        String name = studentEntityBean.getName();

        System.out.println("更新成功 学生id：" + id + " 姓名：" + name);
    }
}
