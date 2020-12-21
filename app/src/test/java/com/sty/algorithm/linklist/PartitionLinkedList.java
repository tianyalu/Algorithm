package com.sty.algorithm.linklist;

import com.sty.algorithm.linklist.util.LinkListUtils;
import com.sty.algorithm.linklist.util.ListNode;

/**
 * 题目：
 *     给定一个单链表和数值`x`，划分链表使得所有小于`x`的节点排在大于等于`x`的节点之前。你应该保留两部分内链表节点原有的相对顺序。
 *   Given a linked list and a value x, partition it such that all nodes less that x come before nodes greater than or equal to x.
 *   You should preserve the original relative order of the nodes in each of the two partitions.
 *   For example, Given 1->4->3->2->5->2, return 1->2->2->4->3->5.
 *
 * 算法思路：
 *     依据题意，是要根据值`x`对链表进行分割操作，具体是指将所有小于`x`的节点放到不小于`x`的节点之前，
 *   但它和快排分割不同的是只需要将小于`x`的节点放到前面，而并不要求对元素进行排序。
 *     这个分割使用两路指针即可解决：左边指针指向小于`x`的节点，右边指针指向不小于`x`的节点，
 *   由于左右头节点不确定，我们可以使用两个`dummy`节点。
 * @Author: tian
 * @UpdateDate: 2020/12/21 3:56 PM
 */
public class PartitionLinkedList {
    public static void main(String[] args) {
        int[] arr = {1, 4, 3, 2, 5, 2};
        ListNode head = LinkListUtils.createLinkList(arr);
        ListNode newHead = partition(head, 3);
        LinkListUtils.printLinkList(newHead);
    }

    public static ListNode partition(ListNode head, int x) {
        ListNode leftDummy = new ListNode(0);
        ListNode leftCurr = leftDummy;
        ListNode rightDummy = new ListNode(0);
        ListNode rightCurr = rightDummy;

        ListNode runner = head;
        while (runner != null) {
            if(runner.val < x) {
                leftCurr.next = runner;
                leftCurr = runner;
            }else {
                rightCurr.next = runner;
                rightCurr = runner;
            }
            runner = runner.next;
        }

        //cut off ListNode after rightCurr to avoid cylic
        rightCurr.next = null;
        leftCurr.next = rightDummy.next;

        return leftDummy.next;
    }
}
