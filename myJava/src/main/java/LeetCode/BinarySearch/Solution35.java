package LeetCode.BinarySearch;

/*
35. 搜索插入位置
        给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
        请必须使用时间复杂度为 O(log n) 的算法。

        示例 1:

        输入: nums = [1,3,5,6], target = 5
        输出: 2
        示例 2:

        输入: nums = [1,3,5,6], target = 2
        输出: 1
        示例 3:

        输入: nums = [1,3,5,6], target = 7
        输出: 4


        提示:

        1 <= nums.length <= 104
        -104 <= nums[i] <= 104
        nums 为 无重复元素 的 升序 排列数组
        -104 <= target <= 104
*/
public class Solution35 {
    public int searchInsert(int[] nums, int target) {
        int result;
        if (target>nums[nums.length-1]){
            return nums.length;
        }else if( target<nums[0]){
            return 0;
        }

        //当左右区间中还有数字时就使用游标压缩空间，除非找到target
        long left = 0;
        long right = nums.length-1;
        while(right-left > 1) {
            int mid = (int) Math.floor((left + right) / 2);
            if (nums[mid] == target) {
                result = mid;
                System.out.println("取mid="+result);
                return result;
            } else if (nums[mid] > target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        //左右区间中间已经不存在target了，只要再判断left，right有没有可能存在target
        if (nums[(int)left]==target) {
            result = (int)left;
            System.out.println("取left="+result);
            return result;
        }else if (nums[(int)right]==target) {
            result = (int)right;
            System.out.println("取right="+result);
            return result;
        }else{
            result = (int)left+1;
            System.out.println("没找到，插入位置="+result);
            return result;
        }

    }

    public static void main(String[] args) {
//        int[] nums = new int[]{1,3,5,6};
        int[] nums = new int[]{1,4,6,7,8,9};
        int target = 6;
        new Solution35().searchInsert(nums,target);
    }
}
