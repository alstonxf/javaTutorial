package DEMO.school.management.system;
/**
This class is responsible for keep the track of students
 fees, name, grade&fees paid
 */
public class Student {

    private int id;
    private String name;
    private int grade;
    private int feesPaid;
    private int feesTotal;

    /**
     * To create a new student by initializing
     * Fees for every student is $30,000.
     * Fees paid initially is 0.
     * @param id id for the student:unique.
     * @param name name of the student.
     * @param grade grade of the student.
     */
    public Student(int id,String name, int grade){
        this.feesPaid = 0;
        this.feesTotal = 30000; // 每个学生要支付的总的学费
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    //Not going to alter student's name, student's id.

    /**
     * Used to update the student's grade
     * @param grade new grade of the student.
     */
    public void setGrade(int grade){
        this.grade = grade;
    }

    /**
     * keep adding the fees to feesPaid Field.
     * feespaid = 10,000 + 5,000 + 15,000
     * Add the fees to the fees paid
     * The school is going receive the funds.
     *
     * @param fees the fees that the student pays.
     */
    public void payFees(int fees){
        feesPaid+=fees;
        School.updateTotalMoneyEarned(feesPaid); // ？ 为什么是加入feespaid 而不是 fees
    }

    /**
     *
     * @return id of the student,
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return id of the student,
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return grade of the student,
     */
    public int getGrade() {
        return grade;
    }

    /**
     *
     * @return fees paid of the student,
     */
    public int getFeesPaid() {
        return feesPaid;
    }

    /**
     *
     * @return total fees of the student,
     */
    public int getFeesTotal() {
        return feesTotal;
    }

    /**
     *
     * @return the remaining fees.
     */
    public int getRemainingFees(){
        return feesTotal-feesPaid;
    }

    @Override
    public String toString(){
        return "student's name :" +name+
                "Total fees paid so far $" + feesPaid;
    }

}


    //private float salary;