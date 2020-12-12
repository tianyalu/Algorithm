package com.sty.algorithm.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目：
 *     给定一个整型数组，找出能相加起来等于一个特定目标数字的两个数。
 *     Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 *     You may assume that each input would have exactly one solution.
 *     Example:
 *       Given nums = [2, 7, 11, 15], target = 9, Because nums[0] + nums[1] = 2 + 7 = 9, return [0, 1].
 *
 * 算法思路：
 *      用`HashMap`，`HashMap`是内部存储方式为哈希表的`map`结构。遍历数组，其中`key`存放目标值减去当前值，
 *    `value`存放对应索引，如果遍历过程中发现`map`中存在与当前值相等的`key`，则返回结果。
 * @Author: tian
 * @UpdateDate: 2020/12/12 4:15 PM
 */
public class TwoSum {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 11, 15, 4, 7, 6};
        printArr(findTwoSum(numbers, 9));
    }

    public static int[] findTwoSum(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {
            if(map.get(numbers[i]) != null) {
                int[] result = {map.get(numbers[i]), i};
                return result;
            }
            map.put(target - numbers[i], i);
        }

        int[] result = {};
        return result;
    }

    private static void printArr(int[] arr) {
        if(arr == null || arr.length == 0) {
            System.out.println("未找到满足要求的结果");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }
}
