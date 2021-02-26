package com.sty.util;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/5 11:16 AM
 */
public class BinaryTreeUtils {
    /**
     * 先根遍历
     * @param node
     */
    public static void printTreePreOrder(BinaryTreeNode node) {
        if(node != null) {
            System.out.print(node.value + " ");
            printTreePreOrder(node.left);
            printTreePreOrder(node.right);
        }
    }

    /**
     * 中根遍历
     * @param node
     */
    public static void printTreeInOrder(BinaryTreeNode node) {
        if(node != null) {
            printTreeInOrder(node.left);
            System.out.print(node.value + " ");
            printTreeInOrder(node.right);
        }
    }

    public static void printTreeByDoubleLinkList(BinaryTreeNode node) {
        BinaryTreeNode cur = node;
        if(cur == null) {
            return;
        }
        System.out.print(cur.value + " ");
        while (cur.right != null) {
            System.out.print(cur.right.value + " ");
            cur = cur.right;
        }
        System.out.println();
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.left;
        }
        System.out.println();
    }
}
