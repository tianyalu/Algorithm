package com.sty.foroffer.array;

import java.util.Stack;

/**
 * 包含`min`函数的栈
 *  ①题目：
 *      定义栈的数据结构，请在该类型中实现一个能够得到栈的最小数的`min`函数。在该栈中，调用`min`、`push`及`pop`
 *    的时间复杂度都是`O(1)`。
 *
 *  ②算法思路：
 *      把每次的最小元素（之前的最小元素和新压入栈的元素两者的较小值）都保存起来放入另外一个辅助栈里。
 *    如果每次都把最小元素压入辅助栈，那么就能保证辅助栈的栈顶一直都是最小元素。当最小元素从数据栈内被弹出之后，
 *    同时弹出辅助栈的栈顶元素，此时辅助栈的新栈顶元素就是下一个最小值。
 * @Author: tian
 * @UpdateDate: 2021/2/23 9:40 AM
 */
public class MinStack {
    public static void main(String[] args) {

    }

    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>(); //辅助栈：栈顶永远保持stack中当前的最小元素

    public void push(int data) {
        stack.push(data); //直接往栈中添加数据
        //在辅助栈中需要做判断
        if(minStack.size() == 0 || data < minStack.peek()) {
            minStack.push(data);
        }else {
            minStack.add(minStack.peek()); //核心代码，peek方法返回的是栈顶的元素（不出栈）
        }
    }

    public int pop() throws Exception {
        if(stack.size() == 0) {
            throw new Exception("栈中为空");
        }

        int data = stack.pop();
        minStack.pop(); //核心代码
        return data;
    }

    public int min() throws Exception {
        if(minStack.size() == 0) {
            throw new Exception("栈空了");
        }
        return minStack.peek();
    }
}
