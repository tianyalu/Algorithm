package com.sty.util;

/**
 * @Author: tian
 * @UpdateDate: 2020/12/21 11:20 AM
 */
public class LinkListUtils {

    /**
     * 根据输入的数组用尾插法创建链表
     * @param arr 输入的数组
     * @return
     */
    public static ListNode createLinkList(int[] arr) {
        if(arr == null || arr.length == 0) {
            return null;
        }
        ListNode cur = new ListNode(arr[0]);
        ListNode head = cur;
        for (int i = 1; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            cur.next = node;
            cur = node;
        }
        return head;
    }

    /**
     * 打印链表
     * @param head
     */
    public static void printLinkList(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static ListNode getListNodeByValue(ListNode head, int val) {
        ListNode cur = head;
        while (cur != null) {
            if(cur.val == val) {
                return cur;
            }else {
                cur = cur.next;
            }
        }
        return cur;
    }
}
