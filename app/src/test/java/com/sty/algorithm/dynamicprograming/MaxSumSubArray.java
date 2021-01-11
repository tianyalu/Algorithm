package com.sty.algorithm.dynamicprograming;

/**
 * 题目：求一个数组中和最大的连续子序列
 *
 * 算法思路：
 *   方案一：动态规划
 *     1. 状态`dp[i]`：以`A[i]`为最后一个数的所有`max subarray`的和；
 *     2. 递推公式：`dp[i] = A[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0)`；
 *     3. 由于`dp[i]`仅取决于`dp[i - 1]`，所以可仅用一个变量来保存前一个状态，而节省内存。
 *   方案二：分治求解
 *     假设数组`A[left, right]`存在最大区间，`mid=(left+right)/2`，那么无非就是三种情况：
 *     1. 最大值在`A[left, mid - 1]`里面；
 *     2. 最大值在`A[mid + 1, right]`里面；
 *     3. 最大值跨过了`mid`，也就是我们需要计算`[left, mid - 1]`区间的最大值，以及`[mid + 1, right]`的最大值，然后加上`mid`，三者之和就是总的最大值。
 *     可以看到，对于1和2，我们通过递归可以很方便地求解，然后在同第3的结果比较，就是得到的最大值。
 * @Author: tian
 * @UpdateDate: 2021/1/11 10:09 AM
 */
public class MaxSumSubArray {
    public static void main(String[] args) {
        int[] A = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(A));
        System.out.println(maxSubArray2(A));
    }

    /**
     * 和最大的连续子序列 --> 动态规划
     * A:     -2 1 -3 4 -1 2 1 -5 4
     * dp[i]: -2 1 -2 4  3 5 6  1 5
     * @param A：A list of integers
     * @return An integer indicate the sum of max subArray
     */
    public static int maxSubArray(int[] A) {
        if(A == null || A.length == 0) {
            return 0;
        }

        int[] dp = new int[A.length]; //dp[i] means the maximum subArray ending with A[i]
        dp[0] = A[0];
        int max = dp[0];
        for (int i = 1; i < A.length; i++) {
            dp[i] = A[i] + Math.max(dp[i - 1], 0);
            max = Math.max(max, dp[i]);
        }

        return max;
    }


    /**
     * 和最大的连续子序列 --> 分治求解
     * @param A A list of integers
     * @return An integer indicate the sum of max subArray
     */
    public static int maxSubArray2(int[] A) {
        if(A == null || A.length == 0) {
            return 0;
        }

        return findMaxSub(A, 0, A.length - 1, Integer.MIN_VALUE);
    }
    /**
     * recursive to find max sum, may appear on the left or right part, or across mid(from left to right)
     * @param A
     * @param left
     * @param right
     * @param maxSum
     * @return
     */
    public static int findMaxSub(int[] A, int left, int right, int maxSum) {
        if(left > right) {
            return Integer.MIN_VALUE;
        }

        //get max sub sum from both left and right cases
        int mid = (left + right) / 2;
        int leftMax = findMaxSub(A, left, mid - 1, maxSum);
        int rightMax = findMaxSub(A, mid + 1, right, maxSum);
        maxSum = Math.max(maxSum, Math.max(leftMax, rightMax));

        //get max sum of this range(case: across mid)
        //so need to expend to both left and right using mid as center
        //mid -> left
        int sum = 0, midLeftMax = 0;
        for(int i = mid - 1; i >= left; i--) {
            sum += A[i];
            if(sum > midLeftMax) {
                midLeftMax = sum;
            }
        }
        //mid -> right
        int midRightMax = 0; sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += A[i];
            if(sum > midRightMax) {
                midRightMax = sum;
            }
        }

        // get the max value from the left, right and across mid
        maxSum = Math.max(maxSum, midLeftMax + midRightMax + A[mid]);

        return maxSum;
    }
}
