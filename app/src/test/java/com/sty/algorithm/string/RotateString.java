package com.sty.algorithm.string;

/**
 * 题目：翻转字符串
 *   给定一个字符串和一个偏移量，根据偏移量旋转字符串（从左向右旋转）。
 *   Given a string and an offset, rotate string by offset.(rotate from left to right)
 *   Example:
 *     Given "abcdefg", offset=0 =>"abcdefg"; offset=1 =>"gabcdef"; offset=2 =>"fgabcde"; offset=3 =>"efgabcd".
 *   Challenge: Rotate in-place with O(1) extra memory.
 * 算法思路：
 *   常见的翻转法应用题，仔细观察规律可知翻转的分隔点在从数组末尾起`offset`位置，先翻转前半部分，随后翻转后半部分，最后整体翻转。
 *
 * @Author: tian
 * @UpdateDate: 2020/12/15 11:27 AM
 */
public class RotateString {
    public static void main(String[] args) {
        String s = "abcdefg";
        char[] A = s.toCharArray();

        printArr(rotateString(A, 3));
    }

    /**
     * 翻转字符串
     * @param A 原数组
     * @param offset 偏移量
     * @return
     */
    public static char[] rotateString(char[] A, int offset) {
        if(A == null || A.length == 0) {
            return A;
        }

        int len = A.length;
        offset %= len;
        reverse(A, 0, len - offset - 1);
        reverse(A, len - offset, len - 1);
        reverse(A, 0, len - 1);

        return A;
    }

    private static void reverse(char[] str, int start, int end) {
        while (start < end) {
            char temp = str[start];
            str[start] = str[end];
            str[end] = temp;
            start++;
            end--;
        }
    }

    private static void printArr(char[] A) {
        if(A == null || A.length == 0) {
            System.out.println("数据非法");
            return;
        }

        for (int i = 0; i < A.length; i++) {
            System.out.print(A[i]);
        }
        System.out.println();
    }

}
