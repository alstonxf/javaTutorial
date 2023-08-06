package DEMO.mvc2;

import DEMO.mvc2.model.StudentEntityBean;
import DEMO.mvc2.model.StudentServiceBean;
import org.apache.commons.lang.ObjectUtils;

public class StudentController {

    StudentEntityBean studentModel;
    StudentServiceBean studentServiceBean;

    StudentView studentView;

    public StudentController(StudentEntityBean studentModel, StudentView studentView){
        this.studentModel = studentModel;
        this.studentView = studentView;
        this.studentServiceBean = new StudentServiceBean(this.studentModel);
    }

    public void UpdateStudentName(int id, String name){
        //调用model
        Boolean updateSuccessOrNot = studentServiceBean.updateStudentEntityBean(id, name);

        //传递给view
        if (Boolean.TRUE == updateSuccessOrNot){
            studentView.displayStudentSuccess(id,name);
        } else {
            studentView.displayStudentFail(id,name);
        }

    }

}
