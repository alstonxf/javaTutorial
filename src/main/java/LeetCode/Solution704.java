package LeetCode;

import org.junit.Test;
import JunitTest.test.My.MyTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*# 方案二：二分法
        nums=[-3,4,7,10,13,21,43,77,89]
        find_num=8
        def binary_search(find_num,l):
        print(l)
        if len(l) == 0:
        print('找的值不存在')
        return
        mid_index=len(l) // 2

        if find_num > l[mid_index]:
        # 接下来的查找应该是在列表的右半部分
        l=l[mid_index+1:]
        binary_search(find_num,l)
        elif find_num < l[mid_index]:
        # 接下来的查找应该是在列表的左半部分
        l=l[:mid_index]
        binary_search(find_num,l)
        else:
        print('find it')

        binary_search(find_num,nums)*/
/*
704. 二分查找
        给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。


        示例 1:

        输入: nums = [-1,0,3,5,9,12], target = 9
        输出: 4
        解释: 9 出现在 nums 中并且下标为 4
        示例 2:

        输入: nums = [-1,0,3,5,9,12], target = 2
        输出: -1
        解释: 2 不存在 nums 中因此返回 -1
*/

public class Solution704 {

    public int search(int[] nums, int target) {

        int result = -1;
        if (target>nums[nums.length-1] || target<nums[0]){
            return result;
        }

        //当左右区间中还有数字时就压缩空间，除非找到target
        long left = 0;
        long right = nums.length-1;
        while(right-left > 1) {
            int mid = (int) Math.floor((left + right) / 2);
            if (nums[mid] == target) {
                result = mid;
                break;
            } else if (nums[mid] > target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        //左右区间中间已经不存在target了，只要再判断left，right有没有可能存在target
        if (nums[(int)left]==target) {
            result = (int)left;
        }
        if (nums[(int)right]==target) {
            result = (int)right;
        }
        try{System.out.println("result = "+result+"  nums["+result+"]="+nums[result] + "  target="+target);}
        catch (Exception eee){
            System.out.println("不存在");
        }

        return result;

    }

/*    给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
    示例 1:
    输入: nums = [-1,0,3,5,9,12], target = 9
    输出: 4
    解释: 9 出现在 nums 中并且下标为 4

    示例 2:
    输入: nums = [-1,0,3,5,9,12], target = 2
    输出: -1
    解释: 2 不存在 nums 中因此返回 -1
             
    提示：
    你可以假设 nums 中的所有元素是不重复的。
    n 将在 [1, 10000]之间。
    nums 的每个元素都将在 [-9999, 9999]之间。

    来源：力扣（LeetCode）
    链接：https://leetcode.cn/problems/binary-search
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/

    public static void main(String[] args) {
//        int[] nums = new int[]{-1,0,3,5,9,12};
        int[] nums = new int[]{-1,0,3,5,9,12};
        new Solution704().search(nums,2);
    }

}