package LeetCode.TwoPointers;

class SolutionD {
    public String reverseWords(String s) {
        char[] array = s.toCharArray();
        int start = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ' ') {
                reverse(array, start, i - 1);
                start = i + 1; // 更新start为下一个单词的左索引
                continue;
            }
            if (i == array.length - 1) {
                reverse(array, start, i);
            }
        }
        return new String(array);
    }

    private void reverse(char[] array, int l, int r) {
        while (l < r) {
            char temp = array[l];
            array[l] = array[r];
            array[r] = temp;
            l += 1;
            r -= 1;
        }
    }

    public static void main(String[] args) {
        String s = "Let's take LeetCode contest";
        String s1 = new SolutionD().reverseWords(s);
        System.out.println(s1);
    }
}

