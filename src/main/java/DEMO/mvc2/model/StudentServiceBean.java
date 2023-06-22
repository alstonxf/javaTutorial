package DEMO.mvc2.model;

import org.apache.commons.io.filefilter.TrueFileFilter;

public class StudentServiceBean {

    StudentEntityBean studentEntityBean;

    public StudentServiceBean(StudentEntityBean studentEntityBean){
        this.studentEntityBean = studentEntityBean;
    }
    public Boolean updateStudentEntityBean (int idNew, String name){

//        判断新的id是否符合规则，比如比旧的id大
        int idBefore = studentEntityBean.getId();
        if (idNew > idBefore){
            studentEntityBean.setName(name);
            studentEntityBean.setId(idNew);
            return Boolean.TRUE;
        } else {
            System.out.println("新的id("+idBefore+")小于旧的id("+idNew+")，根据规则无效，无法更新此id，返回空studentEntityBean");
            return Boolean.FALSE;
        }

    }
}
