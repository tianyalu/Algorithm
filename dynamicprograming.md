## 动态规划

[TOC]

### 4.1 求两个字符串的公共子序列个数

`dynamicprograming/DistinctSubsequences` 参考：[https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Distinct-Subsequences.md](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Distinct-Subsequences.md)

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

