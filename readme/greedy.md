## 贪心算法

[TOC]

### 5.1 跳跃游戏

#### 5.1.1 能否跳跃到数组的最后一个位置



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

