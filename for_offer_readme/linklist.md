## 链表相关算法

[TOC]

### 3.1 逆序打印链表

`foroffer/linklist/ReversePrintLinkList`, 参考：[03](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/03.md)

①题目：

输入个链表的头结点，从尾到头反过来打印出每个节点的值。

②算法思路：

使用栈的方式进行：将链表从头到尾压入栈内，出栈的过程就对应着从尾到头；

也可以递归打印。

③算法实现：

```java
    /**
     * 输入个链表的头结点，从尾到头反过来打印出每个节点的值
     * 使用栈的方式
     * @param header 链表头结点
     */
    public static void reversePrintByStack(ListNode header) {
        Stack<ListNode> stack = new Stack<>();
        ListNode cur = header;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (!stack.isEmpty()) {
            cur = stack.pop();
            System.out.print(cur.val + " ");
        }
        System.out.println();
    }

    /**
     * 输入个链表的头结点，从尾到头反过来打印出每个节点的值
     * 递归的方式
     * @param header 链表头结点
     */
    public static void reversePrintByRecursion(ListNode header) {
        if(header != null) {
            reversePrintByRecursion(header.next);
            System.out.print(header.val + " ");
        }
    }
```

