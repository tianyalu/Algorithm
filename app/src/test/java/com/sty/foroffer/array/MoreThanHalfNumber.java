package com.sty.foroffer.array;

/**
 *  解法二：根据数组特点找出`O(n)`的算法
 *      数组中有一个数字出现的次数超过数组长度的一半，也就是说它出现的次数比其它所有数字出现的和还要多。
 *    因此我们可以考虑在遍历数组的时候保存两个值：一个是数组中的一个数字，一个是次数。当我们遍历到下一个数字的时候，
 *    如果下一个数字和我们之前保存的数字相同，则次数加1，如果下一个数字和我们之前保存的数字不同，则次数减1。如果次数为0，
 *    我们需要保存下一个数字，并把次数设为1。由于我们要找的数字出现的次数比其它所有数字出现的次数之和还要多，
 *    那么要找的数字肯定是最后一次把次数设为1时对应的数字。
 *
 * @Author: tian
 * @UpdateDate: 2021/3/2 9:54 AM
 */
public class MoreThanHalfNumber {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 1, 2, 2, 4, 2 , 2, 5, 2};
        System.out.println(moreThanHalfNumber(numbers));
    }

    /**
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字
     * 解法二：根据数组特点找出`O(n)`的算法
     * @param numbers 输入的数组
     * @return 找到的数字
     */
    public static int moreThanHalfNumber(int[] numbers) {
        //校验
        if(numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("array length must large than 0");
        }
        //用于记录出现次数大于数组一半的数
        int result = numbers[0];
        //与当前记录的数不同的数的个数
        int count = 0;
        for (int i = 1; i < numbers.length; i++) {
            if(count == 0) {
                result = numbers[i]; //重新记录一个数，假设它是出现次数大于数组一半的数
                count = 1; //记录统计值
            }else if(result == numbers[i]) {  //如果记录的值与统计值相等，计数值增加
                count++;
            }else { //如果不相同就减少，相互抵消
                count--;
            }
        }

        //最后的result可能是出现次数大于数组一半长度的值
        //统计result出现的次数
        count = 0;
        for (int number : numbers) {
            if(result == number) {
                count++;
            }
        }
        //如果出现次数大于数组的一半就返回对应的值
        if(count > numbers.length / 2) {
            return result;
        }else { //否则抛出异常
            throw new IllegalArgumentException("Invalid input");
        }
    }
}
