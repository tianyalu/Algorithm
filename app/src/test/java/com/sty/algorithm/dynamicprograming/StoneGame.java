package com.sty.algorithm.dynamicprograming;

/**
 *  ①题目：石头合并游戏最小代价问题
 *      一堆石头，每个石头代表一个值，每次可以合并两个相邻的石头，得分是合并后的和。一直合并，同时累计得分，
 *      直到变成一个石头，并求出得分最小的值。
 *      There is a stone game. At the beginning of the game the player picks n piles of stones in a line.
 *      The goal is to merge the stones in one pile observing the following rules:
 *          1.At each step of the game, the player can merge two adjacent  piles to a new pile.
 *          2.The score is the number of stones in the new pile.
 *      You are to determine the minimum of the total score.
 *      Example: For [4, 1, 1, 4], in the best solution, the total score is 18:
 *          ①Merge second and third piles => [4, 2, 4], score + 2
 *          ②Merge the first two piles => [6, 4], score + 6
 *          ③Merge the last two piles => [10], score + 10
 *      Other two examples:
 *          [1, 1, 1, 1] return 8, [4, 4, 5, 9] return 43.
 *
 *  ②算法思路：
 *      此题可以用`dp`求解，`dp[i][j]`表示合并`i`到`j`的石头需要的最小代价。
 *      转移函数：
 *          dp[i][j] = dp[i][k] + dp[k+1][j] + sum[i][j], \quad i \leq k < j
 *          即合并`i~j`的代价为合并左边部分的代价+合并右边部分的代价+合并左右部分的代价（即`i~j`所有元素的总和），
 *          找到使`dp[i][j]`最小的`k`。
 *      `dp`四要素：
 *          * **`State`**：`dp[i][j]`表示把第`i`到第`j`个石子合并到一起 的最小花费；
 *          * **`Function`**：预处理`sum[i][j]`表示`i`到`j`所有石子价值和；
 *            `dp[i][j] = min(dp[i][k] + dp[k+1][j] + sum[i][j])` 对于所有`k`属于`{i, j}`；
 *          * **`Initialize`**：`for each i -> dp[i][i] = 0`；
 *          * **`Answer`**：`dp[0][n - 1]`.
 *
 * @Author: tian
 * @UpdateDate: 2021/1/27 9:14 AM
 */
public class StoneGame {
    public static void main(String[] args) {
        int[] A = {4, 1, 1, 4};
//        int[] A = {1, 1, 1, 1};
//        int[] A = {4, 4, 5, 9};
        System.out.println(stoneGame(A));
    }

    /**
     * 石头合并游戏最小代价问题
     * [4, 1, 1, 4]
     *
     * sumij 4  1  1  4
     *    4  4  5  6  10
     *    1     1  2  6
     *    1        1  5
     *    4           4
     *
     *      [4  1  1  4]
     * dpij  0  1  2  3  //下标索引
     *    0  0  5  8  18 //\从i~j合并4个石头的最小代价
     *    1     0  2  8  //\从i~j合并3个石头的最小代价
     *    2        0  5  //\从i~j合并2个石头的最小代价
     *    3           0
     *
     * @param A An integer array
     * @return an integer
     */
    public static int stoneGame(int[] A) {
        if(A == null || A.length == 0) {
            return 0;
        }

        int n = A.length;
        int[][] sum = new int[n][n];
        for (int i = 0; i < n; i++) {
            sum[i][i] = A[i];
            for (int j = i + 1; j < n; j++) {
                sum[i][j] = sum[i][j - 1] + A[j];
            }
        }

        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 0;
        }

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                int min = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    min = Math.min(min, dp[i][k] + dp[k + 1][j]);
                }
                dp[i][j] = min + sum[i][j];
            }
            //print(dp);
        }

        return dp[0][n - 1];
    }

    private static void print(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
