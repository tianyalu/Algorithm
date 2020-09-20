package com.sty.algorithm.search;

/**
 * 二分查找
 * @Author: tian
 * @Date: 2020/9/19 11:03
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(binarySearchIndex(arr, 8));
    }

    public static int binarySearchIndex(int[] arr, int num) {
        //定义变量，表示查找数组范围的最左侧，先从0索引开始
        int left = 0;
        //定义变量，表示查找数组范围的最右侧，先从最大索引开始
        int right = arr.length - 1;
        //定义变量，表示查找范围的中间值
        int mid;
        while (left <= right) {
            //中间索引 = （左侧 + 右侧）/2
            mid = (left + right) / 2;
            //为了提高效率，可以用位运算代替除法运算
            //mid = (left + right) >>> 2;
            if(arr[mid] > num) {
                //如果中间元素大于要查找元素，则在中间元素左侧去查找正确元素
                right = mid - 1;
            }else if(arr[mid] < num) {
                //如果中间元素小于要查找元素，则在中间元素右侧去查找正确元素
                left = mid + 1;
            }else {
                //如果正好等于，则返回其索引
                return mid;
            }
        }
        // 当查找范围的最左侧和最右侧重叠后还没有找到元素，则返回-1表示没有找到
        return -1;
    }
}
