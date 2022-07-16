package DEMO.school.management.system;

import java.util.List;

/**
 * Many teachers, many students.
 * Implements teachers and student
 * using array list
 */
public class School {
    private List<Teacher> teacher;
    private List<Student> student;
    private static int totalMoneyEarned;//是扣除支出后的净收入
    private static int totalMoneySpent;

    /**
     * new school object is created
     * @param teacher list of teachers in the school.
     * @param student list of student in the school.
     */
    public School(List<Teacher> teacher, List<Student> student) {
        this.teacher = teacher;
        this.student = student;
        totalMoneyEarned=0;//收入是加上学生交的学费
        totalMoneySpent=0;//支出是减去教师的工资

    }

    /**
     *
     * @return the list of teacher in the school.
     */
    public List<Teacher> getTeacher() {
        return teacher;
    }

    /**
     * Adds a teacher to the school.
     * @param teacher the teacher to be added
     */

    public void addTeacher(Teacher teacher) {
        this.teacher.add(teacher);
    }//注意方法的参数(Teacher teacher)里第一个Teacher是类, teacher 是实例化的类名，而this.teacher 是列表 List<Teacher> teacher

    public List<Student> getStudent() {
        return student;
    }

    /**
     * Adds a student to the school
     * @param student the sutdent to be added.
     */
    public void addStudent(Student student){
        this.student.add(student);
    }

    /**
     *
     * @return the total money earned by the school
     */
    public int getTotalMoneyEarned() {
        return totalMoneyEarned;
    }

    /**
     *
     * @return MoneyEarned money that is supposed to be added.
     */
    public static void updateTotalMoneyEarned(int MoneyEarned) {
        totalMoneyEarned += MoneyEarned;
    }

    /**
     *
     * @return the total money spent by the school
     */
    public int getTotalMoneySpent() {
        return totalMoneySpent;
    }

    /**
     * update the money that is spent by the school which
     * is the salary given by th school to its teachers.
     * @param MoneySpent the money spent by school.
     */
    public static void updateTotalMoneySpent(int MoneySpent) {

        totalMoneyEarned -= MoneySpent;
    }

}
