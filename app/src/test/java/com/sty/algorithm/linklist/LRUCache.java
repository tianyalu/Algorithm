package com.sty.algorithm.linklist;

import java.util.HashMap;

/**
 * 题目：最近最少缓存策略
 *     为最近最少使用（`LRU`）缓存策略设计一个数据结构，它应该支持以下操作：获取数据（`get`）和写入数据（`set`）。
 *     获取数据`get(key)`：如果缓存中存在`key`，则获取其数据值（通常是正数），否则返回-1。
 *     写入数据`set(key, value)`：如果`key`还没有在缓存中，则写入其数据值，当缓存达到上限，
 *     它应该在写入新数据之前删除最近最少使用的数据来腾出空闲位置。
 * 算法思路：双向链表加哈希表
 *     缓存讲究的就是快，所以我们必须做到`O(1)`的获取速度，这样看来只有哈希表可以胜任。但是哈希表是无序的，
 *     我们没有办法在缓存满时，将最早更新的元素给删去。那么是否有一种数据结构，可以将先进的元素先出呢？那就是队列，
 *     所以我们将元素缓存在队列中，并用一个哈希表记录下键值和元素的映射，就可以做到`O(1)`的访问速度和先进先出的效果。
 *     然而，当我们获取一个元素时，还需要把这个元素再次放到队列头，这个元素可能在队列的任意位置，可是队列并不支持对任意位置的增删操作。而最合适对任意位置增删操作的数据结构又是什么呢？是链表，我们可以用链表来实现一个队列，这样就同时拥有链表和队列的特性了。不过如果仅用单链表的话，在任意位置删除一个节点还是很麻烦的，要么记录下该节点的上一个节点，要么遍历一遍，所以双向链表是最好的选择。我们用双向链表实现一个队列，用来记录每个元素的顺序，用一个哈希表来记录键和值的关系就行了。
 *
 * @Author: tian
 * @UpdateDate: 2020/12/25 9:53 AM
 */
public class LRUCache {

    private int capacity;
    private HashMap<Integer, DNode> map = new HashMap<>();
    private DNode head = new DNode(-1, -1);
    private DNode tail = new DNode(-1, -1);

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(4);
        lruCache.set(1, 2);
        lruCache.set(2, 4);
        lruCache.printDNodeList(lruCache.head);
        lruCache.set(3, 6);
        lruCache.set(4, 8);
        lruCache.printDNodeList(lruCache.head);
        lruCache.set(2, 11);
        lruCache.printDNodeList(lruCache.head);
    }

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

        public DNode() {

        }

        public DNode(int key, int val) {
            this.key = key;
            this.val = val;
            prev = null;
            next = null;
        }

        @Override
        public String toString() {
            return "{" +
                    "key=" + key +
                    ", val=" + val +
                    '}';
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
