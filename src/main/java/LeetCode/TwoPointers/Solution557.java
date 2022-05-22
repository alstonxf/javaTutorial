package LeetCode.TwoPointers;

/*
557. 反转字符串中的单词 III
        给定一个字符串 s ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。

        示例 1：

        输入：s = "Let's take LeetCode contest"
        输出："s'teL ekat edoCteeL tsetnoc"
        示例 2:

        输入： s = "God Ding"
        输出："doG gniD"


        提示：

        1 <= s.length <= 5 * 104
        s 包含可打印的 ASCII 字符。
        s 不包含任何开头或结尾空格。
        s 里 至少 有一个词。
        s 中的所有单词都用一个空格隔开。
*/

public class Solution557 {

    public String reverseWord(String s2){
//        for (int i = 0;i<s2.length()-1;i++){
//            char temp = s2.charAt(s2.length() - 1);
//            s2.repla
//        }
        String s3 = "";
        for (int i = s2.length()-1;i>=0;i--){
//            System.out.println("i="+i+" s2.charAt(i)="+s2.charAt(i));
            s3 += s2.charAt(i);
        }

        return s3;
    }

    public String reverseWords(String s) {
        //单词的左边索引left,右边索引right
        String[] s1 = s.trim().split(" ");
        String s4 = "";
        for(String each : s1){
            s4+=reverseWord(each.trim());
            s4+=" ";
        }
        //System.out.println(s4);
        return s4.trim();

    }

    public static void main(String[] args) {
        String s = "Let's take LeetCode contest";
        new Solution557().reverseWords(s);
    }
}
