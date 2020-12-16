package com.sty.algorithm.string;

/**
 * 题目：判断一个字符串是否是回文
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 * For example, "A man, a plan, a canal: Panama" is a palindrome. "race a car" is a palindrome.
 * Note:
 * 	Have you consider that the string might be empty? This is a good question to ask during an interview.
 * 	For the purpose of this problem, we define empty string as valid palindrome.
 *
 * 算法思路：
 *  字符串的回文判断问题，由于字符串可随机访问，故逐个比较首尾字符是否相等最为便利，即常见的“两根指针”技法。
 *  两步走：a. 找到最左边和最右边的第一个合法字符串（字母或者字符）b. 一致转换为小写进行比较。
 * @Author: tian
 * @UpdateDate: 2020/12/16 10:06 AM
 */
public class JudgePalindrome {
    public static void main(String[] args) {
        //String s = "A man, a plan, a canal: Panama";
        String s = "race a car";
        System.out.println("isPalindrome: " + isPalindrome(s));
    }

    public static boolean isPalindrome(String s) {
        if(s == null || s.isEmpty()) {
            return true;
        }
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if(!Character.isAlphabetic(s.charAt(left))){
                left++;
                continue;
            }
            if(!Character.isAlphabetic(s.charAt(right))) {
                right--;
                continue;
            }
            if(Character.toLowerCase(s.charAt(left)) == Character.toLowerCase(s.charAt(right))) {
                left++;
                right--;
            }else {
                return false;
            }
        }
        return true;
    }
}
