package com.sty.foroffer.linklist;

import com.sty.util.ComplexLinkListUtils;
import com.sty.util.ComplexListNode;

/**
 * 复杂链表的复制
 *  ①题目：
 *      请实现函数`ComplexListNode clone(ComplexListNode head)`，复制一个复杂链表。在复杂链表中，每个结点
 *    除了有一个`next`域指向下一个结点外，还有一个`sibling`指向链表中的任意结点或者`null`。
 *
 *  ②算法思路：
 *      在不用辅助空间的情况下实现`O(n)`的时间效率。
 *      > 1. 仍然是根据元素链表的每个结点`N`创建对应的`N'`，把`N'`链接在`N`的后面；
 *      > 2. 设置复制出来的结点的`sibling`，假设原始链表上的`N`的`sibling`指向结点`S`，那么其对应复制出来
 *           的`N'`是`N`的`next`指向的结点，同样`S'`也是`S`的`next`指向的结点；
 *      > 3. 把这个长链表拆分成两个链表，把奇数位置的结点用`next.`链接起来就是原始链表，把偶数位置的结点用`next`
 *           链接起来就是复制出来的链表。
 *
 * @Author: tian
 * @UpdateDate: 2021/2/25 9:24 AM
 */
public class CloneComplexLinkList {
    public static void main(String[] args) {
        int[] values = {1, 2, 3, 4, 5, 6};
        int[] siblingIndexArr = {5, 3, 0, -1, 2, 4};
        ComplexListNode head = ComplexLinkListUtils.createComplexLinkList(values, siblingIndexArr);
        ComplexLinkListUtils.printComplexLinkList(head);
        ComplexListNode copyHead = clone(head);
        ComplexLinkListUtils.printComplexLinkList(copyHead);
    }

    /**
     * 实现复制复杂链表的功能
     * @param head
     * @return
     */
    public static ComplexListNode clone(ComplexListNode head) {
        //如果链表为空就直接返回
        if(head == null) {
            return null;
        }

        //先复制结点
        cloneNodes(head);
        //再链接sibling字段
        connectNodes(head);
        //将整个链表拆分，返回复制链表的头结点
        return reconnectNodes(head);
    }

    /**
     * 复制一个链表，并且将复制后的结点插入到被复制的结点后面，只链接复制结点的next字段
     * @param head 待复制链表的头结点
     */
    private static void cloneNodes(ComplexListNode head) {
        ComplexListNode cur = head;
        //如果链表不为空，进行复制操作
        while(cur != null) {
            //创建一个新的结点
            ComplexListNode tmp = new ComplexListNode();
            //将被复制结点的值传给复制结点
            tmp.value = cur.value;
            //复制结点的nex指向下一个要被复制的结点
            tmp.next = cur.next;
            //被复制结点的next指向复制结点
            cur.next = tmp;
            //到此处就已经完成了一个结点的复制并且插入到被复制结点的后面
            //cur指向下一个被复制结点的位置
            cur = tmp.next;
        }
    }

    /**
     * 设置复制结点的sibling字段
     * @param head 待复制链表的头结点
     */
    private static void connectNodes(ComplexListNode head) {
        ComplexListNode cur = head;
        //如果链表不为空
        while (cur != null) {
            //当前处理的结点sibling字段不为空，则要设置其复制结点的sibling字段
            if(cur.sibling != null) {
                //复制结点的sibling指向被复制结点的sibling字段的下一个结点
                cur.next.sibling = cur.sibling.next;
            }
            //指向下一个要处理的结点
            cur = cur.next.next;
        }
    }

    /**
     * 拆分刚刚生成的带复制结点的大链表，还原被复制的链表，同时生成复制链表
     * @param head 链表的头结点
     * @return 复制链表的头结点
     */
    private static ComplexListNode reconnectNodes(ComplexListNode head) {
        //当链表为空就直接返回
        if(head == null) {
            return null;
        }
        ComplexListNode cur = head;
        //用于记录复制链表的头结点
        ComplexListNode newHead = head.next;
        //用于记录当前处理的复制结点
        ComplexListNode pointer = newHead;
        //被复制结点的next指向下一个原链表结点
        cur.next = pointer.next;
        //指向新的被复制结点
        cur = cur.next;
        while (cur != null) {
            //pointer指向复制结点
            pointer.next = cur.next;
            pointer = pointer.next;
            //cur的下一个结点指向复制结点的下一个结点，即原来链表的结点
            cur.next = pointer.next;
            //cur指向下一个原来链表上的结点
            cur = cur.next;
        }
        //返回复制链表的头结点
        return newHead;
    }
}
