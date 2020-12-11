package com.sty.algorithm.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 寻找和为0的子数组
 * 题目：
 *      Given an integer array,find a subarray where the sum of numbers is zero.Your code should
 *      return the index of the first number and the index of the last number.
 *      Example:
 *      Given [-3, 1, 2, -3, 4],return [0, 2] or [1, 3]
 * 算法思路：
 *      记录每一个位置的`sum`，存入`HashMap`中，如果某一个`sum`已经出现过，那么说明中间的`subarray`的`sum`为0，
 *      时间复杂度为`O(n)`，空间复杂度为`O(n)`.
 * @Author: tian
 * @UpdateDate: 2020/12/11 9:56 AM
 */
public class SubArraySum {
    public static void main(String[] args) {
        //Integer arr[] = {-3, 1, 2, -3, 4};
        Integer arr[] = {-2, 1, 2, -3, 4};

        List<Integer> ans = subArraySum(arr);
        if(ans.isEmpty()) {
            System.out.println("未找到要求的结果");
        }else {
            System.out.println(ans.get(0) + ", " + ans.get(1));
        }
    }

    public static List<Integer> subArraySum(Integer[] arr) {
        List<Integer> ansIndexList = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>(); //<和, arr中的索引>
        map.put(0, -1);
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if(map.containsKey(sum)) {
                ansIndexList.add(map.get(sum) + 1); // 左开右闭区间：( ]
                ansIndexList.add(i);
                return ansIndexList;
            }
            map.put(sum, i);
        }
        return ansIndexList;
    }
}
