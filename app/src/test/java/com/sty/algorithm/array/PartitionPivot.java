package com.sty.algorithm.array;

/**
 * 题目：
 * Given an nums of integers and an int k, partition the array(i.e move the elements in "numbs") such that:
 *    * All elements < k are moved tho the left
 *    * All elements >= k are moved to the right
 *    * Return the partitioning index, i.e the first index i nums[i] >= k.
 * Notice
 *     You shuod do really partition in array nums instead of just counting the numbers of integers smaller than k.
 *     if all elements in nums are smaller than k, then return nums.length
 *
 * 算法思路：
 *     根据给定的k, 也就是类似于Quick Sort中的pivot，将array从两头进行缩进，时间复杂度O(n)
 * @Author: tian
 * @UpdateDate: 2020/12/10 9:21 AM
 */
public class PartitionPivot {

    public static void main(String[] args) {
        int[] arr = {1, 4, 8, 2, 5, 7, 6, 3, 9};
        System.out.println(partitionArray(arr, 5));
        printArr(arr);
    }

    /**
     * 数组分治pivot
     * @param nums The integer array you should partition
     * @param k As description
     * @return The index after partition
     */
    public static int partitionArray(int[] nums, int k) {
        int pl = 0;
        int pr = nums.length - 1;
        while (pl <= pr) {
            while (pl <= pr && nums[pl] < k) { //从左向右找到第一个大于k的索引
                pl++;
            }
            while (pl <= pr && nums[pr] >= k) { //从右向左找到第一个小于等于k的索引
                pr--;
            }
            if(pl <= pr) { //如果左索引小于等于右索引，交换两个数并继续
                swap(pl, pr, nums);
                pl++;
                pr--;
            }
        }
        return pl;
    }

    private static void swap(int i, int j, int[] arr) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
