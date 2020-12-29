package com.sty.algorithm.dynamicprograming;

/**
 * 题目：
 *     给定两个字符串`S`和`T`，求`S`有多少个不同的子串和`T`相同。`S`的子串定义为在`S`中任意去掉0个或者多个字符形成的串。
 *     Given a string S and a string T, count the number of distinct subsequences of T in S. Asubsequence of a string is a new string whict is formed from the original string by deleting some(can be none) of the characters without disturbing the relative positions of the remaining characters.(ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).
 *     Here is an example:
 *     S = "rabbbit", T = "rabbit", Return 3.
 * 算法思路：
 *     动态规划，设dp[i][j] 是从字符串S[0...i]中删除几个字符得到字符串T[0...j]的不同的删除方法种类，动态规划方程如下
 *     如果S[i] = T[j], dp[i][j] = dp[i-1][j-1]+dp[i-1][j]
 *     如果S[i] 不等于 T[j], dp[i][j] = dp[i-1][j]
 *     初始条件：当T为空字符串时，从任意的S删除几个字符得到T的方法为1
 *     dp[0][0] = 1; // T和S都是空串.
 *     dp[1 ... S.length() - 1][0] = 1; // T是空串，S只有一种子序列匹配。
 *     dp[0][1 ... T.length() - 1] = 0; // S是空串，T不是空串，S没有子序列匹配。
 * @Author: tian
 * @UpdateDate: 2020/12/29 10:52 AM
 */
public class DistinctSubsequences {
    public static void main(String[] args) {
        String S = "rabbbit";
        String T = "rabbit";
        System.out.println(numDistinct(S, T)); //3
    }

    /**
     * S\T 0 0 0 0 0 0
     *  1
     *  1
     *  1
     *  1
     *  1
     *  1
     *  1
     */
    public static int numDistinct(String S, String T) {
        int[][] table = new int[S.length() + 1][T.length() + 1];
        for (int i = 0; i < S.length(); i++) {
            table[i][0] = 1;
        }

        for (int i = 1; i <= S.length(); i++) {
            for (int j = 1; j <= T.length(); j++) {
                if(S.charAt(i - 1) == T.charAt(j - 1)) {
                    table[i][j] += table[i - 1][j] + table[i - 1][j - 1];
                }else {
                    table[i][j] += table[i - 1][j];
                }
            }
        }
        return table[S.length()][T.length()];
    }
}
