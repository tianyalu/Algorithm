package com.sty.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 大顶堆
 * @Author: tian
 * @UpdateDate: 2021/3/5 5:18 PM
 */
public class MaxHeep<T extends Comparable<T>> {
    //堆中元素存放的集合
    private List<T> items;
    //用于计数
    private int cursor;

    /**
     * 构造一个堆，初始大小是32
     */
    public MaxHeep() {
        this(32);
    }

    /**
     * 构造一个指定初始大小的堆
     * @param size 初始大小
     */
    public MaxHeep(int size) {
        items = new ArrayList<>(size);
        cursor = -1;
    }

    /**
     * 向上调整堆
     * @param index 被上移元素的起始位置
     */
    public void siftUp(int index) {
        T intent = items.get(index); //获取开始调整的元素对象
        while(index > 0) { //如果不是根元素
            int parentIndex = (index - 1) / 2; //找父元素对象的位置
            T parent = items.get(parentIndex); //获取父元素对象
            if(intent.compareTo(parent) > 0) { //上移的条件，子结点比父结点大
                items.set(index, parent); //将父结点向下放
                index = parentIndex; //记录父结点下放的位置
            }else { //子结点不比父结点大，说明父子路径已经按从大到小排好顺序了，不需要调整了
                break;
            }
        }

        // index此时记录的是最后一个被下放的父结点的位置（也可能是自身），所以将最开始的调整的元素值放入index位置即可
        items.set(index, intent);
    }

    /**
     * 向下调整堆
     * @param index 被下移的元素的起始位置
     */
    public void siftDown(int index) {
        T intent = items.get(index); //获取开始调整的元素对象
        int leftIndex = 2 * index + 1; //获取开始调整的元素对象的左子结点的元素位置

        while(leftIndex < items.size()) { //如果有左子结点
            T maxChild = items.get(leftIndex); //取左子结点的元素对象，并且假定其为两个子结点中最大的
            int maxIndex = leftIndex; //两个子结点中最大结点元素的位置，假定开始时为左子结点的位置

            int rightIndex = leftIndex + 1; //获取右子结点的位置
            if(rightIndex < items.size()) { //如果有右子结点
                T rightChild = items.get(rightIndex); //获取右子结点的元素对象
                if(rightChild.compareTo(maxChild) > 0) { // 找出两个子结点中的最大子结点
                    maxChild = rightChild;
                    maxIndex = rightIndex;
                }
            }

            //如果最大子结点比父结点大，则需要向下调整
            if(maxChild.compareTo(intent) > 0) {
                items.set(index, maxChild); //将子结点上移
                index = maxIndex; //记录上移的位置
                leftIndex = index * 2 + 1; //找到上移结点的左子结点的位置
            }else { //最大子结点不比父结点大，说明父结点已经按从大到小排好顺序了，不需要调整了
                break;
            }
        }

        //index 此时记录的是最后一个被上移的子结点的位置（也可能是自身），所以将最开始的调整的元素值放入index位置即可
        items.set(index, intent);
    }

    /**
     * 向堆中添加一个元素
     * @param item 等待添加的元素
     */
    public void add(T item) {
        items.add(item); //将元素添加到最后
        siftUp(items.size() - 1); //循环上移，以完成重构
    }

    /**
     * 删除堆顶元素
     * @return 堆顶部的元素
     */
    public T deleteTop() {
        if(items.isEmpty()) { //如果堆已经为空，就抛出异常
            throw new RuntimeException("The heap is empty.");
        }

        T maxItem = items.get(0); //获取堆顶元素
        T lastItem = items.remove(items.size() - 1); //删除最后一个元素
        if(items.isEmpty()) { //删除元素后，如果堆为空的情况，说明删除的元素也是堆顶元素
            return lastItem;
        }

        items.set(0, lastItem); //将删除的元素放入堆顶
        siftDown(0); //自上向下调整堆
        return maxItem; //返回堆顶元素
    }

    /**
     * 取下一个元素
     * @return 下一个元素对象
     */
    public T next() {
        if(cursor >= items.size()) {
            throw new RuntimeException("No more element");
        }
        return items.get(cursor);
    }

    /**
     * 判断堆中是否还有下一个元素
     * @return true：堆中还有下一个元素；false：堆中没有下个元素了
     */
    public boolean hasNext() {
        cursor++;
        return cursor < items.size();
    }

    /**
     * 获取堆中第一个元素
     * @return 堆中的第一个元素
     */
    public T first() {
        if(items.size() == 0) {
            throw new RuntimeException("The heap is empty");
        }
        return items.get(0);
    }

    /**
     * 判断堆是否为空
     * @return true:是；false:否
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * 获取堆的大小
     * @return 堆的大小
     */
    public int size() {
        return items.size();
    }

    /**
     * 清空堆
     */
    public void clear() {
        items.clear();
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
