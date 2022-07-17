package LeetCode.TwoPointers;

/*
567. 字符串的排列
        给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。

        换句话说，s1 的排列之一是 s2 的 子串 。
        示例 1：

        输入：s1 = "ab" s2 = "eidbaooo"
        输出：true
        解释：s2 包含 s1 的排列之一 ("ba").
        示例 2：

        输入：s1= "ab" s2 = "eidboaoo"
        输出：false


        提示：

        1 <= s1.length, s2.length <= 104
        s1 和 s2 仅包含小写字母
*/

public class Solution567 {

    public boolean checkInclusion2(String s1, String s2) {
        /**
         * 错误解法：得到的是s1的正序或倒叙是不是s2里面的一个子集，顺序一模一样，但实际上这一题并不要求顺序一模一样，只要有就可以。
         */
        if(s1.length()>s2.length()){
            return false;
        }

        for(int j = 0;j<=s2.length()-s1.length();j++){
            char a = s1.charAt(0);
            char b = s2.charAt(j);
            if(s1.charAt(0)==s2.charAt(j)){
                if(s1.length()==1){
                    return true;
                }
                String temp = "yes";
                for(int i = 1;i<s1.length();i++){
                    char c = s1.charAt(i);
                    char d = s2.charAt(j+i);
                    if(s1.charAt(i) != s2.charAt(j+i)){
                        temp = "no";
                    }
                }
                if(temp=="yes"){
                    return true;
                }
                System.out.println("正序判断："+temp);
            }
        }

        s1 = new StringBuffer(s1).reverse().toString();

        for(int j = 0;j<=s2.length()-s1.length();j++){
            char a = s1.charAt(0);
            char b = s2.charAt(j);
            if(s1.charAt(0)==s2.charAt(j)){
                String temp = "yes";
                for(int i = 1;i<s1.length();i++){
                    char c = s1.charAt(i);
                    char d = s2.charAt(j+i);
                    if(s1.charAt(i) != s2.charAt(j+i)){
                        temp = "no";
                    }
                }
                if(temp=="yes"){
                    return true;
                }
                System.out.println("正序判断："+temp);
            }
        }
        return false;
    }


    public boolean checkInclusion(String s1, String s2) {
        if(s1.length()>s2.length()){
            return false;
        }

        for(int j = 0;j<=s2.length()-s1.length();j++){
            char a = s1.charAt(0);
            char b = s2.charAt(j);
            if(s1.charAt(0)==s2.charAt(j)){
                if(s1.length()==1){
                    return true;
                }
                String temp = "yes";
                for(int i = 1;i<s1.length();i++){
                    char c = s1.charAt(i);
                    char d = s2.charAt(j+i);
                    if(s1.charAt(i) != s2.charAt(j+i)){
                        temp = "no";
                    }
                }
                if(temp=="yes"){
                    return true;
                }
                System.out.println("正序判断："+temp);
            }
        }
        return false;
    }
    public static void main(String[] args) {
//        String s1 = "ab";
//        String s2 = "eidbaooo";
//        String s2 = "eidboaoo";
//        String s1 ="adc";
//        String s2 = "dcda";
        String s1 ="abc";
        String s2 = "bbbca";
        boolean result = new Solution567().checkInclusion(s1,s2);
        System.out.println(result);
    }
}
