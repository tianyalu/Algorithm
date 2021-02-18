package com.sty.foroffer.linklist;

import com.sty.util.LinkListUtils;
import com.sty.util.ListNode;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/18 9:54 AM
 */
public class DeleteLinkListNode {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        ListNode head = LinkListUtils.createLinkList(arr);
        LinkListUtils.printLinkList(head);
        ListNode targetNode = LinkListUtils.getListNodeByValue(head, 4);
        LinkListUtils.printLinkList(deleteLinkListNode(head, targetNode));
    }

    /**
     * 给定单向链表的头指针和一个节点指针，定义一个函数在O(1)时间删除该节点
     * 【注意1：这个方法和文本上的不一样，书上的没有返回值，这个因为Java引用传递的原因，如果删除的节点是头结点，
     *      如果不采用返回值的方式，那么头结点永远删除不了】
     * 【注意2：输入的待删除节点必须是链表中的节点，否则会引起错误，这个条件由用户进行保证】
     * @param head 链表的头结点
     * @param targetNode 待删除的节点
     * @return 删除后的头结点
     */
    public static ListNode deleteLinkListNode(ListNode head, ListNode targetNode) {
        //如果输入的参数有空值就返回链表头结点
        if(head == null || targetNode == null) {
            return head;
        }

        //如果删除的是头结点，直接返回头结点的下一个节点
        if(head == targetNode) {
            return head.next;
        }

        //下面的情况链表至少有两个结点
        //在多个节点的情况下，如果删除的是最后一个元素
        if(targetNode.next == null) {
            //找待删除元素的前驱
            ListNode tmp = head;
            while (tmp.next != targetNode) {
                tmp = tmp.next;
            }
            //删除目标节点
            tmp.next = null;
        }else { //在多个节点的情况下，如果删除的是某个中间节点
            //将下一个结点的值输入当前待删除的结点
            targetNode.val = targetNode.next.val;
            //待删除的结点的下一个指向原先待删除节点的下下个节点，即将待删除的下一个结点删除
            targetNode.next = targetNode.next.next;
        }

        return head;
    }
}
