package com.sty.foroffer.tree;

import com.sty.util.BinaryTreeNode;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/20 10:27 AM
 */
public class SubBinaryTree {

    /**
     *        1               3
     *       ↙ ↘             ↙ ↘
     *      2   3           5   6
     *     ↙   ↙ ↘             ↙
     *    4   5   6           8
     *     ↘     ↙
     *      7   8
     * @param args
     */
    public static void main(String[] args) {
        int[] preOrder1 = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] inOrder1 = {4, 7, 2, 1, 5, 3, 8, 6};
        int[] preOrder2 = {3, 5, 6, 8};
        int[] inOrder2 = {5, 3, 8, 6};
        BinaryTreeNode root1 = RecreateBinaryTree.construct(preOrder1, inOrder1);
        BinaryTreeNode root2 = RecreateBinaryTree.construct(preOrder2, inOrder2);
        System.out.println(hasSubTree(root1, root2));
    }

    /**
     * 输入两棵二叉树A和B，判断B是不是A的子结构
     * @param root1 数A的根结点
     * @param root2 数B的根结点
     * @return 树B是否是树A的子结构
     */
    public static boolean hasSubTree(BinaryTreeNode root1, BinaryTreeNode root2) {
        //只要两个对象是同一个就返回true
        if(root1 == root2) {
            return true;
        }
        //只要数B的根节点为空就返回true
        if(root2 == null) {
            return true;
        }
        //树B的根结点不为空，如果树A的根结点为空就返回false
        if(root1 == null) {
            return false;
        }

        boolean result = false;
        //如果结点的值相等就调用匹配方法
        if(root1.value == root2.value) {
            result = match(root1, root2);
        }

        if(result) {  //如果匹配就直接返回结果
            return true;
        }else {  //如果不匹配就找树A的左子结点和右子结点进行判断
            return match(root1.left, root2) || match(root1.right, root2);
        }
    }

    /**
     * 判断以root1为根结点和以root2为根结点的两棵树是否完全相同
     * @param root1
     * @param root2
     * @return
     */
    public static boolean match(BinaryTreeNode root1, BinaryTreeNode root2) {
        //只要两个对象是同一个就返回true
        if(root1 == root2) {
            return true;
        }
        //只要数B的根节点为空就返回true
        if(root2 == null) {
            return true;
        }
        //树B的根结点不为空，如果树A的根结点为空就返回false
        if(root1 == null) {
            return false;
        }

        //如果两个结点的值相等，则分别判断其左子结点和右子结点
        if(root1.value == root2.value) {
            return match(root1.left, root2.left) && match(root1.right, root2.right);
        }

        //结点值不相等，返回false
        return false;
    }
}
