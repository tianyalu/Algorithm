package com.sty.foroffer.tree;

import com.sty.util.BinaryTreeNode;
import com.sty.util.BinaryTreeUtils;

/**
 * 二叉搜索树与双向链表
 *  ①题目：
 *      输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表，要求不能创建任何新的结点，只能调整树中结点指针的指向。
 *
 *  ②算法思路：
 *      在二叉树中，每个结点都有两个指向子结点的指针，在双向链表中，每个结点也有两个指针，他们分别指向前一个结点和
 *    后一个结点，由于这两种结点的结构相似，同时二叉搜索树也是一种排序的数据结构，因此理论上有可能实现二叉搜索树和
 *    排序的双向链表的转换。
 *      在搜索二叉树中，左子结点的值总是小于父结点的值，右子结点的值总是大于父结点的值。因此我们在转换成双向链表时，
 *    原先指向左子结点的指针调整为链表中指向前一个结点的指针，原先指向有子结点的指针调整为链表中指向后一个结点的指针。
 *    接下来我们考虑如何转换。
 *      由于要求转换之后的链表是排好序的，我们可以中序遍历树中的每一个结点，这是因为中序遍历算法的特点是按照从小
 *    到大的顺序遍历二叉树的每一个结点。当遍历到根结点的时候，我们把树看成三部分：根结点、左子树和右子树。
 *    根据排序链表的定义，根结点将和它的左子树的最大一个结点链接起来，同时它还将和右子树最小的结点链接起来。
 *
 * @Author: tian
 * @UpdateDate: 2021/2/26 3:52 PM
 */
public class BinarySearchTreeConvert {
    /**
     *        4
     *       ↙ ↘
     *      3   6
     *     ↙   ↙ ↘
     *    1   5   8
     *     ↘     ↙
     *      2   7
     * @param args
     */
    public static void main(String[] args) {
        int[] preOrder = {4, 3, 2, 1, 6, 5, 8, 7};
        int[] inOrder = {1, 2, 3, 4, 5, 6, 7, 8};
        BinaryTreeNode node = RecreateBinaryTree.construct(preOrder, inOrder);
        BinaryTreeNode head = convert(node);
        BinaryTreeUtils.printTreeByDoubleLinkList(head);
    }

    /**
     * 输入一棵二叉搜索树，将该二叉搜索树转换从一个排序的双向链表
     * @param root 二叉树的根结点
     * @return 双向链表的头结点
     */
    public static BinaryTreeNode convert(BinaryTreeNode root) {
        //用于保存处理过程中的双向链表的尾结点
        BinaryTreeNode[] lastNode = new BinaryTreeNode[1];
        convertNode(root, lastNode);
        //找到双向链表的头结点
        BinaryTreeNode head = lastNode[0];
        while (head != null && head.left != null) {
            head = head.left;
        }
        return head;
    }

    /**
     * 链表转换操作
     * @param node 当前的根结点
     * @param lastNode 已经处理好的双向链表的尾结点，使用一个长度为1的数组，类似C++中的二级指针
     */
    private static void convertNode(BinaryTreeNode node, BinaryTreeNode[] lastNode) {
        //结点不为空
        if(node != null) {
            //如果有左子树就优先处理左子树
            if(node.left != null) {
                convertNode(node.left, lastNode);
            }
            //将当前结点的前驱指向已经处理好的双向链表（有当前结点的左子树构成）的尾结点
            node.left = lastNode[0];
            //如果左子树转换成的双向链表不为空，设置尾结点的后继
            if(lastNode[0] != null) {
                lastNode[0].right = node;
            }
            //记录当前结点为尾结点
            lastNode[0] = node;
            //处理右子树
            if(node.right != null) {
                convertNode(node.right, lastNode);
            }
        }
    }
}
