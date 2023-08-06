package test.interfaceExercise;

public class com implements lengthComparetor{
    public static void main(String[] args) {
        com c = new com();
        int d = c.compare("abc","abcd");
        if(d>0){
            System.out.println("第二个参数长");
        }else{
            System.out.println("第一个参数长");
        }
    }

    @Override
    public int compare(String a, String b) {
        return a.length()-b.length();
    }

    @Override
    public String testi(){
        return "a";
    }
}
