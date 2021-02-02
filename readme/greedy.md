## 贪心算法

[TOC]

### 5.1 跳跃游戏

#### 5.1.1 能否跳跃到数组的最后一个位置

`greedy/JumpGame`, 参考：[Jump-Game](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Greedy/Jump-Game.md)

①题目：

```java
Given an array of non-negative integers, you are initially positioned at the first index of the array. Each element in the array represents your maximum jump length at that position. Determine if you are able to reach the last index.
For example: A = [2, 3, 1, 1, 4], return true; A = [3, 2, 1, 0, 4], return false.
```

给出一个非负整数数组，你最初定位在数组的第一个位置，数组中的每个元素代表你在那个位置可以跳跃的最大长度，判断你能否达到数组的最后一个位置。

②算法思路：

注意题目中`A[i]`表示的是在位置`i`“最大”的跳跃距离，而并不是指在位置`i`只能跳`A[i]`的距离。所以跳到位置`i`后台，能达到的最大的距离至少是`i+A[i]`。用`greedy`来解，记录一个当前能达到的最远的距离`maxIndex`:

> 1. 能跳到位置`i`的条件：`i <= maxIndex`;
> 2. 一旦跳到`i`，则`maxIndex = max(maxIndex, i + A[i])`；
> 3. 能跳到最后一个位置`n - 1`的条件是：`maxIndex >= n - 1`.

③算法实现：

```java
    //能否跳跃到数组的最后一个位置
    public static boolean canJump2End(int[] A) {
        //think it as merging n intervals
        if(A == null || A.length == 0) {
            return false;
        }

        int farthest = A[0];
        for (int i = 1; i < A.length; i++) {
            if(i <= farthest && A[i] + i >= farthest) { //能跳到i，并且i位置处能跳得更远
                farthest = A[i] + i;
            }
        }
        return farthest >= A.length - 1;
    }
```

#### 5.1.2 最少的跳跃次数到达数组的最后一个位置

①题目：

```java
Given an array of non-negative integers, you are initially positioned at the first index of the array. Each element in the array represents your maximum jump length at that position.Your goal is to reach the last index in the minimum number of jumps.
For example: Given array A = [2, 3, 1, 1, 4], The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
```

给出一个非负整数数组，你最初定位在数组的第一个位置，数组中的每个元素代表你在哪个位置可以跳跃的最大长度，你的目标是使用最少的跳跃测试到达数组的最后一个位置。

②算法思路：

同样可以用`greedy`解决，与5.1.1不同的是，求的不是对每个`i`从`A[0:i]`能跳到最远的距离，而是计算跳了`k`次后能达到的最远距离，这里的通项公式为：
$$
d[k] = max(i + A[i]), \quad d[k - 2] < i \leq d[k - 1]
$$
③算法实现：

```java
    //最少的跳跃次数到达数组的最后一个位置
    public static int minimumTimeJump2End(int[] A) {
        if(A == null || A.length == 0) {
            return  -1;
        }

        int start = 0, end = 0, jumps = 0;
        while (end < A.length - 1) {
            jumps++;
            int farthest = end;
            for (int i = start; i <= end; i++) {
                if(A[i] + i > farthest) {
                    farthest = A[i] + i;
                }
            }
            start = end + 1;
            end = farthest;
        }
        return jumps;
    }
```

### 5.2 加油站问题

`greedy/GasStation`，参考：[Gas-Station](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Greedy/Gas-Station.md)

①题目：

```java
There are N gas stations along a circular route, where the amount of gas at station i is gas[i]. You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.
Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.
Note: The solution is guaranteed to be unique.
```

在一条环路上有`N`个加油站，其中第`i`个加油站有汽油`gas[i]`，并且从第`i`个加油站前往第`i+1`个加油站需要消耗汽油`cost[i]`。你有一辆油箱容量无限大的汽车，现在要从某一个加油站出发绕路一周，一开始油箱为空。求可环绕环路一周时出发的加油站的编号，若不存在环绕一周的方案，则返回-1。

②算法思路：

首先我们可以得到所有加油站的油量`totalGas`，以及总里程需要消耗的油量`totalCost`，如果`totalCost`大于`totalGas`，那么铁定不能够走完整个里程。

如果`totalGas`大于`totalCost`了，那么就能走完整个里程了，假设现在我们到达了第`i`个加油站，这时候还剩余的油量为`sum`，如果`sum + gas[i] - cost[i]`小于0，我们无法走到下一个加油站，所以起点一定不在第`i`个以及之前的加油站里面（都铁定走不到第`i+1`号加油站），起点只能在`i+1`或者其后面。

③算法实现：

```java
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
```

### 5.3 分糖果

`greedy/candy`, 参考：[Candy](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Greedy/Candy.md)

①题目：

```java
There are N children standing in a line. Each child is assigned a rating value. You are giving candies to these children subjected to the following requirements:
Each child must have at least one candy. Children with a higher rating get more candies than their neighbors. What is the minimum candies you must give?
```

有`N`个小孩站成一列，每个小孩有一个评级，按照以下要求，给小孩分糖果：

* 每个小孩至少得到一颗糖果；
* 评级越高的小孩比他相邻的两个小孩得到更多的糖果。

最少需要准备多少颗糖果？

②算法思路：

首先我们会给每个小孩一颗糖果，然后从左到右，假设第`i`个小孩的等级比第`i-1`个小孩高，那么第`i`个小孩的糖果数量就是第`i-1`个小孩的糖果数量再加一。然后我们从右到左，如果第`i`个小孩的等级大于第`i+1`个小孩的，同时第`i`个小孩此时的糖果数量小于第`i+1`的小孩，那么第`i`个小孩的糖果数量就是第`i+1`个小孩的糖果数量再加一。

③算法实现：

```java
    /**
     * [1 0 2]
     *  1 1 1
     *  1 1 2
     *  2 1 2
     * @param ratings
     * @return
     */
    public static int candy(int[] ratings) {
        if(ratings == null || ratings.length == 0) {
            return 0;
        }

        int[] count = new int[ratings.length];
        Arrays.fill(count, 1);
        int sum = 0;
        for (int i = 1; i < ratings.length; i++) {
            if(ratings[i] > ratings[i - 1]) {
                count[i] = count[i - 1] + 1;
            }
        }

        for (int i = ratings.length - 1; i > 0; i--) {
            sum += count[i];
            if(ratings[i - 1] > ratings[i] && count[i - 1] <= count[i]) { //second round has 2 conditions
                count[i - 1] = count[i] + 1;
            }
        }
        sum += count[0];

        return sum;
    }
```

