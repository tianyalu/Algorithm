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

### 7.3 顺序输出从1到n位最大十进制的数值

`foroffer/other/PrintOneToNthDigits`, 参考：[09](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/09.md)

①题目：

输入数字`n`，按顺序打印出从 1 到`n`为位最大十进制数的数值，比如输入 3 ，则打印出1、2、3一直到最大三位数即999。

②算法思路：

**思路一**：递归法

使用一个`n`位的数组来存储每一位的元素，例如`n`位 3，则 000 表示为 [0, 0, 0]；使用递归的方式，存放每一位元素值。

```java
    /**
     * 方法一：递归法
     * 输入数字n，按照顺序打印出从1到最大的n位十进制数
     * @param n 输入的数字
     */
    public static void printOneToNthDigits(int n) {
        //输入的数字不能为小于1
        if(n < 1) {
            throw new RuntimeException("The input number must larger than 0");
        }
        //创建一个数组用于打印存放值
        int[] arr = new int[n];
        printOneToNthDigits(0, arr);
    }

    /**
     *
     * @param n  当前处理的是第几个元素，从0开始计数
     * @param arr 存放结果的数组
     */
    private static void printOneToNthDigits(int n, int[] arr) {
        //说明数组已经装满元素
        if(n >= arr.length) {
            //可以输出数组的值
            printArray(arr);
        }else {
            for (int i = 0; i <= 9; i++) {
                arr[n] = i;
                printOneToNthDigits(n + 1, arr);
            }
        }
        // 0 0 0
        // 0 0 1
    }

    /**
     * 输入数组的元素，从左到右，从第一个非0值到开始输出到最后的元素
     * @param arr 要输出的数组
     */
    public static void printArray(int[] arr) {
        //找第一个非0的元素
        int index = 0;
        while (index < arr.length && arr[index] == 0) {
            index++;
        }

        //从第一个非0值开始输出到最后的元素
        for (int i = index; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        //条件成立说明数组中有非0元素，所以需要换行
        if(index < arr.length) {
            System.out.println();
        }
    }
```

**思路二**：累加法

同上，使用一个`n`位的数组来存储每一位的元素，然后循环执行加 1 运算，并在数组中进行模拟进位，直到最高位需要进位，则表示循环结束。

```java
    /**
     * 方法二：累加法
     * 输入数字n，按照顺序打印出从1到最大的n位十进制数
     * @param n 输入的数字
     */
    public static void printOneToNthDigits2(int n) {
        //输入值必须大于0
        if(n < 1) {
            throw new RuntimeException("The input number must larger than 0");
        }

        //创建长度为n的数组
        int[] arr = new int[n];
        //为数组元素赋初始值
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }

        //求结果，如果最高位没有进位就一直进行处理
        while (addOne(arr) == 0) {
            printArray(arr);
        }
    }

    /**
     * 对arr表示的数组最低位加1，arr中的每个数都不能超过9且不能小于0，每个位置模拟一个数位
     * @param arr 待加数组
     * @return 判断高位是否有进位，如果有进位就返回1，否则返回0
     */
    private static int addOne(int[] arr) {
        //保存进位值，因为每次最低位加1
        int carry = 1;
        //最低位的位置的后一位
        int index = arr.length;

        do {
            //指向上一个位置
            index--;
            //处理位置的值加上进位的值
            arr[index] += carry;
            //求处理位置的进位
            carry = arr[index] / 10;
            //求处理位置的值
            arr[index] %= 10;
        }while (carry != 0 && index > 0);

        //如果index==0 说明已经处理了最高位，carry > 0 说明最高位有进位，返回1
        if(carry > 0 && index == 0) {
            return 1;
        }
        //无进位返回0
        return 0;
    }
```







