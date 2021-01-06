package com.sty.algorithm.dynamicprograming;

/**
 * 题目：
 *     Given a sequence of integers, find the longest increasing subsequence(LIS). Your code should return the length of the LIS.
 *     Example For [5, 4, 1, 2, 3], the LIS is [1, 2, 3], return 3.
 *     For [4, 2, 4, 5, 3, 7], the LIS is [4, 4, 5, 7], return 4.
 * 方案一：动态规划
 *     `dp[i]`表示以`i`结尾的子序列中`LIS`的长度，然后我们用`dp[j](0 <= j < i)`来表示在`i`之前的`LIS`的长度。
 *     当`a[i] > a[j]`时需要进行判断，是否将`a[i]`加入到`dp[j]`当中。为了保证我们每次加入都是得到一个最优的`LIS`，
 *     有两点需要注意：第一，每次`a[i]`都应当加入最大的那个`dp[j]`，保证局部性质最优，也就是需要找到`max(dp[j](0 <= j < i))`；
 *     第二，每次加入之后，我们都应当更新`dp[i]`的值，显然`dp[i] = dp[j] + 1`。递推公式如下所示：
 *     dp[i] = max(dp[j](0< =j < i)) + (a[i]>a[j] ? 1:0)
 * 方案二：堆栈二分搜索法
 *     开一个栈，每次取栈顶元素`top`和读到的元素`temp`做比较，如果`temp > top`则将`temp`入栈；如果`temp < top`
 *   则二分查找栈中的比`temp`大的第一个数，并用`temp`替换它，最长序列长度即为栈的大小`top`。
 *     这也是很好理解的，对于`x`和`y`，如果`x < y`且`Stack[y] < Stack[x]`，用`Stack[x]`替换`Stack[y]`，
 *   此时最长序列长度没有改变，但序列`Q`的“潜力”增大了。
 *     举例：原序列为 `1, 5, 8, 3, 6, 7`，栈为`1, 5, 8`，此时读到`3`，用`3`替换`5`，得到`1, 3, 8`；再读`6`，
 *   用`6`替换`8`，得到`1, 3, 6`；再读`7`，得到最终栈为`1, 3, 6, 7`，最长递增子序列长度为`4`。
 * @Author: tian
 * @UpdateDate: 2021/1/5 10:17 AM
 */
public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        int[] nums1 = {5, 4, 1, 2, 3}; //[1, 1, 1, 2, 3]
        int[] nums2 = {4, 2, 4, 5, 3, 7}; //[1, 1, 2, 3, 4]
        System.out.println(longestIncreasingSubsequence(nums2));
        System.out.println(findLongest(nums2));
    }


    /**
     * 法一：动态规划
     * @param nums
     * @return
     */
    public static int longestIncreasingSubsequence(int[] nums){
        int[] f = new int[nums.length];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            f[i] = 1; //相当于给表中每个位置初始化为1
            for (int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
            if(f[i] > max) {
                max = f[i];
            }
            //System.out.println(f[i]);
        }
        return max;
    }

    /**
     * 法二：堆栈+二分搜索法(缺点：仅能找出长度，最后的结果不一定是真正的子序列)
     * @param A 目标数组
     * @return
     */
    public static int findLongest(int[] A) {
        int length = A.length;
        int[] B = new int[length];
        B[0] = A[0];
        int end = 0;
        for (int i = 1; i < length; i++) {
            //如果当前数比B中最后一个数还大，直接添加
            if(A[i] >= B[end]) {
                B[++end] = A[i];
                continue;
            }
            //否则需要先找到替换位置
            int pos = findInsertPos(B, A[i], 0, end);
            B[pos] = A[i];
        }
        for (int i = 0; i <= end; i++) {
            System.out.print(B[i] + " ");
        }
        System.out.println();
        return end + 1;
    }

    /**
     * 二分查找第一个大于等于n的位置
     * @param B 目标数组
     * @param n 目标值
     * @param start 目标搜索起始位置
     * @param end 目标搜索结束位置
     * @return
     */
    private static int findInsertPos(int[] B, int n, int start, int end) {
        while (start <= end) {
            int mid = start + (end - start) / 2;  //直接使用（high + low）/ 2 可能导致溢出
            if(B[mid] < n) {
                start = mid + 1;
            }else if(B[mid] > n) {
                end = mid - 1;
            }else {
                return mid;
            }
        }
        return start;
    }
}
