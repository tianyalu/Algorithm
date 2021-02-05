package com.sty.algorithm.linklist;

import com.sty.util.ListNode;

/**
 * 链表反转
 * 参考：https://www.jianshu.com/p/a29834955ffb
 * @Author: tian
 * @Date: 2020/9/19 9:06
 */
public class ReverseLinkedList {
    public static void main(String[] args) {
        ListNode header = createLinkedList();
        //printList(header);

        //printList(reverseLinkedList1(header));

        //printList(reverseLinkedList2(header));

        //printList(reverseBetweenMn(header, 2, 4));

        printList(reverseKGroup(header, 3));
    }


    /**
     * 迭代反转
     *   每次循环只需要做三件事：
     *     1.把当前节点的next指针指向前驱节点
     *     2.把前驱节点的指针指向当前节点
     *     3.把当前节点的指针指向下一个节点
     * @param header
     * @return
     */
    public static ListNode reverseLinkedList1(ListNode header) {
        ListNode prev = null;
        ListNode cur = header;
        //用temp暂存下一个节点
        ListNode temp;
        while (cur != null) {
            //获取下一个节点
            temp = cur.next;
            //把当前节点的next指针指向其前驱节点
            cur.next = prev;
            //把前驱节点的指针指向当前节点
            prev = cur;
            //把当前节点指针指向其下一个节点
            cur = temp;
        }
        // 注意最后返回的是prev
        return prev;
    }

    /**
     * 递归反转
     * @param header
     * @return
     */
    public static ListNode reverseLinkedList2(ListNode header) {
        //递归终止条件是当前节点为空，或者当前节点的下一节点为空
        //毫无疑问，在这道题中返回的就是单链表的尾结点
        if(header == null || header.next == null) {
            return header;
        }
        //将当前节点之后的子链表反转任务交给子过程处理，不要陷入细节
        //只需要知道子过程可以完成子链表的反转就够了
        ListNode p = reverseLinkedList2(header.next);
        //经过上面的反转，子链表已经反转完成，接下来就是要处理子链表和当前节点的关系
        header.next.next = header;
        //防止链表循环，需将head.next置为空
        header.next = null;
        //每一层递归函数返回的都是p，也就是初始链表的尾结点
        return p;
    }

    /**
     * LeetCode 92题
     * 题目描述：
     *   反转从位置m到n的链表，请使用一趟扫描完成反转。
     * 说明：
     *   1 <= m <= n <= 链表长度
     * 示例：
     *   输入：1->2->3->4->5->null, m = 2, n = 4
     *   输出：1->4->3->3->5->null
     *
     * 思路：
     *   1.给链表添加虚拟头节点dummy，这样就不需要单独考虑头节点了，可以省去很多麻烦
     *   2.找到需要操作的链表区间，区间起始节点用start表示，结束节点用end表示
     *   3.对区间上的链表进行操作
     *   4.将操作后的链表重新接回原来的链表，这里需要另外两个变量，前驱节点prev和后继节点successor
     * @param header
     * @return
     */
    public static ListNode reverseBetweenMn(ListNode header,int m, int n) {
        //添加虚拟头节点
        ListNode dummy = new ListNode();
        dummy.next = header;
        //前驱节点
        ListNode prev = dummy;
        //反转区间开始节点
        ListNode start = header;
        //反转区间结束节点
        ListNode end = header;
        //后继节点
        ListNode successor;
        //第一个for循环找到前驱节点和区间开始节点
        for (int i = 1; i < m; i++) {
            prev = prev.next;
            start = start.next;
            end = end.next;
        }
        //第二个for循环找到区间结束节点
        for (int i = m; i < n; i++) {
            end = end.next;
        }
        //找到后继节点
        successor = end.next;
        //至关重要的一步，将反转区间的最后一个节点和原链表断开，否则会造成死循环
        end.next = null;
        //将反转后的头节点连在前驱节点后面，这里的reverseLinkedList1()方法直接使用上面的即可
        prev.next = reverseLinkedList1(start);
        //反转后，start变成尾结点，把它和后继节点连接起来
        start.next = successor;
        return dummy.next;
    }

    /**
     * LeetCode 25题 k个一组反转链表
     * 题目描述：
     *   给你一个链表，每k个节点一组进行翻转，请你返回翻转后的链表
     *   k是一个正整数，它的值小于或等于链表的长度。
     *   如果节点总数不是k的整数倍，那么请将最后剩余的节点保持原有顺序
     * 示例：
     *   给定这个链表：1->2->3->4->5
     *   当k=2时，应当返回：2->1->4->3->5
     *   当k=3时，应当返回：3->2->1->4->5
     * 说明：
     *   你的算法只能使用常数的额外空间
     *   你不能只是单纯地改变节点内部的值，而是需要实际地进行节点交换
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        //添加虚拟头节点
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode prev = dummy;
        ListNode start = head;
        ListNode end = head;
        ListNode successor;
        while (end != null) {
            //循环k次，确定待反转区间
            for(int i = 1; i < k && end != null; i++) {
                end = end.next;
            }
            //end节点为空的说明待反转节点不足k个，直接返回
            if(end == null) {
                break;
            }
            successor = end.next;
            //至关重要的一步，将待反转链表的最后一个节点和后续链表断开
            end.next = null;
            //进行翻转链表操作，并将翻转后的链表头节点连接在prev之后
            //这里的reverseLinkedList1()方法直接使用上面的即可
            prev.next = reverseLinkedList1(start);
            //start在翻转后会变成尾结点，将其与successor连接起来
            start.next = successor;
            //以下对各个变量重新赋值，然后进行下一次循环
            prev = start;
            start = successor;
            end = successor;
        }
        return dummy.next;
    }

    //头插法创建链表
    private static ListNode createLinkedList() {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        ListNode header = null;
        for (int i = arr.length - 1; i >= 0; i--) {
            ListNode node = new ListNode();
            node.val = arr[i];
            node.next = header;
            header = node;
        }
        return header;
    }

    private static void printList(ListNode header) {
        ListNode tempHeader = header;
        while (tempHeader != null) {
            System.out.println(tempHeader.val);
            tempHeader = tempHeader.next;
        }
    }
}
