package com.sty.util;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/18 11:08 AM
 */
public class ArrayUtils {

    public static void printArray(int[] arr) {
        if(arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
