package com.sty.foroffer.tree;

import com.sty.util.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  ①题目：
 *      从上往下打印出二叉树的每个结点，同一层的结点按照从左向右的顺序打印。
 *
 *  ②算法思路：
 *      这道题实质是考察树的遍历算法。从上到下打印二叉树的规律：每次打印一个节点的时候，如果该结点有子结点，
 *    则把该结点的子结点放到一个队列的末尾。接下来到队列的头部取出最早进入队列的结点，重复前面的打印操作，
 *    直至队列中所有的结点都被打印出来为止。
 *
 * @Author: tian
 * @UpdateDate: 2021/2/24 9:37 AM
 */
public class TraversalFromTopToBottom {
    public static void main(String[] args) {
        int[] preOrder = {8, 6, 5, 7, 10, 9, 11};
        int[] inOrder = {5, 6, 7, 8, 9, 10, 11};
        BinaryTreeNode root = RecreateBinaryTree.construct(preOrder, inOrder);
        printFromTopToBottom(root);
    }

    /**
     * 从上往下打印出二叉树的每个结点，同一层的结点按照从左往右的顺序打印
     * 例如下面的二叉树：
     *        8
     *       ↙ ↘
     *      6   10
     *     ↙ ↘  ↙ ↘
     *    5   7 9  11
     * 则依次打印出8、6、10、5、7、9、11
     * @param root 树的根节点
     */
    public static void printFromTopToBottom(BinaryTreeNode root) {
        //当结点非空时才进行操作
        if(root != null) {
            //用于存放还未遍历的元素
            Queue<BinaryTreeNode> list = new LinkedList<>();
            //将根节点入队
            list.add(root);
            //用于记录当前处理的结点
            BinaryTreeNode curNode;

            //队列非空则进行处理
            while(!list.isEmpty()) {
                //删除队首元素
                curNode = list.remove();
                //输出队首元素的值
                System.out.print(curNode.value + " ");
                //如果左子结点不为空，则左子结点入队
                if(curNode.left != null) {
                    list.add(curNode.left);
                }
                //如果右子结点不为空，则有子结点入队
                if(curNode.right != null) {
                    list.add(curNode.right);
                }
            }
        }
        System.out.println();
    }
}
