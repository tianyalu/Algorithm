## 链表相关

[TOC]

### 3.1 链表翻转

迭代翻转、递归翻转，翻转从位置m到n的链表，k个一组翻转链表。

`linklist/ReverseLinkedList` 参考：[https://www.jianshu.com/p/a29834955ffb](https://www.jianshu.com/p/a29834955ffb)]

### 3.2 删除排序链表所有重复元素

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

