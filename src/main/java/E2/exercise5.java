package E2;

public class exercise5 {
    public static void main(String[] args) {
        int mark = 100;
        String grade = result(mark);
        System.out.println("成绩"+mark+"的等级是"+grade);
    }
    public static String createId(String year,String product){
        String id = null;
        id = year+product+ String.valueOf(Math.random()*10000000).substring(0,4);
        return id;
    }

    public static String result(int mark){
        String grade = null;
        if (mark<60){
            grade = "C";
        }else if(mark>90){
            grade = "A";
        }else{
            grade = "B";
        }
        return grade;
    }
}
