package com.sty.foroffer.array;

/**
 * 找出排序旋转数组中的最小值
 *  ①题目
 *      把一个数组最开始的若干个元素搬到数组的末尾，我们称之为旋转。输入一个递增排序的数组的一个旋转，
 *    输出旋转数组的最小元素。例如`{3, 4, 5, 1, 2}`为`{1, 2, 3, 4, 5}`的一个旋转，该数组的最小值为1.
 *
 *  ②算法思路：
 *       和二分查找法一样，我们用两个指针分别指向数组的第一个元素和最后一个元素；
 *       接着我们可以找到数组的中间元素：
 *     如果该中间元素位于前面的递增子数组，那么它应该大于或者等于第一个指针指向的元素，此时数组中最小的元素应该
 *     位于该中间元素的后面，我们可以把第一个指针指向该中间元素，这样可以缩小寻找的范围；如果中间元素位于后面的
 *     递增子数组，那么它应该小于或者等于第二个指针指向的元素，此时该数组中最小的元素应该位于该中间元素的前面；
 *       接下来我们再用更新后的两个指针，重新做新一轮的查找。
 * @Author: tian
 * @UpdateDate: 2021/2/6 10:21 AM
 */
public class MinReverseArray {

    public static void main(String[] args) {
        // {1, 2, 3, 4, 5}
        int[] numbers = {3, 4, 5, 1, 2};
        System.out.println(findMin(numbers));
    }

    public static int findMin(int[] numbers) {
        //判断输入是否合法
        if(numbers == null || numbers.length == 0) {
            throw new RuntimeException("Invalid input.");
        }

        //开始处理的第一个位置
        int left = 0;
        //开始处理的最后一个位置
        int right = numbers.length - 1;
        //设置初始值
        int mid = left;

        //确保left在前一个排好序的部分，right在排好序的后一个部分
        while(numbers[left] >= numbers[right]) {
            // 当处理范围只有两个数据时，返回后一个结果，
            // 因为numbers[left] >= numbers[right] 总是成立，后一个结果对应的是最小的值
            if(right - left == 1) {
                return numbers[right];
            }

            //取中间的位置
            mid = left + (right - left) / 2;

            //如果三个数都相等，则需要进行顺序处理从头到尾找最小的值
            if(numbers[mid] == numbers[left] && numbers[mid] == numbers[right]) {
                return minInOrder(numbers, left, right);
            }

            // 如果中间位置对应的值在前一个排好序的部分，将left设置为新的处理位置
            if(numbers[mid] >= numbers[left]) {
                left = mid;
            }
            //如果中间位置对应的值在后一个排好序的部分，将right设置为新的处理位置
            else if(numbers[mid] <= numbers[right]) {
                right = mid;
            }
        }
        //返回最终的处理结果
        return numbers[mid];
    }

    /**
     * 找数组中的最小值
     * @param numbers 数组
     * @param left    数组的起始位置
     * @param right   数组的结束位置
     * @return 找到的最小的数
     */
    private static int minInOrder(int[] numbers, int left, int right) {
        int result = numbers[left];
        for (int i = left + 1; i <= right; i++) {
            if(result > numbers[i])  {
                result = numbers[i];
            }
        }
        return result;
    }
}
