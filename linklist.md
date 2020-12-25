## 链表相关

[TOC]

### 3.1 链表翻转

迭代翻转、递归翻转，翻转从位置m到n的链表，k个一组翻转链表。

`linklist/ReverseLinkedList` 参考：[https://www.jianshu.com/p/a29834955ffb](https://www.jianshu.com/p/a29834955ffb)]

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

