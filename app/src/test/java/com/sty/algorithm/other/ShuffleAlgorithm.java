package com.sty.algorithm.other;

import java.util.Map;
import java.util.Random;

/**
 * 洗牌算法
 *
 * 算法思想：
 *      该算法的基本思想和 Fisher 类似，每次从未处理的数据中随机取出一个数字，然后把该数字放在数组的尾部，
 *  即数组尾部存放的是已经处理过的数字。
 *
 * 它的proof如下：
 *      对于arr[i],洗牌后在第n-1个位置的概率是1/n（第一次交换的随机数为i）
 *      在n-2个位置概率是[(n-1)/n] * [1/(n-1)] = 1/n，（第一次交换的随机数不为i，第二次为arr[i]所在的位置
 *          （注意，若i=n-1，第一交换arr[n-1]会被换到一个随机的位置））
 *      在第n-k个位置的概率是[(n-1)/n] * [(n-2)/(n-1)] *...* [(n-k+1)/(n-k+2)] *[1/(n-k+1)] = 1/n
 *          （第一个随机数不要为i，第二次不为arr[i]所在的位置(随着交换有可能会变)……第n-k次为arr[i]所在的位置）.
 * @Author: tian
 * @UpdateDate: 2020/11/13 9:19 AM
 */
public class ShuffleAlgorithm {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] arrCopy = arr.clone();
        shuffle(arrCopy);
        printArray(arrCopy);
        printArray(arr);
    }

    public static void shuffle(int[] arr) {
        if(arr == null || arr.length == 0) {
            System.out.println("参数非法");
            return;
        }

        Random random = new Random(System.currentTimeMillis());
        for (int i = arr.length - 1; i >= 0; i--) {
            //int randomIndex = Math.abs(random.nextInt()) % (i + 1); //(-i-1, i+1)
            int randomIndex = random.nextInt(i + 1) % (i + 1); //[0, i+1)
            int temp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = temp;
        }
    }

    public static void printArray(int[] arr) {
        if(arr == null || arr.length == 0) {
            System.out.println("参数非法");
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
