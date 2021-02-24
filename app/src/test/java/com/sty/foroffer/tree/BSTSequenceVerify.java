package com.sty.foroffer.tree;

/**
 *  二叉搜索树的后序遍历序列
 *  ①题目：
 *      输入一个整数数组，判断该数组是不是某二叉搜索时的后序遍历的结果。如果是则返回`true`，否则返回`false`。
 *    假设输入的数组任意两个数字都互不相同。
 *
 *  ②算法思路：
 *      在后序遍历得到的序列中，最后一个数字是树的根节点的值。数组中前面的数字可以分为两部分：第一部分是左子树
 *    结点的值，它们都比根结点的值小；第二部分是右子树结点的值，它们都比根结点的值大。
 *
 * @Author: tian
 * @UpdateDate: 2021/2/24 10:04 AM
 */
public class BSTSequenceVerify {
    public static void main(String[] args) {
        int[] sequence = {4, 8, 6, 12, 16, 14, 10};
        System.out.println(verifySequenceOfBST(sequence)); //true
    }

    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索时的后序遍历的结果
     * @param sequence 某二叉搜索树后序遍历的结果
     * @return
     */
    public static boolean verifySequenceOfBST(int[] sequence) {
        //输入的数组不能为空，并且有数据
        if(sequence == null || sequence.length == 0) {
            return false;
        }
        //有数据，就调用辅助方法
        return verifySequenceOfBST(sequence, 0, sequence.length - 1);
    }

    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索时的后序遍历的结果
     * @param sequence 某二叉搜索树后序遍历的结果
     * @param start 处理的开始位置
     * @param end 处理的结束位置
     * @return
     */
    public static boolean verifySequenceOfBST(int[] sequence, int start, int end) {
        //如果对应要处理的数据只有一个或者已经没有数据要处理（start>end）就返回true
        if(start > end) {
            return true;
        }
        //从左向右找第一个不小于根节点（sequence[end]）的元素位置
        int index = start;
        while (index < end && sequence[index] < sequence[end]) {
            index++;
        }
        //执行到此处时[start, index - 1]的元素都是小于根节点 sequence[end] 的
        //[start, index - 1] 可以看做是根节点的左子树

        //right用于记录第一个大于根节点的元素的位置
        int right = index;
        //接下来要保证[index, end - 1]的所有元素都是大于根结点的值
        //因为[index, end - 1]是根结点的右子树
        //从第一个不小于根结点的元素开始，找到第一个不大于根结点的元素
        while(index < end && sequence[index] > sequence[end]) {
            index++;
        }
        //如果[index, end - 1]中有小于等于根结点的元素
        //不符合二叉搜索树的定义，返回false
        if(index != end) {
            return false;
        }

        //执行到此处说明到目前为止还是合法的
        //[start, index - 1]为根结点左子树的位置
        //[index, end - 1]为根结点右子树的位置
        index = right;
        return verifySequenceOfBST(sequence, start, index - 1) && verifySequenceOfBST(sequence, index, end - 1);
    }
}
