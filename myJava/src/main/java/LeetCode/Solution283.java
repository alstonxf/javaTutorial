package LeetCode;

import java.util.Arrays;

/*
283. 移动零
        给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。

        请注意 ，必须在不复制数组的情况下原地对数组进行操作。



        示例 1:

        输入: nums = [0,1,0,3,12]
        输出: [1,3,12,0,0]
        示例 2:

        输入: nums = [0]
        输出: [0]

*/
public class Solution283 {
//    public int[] rotate(int[] nums,int start,int end){
//        //start:nums中第一个0的下标
//        //end:nums中第一个非0的下标
//        //返回把【第一个0的下标，第一个非0的下标】这个数组颠倒后的nums
//        for (start = start,end = end;start<end;start++){
//            int temp = nums[end];
//
//        }
//        return nums;
//    }
//    public static void moveZeroes(int[] nums) {
//        int n = nums.length;
//        for(int i=0,j=0;i<n;i++,j++){
//            if
//            int temp = nums[i,j];
//        }
//
//    }

    public static void moveZeroes(int[] nums) {
        //每个0都找下一个非0的数交换，然后从下一个非0的数开始。所以只要遍历一次。
        for (int i =0;i<nums.length-1;i++){
            System.out.println("i ="+i+"  nums[i]="+nums[i]+"  "+Arrays.toString(nums));
            if (nums[i]!=0){
                continue;
            }
            int temp = 0;
            for(int j = i+1; j<=nums.length-1; j++){
                if (nums[j]!=0){
                    temp = nums[j];
                    nums[j] = 0;
                    nums[i] = temp;
                    break;
                }
            }
        }
        System.out.println("FINAL:  "+Arrays.toString(nums));
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0,1,0,3,12};
        moveZeroes(nums);

    }
}
