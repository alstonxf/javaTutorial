package LeetCode;

/*
977. 有序数组的平方
        给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
        示例 1：

        输入：nums = [-4,-1,0,3,10]
        输出：[0,1,9,16,100]
        解释：平方后，数组变为 [16,1,0,9,100]
        排序后，数组变为 [0,1,9,16,100]
        示例 2：

        输入：nums = [-7,-3,2,3,11]
        输出：[4,9,9,49,121]


        提示：

        1 <= nums.length <= 104
        -104 <= nums[i] <= 104
        nums 已按 非递减顺序 排序


        进阶：

        请你设计时间复杂度为 O(n) 的算法解决本问题
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution977 {
    public static int[] sortedSquares(int[] nums) {

        ArrayList<Integer> resultFinal = new ArrayList<Integer>(){};
        if (nums.length==1){
            return new int[]{nums[0]*nums[0]};
        }
        if (nums[0]*nums[0]>nums[1] * nums[1]){
            resultFinal.add(nums[1] * nums[1]);
            resultFinal.add(nums[0] * nums[0]);
        }else{
            resultFinal.add(nums[0] * nums[0]);
            resultFinal.add(nums[1] * nums[1]);
        }
        for(int i=2;i<nums.length;i++){
            int sq = nums[i] * nums[i];
            for (int j=0;j<resultFinal.size()-1;j++){
                int resultThis = resultFinal.get(j);
                int resultNext = resultFinal.get(j+1);
                if(sq >= resultFinal.get(resultFinal.size()-1)){
                    resultFinal.add(sq);
                    break;
                }
                if (sq < resultThis){
                    resultFinal.add(j,sq);
                    break;
                }
                else if(sq >= resultThis  && sq <= resultNext){
                    resultFinal.add(j+1,sq);
                    break;
                }
            }
        }

        for (int i=0;i<resultFinal.size();i++){
            nums[i] = resultFinal.get(i);
        }
        return nums;

    }
        public static void main(String[] args) {
//            输入：nums = [-7,-3,2,3,11]
//            输出：[4,9,9,49,121]
            int[] nums = new int[]{-7};
            System.out.println(nums.length);
            int[] re = sortedSquares(nums);
            System.out.println(Arrays.toString(re));
    }
}
