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

### 3.2 在`O(1)`时间内删除给定的单链表节点

`foroffer/linklist/DeleteLinkListNode`, 参考：[10](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/10.md)

①题目：

给定单向链表的一个头指针和节点指针，定义一个函数在`O(1)`时间删除该节点。

②算法思路：

由于给定了节点指针，那么要删除该节点，只要把该节点的值替换为下一个节点的值，同时让该节点直接指向下一个节点的下一个节点，相当于顶包代替了下一个节点，该节点自然就不存在了。

需要注意的是如果指定节点是头节点，那么直接把头节点定义为下一个节点即可；如果是尾节点，需要循环遍历到该节点，然后让尾节点的上一个节点的指针为空即可。

③算法实现：

```java
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
```

### 3.3 链表中倒数第`k`个结点

`foroffer/linklist/DeleteLinkListNode`, 参考：[12](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/12.md)

①题目：

输入一个链表，输出该链表中倒数第`k`个结点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个结点。例如一个链表有6个结点，从头结点开始它们的值依次是1、2、3、4、5、6，那么这个链表的倒数第3个结点是值为4的结点。

②算法思路：

为了实现只遍历链表一次就能找到倒数第`k`个结点，我们可以定义两个指针，第一个指针从链表的头指针开始遍历向前走`k-1`步，第二个指针保持不动；从第`k`步开始，第二个指针也开始从链表的头指针开始遍历。由于两个指针的距离保持在`k-1`，当第一个（走在前面的）指针到达链表的尾节点时，第二个指针（走在后面的）指针正好是倒数第`k`个结点。

③算法实现：

```java
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
```

### 3.4 链表反转【13】

参考：[3.1 反转链表](https://github.com/tianyalu/Algorithm/blob/master/readme/linklist.md)

### 3.5 合并两个排序的链表 【14】

参考：[3.4 合并两个排序的链表](https://github.com/tianyalu/Algorithm/blob/master/readme/linklist.md)

