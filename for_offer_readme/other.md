## 其它算法

[TOC]

### 7.1 斐波那契数列

`foroffer/string/Fibonacci`, 参考：[07](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/07.md)

①题目：

写一个函数，输入`n`，求斐波那契数列的第`n`项值。斐波那契数列的定义如下：
$$
F_0 = 0 \\
F_1 = 1 \\
F_n = F_{n-1} + F_{n-2}
$$
②算法思路：

按照上述递推式，可使用循环或递归的方式获取第`n`项式。

③算法实现：

```java
    /**
     * 写一个函数，输入n，求斐波那契数列的第n项  --> 循环法
     * @param n Fibonacci数的项数
     * @return 第n项的结果
     */
    public static long fibonacci(int n) {
        //当输入非正整数时返回0
        if(n <= 0) {
            return 0;
        }

        //输入1或者2的时候返回1
        if(n == 1 || n == 2) {
            return 1;
        }

        //第n-2个的Fibonacci数的值
        long prePre = 1;
        //第n-1个的Fibonacci数的值
        long pre = 1;
        //第n个的Fibonacci数的值
        long current = 2;

        //求解第i个的Fibonacci数的值
        for (int i = 3; i <= n; i++) {
            //求第i个的Fibonacci数的值
            current = prePre + pre;
            //更新记录的结果，prePre原先记录第i-2个Fibonacci数的值，现在记录第i-1个Fibonacci数的值
            prePre = pre;
            //更新记录的结果，pre原先记录第i-1个Fibonacci数的值，现在记录第i个Fibonacci数的值
            pre = current;
        }
        //返回所求的结果
        return current;
    }

    /**
     * 写一个函数，输入n，求斐波那契数列的第n项  --> 递归法
     * @param n Fibonacci数的项数
     * @return 第n项的结果
     */
    public static long fibonacciInRecursion(int n) {
        if(n <= 0) {
            return 0;
        }

        if(n == 1 || n == 2) {
            return 1;
        }

        return fibonacciInRecursion(n - 2) + fibonacciInRecursion(n -1);
    }
```

