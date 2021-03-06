package com.sty.algorithm.sort;

import java.util.Random;

/**
 * 快速排序算法
 * 思想如下：
 *   选择最后一个数字作为基准值，使得
 *     小于最后一个元素的值，放到数组的左边（左边小于基准值的部分不一定有序）
 *     大于于最后一个元素的值，放到数组的右边（右边大于基准值的部分不一定有序）
 *     等于最后一个元素的值，放到数组的中间
 *   递归执行上述“分区”过程直至整个数组有序。
 *
 * 随机快速排序
 *   经典快速排序总是指定数组或者某部分的最后一个元素作为基准值，
 *   随机快速排序是指定数组或者某一部分中的随机值作为基准值。
 * @Author: tian
 * @Date: 2020/9/18 23:19
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 3, 2, 6, 5, 6, 2, 7, 5, 8, 4};
        quickSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    /**
     * 快速排序，使得整数数组arr有序
     * @param arr
     */
    public static void quickSort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 快速排序，使得整数数组arr 的 [L,R] 部分有序
     * @param arr
     * @param L
     * @param R
     */
    public static void quickSort(int[] arr, int L, int R) {
        if(L < R) {
            //把数组中随机的一个元素与最后一个元素交换，这样以最后一个元素为基准值实际上就是以数组中随机的一个元素作为基准值
            swap(arr, new Random().nextInt(R - L + 1) + L, R);
            int[] p = partition(arr, L, R);
            quickSort(arr, L, p[0] - 1);
            quickSort(arr, p[1] + 1, R);
        }
    }

    /**
     * 分区的过程，使得整数数组arr的[L,R]部分上，使得
     *   小于 arr[R] 的元素位于 [L,R] 部分的左边（但这部分不一定有序）
     *   大于 arr[R] 的元素位于 [L,R] 部分的右边（但这部分不一定有序）
     *   等于 arr[R] 的元素位于 [L,R] 部分的中间
     *
     * @param arr 源数组
     * @param L 左下标
     * @param R 右下标
     * @return 返回等于部分的第一个元素的下标和最后一个下标组成的整数数组
     *    4, 3, 2, 6, 5, 6, 2, 7, 5, 8, 4
     * 1：4, 3, 2, 6, 5, 6, 2, 7, 5, 8, 4
     * 2：3, 4, 2, 6, 5, 6, 2, 7, 5, 8, 4
     * 3：3, 2, 4, 6, 5, 6, 2, 7, 5, 8, 4
     * 4：3, 2, 4, 4, 5, 6, 2, 7, 5, 8, 6
     * 5：3, 2, 4, 4, 8, 6, 2, 7, 5, 5, 6
     * 6：3, 2, 4, 4, 5, 6, 2, 7, 8, 5, 6
     * 7：3, 2, 4, 4, 7, 6, 2, 5, 8, 5, 6
     * 8：3, 2, 4, 4, 2, 6, 7, 5, 8, 5, 6
     * 9：3, 2, 2, 4, 4, 6, 7, 5, 8, 5, 6
     *10：3, 2, 2, 4, 4, 6, 7, 5, 8, 5, 6
     */
    private static int[] partition(int[] arr, int L, int R) {
        int basic = arr[R];
        int less = L - 1;
        int more = R + 1;
        while (L < more) {
            if(arr[L] < basic) {
                swap(arr, ++less, L++);  //L代表current, less代表前驱（左边最后一个小于基准的下标）
            }else if(arr[L] > basic) {
                swap(arr, --more, L);  //more 代表右边第一个大于基准的下标
            }else {
                L++;
            }
        }
        return new int[] {less + 1, more - 1};
    }


    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
