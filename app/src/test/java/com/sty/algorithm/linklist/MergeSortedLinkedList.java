package com.sty.algorithm.linklist;

import com.sty.util.LinkListUtils;
import com.sty.util.ListNode;

/**
 * 题目：将两个排序链表合并成一个新的排序链表。
 *     Merge two sorted (ascending) linked lists and return it as a new sorted list.
 *     The new sorted list should be made by splicing together the nodes of the two lists and sorted in ascending order.
 *     Example: Given 1->3->8->11->15->null, 2->null, return 1->2->3->8->11->15->null.
 * 思路：
 *     只需要从头开始比较已排序的两个链表，新链表指针每次指向值小的节点，依次比较下去，最后，当其中一个链表到达了末尾，
 *     我们只需要把新链表指针指向另一个没有到末尾的链表此时的指针即可。
 * @Author: tian
 * @UpdateDate: 2020/12/24 9:39 AM
 */
public class MergeSortedLinkedList {
    public static void main(String[] args) {
        int[] arr1 = {1, 3, 8, 11, 15};
        int[] arr2 = {2};
        ListNode h1 = LinkListUtils.createLinkList(arr1);
        ListNode h2 = LinkListUtils.createLinkList(arr2);
        //LinkListUtils.printLinkList(mergeLinkedList(h1, h2));

        LinkListUtils.printLinkList(mergeByRecursion(h1, h2));
    }

    public static ListNode mergeLinkedList(ListNode h1, ListNode h2) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;

        while (h1 != null && h2 != null) {
            if(h1.val < h2.val) {
                cur.next = h1;
                h1 = h1.next;
            }else {
                cur.next = h2;
                h2 = h2.next;
            }
            cur = cur.next;
        }

        //link to non-null list
        if(h1 == null) {
            cur.next = h2;
        }else {
            cur.next = h1;
        }

        return dummy.next;
    }

    /**
     * 递归实现合并两个排序的链表
     * @param h1 排序链表1头结点
     * @param h2 排序链表2头结点
     * @return 合并后的排序链表头结点
     */
    public static ListNode mergeByRecursion(ListNode h1, ListNode h2) {
        if(h1 == null) {  //第一个链表为空，返回第二个链表头结点
            return h2;
        }else if(h2 == null) {  //第二个链表为空，返回第二个链表头结点
            return h1;
        }

        //记录两个链表中头部较小的结点
        ListNode tmp = h1;
        if(tmp.val < h2.val) {
            //如果第一个链表的头结点小，就递归处理第一个链表的下一个结点和第二个链表的头结点
            tmp.next = mergeByRecursion(h1.next, h2);
        }else {
            //如果第二个链表的头结点小，就递归处理第一个链表的头结点和第二个链表的头结点的下一个结点
            tmp = h2;
            tmp.next = mergeByRecursion(h1, h2.next);
        }

        return tmp;
    }
}
