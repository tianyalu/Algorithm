package com.sty.algorithm.dynamicprograming;

/**
 * ①题目：其一个字符串中的最长回文子串
 *      Given a string S, find the longest palindromic substring in S. You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.
 *      Example: Given the string = "abcdzdcab", return "cdzdc".
 *      Challenge: O(n^2) time is acceptablel. Can you do it in O(n) time.
 * ②算法思路：
 *  一、区间类动态规划 --> `Time O(n^2), Space O(n^2)`
 *      用`dp[i][j]`来存`DP`的状态，需要较多的额外控件：`Space O(n^2)`
 *  `DP`的四要素：
 *      状态：`dp[i][j]`,`s.charAt(i)`到`s.charAt(j)`是否构成一个`Palindrome`.
 *      转移方程：`dp[i][i] = s.charAt(i) == s.charAt(j) && (j - i <= 2) || dp[i + 1][j - 1]`.
 *      初始化：`dp[i][j] = true when j - i <= 2`.
 *      结果：找`maxLen = j - i + 1;`，并得到相应`longest substring`: `longest = s.substring(i, j + 1)`.
 *
 *  二、中心扩展法 --> Time O(n^2) Space O(1)
 *      这种方法的基本思路是遍历数组，以其中的一个元素或者2个元素作为`palindrome`的中心，通过辅助函数，寻找能扩展得到的最长子字符串。
 *      外层循环`O(n)`，内层循环`O(n)`，因此时间复杂度`Time O(n^2)`，相比动态规划二维数组存状态的方法，
 *      因为只需要存最长`palindrome`子字符串本身，这里空间更优化：`Space O(1)`.
 * @Author: tian
 * @UpdateDate: 2021/1/18 6:01 PM
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        String s = "abcdzdcab";
        System.out.println(longestPalindrome1(s));
        System.out.println(longestPalindrome2(s));
    }

    /**
     * 动态规划法：区间DP，Time O(n^2) Space O(n^2)
     *      0 1 2 3 4 5 6 7 8
     *      a b c d z d c a b
     *  0   1
     *  1   0 1
     *  2   0 0 1
     *  3   0 0 0 1
     *  4   0 0 0 0 1
     *  5   0 0 0 1 0 1
     *  6   0 0 1 0 0 0 1
     *  7   0 0 0 0 0 0 0 1
     *  8   0 0 0 0 0 0 0 0 1
     * @param s 待解析字符串
     * @return
     */
    public static String longestPalindrome1(String s) {
        if(s == null || s.length() <= 1) {
            return s;
        }

        int len = s.length();
        int maxLen = 1;
        boolean[][] dp = new boolean[len][len];

        String longest = null;
        for (int k = 0; k < len; k++) {
            for (int i = 0; i < len - k; i++) {
                int j = i + k;
                if(s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;

                    if(j - i + 1 > maxLen) {
                        maxLen = j - i + 1;
                        longest = s.substring(i, j + 1);
                    }
                }
            }
        }

        return longest;
    }

    /**
     * 中心扩展法：Time O(n^2) Space O(1)
     * @param s 待解析字符串
     * @return
     */
    public static String longestPalindrome2(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        String longest = s.substring(0, 1);
        int len = s.length();
        for (int i = 0; i < len; i++) {
            //get the longest palindrome with center of i
            String tmp = helper(s, i, i);
            if(tmp.length() > longest.length()) {
                longest = tmp;
            }

            //get the longest palindrome with center of i, i+1
            tmp = helper(s, i, i + 1);
            if(tmp.length() > longest.length()) {
                longest = tmp;
            }
        }

        return longest;
    }

    /**
     * Given a center, either one letter or two letter, Find the longest palindrome
     * @param s
     * @param begin
     * @param end
     * @return
     */
    private static String helper(String s, int begin, int end) {
        while(begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
            begin--;
            end++;
        }
        return s.substring(begin + 1, end);
    }
}
