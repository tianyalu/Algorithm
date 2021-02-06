package com.sty.foroffer.array;

import java.util.Stack;

/**
 * 用两个栈实现队列
 *  ①题目：
 *      用两个栈实现一个队列，队列的声明如下，请实现它的两个函数`appendTail`和`deleteHead`，
 *    分别完成在队列尾部插入结点和在队列头部删除结点的功能。
 *
 *  ②解题思路：
 *      栈1用于存储元素，栈2用于弹出元素，负负得正。说得通俗一点，现在把数据1、2、3分别入栈1，
 *    然后从栈1中出来（3、2、1）放入到栈2中，那么从栈2中出来的数据（1、2、3）就符合队列的规律了，即负负得正。
 * @Author: tian
 * @UpdateDate: 2021/2/6 9:50 AM
 */
public class StackQueue {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        MStackList<Integer> mStackList = new MStackList<>();
        for (int i = 0; i < arr.length; i++) {
            mStackList.appendTail(arr[i]);
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(mStackList.deleteHead() + " ");
        }
        System.out.println();
    }

    public static class MStackList<T> {
        //插入栈，只用于插入的数据
        private Stack<T> stack1 = new Stack<>();
        //弹出栈，只用于弹出数据
        private Stack<T> stack2 = new Stack<>();

        //添加操作，在队列尾部插入结点
        public void appendTail(T t) {
            stack1.add(t);
        }

        //删除操作，在队列头部删除结点
        public T deleteHead() {
            //先判断弹出栈是否为空，如果为空就将插入栈的所有数据弹出栈并压入弹出栈中
            if(stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.add(stack1.pop());
                }
            }

            //如果弹出栈中还是没有数据就抛出异常
            if(stack2.isEmpty()) {
                throw new RuntimeException("No more element");
            }

            //返回弹出栈的栈顶元素，对应的就是队首元素
            return stack2.pop();
        }
    }
}
