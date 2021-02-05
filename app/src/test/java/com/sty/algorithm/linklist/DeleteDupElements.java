package com.sty.algorithm.linklist;

import com.sty.util.LinkListUtils;
import com.sty.util.ListNode;

/**
 * 题目：
 *   给定一个排序链表，删除所有重复的元素，每个元素只留下一个。
 *   Given a sorted linked list, delete all duplicates such that each element appear only once.
 *   For example, Given 1->1->2, return 1->2. Given 1->1->2->3->3, return 1->2->3.
 * 算法思路：
 *   遍历之，遇到当前节点和下一节点的值相同时，删除下一节点，并将当前节点`next`指向下一个节点的`next`，
 *   当前节点首先保持不变，知道相邻节点的值不等时才移动到下一节点。
 *
 * @Author: tian
 * @UpdateDate: 2020/12/18 10:52 AM
 */
public class DeleteDupElements {
    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3, 3};
        ListNode head = LinkListUtils.createLinkList(arr);
        LinkListUtils.printLinkList(head);
        deleteDupElements(head);
        LinkListUtils.printLinkList(head);
    }

    public static ListNode deleteDupElements(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            while (curr.next != null && curr.val == curr.next.val) {
                curr.next = curr.next.next;
            }
            curr = curr.next;
        }
        return head;
    }

}
