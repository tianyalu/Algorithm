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

