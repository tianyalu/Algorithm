package com.sty.algorithm.greedy;

/**
 * 加油站问题
 *  ①题目：
 *      在一条环路上有`N`个加油站，其中第`i`个加油站有汽油`gas[i]`，并且从第`i`个加油站前往第`i+1`个加油站需要消耗汽油`cost[i]`。
 *    你有一辆油箱容量无限大的汽车，现在要从某一个加油站出发绕路一周，一开始油箱为空。求可环绕环路一周时出发的加油站的编号，
 *    若不存在环绕一周的方案，则返回-1。
 *      There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
 *    You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to
 *    its next station (i+1). You begin the journey with an empty tank at one of the gas stations.
 *      Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.
 *      Note: The solution is guaranteed to be unique.
 *  ②算法思路：
 *      首先我们可以得到所有加油站的油量`totalGas`，以及总里程需要消耗的油量`totalCost`，如果`totalCost`大于`totalGas`，
 *    那么铁定不能够走完整个里程。
 *      如果`totalGas`大于`totalCost`了，那么就能走完整个里程了，假设现在我们到达了第`i`个加油站，这时候还剩余的油量为`sum`，
 *    如果`sum + gas[i] - cost[i]`小于0，我们无法走到下一个加油站，所以起点一定不在第`i`个以及之前的加油站里面
 *    （都铁定走不到第`i+1`号加油站），起点只能在`i+1`或者其后面。
 *
 * @Author: tian
 * @UpdateDate: 2021/2/1 5:07 PM
 */
public class GasStation {
    public static void main(String[] args) {
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        System.out.println(canCompleteCircuit(gas, cost));
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        if(gas == null || cost == null || gas.length == 0 || cost.length == 0) {
            return -1;
        }

        int sum = 0, total = 0, index = -1;
        for (int i = 0; i < gas.length; i++) {
            sum += gas[i] - cost[i];
            total += gas[i] - cost[i];
            if(sum < 0) {
                index = i;
                sum = 0;
            }
        }

        return total < 0 ? -1 : index + 1;
        //index should be updated here for cases ([5], [4])
        //total < 0 is for case [2], [2]
    }
}
