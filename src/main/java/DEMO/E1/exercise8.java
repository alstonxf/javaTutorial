package DEMO.E1;

public class exercise8 {

    /**
     * 8.编写一个程序，设定一个有大小写字母的字符串，先将字符串的大写字符输出，再将字符串中的小写字符输出。
     */
    public static void main(String[] args) {
        String cha1 = "aAbbBcdAAa";
        String upperChar = "";
        String lowerChar = "";

        for (int i=0;i<cha1.length();i++){
            if(Character.isUpperCase(cha1.charAt(i))){
                upperChar+=cha1.charAt(i);
            }else{
                lowerChar+=cha1.charAt(i);
            }

        }
        System.out.println("大写字母："+upperChar);
        System.out.println("小写字母："+lowerChar);

//        char[] l = cha1.toCharArray();
//        for(char i:l){
//            System.out.println(i);
//        }
    }

}
