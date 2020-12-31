## 动态规划

[TOC]

### 4.1 求两个字符串的公共子序列个数

`dynamicprograming/DistinctSubsequences` 参考：[Distinct-Subsequences](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Distinct-Subsequences.md)

①题目：

```java
Given a string S and a string T, count the number of distinct subsequences of T in S. A subsequence of a string is a new string whict is formed from the original string by deleting some(can be none) of the characters without disturbing the relative positions of the remaining characters.(ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).
Here is an example:
S = "rabbbit", T = "rabbit", Return 3.
```

给定两个字符串`S`和`T`，求`S`有多少个不同的子串和`T`相同。`S`的子串定义为在`S`中任意去掉0个或者多个字符形成的串。

②算法思路：

动态规划，设`dp[i][j]`是从字符串`S[0...i]`中删除几个字符得到字符串`T[0...j]`的不同的删除方法种类，动态规划方程如下：
$$
dp[i][j] = 
\begin{cases} 
dp[i-1][j-1] + dp[i-1][j], \quad S[i] = T[j] \\  
dp[i-1][j], \quad S[i] \neq T[j]
\end{cases} \\
初始条件: 当T为空字符串时，从任意的S删除几个字符得到T的方法为1 \\
dp[0][0] = 1, \quad T和S都是空串 \\
dp[1...S.length() - 1][0] = 1, \quad T是空串，S只有一种子序列匹配 \\
dp[0][1...T.length() - 1] = 0, \quad S是空串，T不是空串，S没有子序列匹配
$$
状态方程理解：

> 1. `S[i] = T[j]`：此时问题等价于去掉`S[i]`中的第`i`项之后的集合与`T[j]`中去掉第`j`项之后的集合求子序列的子问题；或者仅`S[i]`中去掉第`i`项之后的集合与`T`集合求子序列的问题（此时也肯能存在解）；
> 2. `S[i] != T[j]`：此时因为不相等，所以只能求`S[i]`中去掉第`i`项之后的集合与`T`集合的子序列。

③算法实现：

```java
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
```

### 4.2 求两个字符串的最长公共子序列个数

`dynamicprograming/LongestCommonSubsequence` 参考：[Longest-Common-Subsequence](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Longest-Common-Subsequence.md)

①题目：

```java
Given tow strings, find the longest common subsequence (LCS). Your code should return the length of LCS. 
Have you met this question in a real interview?
Yes. Example: 
For "ABCD" and "EDCA", the LCS is "A"(or "D", "C"), return 1.
For "ABCD" and "EACB", the LCS is "AC", return 2.
```

求最长公共子序列的数目，注意这里的子序列可以不是连续序列。

②解题思路：

求“最长”类的题目往往与动态规划有点关系，这里是两个字符串，故应为双序列动态规划。

这道题的状态很容易找，不妨先试试以`f[i][j]`表示字符串`A`的前`i`位字符串和字符串`B`的前`j`位的最长公共子序列数目，那么接下来试试寻找其状态转移方程。从实际例子`ABCD`和`EDCA`出发，首先初始化`f`的长度为字符串长度加1，那么有`f[0][0]=0`，`f[0][*]=0`，`f[*][0]=0`，最后应该返回`f[lenA][lenB]`，即`f`中索引与字符串索引对应（字符串索引从1开始算起），那么在`A`的第一个字符与`B`的第一个字符相等时，`f[1][1]=1+f[0][0]`，否则`f[1][1]=max(f[0][1], f[1][0])`。

推而广之，也就意味着若`A[i]=B[j]`，则分别去掉这两个字符后，原`LCS`数目减一，那为什么一定是1而不是0呢？因为不管公共子序列是以那个字符结尾，在`A[i]=B[j]`时`LCS`最多只能增加1；而在`A[i]!=B[j]`时，由于`A[i]`或者`B[j]`不可能同时出现在最终的`LCS`中，故这个问题可进一步缩小，`f[i][j]=max(f[i-1][j],f[i][j-1])`.需要注意的是这种状态转移方程只依赖最终的`LCS`数目，而不依赖于公共子序列到底是以第几个索引结束。

总结状态转移方程如下：
$$
f[i][j] = 
\begin{cases}
f[i-1][j-1] + 1, \quad A[i] = B[j] \\
max(f[i-1][j], f[i][j-1]), \quad A[i] \neq B[j]
\end{cases}
$$
③算法实现：

```java
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
```

