package com.sty.algorithm.other;

import org.junit.Test;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/18 9:04 AM
 */
public class LogicOperator {
    public static void main(String[] args) {
        //logicOperatorTest();

    }

    /**
     * 优先级： ！> && > \\      从左向右计算
     */
    @Test
    public void logicOperatorTest() {
        int a = 20;
        int b = 30;
        boolean result;
        result = a > 60 && b > 60 || a < -10 && b < -20 || a > 40 && b > 40;  //false
        //等价于：result = (a > 60 && b > 60) || (a < -10 && b < -10) || (a > 40 && b > 40);
        // result = a > 60 && b > 60 || a < 60 && b < 40 || a > 40 && b > 40; //true
        System.out.println("result: " + result);
    }
}
