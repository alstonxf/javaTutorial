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

public class Solution704 {
    public int search2(int[] nums2, int target2) {
        return 1;
    }

    public int search(int[] nums, int target) {

        if (target>nums[-1] || target<nums[0]){
            return -1;
        }

        int targetSub = nums.length;
        int mid = 0;

        List numsl = Arrays.asList(nums);
        ArrayList<Integer> numsa = new ArrayList<Integer>(numsl);
        while(numsa.size()>1){
            //取中间值,如果是偶数，直接除以2，如果是奇数，取中间值。
            if(numsa.size()%2==0){
                mid = numsa.size()/2;
            }else{
                mid = (numsa.size()+1)/2;
            }
            int numMid = nums[mid];
            //如果中间值比目标值大，取前半截为新的nums，否则取后半截。
            if(numMid>target){
                numsa = new ArrayList(numsa.subList(0,mid));
                Integer[] a = numsa.toArray(new Integer [numsa.size()]);
                int[] b = Arrays.stream(a).mapToInt(Integer::valueOf).toArray();
                search(b,target);

                targetSub = targetSub - numsa.size();
            }else if(numMid<target){
                numsa = new ArrayList(numsa.subList(mid,numsa.size()));

                targetSub =targetSub ;
            }else {
                return mid;
            }
        }

        return mid;
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
        int[] nums = new int[]{-1,0,3,5,9,12};

    }

}