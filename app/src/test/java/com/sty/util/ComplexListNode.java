package com.sty.util;

import androidx.annotation.NonNull;

/**
 * 复杂链表结点，其中sibling可以指向链表中的任意结点，也可以为null
 * @Author: tian
 * @UpdateDate: 2021/2/25 9:24 AM
 */
public class ComplexListNode {
    public int value;
    public ComplexListNode next;
    public ComplexListNode sibling;

    public ComplexListNode() {
    }

    public ComplexListNode(int value) {
        this(value, null, null);
    }

    public ComplexListNode(int value, ComplexListNode next, ComplexListNode sibling) {
        this.value = value;
        this.next = next;
        this.sibling = sibling;
    }

    @NonNull
    @Override
    protected ComplexListNode clone() throws CloneNotSupportedException {
        return new ComplexListNode(this.value, this.next, this.sibling);
    }
}
