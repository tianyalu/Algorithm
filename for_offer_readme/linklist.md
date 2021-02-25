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

`algorithm/linklist/ReverseLinkedList`

参考：[3.1 反转链表](https://github.com/tianyalu/Algorithm/blob/master/readme/linklist.md)

### 3.5 合并两个排序的链表 【14】

`algorithm/linklist/MergeSortedLinkedList`

参考：[3.4 合并两个排序的链表](https://github.com/tianyalu/Algorithm/blob/master/readme/linklist.md)

### 3.6 复杂链表的复制

`foroffer/linklist/CloneComplexLinkList`, 参考：[23](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/23.md)

①题目：

请实现函数`ComplexListNode clone(ComplexListNode head)`，复制一个复杂链表。在复杂链表中，每个结点除了有一个`next`域指向下一个结点外，还有一个`sibling`指向链表中的任意结点或者`null`。

②算法思路：

在不用辅助空间的情况下实现`O(n)`的时间效率。

> 1. 仍然是根据元素链表的每个结点`N`创建对应的`N'`，把`N'`链接在`N`的后面；
> 2. 设置复制出来的结点的`sibling`，假设原始链表上的`N`的`sibling`指向结点`S`，那么其对应复制出来的`N'`是`N`的`next`指向的结点，同样`S'`也是`S`的`next`指向的结点；
> 3. 把这个长链表拆分成两个链表，把奇数位置的结点用`next.`链接起来就是原始链表，把偶数位置的结点用`next`链接起来就是复制出来的链表。

③算法实现：

```java
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
```

