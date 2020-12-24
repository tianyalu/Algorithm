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

### 3.4 合并连个排序的链表

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

