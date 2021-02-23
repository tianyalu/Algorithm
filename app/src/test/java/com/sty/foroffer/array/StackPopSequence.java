package com.sty.foroffer.array;

import java.util.Stack;

/**
 * 栈的压入、弹出序列
 *  ①题目：
 *      输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。
 *
 *  ②算法思路：
 *      解决这个问题很直观的想法就是建立一个辅助栈，把输入的第一个序列中的数字依次压入该辅助栈，并按照第二个序列的
 *    顺序依次从该栈中弹出数字。
 *      判断一个序列是不是栈的弹出序列的规律：如果下一个弹出的数字刚好是栈顶数字，那么直接弹出；如果下一个弹出的
 *    数字不在栈顶，我们把压栈序列中还没有入栈的数字压入辅助栈，直到把下一个需要弹出的数字压入栈顶为止。
 *    如果所有的数字都压入栈了仍然没有找到下一个弹出的数字，那么该序列不可能是一个弹出序列。
 *    示例： pushed = [1,2,3,4,5], popped = [4,5,3,2,1]  --> true
 *          pushed = [1,2,3,4,5], popped = [4,3,5,1,2]  --> false   1不能在2之前弹出
 * @Author: tian
 * @UpdateDate: 2021/2/23 10:15 AM
 */
public class StackPopSequence {
    public static void main(String[] args) {
        int[] pushed = {1, 2, 3, 4, 5};
        int[] popped1 = {4, 5, 3, 2, 1};
        int[] popped2 = {4, 3, 5, 1, 2};
        System.out.println(validateStackSequences(pushed, popped1));
        System.out.println(validateStackSequences(pushed, popped2));
    }

    /**
     * 判断popped的出栈顺序是否正确
     * @param pushed 该数组顺序表示入栈的顺序
     * @param popped 该数组顺序表示出栈顺序
     * @return 判断data2的出栈顺序是否正确
     */
    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        if(pushed == null || popped == null) {
            return false;
        }

        Stack<Integer> stack = new Stack<>();
        for(int i = 0, j = 0; i < pushed.length; i++){
            stack.push(pushed[i]);
            while(stack.size() > 0 && stack.peek() == popped[j]){
                stack.pop();
                j++;
            }
        }
        return stack.size() == 0;
    }
}
