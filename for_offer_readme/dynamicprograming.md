## 动态规划

[TOC]


### 5.1 求一个数组中和最大的连续子序列

`foroffer/dynamicprograming/MaxSumOfSubArray`, 参考：[28](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/28.md)  [Maximum-Subarray](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Maximum-Subarray.md)

①题目：

```java
Find the contiguous subarray wijthin an array(containing at least one number) which has the largest sum.
For example, given the array [-2, 1, -3, 4, -1, 2, 1, -5, 4], the contiguous subarray [4, -1, 2, 1] has the largest sum = 6.
More practice: If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
```

输入一个整数数组，数组里有正数也有负数，数组中一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。要求时间复杂度为`O(n)`。

举例说明：例如输入的数组为`{1, -2, 3, 10, -4, 7, 2, -5}`，和最大的子数组为`{3, 10, -4, 7, 2}`，因此输出为该子数组的和18。

②算法思路：

**方案零：分析数组的规律**

我们试着从头到尾逐个累加示例数组中的每个数字，初始化和为0。第一步加上第一个数字1，此时和为1；接下来第二步加上数字-2，和就变成了-1；第三步加上数字3，我们注意到由于此前累计的和是-1，小于0，那如果用-1加上3得到的和是2，比3本身还小。也就是说从第一个数字开始的子数组的和会小于从第三个数字开始的子数组的和，因此我们不用考虑从第一个数组开始的子数组，之前累计的和也被抛弃。

我们从第三数字重新开始累加，此时得到的和是3，接下来第四步加10，得到和为13；第五步加上-4，和为9.我们发现由于-4是一个负数，因此累加-4之后得到的比原来的和还要小。因此我们要把之前得到的和13保存下来，它有可能是最大的子数组的和；第六步加上数字7，9加7的结果是16，此时和比之前最大的和13还要大，把最大的子数组的和由13更新为16；第七步加上2，累加得到的和为18，同时我们也要更新最大子数组的和；第八步加上最后一个数字-5，由于得到的和为13，小于此前最大的和18，因此最终最大的子数组的和为18，对应的子数组是`{3, 10, -4, 7, 2}`。

```java
    /**              ↓
     * arr    1  -2  3  10  -4  7  2  -5
     * curMax 1  -1  3  13   9 16 18  13
     * max    1   1  3  13  13 16 18  18
     * 求一个数组中和最大的连续子序列
     * @param arr 输入的数组
     * @return 最大子序列的和
     */
    public static int findMaximumSumOfSubArray(int[] arr) {
        //参数校验
        if(arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must contain an element");
        }

        //记录最大的子数组和，开始时是最小的整数
        int max = Integer.MIN_VALUE;
        //当前的和
        int curMax = 0;
        //数组遍历
        for (int i : arr) {
            //如果当前和小于等于0，就重新设置当前和
            if(curMax <= 0) {
                curMax = i;
            }else { //如果当前和大于0，累加当前和
                curMax += i;
            }

            //更新记录到最新的子数组和
            if(max < curMax) {
                max = curMax;
            }
        }

        return max;
    }
```

以下参考：`algorithm/dynamicprograming/MaxSumSubArray`

**方案一**

典型的`DP`题：

> 1. 状态`dp[i]`：以`A[i]`为最后一个数的所有`max subarray`的和；
> 2. 递推公式：`dp[i] = A[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0)`；
> 3. 由于`dp[i]`仅取决于`dp[i - 1]`，所以可仅用一个变量来保存前一个状态，而节省内存。

```java
    /**
     * 和最大的连续子序列
     * A:     -2 1 -3 4 -1 2 1 -5 4
     * dp[i]: -2 1 -2 4  3 5 6  1 5 
     * @param A：A list of integers
     * @return An integer indicate the sum of max subArray
     */
    private static int maxSubArray(int[] A) {
        if(A == null || A.length == 0) {
            return 0;
        }

        int[] dp = new int[A.length]; //dp[i] means the maximum subArray ending with A[i]
        dp[0] = A[0];
        int max = dp[0];
        for (int i = 1; i < A.length; i++) {
            dp[i] = A[i] + Math.max(dp[i - 1], 0);
            max = Math.max(max, dp[i]);
        }

        return max;
    }
```

**方案二**

虽然这到题目用`dp`解起来很简单，但是题目说了，问我们能不能采用`divide and conquer`的方法解答，也就是二分法。

假设数组`A[left, right]`存在最大区间，`mid=(left+right)/2`，那么无非就是三种情况：

> 1. 最大值在`A[left, mid - 1]`里面；
> 2. 最大值在`A[mid + 1, right]`里面；
> 3. 最大值跨过了`mid`，也就是我们需要计算`[left, mid - 1]`区间的最大值，以及`[mid + 1, right]`的最大值，然后加上`mid`，三者之和就是总的最大值。

可以看到，对于1和2，我们通过递归可以很方便地求解，然后在同第3的结果比较，就是得到的最大值。

```java
    /**
     * 和最大的连续子序列 --> 分治求解
     * @param A A list of integers
     * @return An integer indicate the sum of max subArray
     */
    public static int maxSubArray2(int[] A) {
        if(A == null || A.length == 0) {
            return 0;
        }

        return findMaxSub(A, 0, A.length - 1, Integer.MIN_VALUE);
    }
    /**
     * recursive to find max sum, may appear on the left or right part, or across mid(from left to right)
     * @param A
     * @param left
     * @param right
     * @param maxSum
     * @return
     */
    public static int findMaxSub(int[] A, int left, int right, int maxSum) {
        if(left > right) {
            return Integer.MIN_VALUE;
        }

        //get max sub sum from both left and right cases
        int mid = (left + right) / 2;
        int leftMax = findMaxSub(A, left, mid - 1, maxSum);
        int rightMax = findMaxSub(A, mid + 1, right, maxSum);
        maxSum = Math.max(maxSum, Math.max(leftMax, rightMax));

        //get max sum of this range(case: across mid)
        //so need to expend to both left and right using mid as center
        //mid -> left
        int sum = 0, midLeftMax = 0;
        for(int i = mid - 1; i >= left; i--) {
            sum += A[i];
            if(sum > midLeftMax) {
                midLeftMax = sum;
            }
        }
        //mid -> right
        int midRightMax = 0; sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += A[i];
            if(sum > midRightMax) {
                midRightMax = sum;
            }
        }

        // get the max value from the left, right and across mid
        maxSum = Math.max(maxSum, midLeftMax + midRightMax + A[mid]);

        return maxSum;
    }
```



