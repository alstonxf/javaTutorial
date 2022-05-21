package LeetCode;

import java.util.Arrays;


/*
189. 轮转数组
        给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。

        示例 1:

        输入: nums = [1,2,3,4,5,6,7], k = 3
        输出: [5,6,7,1,2,3,4]
        解释:
        向右轮转 1 步: [7,1,2,3,4,5,6]
        向右轮转 2 步: [6,7,1,2,3,4,5]
        向右轮转 3 步: [5,6,7,1,2,3,4]
        示例 2:

        输入：nums = [-1,-100,3,99], k = 2
        输出：[3,99,-1,-100]
        解释:
        向右轮转 1 步: [99,-1,-100,3]
        向右轮转 2 步: [3,99,-1,-100]


        提示：

        1 <= nums.length <= 105
        -231 <= nums[i] <= 231 - 1
        0 <= k <= 105
*/
public class Solution189 {
    public static void rotate(int[] nums, int k) {
        //向左旋转
//        int temp;
//        for(int i=0;i<k+1;i++){
////            System.out.println("i="+i);
//            temp = nums[0];
//            for(int j=0;j< nums.length -1;j++) {
//                nums[j] = nums[j + 1];
//            }
//            nums[nums.length-1] = temp;
//        }

        //向右旋转
        int temp;
        for(int i=0;i<k;i++){
            temp = nums[nums.length-1];
            for(int j=nums.length -1;j>0 ;j--) {
                nums[j] = nums[j - 1];
            }
            nums[0] = temp;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6,7};
//        int[] nums = new int[]{-1,-100,3,99};
        int k = 2;
        rotate(nums,k);
        System.out.println(Arrays.toString(nums));
    }
}

/*
[-1,-100,3,99]
        2
        输出：
        [99,-1,-100,3]
        预期结果：

        [3,99,-1,-100]*/

/*本题是右旋转，其实就是反转的顺序改动一下，优先反转整个字符串，步骤如下：
        [a,b,c,d]
        反转整个字符串         [d,c,b,a]
        反转区间为前k的子串     [c,d,b,a]
        反转区间为k到末尾的子串  [c,d,a,b]

        */

class Solution {
    private void reverse(int[] nums, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            int temp = nums[j];
            nums[j] = nums[i];
            nums[i] = temp;
        }
    }
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }
}