package com.sty.foroffer.tree;

import com.sty.util.BinaryTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树中和为某一值的路径
 *  ①题目：
 *      输入一棵二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。从树的根节点开始往下一直到叶子结点所
 *    经过的结点形成一条路径。
 *
 *  ②算法思路：
 *      由于路径是从根结点触发到叶结点，也就是说路径总是以根结点为起始点，因此我们首先需要遍历根结点。在树的前序、中序
 *    和后序三种遍历方式中，只有前序遍历是首先访问根结点的。
 *      当用前序遍历的方式访问到某一结点时，我们把该结点添加到路径上，并累加该结点的值，如果该结点为叶结点并且路径中
 *    结点的值的和刚好等于输入的整数，则当前的路径符号要求，我们就把它打印出来。如果当前结点不是叶结点，则继续
 *    访问它的子结点，当前结点访问结束后，递归函数自动回到它的父节点。因此我们在函数退出之前要在路径上删除当前结点
 *    并减去当前结点的值，以确保返回父结点时路径刚好是从根结点到父结点的路径。
 *      不难看出保存路径的数据结构实际上是一个枝，因为路径要与递归调用状态一致，二递归调用的本质就是一个压栈和出栈的过程。
 *
 * @Author: tian
 * @UpdateDate: 2021/2/24 11:52 AM
 */
public class FindSumPath {
    /**
     *        10
     *       ↙ ↘
     *      5   12
     *     ↙ ↘
     *    4   7
     * @param args
     */
    public static void main(String[] args) {
        int[] preOrder = {10, 5, 4, 7, 12};
        int[] inOrder = {4, 5, 7, 10, 12};
        BinaryTreeNode root = RecreateBinaryTree.construct(preOrder, inOrder);
        ArrayList<ArrayList<Integer>> paths = findPath(root, 22);
        for (ArrayList<Integer> path : paths) {
            for (Integer integer : path) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }

    /**
     * 输入一棵二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径
     * @param root 树的根结点
     * @param expectedSum 要求的路径和
     */
    public static ArrayList<ArrayList<Integer>> findPath(BinaryTreeNode root, int expectedSum) {
        //创建一个链表，用于存放根结点到当前处理结点的所经过的结点
        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        //如果根结点不为空，就调用辅助处理方法
        if(root != null) {
            ArrayList<Integer> path = new ArrayList<>();
            findPath(paths, path, root, expectedSum);
        }
        return paths;
    }

    private static void findPath(ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> path, BinaryTreeNode root, int expectedSum) {
        path.add(root.value);
        if(root.left == null && root.right == null) {
            if(expectedSum == root.value) {
                paths.add(path);
            }
            return;
        }
        ArrayList<Integer> path2 = new ArrayList<>();
        path2.addAll(path);
        if(root.left != null) {
            findPath(paths, path, root.left, expectedSum - root.value);
        }
        if(root.right != null) {
            findPath(paths, path2, root.right, expectedSum - root.value);
        }
    }
}
