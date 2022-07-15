package effectiveJAVA.Chapter2.rule1;

public class rule1 {
    /**
     * 第一条：用静态工厂方法代替构造器
     */
    public static void main(String[] args) {
        Boolean t = valueOf.valueOf(Boolean.TRUE);
        System.out.println("t="+t);
        Boolean f = valueOf.valueOf(Boolean.FALSE);
        System.out.println("f="+f);
    }
}
