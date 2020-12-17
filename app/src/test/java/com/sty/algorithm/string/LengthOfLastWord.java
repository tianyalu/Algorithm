package com.sty.algorithm.string;

/**
 * 题目：
 *   给定一个字符串，包含大小写字母、空格' '，请返回其最后一个单词的长度；如果不存在最后一个单词，请返回0.
 *   Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word in the string. If the last word does not exist, return 0.
 *   Note: A word is defined as a character sequence consists of non-space characters only.
 *   For example, Given s = "Hello World", return s.
 *
 * 算法思路：
 *   关键点在于确定最后一个字符串之前的空格，此外还需要考虑末尾空格这一特殊情况，从最后往前扫码。
 *
 * @Author: tian
 * @UpdateDate: 2020/12/17 11:37 AM
 */
public class LengthOfLastWord {
    public static void main(String[] args) {
        String s = "Hello World ";
        System.out.println(getLengthOfLastWord(s));
    }

    public static int getLengthOfLastWord(String s) {
        if(s == null || s.isEmpty()) {
            return 0;
        }

        int len = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if(' ' == s.charAt(i)) {
                if(len > 0) {
                    return len;
                }
            }else {
                len++;
            }
        }
        return len;
    }
}
