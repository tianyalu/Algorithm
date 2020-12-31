package com.sty.algorithm.dynamicprograming;

/**
 * ①题目：求最长公共子序列的数目，注意这里的子序列可以不是连续序列。
 *     Given tow strings, find the longest common subsequence (LCS). Your code should return the length of LCS.
 *     Have you met this question in a real interview?
 *     Yes. Example:
 *     For "ABCD" and "EDCA", the LCS is "A"(or "D", "C"), return 1.
 *     For "ABCD" and "EACB", the LCS is "AC", return 2.
 * ②解题思路：
 *     求“最长”类的题目往往与动态规划有点关系，这里是两个字符串，故应为双序列动态规划。
 *     这道题的状态很容易找，不妨先试试以`f[i][j]`表示字符串`A`的前`i`位字符串和字符串`B`的前`j`位的最长公共子序列数目，
 *   那么接下来试试寻找其状态转移方程。从实际例子`ABCD`和`EDCA`出发，首先初始化`f`的长度为字符串长度加1，
 *   那么有`f[0][0]=0`，`f[0][*]=0`，`f[*][0]=0`，最后应该返回`f[lenA][lenB]`，即`f`中索引与字符串索引对应
 *   （字符串索引从1开始算起），那么在`A`的第一个字符与`B`的第一个字符相等时，`f[1][1]=1+f[0][0]`，否则`f[1][1]=max(f[0][1], f[1][0])`。
 *     推而广之，也就意味着若`A[i]=B[j]`，则分别去掉这两个字符后，原`LCS`数目减一，那为什么一定是1而不是0呢？
 *   因为不管公共子序列是以那个字符结尾，在`A[i]=B[j]`时`LCS`最多只能增加1；而在`A[i]!=B[j]`时，
 *   由于`A[i]`或者`B[j]`不可能同时出现在最终的`LCS`中，故这个问题可进一步缩小，`f[i][j]=max(f[i-1][j],f[i][j-1])`.
 *   需要注意的是这种状态转移方程只依赖最终的`LCS`数目，而不依赖于公共子序列到底是以第几个索引结束。
 * @Author: tian
 * @UpdateDate: 2020/12/31 9:28 AM
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
        String A = "ABCD";
        String B = "EACB";
        System.out.println(longestCommonSubsequence(A, B));
    }

    public static int longestCommonSubsequence(String A, String B) {
        if(A == null || A.length() == 0) {
            return 0;
        }
        if(B == null || B.length() == 0) {
            return 0;
        }

        int lenA = A.length();
        int lenB = B.length();
        int[][] table = new int[lenA + 1][lenB + 1];

        for (int i = 1; i < lenA + 1; i++) {
            for (int j = 1; j < lenB + 1; j++) {
                if(A.charAt(i - 1) == B.charAt(j - 1)) {
                    table[i][j] = table[i-1][j-1] + 1;
                }else {
                    table[i][j] = Math.max(table[i - 1][j], table[i][j - 1]);
                }
            }
        }
        return table[lenA - 1][lenB - 1];
    }

}
