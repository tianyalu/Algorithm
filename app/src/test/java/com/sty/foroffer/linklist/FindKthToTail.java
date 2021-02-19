package com.sty.foroffer.linklist;

import com.sty.util.LinkListUtils;
import com.sty.util.ListNode;

/**
 *  ①题目：
 *      输入一个链表，输出该链表中倒数第`k`个结点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒
 *    数第1个结点。例如一个链表有6个结点，从头结点开始它们的值依次是1、2、3、4、5、6，那么这个链表的倒数第3个结点是值为4的结点。
 *
 *  ②算法思路：
 *      为了实现只遍历链表一次就能找到倒数第`k`个结点，我们可以定义两个指针，第一个指针从链表的头指针开始遍历
 *    向前走`k-1`步，第二个指针保持不动；从第`k`步开始，第二个指针也开始从链表的头指针开始遍历。由于两个指针的
 *    距离保持在`k-1`，当第一个（走在前面的）指针到达链表的尾节点时，第二个指针（走在后面的）指针正好是倒数第`k`个结点。
 *
 * @Author: tian
 * @UpdateDate: 2021/2/19 9:41 AM
 */
public class FindKthToTail {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        ListNode head = LinkListUtils.createLinkList(arr);
        ListNode kNode = findKthToTail(head, 3);
        if(kNode == null) {
            System.out.println("未找到该结点");
        }else {
            System.out.println(kNode.val);
        }
    }

    /**
     * 输入一个链表，输出该链表中倒数第`k`个结点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒
     * 数第1个结点。例如一个链表有6个结点，从头结点开始它们的值依次是1、2、3、4、5、6，那么这个链表的倒数第3个结点是值为4的结点。
     *
     * @param head 链表的头结点
     * @param k 倒数第几个结点
     * @return 倒数第k个结点
     */
    public static ListNode findKthToTail(ListNode head, int k) {
        //输入的链表不能为空，并且k要大于0
        if(k < 1 || head == null) {
            return null;
        }

        //声明两个指针指向头结点
        ListNode p1 = head;
        ListNode p2 = head;

        //倒数第k个结点和倒数第一个结点相隔k-1个位置，pointer先走k-1个位置
        for (int i = 1; i < k; i++) {
            //说明还有结点
            if(p1.next != null) {
                p1 = p1.next;
            }else { //已经没有结点了，但是i还是没有到达k-1，说明k太大，链表中没有那么多元素
                return null;
            }
        }

        //p1还没有走到链表的末尾，那么p1和p2一起走
        //当p1走到最后一个节点即 p1.next == null时，p2就是倒数第k个结点
        while(p1.next != null) {
            p2 = p2.next;
            p1 = p1.next;
        }

        return p2;
    }
}
