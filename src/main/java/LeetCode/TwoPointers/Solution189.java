package LeetCode.TwoPointers;

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

//        //向右旋转
//        int temp;
//        for(int i=0;i<k;i++){
//            temp = nums[nums.length-1];
//            for(int j=nums.length -1;j>0 ;j--) {
//                nums[j] = nums[j - 1];
//            }
//            nums[0] = temp;
//        }

        //使用双指针

        for (int left=0,right=left+k;left<k;right++,right++){
            int leftTemp = nums[left];
            int rightTemp = nums[right];


            nums[right] = leftTemp;

            nums[left] = rightTemp;
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



    /*
双指针-对撞指针
1:由于该数组两部分都有序，满足双指针的使用场景
2:最终终止条件 左索引大于右索引
3:由于左边是递增的，如果目标值小于第一个索引值，左边索引增长结束。
4:右边部分是从右往左，为递减，如果右边索引值小于目标值，右减少结束。
5:弹出条件，两边中至少一边的索引值等于目标值，弹出索引。
6:两边索引无变化，那不存在，返回-1;
*/
/*
    int search3(vector<int>& nums, int target) {
        //双指针
        if (nums.size() < 1) return -1;
        int right = nums.size() - 1;
        int left = 0;
        while (right>=left)
        {
            if (nums[right]<target&&nums[left]>target) return -1; //6:两边索引无变化，那不存在，返回 - 1;
            if (nums[right] == target) return right;//5:弹出条件，两边中至少一边的索引值等于目标值，弹出索引。
            if (nums[left] == target) return left;//5:弹出条件，两边中至少一边的索引值等于目标值，弹出索引。
            if (nums[right]>target) right--;//7:如果还满足条件，索引继续变化
            if (nums[left]<target) left++;//7:如果还满足条件，索引继续变化
        }
        return -1;
    }
*/

}