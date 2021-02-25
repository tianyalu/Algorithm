package com.sty.util;

import java.util.ArrayList;

/**
 * @Author: tian
 * @UpdateDate: 2021/2/25 9:24 AM
 */
public class ComplexLinkListUtils {

    /**
     * 创建复杂链表
     * @param values 链表的值数组
     * @param siblingIndexArr 链表sibling的索引数组，如果为-1表示该结点的sibling为null
     * @return 复杂链表头结点
     */
    public static ComplexListNode createComplexLinkList(int[] values, int[] siblingIndexArr) {
        if(values == null || siblingIndexArr == null || values.length == 0 || siblingIndexArr.length == 0) {
            return null;
        }

        if(values.length != siblingIndexArr.length) {
            throw new IllegalArgumentException("参数非法");
        }

        //创建所有node
        ArrayList<ComplexListNode> lists = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            ComplexListNode node = new ComplexListNode(values[i]);
            lists.add(node);
        }

        //链接next并赋值sibling
        for (int i = 0; i < lists.size() - 1; i++) {
            lists.get(i).next = lists.get(i + 1);
            lists.get(i).sibling = siblingIndexArr[i] == -1 ? null : lists.get(siblingIndexArr[i]);
        }
        lists.get(lists.size() - 1).sibling = siblingIndexArr[siblingIndexArr.length - 1] == -1 ?
                null : lists.get(siblingIndexArr[siblingIndexArr.length - 1]);
        return lists.get(0);
    }

    public static void printComplexLinkList(ComplexListNode head) {
        ComplexListNode cur = head;
        while (cur != null) {
            System.out.print(cur.value + "(" + (cur.sibling == null ? "null" : cur.sibling.value) + ") ");
            cur = cur.next;
        }
        System.out.println();
    }
}
