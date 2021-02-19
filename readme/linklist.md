## 链表相关

[TOC]

### 3.1 链表反转

迭代反转、递归反转，翻转从位置`m`到`n`的链表，`k`个一组反转链表。

`linklist/ReverseLinkedList` 参考：[https://www.jianshu.com/p/a29834955ffb](https://www.jianshu.com/p/a29834955ffb)

#### 3.1.1 迭代反转

```java
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
```

#### 3.1.2 递归反转

```java
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
```

#### 3.1.3 翻转从位置`m`到`n`的链表

```java
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
```

#### 3.1.4 `k`个一组反转链表

```java
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
```

### 3.2 删除排序链表所有重复元素

`linklist/DeleteDupElements` 参考：[https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Linked-List/Remove-Duplicates-from-Sorted-List.md](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Linked-List/Remove-Duplicates-from-Sorted-List.md)

①题目：

```java
Given a sorted linked list, delete all duplicates such that each element appear only once.
For example, Given 1->1->2, return 1->2. Given 1->1->2->3->3, return 1->2->3.
```

给定一个**排序**链表，删除所有重复的元素，每个元素只留下一个。

②算法思路：

遍历之，遇到当前节点和下一节点的值相同时，删除下一节点，并将当前节点`next`指向下一个节点的`next`，当前节点首先保持不变，知道相邻节点的值不等时才移动到下一节点。

③算法实现：

```java
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static ListNode deleteDupElements(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            while (curr.next != null && curr.val == curr.next.val) {
                curr.next = curr.next.next;
            }
            curr = curr.next;
        }
        return head;
    }
```

### 3.3 分治将小于`x`的节点放在大于等于`x`的节点前边

`linklist/PartitionLinkedList` 参考：[https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Linked-List/Partition-List.md](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Linked-List/Partition-List.md)

①题目：

```java
Given a linked list and a value x, partition it such that all nodes less that x come before nodes greater than or equal to x.
You should preserve the original relative order of the nodes in each of the two partitions.
For example, Given 1->4->3->2->5->2, return 1->2->2->4->3->5.
```

给定一个单链表和数值`x`，划分链表使得所有小于`x`的节点排在大于等于`x`的节点之前。

你应该保留两部分内链表节点原有的相对顺序。

②算法思路：

依据题意，是要根据值`x`对链表进行分割操作，具体是指将所有小于`x`的节点放到不小于`x`的节点之前，但它和快排分割不同的是只需要将小于`x`的节点放到前面，而并不要求对元素进行排序。

这个分割使用两路指针即可解决：左边指针指向小于`x`的节点，右边指针指向不小于`x`的节点，由于左右头节点不确定，我们可以使用两个`dummy`节点。

③算法实现：

```java
    public static ListNode partition(ListNode head, int x) {
        ListNode leftDummy = new ListNode(0);
        ListNode leftCurr = leftDummy;
        ListNode rightDummy = new ListNode(0);
        ListNode rightCurr = rightDummy;

        ListNode runner = head;
        while (runner != null) {
            if(runner.val < x) {
                leftCurr.next = runner;
                leftCurr = leftCurr.next;
            }else {
                rightCurr.next = runner;
                rightCurr = rightCurr.next;
            }
            runner = runner.next;
        }

        //cut off ListNode after rightCurr to avoid cylic
        rightCurr.next = null;
        leftCurr.next = rightDummy.next;

        return leftDummy.next;
    }
```

### 3.4 合并两个排序的链表

`linklist/MergeSortedLinkedList` 参考：[https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Linked-List/Merge-Two-Sorted-Lists.md](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Linked-List/Merge-Two-Sorted-Lists.md)

①题目：

```java
Merge two sorted (ascending) linked lists and return it as a new sorted list. The new sorted list should be made by splicing together the nodes of the two lists and sorted in ascending order.
Example: Given 1->3->8->11->15->null, 2->null, return 1->2->3->8->11->15->null.
```

将两个排序链表合并成一个新的排序链表。

②算法思路：

只需要从头开始比较已排序的两个链表，新链表指针每次指向值小的节点，依次比较下去，最后，当其中一个链表到达了末尾，我们只需要把新链表指针指向另一个没有到末尾的链表此时的指针即可。

③算法实现：

```java
    public static ListNode mergeLinkedList(ListNode h1, ListNode h2) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;

        while (h1 != null && h2 != null) {
            if(h1.val < h2.val) {
                cur.next = h1;
                h1 = h1.next;
            }else {
                cur.next = h2;
                h2 = h2.next;
            }
            cur = cur.next;
        }

        //link to non-null list
        if(h1 == null) {
            cur.next = h2;
        }else {
            cur.next = h1;
        }

        return dummy.next;
    }
```

### 3.5 最近最少缓存策略

`linklist/LRUCache` 参考：[https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Linked-List/LRU-Cache.md](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Linked-List/LRU-Cache.md)

①题目：

```java
Design and implement a data structure for Least Recentl Used(LRU) cache.It should support the following operations: get and set.
get(key) - Get the value(will always be positive) of the key if the exists in the cache, otherwise return -1.
set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
```

为最近最少使用（`LRU`）缓存策略设计一个数据结构，它应该支持以下操作：获取数据（`get`）和写入数据（`set`）。

获取数据`get(key)`：如果缓存中存在`key`，则获取其数据值（通常是正数），否则返回-1。

写入数据`set(key, value)`：如果`key`还没有在缓存中，则写入其数据值，当缓存达到上限，它应该在写入新数据之前删除最近最少使用的数据来腾出空闲位置。

②算法思路：双向链表加哈希表

缓存讲究的就是快，所以我们必须做到`O(1)`的获取速度，这样看来只有哈希表可以胜任。但是哈希表是无序的，我们没有办法在缓存满时，将最早更新的元素给删去。那么是否有一种数据结构，可以将先进的元素先出呢？那就是队列，所以我们将元素缓存在队列中，并用一个哈希表记录下键值和元素的映射，就可以做到`O(1)`的访问速度和先进先出的效果。然而，当我们获取一个元素时，还需要把这个元素再次放到队列头，这个元素可能在队列的任意位置，可是队列并不支持对任意位置的增删操作。而最合适对任意位置增删操作的数据结构又是什么呢？是链表，我们可以用链表来实现一个队列，这样就同时拥有链表和队列的特性了。不过如果仅用单链表的话，在任意位置删除一个节点还是很麻烦的，要么记录下该节点的上一个节点，要么遍历一遍，所以双向链表是最好的选择。我们用双向链表实现一个队列，用来记录每个元素的顺序，用一个哈希表来记录键和值的关系就行了。

③算法实现：

```java
public class LRUCache {
    private int capacity;
    private HashMap<Integer, DNode> map = new HashMap<>();
    private DNode head = new DNode(-1, -1);
    private DNode tail = new DNode(-1, -1);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        tail.prev = head;
        head.next = tail;
    }

    public int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        }
        // remove current
        DNode currentNode = map.get(key);
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;

        //move current to tail
        moveToTail(currentNode);

        return currentNode.val;
    }

    public void set(int key, int value) {
        if(get(key) != -1) { //命中直接赋值（在get(key)方法中会移到队列尾部）
            map.get(key).val = value;
            return;
        }
        if(map.size() == capacity) { //容量满时移除队首元素
            map.remove(head.next.key);
            head.next = head.next.next;
            head.next.prev = head;
        }
        DNode insertNode = new DNode(key, value);
        map.put(key, insertNode);
        moveToTail(insertNode);
    }

    private void moveToTail(DNode currentNode) {
        currentNode.prev = tail.prev;
        tail.prev.next = currentNode;
        currentNode.next = tail;
        tail.prev = currentNode;
    }

    public static class DNode {
        public int key;
        public int val;
        public DNode prev;
        public DNode next;
        public DNode(int key, int val) {
            this.key = key;
            this.val = val;
            prev = null;
            next = null;
        }

        @Override
        public String toString() {
					//...
        }
    }

    public void printDNodeList(DNode header) {
        DNode curr = header.next;
        while (curr != null && curr != tail) {
            System.out.print(curr + " ");
            curr = curr.next;
        }
        System.out.println();
    }
}
```

### 3.6 移除所有目标元素

`linklist/RemoveTargetElements` 

①题目：

```java
Remove All elements from a linked list of integers that have value val.
Example: Given 1->2->3->3->4->5->3, val = 3, you should return the list as 1->2->4->5.
```

删除链表中等于指定值`val`的所有节点。

②算法思路：

删除链表中指定值，找到其前一个节点即可，将`next`指向下一个节点即可。

③算法实现：

```java
    public static ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr = dummy;

        while (curr.next != null) {
            if(curr.next.val == val) {
                curr.next = curr.next.next;
            }else {
                curr = curr.next;
            }
        }
        return dummy.next;
    }
```

