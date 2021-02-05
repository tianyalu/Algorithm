package com.sty.foroffer.linklist;

import com.sty.util.LinkListUtils;
import com.sty.util.ListNode;

import java.util.Stack;

/**
 *  ①题目：
 *      输入个链表的头结点，从尾到头反过来打印出每个节点的值。
 *
 *  ②算法思路：
 *      使用栈的方式进行：将链表从头到尾压入栈内，出栈的过程就对应着从尾到头；
 *      也可以递归打印。
 * @Author: tian
 * @UpdateDate: 2021/2/5 9:33 AM
 */
public class ReversePrintLinkList {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ListNode header = LinkListUtils.createLinkList(arr);
        LinkListUtils.printLinkList(header);
        reversePrintByStack(header);
        reversePrintByRecursion(header);
    }

    /**
     * 输入个链表的头结点，从尾到头反过来打印出每个节点的值
     * 使用栈的方式
     * @param header 链表头结点
     */
    public static void reversePrintByStack(ListNode header) {
        Stack<ListNode> stack = new Stack<>();
        ListNode cur = header;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (!stack.isEmpty()) {
            cur = stack.pop();
            System.out.print(cur.val + " ");
        }
        System.out.println();
    }

    /**
     * 输入个链表的头结点，从尾到头反过来打印出每个节点的值
     * 递归的方式
     * @param header 链表头结点
     */
    public static void reversePrintByRecursion(ListNode header) {
        if(header != null) {
            reversePrintByRecursion(header.next);
            System.out.print(header.val + " ");
        }
    }
}
