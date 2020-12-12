package com.sty.algorithm.array;

/**
 * @Author: tian
 * @UpdateDate: 2020/12/12 3:00 PM
 */
public class JudgePalindrome {
    public static void main(String[] args) {
        System.out.println(isPalindrome(1221));
    }

    public static boolean isPalindrome(int x) {
        if(x < 0) {
            return false;
        }
        int div = 1;
        while (div <= x/10) {
            div *= 10;
        }
        while (x > 0) {
            if(x/div != x%10) {
                return false;
            }
            x = (x % div) / 10; // %:去掉最高位  /:去掉最低位
            div /= 100; //去掉的最高位和最低位，所以要/100
        }
        return true;
    }
}
