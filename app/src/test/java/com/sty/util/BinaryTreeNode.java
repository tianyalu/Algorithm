package com.sty.util;

/**
 * 二叉树类节点
 * @Author: tian
 * @UpdateDate: 2021/2/5 9:54 AM
 */
public class BinaryTreeNode {
    public int value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(int value) {
        this(value, null, null);
    }

    public BinaryTreeNode(int value, BinaryTreeNode left, BinaryTreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
