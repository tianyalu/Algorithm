## 其它算法

[TOC]

### 7.1 斐波那契数列

`foroffer/other/Fibonacci`, 参考：[07](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/07.md)

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

### 7.2 求一个整数的二进制数中1的个数

`foroffer/other/NumberOfOne`, 参考：[08](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/08.md)

①题目：

请实现一个函数，输入一个整数，输出该整数二进制表示中1的个数，例如把9表示成二进制为1001，有2位1。因此如果输入9，该函数输出2。

②算法思路：

**思路一：**

位移+计数，每次右移一位，不断和1进行与运算，直到位0。

**思路二：**

循环让`(n-1)&n`，如果`n`的二进制表示中有`k`个1，那么这个方法只需要循环`k`次即可。其原理是**不断清除`n`的二进制表示中最右边的1，同时累加计数器，直至`n`为0**。因为从二进制的角度讲，`n`相当于在`n-1`的最低位加上1。

举个例子：8（1000） = 7（0111）+ 1（0001），所以 8 & 7 = （1000）&（0111）= 0（0000），清除了8最右边的1（其实就是最高位的1，因为 8 的二进制中只有一个1）。再比如7（0111）= 6（0110）+ 1（0001），所以 7 & 6 = （0110）+ 1（0001），所以 7 & 6= （0111）& （0110），清除了 7 的二进制表示中最右边的 1（也就是最低位的1）。

③算法实现：

```java
    /**
     * 求一个整数二进制表示中1的个数
     * @param n 输入的整数
     * @return 整数二进制表示中1的个数
     */
    public static int numberOfOne1(int n) {
        //记录数字中1的位数
        int result = 0;

        //Java语言规范中，int整型占四个字节，总计32位
        //对每一个位置与1进行求与操作，再累加就可以求出当前数字的表示是多少位1
        for(int i = 0; i < 32; i++) {
            result += (n & 1);
            //System.out.println("result: " + result);
            n >>>= 1;
            //System.out.println("n : " + n);
        }

        //返回求得的结果
        return result;
    }

    /**
     * 求一个整数二进制表示中1的个数
     * @param n 输入的整数
     * @return 整数二进制表示中1的个数
     */
    public static int numberOfOne2(int n) {
        //记录数字中1的位数
        int result = 0;

        //数字的二进制表示中有多少个1就进行多少次操作
        while (n != 0) {
            result++;
            //从最右边的1开始，每一次操作都使n的最后一个1变成了0，即使符号位也会进行操作
            n = (n - 1) & n;
        }

        //返回求得的结果
        return result;
    }
```

