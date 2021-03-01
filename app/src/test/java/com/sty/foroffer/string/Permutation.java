package com.sty.foroffer.string;

/**
 * 字符串的排列
 *  ①题目：
 *      输入一个字符串，打印出该字符串中字符的所有排列。例如输入字符串`abc`，则打印出由字符`a`、`b`、`c`所能排列
 *    出来的所有字符串`abc`、`acb`、`bac`、`bca`、`cab`和`cba`。
 *
 * ②算法思路：
 *      把一个字符串看成由两部分组成：第一部分为它的第一个字符，第二部分是后面的所有字符。
 *      我们求整个字符串的排列，可以看成两步：首先求所有可能出现在第一个位置的字符，即把第一个字符和后面所有的字符交换。
 *    这时候我们仍把后面的所有字符分成两部分：后面字符的第一个字符以及这个字符之后的所有字符。这其实是很典型的递归思路。
 * @Author: tian
 * @UpdateDate: 2021/3/1 10:04 AM
 */
public class Permutation {
    public static void main(String[] args) {
        char[] chars = {'a', 'b', 'c'};
        permutation(chars);
    }

    /**
     * 输入一个字符串，打印出该字符串中字符的所有排列
     * @param chars 待排列的字符数组
     */
    public static void permutation(char[] chars) {
        //输入校验
        if(chars == null || chars.length == 0) {
            return;
        }
        //进行排列操作
        permutation(chars, 0);
        System.out.println();
    }

    /**
     * 求字符数组的排列
     * @param chars 待排列的字符串
     * @param begin 当前处理的位置
     */
    private static void permutation(char[] chars, int begin) {
        //如果是最后一个元素了，就输出排列结果
        if(chars.length - 1 == begin) {
            System.out.print(new String(chars) + " ");
        }else {
            char tmp;
            //对当前还未处理的字符串进行处理，每个字符都可以作为当前处理位置的元素
            for (int i = begin; i < chars.length; i++) {
                //下面是交换元素的位置
                tmp = chars[begin];
                chars[begin] = chars[i];
                chars[i] = tmp;

                //处理下一个位置
                permutation(chars, begin + 1);

                //处理完之后记得交换回来，避免影响后面的排序
                tmp = chars[begin];
                chars[begin] = chars[i];
                chars[i] = tmp;
            }
        }
    }
}
