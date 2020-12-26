package com.sty.algorithm.linklist;

import com.sty.algorithm.linklist.util.LinkListUtils;
import com.sty.algorithm.linklist.util.ListNode;

/**
 * 题目：删除链表中等于指定值`val`的所有节点。
 *   Remove All elements from a linked list of integers that have value val.
 *   Example: Given 1->2->3->3->4->5->3, val = 3, you should return the list as 1->2->4->5.
 * 算法思路：
 *   删除链表中指定值，找到其前一个节点即可，将`next`指向下一个节点即可。
 * @Author: tian
 * @UpdateDate: 2020/12/26 10:28 AM
 */
public class RemoveTargetElements {
    public static void main(String[] args) {
        int[] arr = {3, 3, 1, 2, 3, 3, 4, 5, 3};
        ListNode head = LinkListUtils.createLinkList(arr);
        LinkListUtils.printLinkList(head);
        LinkListUtils.printLinkList(removeElements(head, 3));
    }

    public static ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr = dummy;

        while (curr.next != null) {
            if(curr.next.val == val) {
                curr.next = curr.next.next;
            }else {
                curr = curr.next;
            }
        }
        return dummy.next;
    }
}
