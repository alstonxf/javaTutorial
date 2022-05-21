package LeetCode;

/*
344. 反转字符串
        编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。

        不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。



        示例 1：

        输入：s = ["h","e","l","l","o"]
        输出：["o","l","l","e","h"]
        示例 2：

        输入：s = ["H","a","n","n","a","h"]
        输出：["h","a","n","n","a","H"]


        提示：

        1 <= s.length <= 105
        s[i] 都是 ASCII 码表中的可打印字符
        通过次数536,368提交次数682,709
*/

import java.util.Arrays;

public class Soulution344 {
    public static void reverseString(char[] s) {

        char temp;
        int m = (int)(Math.floor(s.length/2));

        for(int i=0;i<m;i++){
            System.out.println("i="+i);
            temp = s[i];
            s[i] = s[s.length-1-i];
            s[s.length-1-i] = temp;
        }
        System.out.print(s);
    }

    public static void main(String[] args) {
        reverseString(new char[]{'h','e','l','l','o'});
//        reverseString(new char[]{'a','b','c','d'});
    }
}
