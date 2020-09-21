package com.sty.algorithm.other;

/**
 * 跳台阶问题：
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
 *
 * 分析：此题为典型的递归问题，递归问题的典型解法就是找到递推公式和边界条件。
 * 青蛙跳上第N阶前可能在第N-1，N-2 阶得到递推公式f(n)=f(n-1)+f(n-2)。
 * 考虑边界条件当n为1时，有1种方法。当N为2时有两种方法。
 * @Author: tian
 * @UpdateDate: 2020/9/18 8:47 PM
 */
public class JumpFloor {
    public static void main(String[] args) {
        System.out.println(jumpFloor1(4));
        System.out.println(jumpFloor2(4));
    }

    private static int jumpFloor1(int target) {
        if(target <= 0) {
            return 0;
        }
        if(target < 3) {
            return target;
        }else {
            return jumpFloor1(target - 1) + jumpFloor1(target - 2);
        }
    }

    /**
     * 考虑时间效率，采用动态规划进行优化
     * @param target
     * @return
     */
    private static int jumpFloor2(int target) {
        if(target <= 0) {
            return 0;
        }
        if(target < 3) {
            return target;
        }
        int temp1 = 1, temp2 = 2;
        int res = 0;
        for (int i = 3; i <= target; i++) {
            res = temp1 + temp2;
            temp1 = temp2;
            temp2 = res;
        }
        return res;
    }
}
