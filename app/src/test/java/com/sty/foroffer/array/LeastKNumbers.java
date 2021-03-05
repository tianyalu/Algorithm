package com.sty.foroffer.array;

import com.sty.util.MaxHeep;

/**
 * ①题目：
 *      输入`n`个数，找出其中最小的`k`个数。例如输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
 *
 * ②解题思路：
 * **解法一：`O(n)`时间算法，只有可以修改输入数组时可用**
 *      可以基于`Partition`函数来解决这个问题，如果基于数组的第`k`个数字来调整，使得比第`k`个数字小的所有数字都位于
 *    数组的左边，比第`k`个数字大的所有数字都位于数组的右边，这样调整后，位于数组中左边的`k`个数字就是最小的`k`个数字
 *    （这`k`个数字不一定是排序的）。
 *
 * **解法二：`O(nlogk)`的算法，适合处理海量数据**
 *      先创建一个大小为`k`的数据容器来存储最小的`k`个数字，接下来我们每次从输入的`n`个整数中读入一个数，
 *    如果容器中已有的数字小于`k`个，则直接把这次读入的整数放入容器中；如果容器中已有`k`个数字了，也就是容器已满，
 *    此时我们不能再插入新的数字而只能替换已有的数字。找出这已有的`k`个数中的最大值，然后用这次待插入的整数和最
 *    大值进行比较。如果待插入的值比比当前已有的最大值小，则用这个数替换当前已有的最大值；
 *    如果待插入的值比当前已有的最大值还要大，那么这个数不可能是最小的`k`个整数之一，于是我们可以抛弃这个整数。
 *      因此当容器满了之后，我们要做3件事情：一是在`k`个整数中找到最大数；二是有可能在这个容器中删除最大数；
 *    三是有可能要插入一个新的数字。我们可以使用一个**大顶堆**在`O(logk)`时间内实现这三个步骤操作。
 * @Author: tian
 * @UpdateDate: 2021/3/4 10:11 AM
 */
public class LeastKNumbers {
    public static void main(String[] args) {
        int[] input = {4, 5, 1, 6, 2, 7, 3, 8};
        int[] output = getLeastNumbers(input, 5);
        for (int i = 0; i < output.length; i++) {
            System.out.print(output[i] + " ");
        }
        System.out.println();

        int[] input2 = {4, 5, 1, 6, 2, 7, 3, 8};
        int[] output2 = getLeastNumbers2(input2, 5);
        for (int i = 0; i < output2.length; i++) {
            System.out.print(output2[i] + " ");
        }
        System.out.println();
    }

    /**
     * 题目： 输入n个整数，找出其中最小的k个数。
     * 解法一 O(nlogn)
     * @param input  输入数组
     * @param k 最小的数的个数
     */
    public static int[] getLeastNumbers(int[] input, int k) {
        if(input == null || input.length == 0) {
            throw new IllegalArgumentException("Invalid args");
        }

        int start = 0;
        int end = input.length - 1;
        int index = partition(input, start, end);
        System.out.println(index);
        int target = k - 1;
        while(index != target) {
            if(index < target) {
                start = index + 1;
            }else {
                end = index - 1;
            }
            index = partition(input, start, end);
        }
        int[] output = new int[k];
        System.arraycopy(input, 0, output, 0, output.length);
        return output;
    }

    /**  4
     *   4 5 1 6 2 7 3 8
     *   3 5 1 6 2 7 3 8
     *   3 5 1 6 2 7 5 8 -->
     *   3 5 1 6 2 7 5 8
     *   3 2 1 6 2 7 5 8
     *   3 2 1 6 6 7 5 8
     *   3 2 1 4 6 7 5 8
     * 分区算法
     * @param input 输入数组
     * @param start 输出数组
     * @param end   结束下标
     * @return 分区位置
     */
    private static int partition(int[] input, int start, int end) {
        int tmp = input[start];
        while (start < end) {
            while (start < end && input[end] >= tmp) { //从右往左找到第一个小于start处值的索引
                end--;
            }
            input[start] = input[end]; //赋值
            while (start < end && input[start] <= tmp) { //从左往右找到第一个大于原start处值的索引
                start++;
            }
            input[end] = input[start]; //赋值
        }
        input[start] = tmp;
        return start;
    }



    /**
     * 题目： 输入n个整数，找出其中最小的k个数。
     * 解法二 O(nlogK)
     * @param input  输入数组
     * @param k 最小的数的个数
     */
    public static int[] getLeastNumbers2(int[] input, int k) {
        if(input == null || input.length == 0) {
            throw new IllegalArgumentException("Invalid args");
        }

        MaxHeep<Integer> maxHeep = new MaxHeep<>(k);
        for (int i : input) {
            if(maxHeep.size() < k) {
                maxHeep.add(i);
            }else {
                int max = maxHeep.first();
                if(max > i) {
                    maxHeep.deleteTop();
                    maxHeep.add(i);
                }
            }
        }
        int[] output = new int[k];
        for(int i = 0; i < k && maxHeep.hasNext(); i++) {
            output[i] = maxHeep.next();
        }
        return output;
    }
}
