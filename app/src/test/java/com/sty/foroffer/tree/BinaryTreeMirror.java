package com.sty.foroffer.tree;

import com.sty.util.BinaryTreeNode;
import com.sty.util.BinaryTreeUtils;

/**
 *  ①题目：
 *      请完成一个函数，输入一个二叉树，该函数输出它的镜像。
 *
 *  ②算法思路：
 *      先前序遍历这棵树的每个结点，如果遍历到的结点有子结点，就交换它的两个子结点；当交换完所有非叶子结点的左右
 *    子结点之后，就得到了树的镜像。
 *
 * @Author: tian
 * @UpdateDate: 2021/2/22 9:45 AM
 */
public class BinaryTreeMirror {
    /**
     *        1             1
     *       ↙ ↘           ↙ ↘
     *      2   3         3   2
     *     ↙   ↙ ↘  -->  ↙ ↘   ↘
     *    4   5   6     6   5   4
     *     ↘     ↙       ↘     ↙
     *      7   8          8  7
     * @param args
     */
    public static void main(String[] args) {
        int[] preOrder = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] inOrder = {4, 7, 2, 1, 5, 3, 8, 6};
        BinaryTreeNode node = RecreateBinaryTree.construct(preOrder, inOrder);
        mirror(node);
        BinaryTreeUtils.printTreePreOrder(node); // 1 3 6 8 5 2 4 7
        System.out.println();
        BinaryTreeUtils.printTreeInOrder(node); // 6 8 3 5 1 2 7 4
        System.out.println();
    }

    /**
     * 输出输入二叉树的镜像
     * @param node 二叉树的根结点
     */
    public static void mirror(BinaryTreeNode node) {
        //如果当前的结点不为空则进程操作
        if(node != null) {
            //交换结点的左右两棵子树
            BinaryTreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            //对结点的左右两个子树进行递归处理
            mirror(node.left);
            mirror(node.right);
        }
    }
}
