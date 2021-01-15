package com.sty.algorithm.dynamicprograming;

/**
 * 题目：
 *      求一个数组中积最大的连续子序列
 *
 * 算法思路：
 *      此题是求数组中子区间的最大乘积，对于乘法，我们需要注意，负数乘以负数，会变成正数，所以解这道题的时候我们需要维护两个变量，
 *   当前的最大值，以及最小值，最小值可能为负数，但没准下一步乘以一个负数，当前的最大值就变成最小值，二最小值则变成最大值了。
 *  `dp`四要素：
 *      状态：
 *       `max_product[i]`：以`nums[i]`结尾的`max subarray product`；
 *       `min_product[i]`：以`nums[i]`结尾的`min subarray product`.
 *      方程：
 *        `max_product[i] = getMax(max_product[i - 1] * nums[i], min_product[i - 1] * nums[i], nums[i])`；
 *        `min_product[i] = getMin(max_product[i - 1] * nums[i], min_product[i - 1] * nums[i], nums[i])`；
 *      初始化：
 *        `max_product[0] = min_product[0] = nums[0]`;
 *      结果：
 *         每次循环中`max_product[i]`的最大值。
 *
 * @Author: tian
 * @UpdateDate: 2021/1/15 11:21 AM
 */
public class MaxProductSubArray {

    public static void main(String[] args) {
        int[] nums = {2, 3, -2, 4};
        System.out.println(maxProductSubArray(nums));
    }

    public static int maxProductSubArray(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }

        int[] max = new int[nums.length];
        int[] min = new int[nums.length];

        min[0] = max[0] = nums[0];
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            min[i] = max[i] = nums[i];
            if(nums[i] > 0) {
                max[i] = Math.max(max[i], max[i - 1] * nums[i]);
                min[i] = Math.min(min[i], min[i - 1] * nums[i]);
            }else if(nums[i] < 0){
                max[i] = Math.max(max[i], min[i - 1] * nums[i]);
                min[i] = Math.min(min[i], max[i - 1] * nums[i]);
            }
            result = Math.max(result, max[i]);
        }
        return result;
    }
}
