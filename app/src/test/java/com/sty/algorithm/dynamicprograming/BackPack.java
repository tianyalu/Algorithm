package com.sty.algorithm.dynamicprograming;

/**
 * 背包问题
 * 一、01背包问题
 *  ①题目：在n个物品中挑选若干物品装入背包，最多能装多满？假设背包的大小为m，每个物品的大小为A[i]。
 *  ②算法思路：
 *      本题是典型的`01`背包问题，每种类型的物品最多只能选择一件。
 *      > 1. **`State: `** `dp[i][S]`表示前`i`个物品，取出一些能否组成和为`S`体积的背包；
 *      > 2. **`Function:`** `f[i][S] = f[i - 1][S - A[i]] or f[i - 1][S]`（`A[i]`表示第`i`个物品的大小）;
 *      > 转移方程想得到`f[i][S]`前`i`个物品取出一些物品想组成`S`体积的背包，那么可以从两个状态转换得到：
 *          > > (1) `f[i - 1][S - A[i]]` 放入第`i`个物品，并且前`i-1`个物品能否取出一些组成和为`S - A[i]` 体积大小的背包；
 *          > > (2) `f[i - 1][S]`不放入第`i`个物品，并且前`i - 1`个物品能否取出一些组成和为`S`体积大小的背包。
 *      > 3. **`Initialize: `** `f[1...n][0] = true; f[0][1...n] = false`;
 *      > 初始化`f[1...n][0]`表示前`1...n`个物品，取出一些能否组成和为0大小的背包始终为真，其它初始化为假。
 *      > 4. **`Answer: `** 寻找使`f[n][S]`值为`true`的最大的`S` (`S`的取值范围为`1`到`m`)。
 *
 * 二、背包最大价值问题
 *   ①题目：两个数组，一个表示体积，另一个表示价值，给定一个容积为 m 的背包， 求背包装入物品的最大价值。
 *   ②算法思路：
 *      首先定义状态`K(i,w)`为前`i`个物品放入`size`为`w`的背包中所获得的最大价值，则相应的状态转移方程为：
 *      `K(i,w)=max{K(i-1,w), K(i-1,w-wi)+vi}`
 * @Author: tian
 * @UpdateDate: 2021/1/22 3:28 PM
 */
public class BackPack {

    public static void main(String[] args) {
        int[] A = {2, 3, 5, 7};
        System.out.println(backPack01(11, A));

        int[] V = {1, 5, 2, 4};
        System.out.println(backPackMaxValue(10, A, V));
    }

    /**
     * 01 背包问题
     *
     * fij   1 2 3 4 5 6 7 8 9 10 11
     *     1 0 0 0 0 0 0 0 0 0 0  0
     *   2 1 0 1 0 0 0 0 0 0 0 0  0
     *   3 1 0 1 1 0 1 0 0 0 0 0  0
     *   5 1 0 1 1 0 1 0 1 1 0 1  0
     *   7 1 0 1 1 0 1 0 1 1 1 1  0
     * @param m 背包容量
     * @param A items数组
     * @return
     */
    public static int backPack01(int m, int[] A) {
        if(A == null || A.length == 0) {
            return 0;
        }

        boolean f[][] = new boolean[A.length + 1][m + 1];
        for (int i = 0; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                f[i][j] = false;
            }
        }

        f[0][0] = true;
        for (int i = 1; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                f[i][j] = f[i - 1][j];  //不放入第i个物品， 并且前i-1个物品能否取出一些组成和为S 体积大小的背包。
                if(j >= A[i - 1] && f[i - 1][j - A[i - 1]]) {  //放入第i个物品，并且前i-1个物品能取出一些组成和为S-A[i] 体积大小的背包。
                    f[i][j] = true;
                }
            }
        }

        for(int i = m; i >= 0; i--) {
            for(int j = A.length; j >= 0; j--) {
                if(f[j][i]) {
                    return i;
                }
            }
        }

        return 0;
    }

    /**
     * 背包最大价值问题
     *
     *dpij   1 2 3 4 5 6 7 8 9 10   V
     *     0 0 0 0 0 0 0 0 0 0 0   //前0个item,在给定背包份数可以装得下的情形下能取到的最大价值
     *   2 0 0 1 1 1 1 1 1 1 1 1    1
     *   3 0 0 1 5 5 6 6 6 6 6 6    5
     *   5 0 0 1 5 5 6 6 6 7 7 8    2
     *   7 0 0 1 5 5 6 6 6 7 7 9    4
     *
     *   int[] V = {1, 5, 2, 4};
     * @param m An integer m denotes the size of a backpack
     * @param A Given n items with size A[i]
     * @param V These n items with value V[i]
     * @return The maximum value
     */
    public static int backPackMaxValue(int m, int[]A, int[] V) {
        if(A == null || A.length == 0 || V == null || V.length == 0) {
            return 0;
        }

        int[][] dp = new int[A.length + 1][m + 1];
        for (int i = 0; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                if(i == 0 || j == 0) {
                    dp[i][j] = 0;
                }else if(A[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - A[i - 1]] + V[i - 1]);
                }
            }
        }
        return dp[A.length][m];
    }
}
