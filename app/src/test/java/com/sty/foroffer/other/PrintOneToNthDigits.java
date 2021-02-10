package com.sty.foroffer.other;

/**
 *  顺序输出从1到n位最大十进制的数值
 *  ①题目：
 *      输入数字`n`，按顺序打印出从 1 到`n`为位最大十进制数的数值，比如输入 3 ，则打印出1、2、3一直到最大三位数即999。
 *
 *  ②算法思路：
 *      **思路一**：递归法
 *          使用一个`n`位的数组来存储每一位的元素，例如`n`位 3，则 000 表示为 [0, 0, 0]；使用递归的方式，存放每一位元素值。
 *      **思路二**：累加法
 *          同上，使用一个`n`位的数组来存储每一位的元素，然后循环执行加 1 运算，并在数组中进行模拟进位，
 *      直到最高位需要进位，则表示循环结束。
 *
 * @Author: tian
 * @UpdateDate: 2021/2/10 9:36 AM
 */
public class PrintOneToNthDigits {
    public static void main(String[] args) {
        //printOneToNthDigits(3);
        printOneToNthDigits2(3);
    }

    /**
     * 方法一：递归法
     * 输入数字n，按照顺序打印出从1到最大的n位十进制数
     * @param n 输入的数字
     */
    public static void printOneToNthDigits(int n) {
        //输入的数字不能为小于1
        if(n < 1) {
            throw new RuntimeException("The input number must larger than 0");
        }
        //创建一个数组用于打印存放值
        int[] arr = new int[n];
        printOneToNthDigits(0, arr);
    }

    /**
     *
     * @param n  当前处理的是第几个元素，从0开始计数
     * @param arr 存放结果的数组
     */
    private static void printOneToNthDigits(int n, int[] arr) {
        //说明数组已经装满元素
        if(n >= arr.length) {
            //可以输出数组的值
            printArray(arr);
        }else {
            for (int i = 0; i <= 9; i++) {
                arr[n] = i;
                printOneToNthDigits(n + 1, arr);
            }
        }
        // 0 0 0
        // 0 0 1
    }

    /**
     * 输入数组的元素，从左到右，从第一个非0值到开始输出到最后的元素
     * @param arr 要输出的数组
     */
    public static void printArray(int[] arr) {
        //找第一个非0的元素
        int index = 0;
        while (index < arr.length && arr[index] == 0) {
            index++;
        }

        //从第一个非0值开始输出到最后的元素
        for (int i = index; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        //条件成立说明数组中有非0元素，所以需要换行
        if(index < arr.length) {
            System.out.println();
        }
    }

    // -------------------------------累加法--------------------------------------
    /**
     * 方法二：累加法
     * 输入数字n，按照顺序打印出从1到最大的n位十进制数
     * @param n 输入的数字
     */
    public static void printOneToNthDigits2(int n) {
        //输入值必须大于0
        if(n < 1) {
            throw new RuntimeException("The input number must larger than 0");
        }

        //创建长度为n的数组
        int[] arr = new int[n];
        //为数组元素赋初始值
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }

        //求结果，如果最高位没有进位就一直进行处理
        while (addOne(arr) == 0) {
            printArray(arr);
        }
    }

    /**
     * 对arr表示的数组最低位加1，arr中的每个数都不能超过9且不能小于0，每个位置模拟一个数位
     * @param arr 待加数组
     * @return 判断高位是否有进位，如果有进位就返回1，否则返回0
     */
    private static int addOne(int[] arr) {
        //保存进位值，因为每次最低位加1
        int carry = 1;
        //最低位的位置的后一位
        int index = arr.length;

        do {
            //指向上一个位置
            index--;
            //处理位置的值加上进位的值
            arr[index] += carry;
            //求处理位置的进位
            carry = arr[index] / 10;
            //求处理位置的值
            arr[index] %= 10;
        }while (carry != 0 && index > 0);

        //如果index==0 说明已经处理了最高位，carry > 0 说明最高位有进位，返回1
        if(carry > 0 && index == 0) {
            return 1;
        }
        //无进位返回0
        return 0;
    }


}
