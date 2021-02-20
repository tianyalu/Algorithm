package com.sty.foroffer.tree;

import com.sty.util.BinaryTreeNode;
import com.sty.util.BinaryTreeUtils;

/**
 *  ①题目：
 *      输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字，
 *    例如：前序遍历`{1, 2, 4, 7, 3, 5, 6, 8}`和中序遍历`{4, 7, 2, 1, 5, 3, 8, 6}`，重建二叉树并输出它的头结点。
 *
 *  ②算法思路：
 *      由前序遍历的第一个节点可知根节点，根据根节点，可以将中序遍历划分成左右子树，在前序遍历中找出对应的左右子树，
 *    其第一个节点便是根节点的 左右子节点。安装上述方式递归便可重建二叉树。
 * @Author: tian
 * @UpdateDate: 2021/2/5 9:53 AM
 */
public class RecreateBinaryTree {
    /**
     *        1
     *       ↙ ↘
     *      2   3
     *     ↙   ↙ ↘
     *    4   5   6
     *     ↘     ↙
     *      7   8
     * @param args
     */
    public static void main(String[] args) {
        int[] preOrder = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] inOrder = {4, 7, 2, 1, 5, 3, 8, 6};
        BinaryTreeNode node = construct(preOrder, inOrder);
        BinaryTreeUtils.printTreePreOrder(node);
        System.out.println();
        BinaryTreeUtils.printTreeInOrder(node);
        System.out.println();
    }

    /**
     * 根据前序遍历和中序遍历的结果重建该二叉树
     * @param preOrder 前序遍历
     * @param inOrder 中序遍历
     * @return
     */
    public static BinaryTreeNode construct(int[] preOrder, int[] inOrder) {
        //输入的合法性判断，两个数组都不能为空，并且都有数据，而且数据长度相同
        if(preOrder == null || inOrder == null || preOrder.length != inOrder.length || inOrder.length < 1) {
            return null;
        }
        return construct(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1);
    }

    /**
     * 根据前序遍历和中序遍历的结果重建该二叉树
     * @param preOrder 前序遍历
     * @param ps       前序遍历的开始位置
     * @param pe       前序遍历的结束位置
     * @param inOrder  中序遍历
     * @param is       中序遍历的开始位置
     * @param ie       中序遍历的结束位置
     * @return 树的根节点
     */
    public static BinaryTreeNode construct(int[] preOrder, int ps, int pe, int[] inOrder, int is, int ie) {
        //开始位置大于结束位置，说明已经没有需要处理的元素了
        if(ps > pe) {
            return null;
        }
        //取前序遍历的第一个数字，就是当前的根节点
        int value = preOrder[ps];
        int index = is;
        //在中序遍历的数组中找根节点的位置
        while(index <= ie && inOrder[index] != value) {
            index++;
        }
        //如果在整个中序遍历的数组中没有找到，说明输入的参数是不合法的，抛出异常
        if(index > ie) {
            throw new RuntimeException("Invalid input");
        }

        //创建当前的根节点，并且为节点赋值
        BinaryTreeNode node = new BinaryTreeNode(value);

        //递归构建当前根节点的左子树，左子树的元素个数：index-is 个
        //左子树对应的前序遍历的位置在[ps+1, ps+index-is]  <-- ps+1 + index-is - 1
        //左子树对应的中序遍历的位置在[is, index-1]
        node.left = construct(preOrder, ps+1, ps+index-is, inOrder, is, index-1);
        //递归构建当前根节点的右子树，右子树的元素个数：ie-index 个
        //右子树对应的前序遍历的位置在[ps+index-is+1, pe]
        //右子树对应的中序遍历的位置在[index+1, ie]
        node.right = construct(preOrder, ps+index-is+1, pe, inOrder, index+1, ie);
        //返回创建的根节点
        return node;
    }
}
