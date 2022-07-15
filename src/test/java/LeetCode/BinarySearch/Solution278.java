package LeetCode.BinarySearch;

public class Solution278 {
/*
    278. 第一个错误的版本
    你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
    假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
    你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。

    示例 1：

    输入：n = 5, bad = 4
    输出：4
    解释：
    调用 isBadVersion(3) -> false
    调用 isBadVersion(5) -> true
    调用 isBadVersion(4) -> true
    所以，4 是第一个错误的版本。
    示例 2：

    输入：n = 1, bad = 1
    输出：1


    提示：

            1 <= bad <= n <= 2^31 - 1


    通过次数328,842提交次数726,182

    注意：bad 和 n都是int类型。这里有个坑是，如果两个最大的int数加在一起会超过int的范围，产生负数的。
*/

    /* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

    public static class Solution extends VersionControl {
        public int firstBadVersion(int n) {

            long left = 1;
            long right = n;
            while (true) {
                if (left==right){
                    System.out.println("left=right:  "+left);
                    return (int)left;
                }
                int mid = (int)Math.floor((left+right)/2);
                boolean c1 = isBadVersion(mid) == false;
                boolean c2 = isBadVersion(mid+1) == true;
                if(c1 && c2){
                    System.out.println(mid+1);
                    return mid+1;
                }

                if (isBadVersion(mid) == true) {
                    right = mid;
                } else {
                    left = mid;
                }
            }


        }
    }

    public static void main(String[] args) {
        new Solution().firstBadVersion(2126753390);
    }
}
