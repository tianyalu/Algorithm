## 动态规划

[TOC]

### 4.1 求两个字符串的公共子序列个数

`dynamicprograming/DistinctSubsequences` 参考：[Distinct-Subsequences](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Distinct-Subsequences.md)

①题目：

```java
Given a string S and a string T, count the number of distinct subsequences of T in S. A subsequence of a string is a new string whict is formed from the original string by deleting some(can be none) of the characters without disturbing the relative positions of the remaining characters.(ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).
Here is an example:
S = "rabbbit", T = "rabbit", Return 3.
```

给定两个字符串`S`和`T`，求`S`有多少个不同的子串和`T`相同。`S`的子串定义为在`S`中任意去掉0个或者多个字符形成的串。

②算法思路：

动态规划，设`dp[i][j]`是从字符串`S[0...i]`中删除几个字符得到字符串`T[0...j]`的不同的删除方法种类，动态规划方程如下：
$$
dp[i][j] = 
\begin{cases} 
dp[i-1][j-1] + dp[i-1][j], \quad S[i] = T[j] \\  
dp[i-1][j], \quad S[i] \neq T[j]
\end{cases} \\
初始条件: 当T为空字符串时，从任意的S删除几个字符得到T的方法为1 \\
dp[0][0] = 1, \quad T和S都是空串 \\
dp[1...S.length() - 1][0] = 1, \quad T是空串，S只有一种子序列匹配 \\
dp[0][1...T.length() - 1] = 0, \quad S是空串，T不是空串，S没有子序列匹配
$$
状态方程理解：

> 1. `S[i] = T[j]`：此时问题等价于去掉`S[i]`中的第`i`项之后的集合与`T[j]`中去掉第`j`项之后的集合求子序列的子问题；或者仅`S[i]`中去掉第`i`项之后的集合与`T`集合求子序列的问题（此时也肯能存在解）；
> 2. `S[i] != T[j]`：此时因为不相等，所以只能求`S[i]`中去掉第`i`项之后的集合与`T`集合的子序列。

③算法实现：

```java
    /**
     * S\T 0 0 0 0 0 0
     *  1
     *  1
     *  1
     *  1
     *  1
     *  1
     *  1
     */
    public static int numDistinct(String S, String T) {
        int[][] table = new int[S.length() + 1][T.length() + 1];
        for (int i = 0; i < S.length(); i++) {
            table[i][0] = 1;
        }

        for (int i = 1; i <= S.length(); i++) {
            for (int j = 1; j <= T.length(); j++) {
                if(S.charAt(i - 1) == T.charAt(j - 1)) {
                    table[i][j] += table[i - 1][j] + table[i - 1][j - 1];
                }else {
                    table[i][j] += table[i - 1][j];
                }
            }
        }
        return table[S.length()][T.length()];
    }
```

### 4.2 求两个字符串的最长公共子序列个数

`dynamicprograming/LongestCommonSubsequence` 参考：[Longest-Common-Subsequence](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Longest-Common-Subsequence.md)

①题目：

```java
Given tow strings, find the longest common subsequence (LCS). Your code should return the length of LCS. 
Have you met this question in a real interview?
Yes. Example: 
For "ABCD" and "EDCA", the LCS is "A"(or "D", "C"), return 1.
For "ABCD" and "EACB", the LCS is "AC", return 2.
```

求最长公共子序列的数目，注意这里的子序列可以不是连续序列。

②解题思路：

求“最长”类的题目往往与动态规划有点关系，这里是两个字符串，故应为双序列动态规划。

这道题的状态很容易找，不妨先试试以`f[i][j]`表示字符串`A`的前`i`位字符串和字符串`B`的前`j`位的最长公共子序列数目，那么接下来试试寻找其状态转移方程。从实际例子`ABCD`和`EDCA`出发，首先初始化`f`的长度为字符串长度加1，那么有`f[0][0]=0`，`f[0][*]=0`，`f[*][0]=0`，最后应该返回`f[lenA][lenB]`，即`f`中索引与字符串索引对应（字符串索引从1开始算起），那么在`A`的第一个字符与`B`的第一个字符相等时，`f[1][1]=1+f[0][0]`，否则`f[1][1]=max(f[0][1], f[1][0])`。

推而广之，也就意味着若`A[i]=B[j]`，则分别去掉这两个字符后，原`LCS`数目减一，那为什么一定是1而不是0呢？因为不管公共子序列是以那个字符结尾，在`A[i]=B[j]`时`LCS`最多只能增加1；而在`A[i]!=B[j]`时，由于`A[i]`或者`B[j]`不可能同时出现在最终的`LCS`中，故这个问题可进一步缩小，`f[i][j]=max(f[i-1][j],f[i][j-1])`.需要注意的是这种状态转移方程只依赖最终的`LCS`数目，而不依赖于公共子序列到底是以第几个索引结束。

总结状态转移方程如下：
$$
f[i][j] = 
\begin{cases}
f[i-1][j-1] + 1, \quad A[i] = B[j] \\
max(f[i-1][j], f[i][j-1]), \quad A[i] \neq B[j]
\end{cases}
$$
③算法实现：

```java
    public static int longestCommonSubsequence(String A, String B) {
        if(A == null || A.length() == 0) {
            return 0;
        }
        if(B == null || B.length() == 0) {
            return 0;
        }

        int lenA = A.length();
        int lenB = B.length();
        int[][] table = new int[lenA + 1][lenB + 1];

        for (int i = 1; i < lenA + 1; i++) {
            for (int j = 1; j < lenB + 1; j++) {
                if(A.charAt(i - 1) == B.charAt(j - 1)) {
                    table[i][j] = table[i-1][j-1] + 1;
                }else {
                    table[i][j] = Math.max(table[i - 1][j], table[i][j - 1]);
                }
            }
        }
        return table[lenA - 1][lenB - 1];
    }
```

### 4.3 求一个字符串的最长递增子序列

`dynamicprograming/LongestIncreasingSubsequence` 参考：[Longest-Increasing-Subsequence](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Longest-Increasing-Subsequence.md)

①题目：

```java
Given a sequence of integers, find the longest increasing subsequence(LIS). Your code should return the length of the LIS.
Example For [5, 4, 1, 2, 3], the LIS is [1, 2, 3], return 3.
For [4, 2, 4, 5, 3, 7], the LIS is [4, 4, 5, 7], return 4.
```

②解题思路

**方案一：** 动态规划，时间复杂 O(n<sup>2</sup>)

`dp[i]`表示以`i`结尾的子序列中`LIS`的长度，然后我们用`dp[j](0 <= j < i)`来表示在`i`之前的`LIS`的长度。当`a[i] > a[j]`时需要进行判断，是否将`a[i]`加入到`dp[j]`当中。为了保证我们每次加入都是得到一个最优的`LIS`，有两点需要注意：第一，每次`a[i]`都应当加入最大的那个`dp[j]`，保证局部性质最优，也就是需要找到`max(dp[j](0 <= j < i))`；第二，每次加入之后，我们都应当更新`dp[i]`的值，显然`dp[i] = dp[j] + 1`。递推公式如下所示：
$$
dp[i] = max(dp[j](0< =j < i)) + (a[i]>a[j] ? 1:0)
$$


```java
    public static int longestIncreasingSubsequence(int[] nums){
        int[] f = new int[nums.length];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            f[i] = 1; //相当于给表中每个位置初始化为1
            for (int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
            if(f[i] > max) {
                max = f[i];
            }
            //System.out.println(f[i]);
        }
        return max;
    }
```

**方案二：**二分搜索，时间复杂度`O(nlogn)`

开一个栈，每次取栈顶元素`top`和读到的元素`temp`做比较，如果`temp > top`则将`temp`入栈；如果`temp < top`则二分查找栈中的比`temp`大的第一个数，并用`temp`替换它，最长序列长度即为栈的大小`top`。

这也是很好理解的，对于`x`和`y`，如果`x < y`且`Stack[y] < Stack[x]`，用`Stack[x]`替换`Stack[y]`，此时最长序列长度没有改变，但序列`Q`的“潜力”增大了。

举例：原序列为 `1, 5, 8, 3, 6, 7`，栈为`1, 5, 8`，此时读到`3`，用`3`替换`5`，得到`1, 3, 8`；再读`6`，用`6`替换`8`，得到`1, 3, 6`；再读`7`，得到最终栈为`1, 3, 6, 7`，最长递增子序列长度为`4`。

```java
    /**
     * 法二：堆栈+二分搜索法(缺点：仅能找出长度，最后的结果不一定是真正的子序列)
     * @param A 目标数组
     * @return
     */
    public static int findLongest(int[] A) {
        int length = A.length;
        int[] B = new int[length];
        B[0] = A[0];
        int end = 0;
        for (int i = 1; i < length; i++) {
            //如果当前数比B中最后一个数还大，直接添加
            if(A[i] >= B[end]) {
                B[++end] = A[i];
                continue;
            }
            //否则需要先找到替换位置
            int pos = findInsertPos(B, A[i], 0, end);
            B[pos] = A[i];
        }
        for (int i = 0; i <= end; i++) {
            System.out.print(B[i] + " ");
        }
        System.out.println();
        return end + 1;
    }

    /**
     * 二分查找第一个大于等于n的位置
     * @param B 目标数组
     * @param n 目标值
     * @param start 目标搜索起始位置
     * @param end 目标搜索结束位置
     * @return
     */
    private static int findInsertPos(int[] B, int n, int start, int end) {
        while (start <= end) {
            int mid = start + (end - start) / 2;  //直接使用（high + low）/ 2 可能导致溢出
            if(B[mid] < n) {
                start = mid + 1;
            }else if(B[mid] > n) {
                end = mid - 1;
            }else {
                return mid;
            }
        }
        return start;
    }
```

### 4.4 股票最高价格

`dynamicprograming/StockTransaction` 参考：[stock](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/stock.md)

#### 4.4.1 仅限一次交易

①题目：

```java
Say you have an array for which the ith element is the price of a given stock on day i. If you were only permitted to complete at most one transaction(ie, buy one and sell one share of he stock), design an algorithm to find the maximum profit.
```

买股票问题，一个数组`prices`，其中`prices[i]`表示第`i`天股票的价格，根据题意，我们知道只能进行一次交易，但需要最大利润。

②解题思路：

需要在最低价买入，最高价卖出，当然买入一定要在卖出之前。只需要遍历一次数组，通过一个变量记录当前最低价格，同时算出此次交易利润，并与当前最大值比较即可。

③解题代码：

```java
    private static int maxProfitInOneTransaction(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }

        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            }
            maxProfit = Math.max(maxProfit, price - minPrice);
        }
        
        return maxProfit;
    }
```

#### 4.4.2 可以多次交易

①题目：

```java
Say you have an array for which the ith element is the price of a given stock on day i.
Design an algorithm to find the maximum profit. You may complete as many transactions as you like(ie, buy one and sell one share of the stock multiple times). However, you may not engage in multiple transactions at the same time(ie, you must sell the stock before you buy again).
```

假设有一个数组，它的第`i`个元素是一个给定的股票在第`i`天的价格，设计一个算法来找到最大的利润。你可以完成尽可能多的交易（多次买卖股票），然而你不能同时参与多个交易（你必须在再次购买前出售股票）。

②解题思路：

因为不限制交易次数，我们在第`i`天买入，如果发现`i+1`天比`i`高，那么就可以累加到利润里面。

③算法实现：

```java
    private static int maxProfitInMultiTransactions(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }

        int maxProfit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int diff = prices[i + 1] - prices[i];
            if(diff > 0) {
                maxProfit += diff;
            }
        }

        return maxProfit;
    }
```

#### 4.4.3 最多两笔交易

①题目：

```java
Say ou have an array for which the ith element is the price of a given stock on day i.
Design an algorithm to find the maximum profit. You may complete at most two transactions.
Note: You may not engage in multiple transactions at the sam time(ie, you must sell the stock before you buy again).
```

假设你有一个数组，它的第`i`个元素是一支给定的股票在第`i`天的价格，设计一个算法来找到最大利润。你最多可以完成两笔交易，然而你不能同时参与多个交易（你必须在再次购买前出售股票）。

②解题思路：

最多允许两次不相交的交易，也就意味着这两次交易间存在某一分界线，考虑到可以只交易一次，也可交易0次，故分界线的变化范围为第一天至最后一天，值需要考虑分界线两边各自的最大利润，最后选出利润和最大的即可。

这种方法抽象之后则为首先将 [1, n] 拆分为 [1, i] 和 [i+1, n]，参考买股票的第一题计算各自区间的最大利润即可。[1, i] 区间的最大利润很好算，但是如何计算 [i+1, n] 区间的最大利润呢？难道有重复`n`次才能得到？注意到区间的右侧`n`是个不变值，我们从 [1, i] 计算最大利润时更新波谷的值，那么我们可否逆序计算最大利润呢？这时候就需要更新记录波峰的值了。

③算法实现：

```java
    private static int maxProfitInAtMostTwoTransactions(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }

        //get profit in the front of prices
        int[] profitFront = new int[prices.length]; //列表中存放在每个价格出售时的最大利润（从前往后）
        profitFront[0] = 0;
        for (int i = 1, valley = prices[0]; i < prices.length; i++) {
            profitFront[i] = Math.max(profitFront[i - 1], prices[i] - valley);
            valley = Math.min(valley, prices[i]);  //valley代表当前之前的最低价格
        }

        //get profit in the back of prices, (i, n)
        int[] profitBack = new int[prices.length];  //列表中存放在每个价格出售时的最大利润（从后往前）
        profitBack[prices.length - 1] = 0;
        for (int i = prices.length - 2, peak = prices[prices.length - 1]; i >=0 ; i--) {
            profitBack[i] = Math.max(profitBack[i + 1], peak - prices[i]);
            peak = Math.max(peak, prices[i]); //peak代表当前之后的最高价格
        }

        //add the profit front and back
        int profit = 0;
        for (int i = 0; i < prices.length; i++) {
            profit = Math.max(profit, profitFront[i] + profitBack[i]);
        }

        return profit;
    }
```

#### 4.4.4 最多`K`笔交易

①题目：

```java
Say you have an array for which the ith element is the price of a given stock on day i. Design an algorithm to find the maximum profit. You may complete at most k transactions.
Example: Given prices = [4, 4, 6, 1, 1, 4, 2, 5], and k = 2, return 6.
Note: You may not engage in multiple transactions at the same time(i.e.,you must sell the stock before you buy again).
Challenge: O(nk) time.
```

题目和4.4.3 一样，要求交易`k`次，时间复杂度`O(nk)`。

②算法思路：

仍然用动态规划来完成。我们维护两种变量，一个是当前到达第`i`天可以最多进行`j`次交易，最好的利润是`global[i][j]`，另一个是当前达到第`i`天，最多可进行`j`次交易，并且最后一次交易在当天卖出的最好利润是`local[i][j]`，递推公式如下：
$$
global[i][j] = max(local[i][j], global[i-1][j])
$$
也就是取当前局部最好的，和过往全局最好的两者中最大的那个（因为最后一次交易如果包含当前天一定在局部最好的里面，否则一定在过往全局最优的里面）。全局（到达第`i`天进行`j`次交易的最大收益）=`max{`局部（在第`i`天交易后，恰好满足`j`次交易），全局（到达第`i-1`天时已满足`j`次交易）`}`。对于局部变量的维护，递推公式如下：
$$
local[i][j] = max(global[i-1][j-1] + max(diff, 0), local[i-1][j] + diff)
$$
也就是看两个量：第一个是全局到`i-1`天进行`j-1`次交易，然后加上今天的交易，如果今天是赚钱的话（也就是前面只要`j-1`次交易，最后一次交易取当天），第二个量则是取`local`第`i-1`天`j`次交易，然后加上今天的差值（这里因为`local[i-1][j]`比如包含第`i-1`天卖出的交易，所以现在变成第`i`天卖出，并不会增加交易次数，而且这里无论`diff`是不是大于0都一定要加上，否则就不满足`local[i][j]`必须在最后一天卖出的条件了）。

局部（在第`i`天交易后，总共交易了`j`次）= `max{情况2， 情况1}`

情况1：在第`i-1`天时恰好已经交易了`j`次（`local[i-1][j]`），那么如果`i-1`天到`i`天再交易一次：即在第`i-1`天买入，第`i`天卖出（`diff`）,则并不会增加交易次数！【例如第一天买入，第二天卖出，然后第二天又买入，第三天再卖出的行为和第一天买入、第三天卖出的效果是一样的，其实只进行了一次交易！因为有连续性】；

情况2：在第`i-1`天后，共交易了`j-1`次（`global[i-1][j-1]`），因此未来满足“第`i`天过后共进行了`j`次交易，且第`i`天必须进行交易”的条件，我们可以选择在第`i-1`天买入，然后再在第`i`天卖出（`diff`），或者选择在第`i`天买入，然后同样在第`i`天卖出（收益为0）。

上面的算法对于天数需要一次扫描，而每次要对交易次数进行递推式求解，所以时间复杂度是`O(n*k)`，如果是最多进行两次交易，那么复杂度还是`O(n)`，空间上只需要维护当天数据即可，所以是`O(k)`，当`k=2`，则是`O(1)`。

补充：这道题还有一个陷阱，当`k`大于天数时，其实就退化成`Best Time to Buy and Sell Stock II`了。

③算法实现：

```java
    private static int maxProfitInAtMostKTransactions(int[] prices, int k) {
        if(prices == null || prices.length == 0 || k < 1) {
            return 0;
        }

        int days = prices.length;
        if(days <= k) {
            return maxProfitInMultiTransactions(prices);
        }

        //local[i][j] 表示前i天，至多进行j次交易，第i天必须sell的最大收益
        int[][] local = new int[days][k + 1];
        //global[i][j] 表示前i天，至多进行j次交易，第i天可以不sell的最大获益
        int[][] global = new int[days][k + 1];

        for (int i = 1; i < days; i++) {
            int diff = prices[i] - prices[i - 1];
            for (int j = 1; j <= k; j++) {
                local[i][j] = Math.max(global[i - 1][j - 1] + Math.max(diff, 0), local[i - 1][j] + diff);
                global[i][j] = Math.max(global[i - 1][j], local[i][j]);
            }
        }
        return global[days - 1][k];
    }
```

### 4.5 求一个数组中和最大的连续子序列

`dynamicprograming/MaxSumSubArray`, 参考：[Maximum-Subarray](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Maximum-Subarray.md)

①题目：

```java
Find the contiguous subarray wijthin an array(containing at least one number) which has the largest sum.
For example, given the array [-2, 1, -3, 4, -1, 2, 1, -5, 4], the contiguous subarray [4, -1, 2, 1] has the largest sum = 6.
More practice: If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
```

②算法思路：

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

### 4.6 求一个数组中积最大的连续子序列

`dynamicprograming/MaxProductSubArray`, 参考[Maxmimum-Product-Subarray](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Maxmimum-Product-Subarray.md)

①题目：

```java
Find the contiguous subarray within an array (containing at least on number) which has the largest product.
For example, given the array [2, 3, -2, 4], the contiguous subarray [2, 3] has the largest product = 6.
```

②算法思路：

此题是求数组中子区间的最大乘积，对于乘法，我们需要注意，负数乘以负数，会变成正数，所以解这道题的时候我们需要维护两个变量，当前的最大值，以及最小值，最小值可能为负数，但没准下一步乘以一个负数，当前的最大值就变成最小值，二最小值则变成最大值了。

`dp`四要素：

* 状态：

  `max_product[i]`：以`nums[i]`结尾的`max subarray product`；

  `min_product[i]`：以`nums[i]`结尾的`min subarray product`.

* 方程：

  `max_product[i] = getMax(max_product[i - 1] * nums[i], min_product[i - 1] * nums[i], nums[i])`；

  `min_product[i] = getMin(max_product[i - 1] * nums[i], min_product[i - 1] * nums[i], nums[i])`；

* 初始化：

  `max_product[0] = min_product[0] = nums[0]`;

* 结果：

  每次循环中`max_product[i]`的最大值。

③算法实现：

```java
    public static int maxProductSubArray(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }

        int[] max = new int[nums.length];
        int[] min = new int[nums.length];

        min[0] = max[0] = nums[0];
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            min[i] = max[i] = nums[i];
            if(nums[i] > 0) {
                max[i] = Math.max(max[i], max[i - 1] * nums[i]);
                min[i] = Math.min(min[i], min[i - 1] * nums[i]);
            }else if(nums[i] < 0){
                max[i] = Math.max(max[i], min[i - 1] * nums[i]);
                min[i] = Math.min(min[i], max[i - 1] * nums[i]);
            }
            result = Math.max(result, max[i]);
        }
        return result;
    }
```

### 4.7 求一个字符串中的最长回文子串

`dynamicprograming/LongestPalindrome` 参考：[Longest-Palindromic-Substring](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Longest-Palindromic-Substring.md)

①题目：其一个字符串中的最长回文子串

```java
Given a string S, find the longest palindromic substring in S. You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.
Example: Given the string = "abcdzdcab", return "cdzdc".
Challenge: O(n^2) time is acceptablel. Can you do it in O(n) time.
```

②解题思路：

**区间类动态规划** --> `Time O(n^2), Space O(n^2)`

用`dp[i][j]`来存`DP`的状态，需要较多的额外控件：`Space O(n^2)`

`DP`的四要素：

* 状态：`dp[i][j]`,`s.charAt(i)`到`s.charAt(j)`是否构成一个`Palindrome`.
* 转移方程：`dp[i][i] = s.charAt(i) == s.charAt(j) && (j - i <= 2) || dp[i + 1][j - 1]`.
* 初始化：`dp[i][j] = true when j - i <= 2`.
* 结果：找`maxLen = j - i + 1;`，并得到相应`longest substring`: `longest = s.substring(i, j + 1)`.

```java
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
```

**中心扩展**

这种方法的基本思路是遍历数组，以其中的一个元素或者2个元素作为`palindrome`的中心，通过辅助函数，寻找能扩展得到的最长子字符串。外层循环`O(n)`，内层循环`O(n)`，因此时间复杂度`Time O(n^2)`，相比动态规划二维数组存状态的方法，因为只需要存最长`palindrome`子字符串本身，这里空间更优惠：`Space O(1)`.

```java
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
```

### 4.8 矩阵最大正方形面积

`dynamicprograming/MaximalSquare` 参考：[Maximal-Square](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/Maximal-Square.md)

①题目：

```java
Given a 2D binary matrix filled with 0's and 1's, find the largest square containing all 1's and return its area.
Example. For example, given the following matrix:
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
return 4.
```

给定一个二维矩阵，其中元素值为0或1，找出最大的一个正方形，使得其元素都为1，返回其面积。

②算法思路：

当我们判断以某个点为正方形右下角时最大的正方形时，那它的上方、左方和左上方三个点也一定是某个正方形的右下角，否则该点为右下角的正方形最大就是它自己了。

这是定性的判断，那具体的最大正方形边长呢？我们知道，该点为右下角的正方形的最大边长，最多比它的上方、左方和右下角的正方形的边长多1，最好的情况是它的上方、左方和左上方为右下角的正方形的大小都是一样的，这样加上该点就可以构成一个更大的正方形。

但如果它的上方、左方和左上方为右下角的正方形大小不一样，合起来就会缺了某个角落，这时候只能取那三个正方形中最小的正方形的边长加1了。假设`dp[i][j]`表示以 i,j 为右下角的正方形的最大边长，则有：
$$
dp[i][j] = 
\begin{cases}
min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1 \\
0, \quad A[i][j] = 0
\end{cases}
$$
③算法实现：

```java
    public static int maximalSquare(int[][] matrix) {
        if(matrix == null || matrix.length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int max = 0;
        int[][] dp = new int[m][n];
        //第一列赋值
        for (int i = 0; i < m; i++) {
            dp[i][0] = matrix[i][0];
            max = Math.max(max, dp[i][0]);
        }
        //第一行赋值
        for (int i = 0; i < n; i++) {
            dp[0][i] = matrix[0][i];
            max = Math.max(max, dp[0][i]);
        }
        //递推
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = matrix[i][j] == 1 ? Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1 : 0;
                max = Math.max(max, dp[i][j]);
            }
        }
        return max * max;
    }
```

### 4.9 背包问题

`dynamicprograming/BackPack` 参考：[backpack](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/Dynamic-Programming/backpack.md)

#### 4.9.1 背包能装多满问题

①题目：

```java
Given n items with size A[i], an integer m denotes the size of a backpack. Howfull you can fill this backpack?
Note You: Can not divide any item into small pieces.
Example: if we have 4 items with size [2, 3, 5, 7], the backpack size is 11, so that the max size we can fill this backpack is 10.
If the backpace size is 12, we can select [2, 3, 7], so that we can fulfill the backpack.
Your function should return the max size we can fill in the given backpack.
```

在 n 个物品中挑选若干物品装入背包，最多能装多满？假设背包的大小为 m，每个物品的大小为`A[i]`。

②算法思路：

本题是典型的`01`背包问题，每种类型的物品最多只能选择一件。

> 1. **`State: `** `dp[i][S]`表示前`i`个物品，取出一些能否组成和为`S`体积的背包；
> 2. **`Function:`** `f[i][S] = f[i - 1][S - A[i]] or f[i - 1][S]`（`A[i]`表示第`i`个物品的大小）;
>
> 转移方程想得到`f[i][S]`前`i`个物品取出一些物品想组成`S`体积的背包，那么可以从两个状态转换得到：
>
> > (1) `f[i - 1][S - A[i]]` 放入第`i`个物品，并且前`i-1`个物品能否取出一些组成和为`S - A[i]` 体积大小的背包；
> >
> > (2) `f[i - 1][S]`不放入第`i`个物品，并且前`i - 1`个物品能否取出一些组成和为`S`体积大小的背包。
>
> 3. **`Initialize: `** `f[1...n][0] = true; f[0][1...n] = false`;
>
> 初始化`f[1...n][0]`表示前`1...n`个物品，取出一些能否组成和为0大小的背包始终为真，其它初始化为假。
>
> 4. **`Answer: `** 寻找使`f[n][S]`值为`true`的最大的`S` (`S`的取值范围为`1`到`m`)。

③算法实现：

```java
/**
  * 01 背包问题
  *
  * fij   1 2 3 4 5 6 7 8 9 10 11
  *     1 0 0 0 0 0 0 0 0 0 0  0
  *   2 1 0 1 0 0 0 0 0 0 0 0  0
  *   3 1 0 1 1 0 1 0 0 0 0 0  0
  *   5 1 0 1 1 0 1 0 1 1 0 1  0
  *   7 1 0 1 1 0 1 0 1 1 1 1  0
  * @param m 背包容量
  * @param A items数组
  * @return
  */
public static int backPack01(int m, int[] A) {
  if(A == null || A.length == 0) {
    return 0;
  }

  boolean f[][] = new boolean[A.length + 1][m + 1];
  for (int i = 0; i <= A.length; i++) {
    for (int j = 0; j <= m; j++) {
      f[i][j] = false;
    }
  }

  f[0][0] = true;
  for (int i = 1; i <= A.length; i++) {
    for (int j = 0; j <= m; j++) {
      f[i][j] = f[i - 1][j];  //不放入第i个物品， 并且前i-1个物品能否取出一些组成和为S 体积大小的背包。
      if(j >= A[i - 1] && f[i - 1][j - A[i - 1]]) {  //放入第i个物品，并且前i-1个物品能取出一些组成和为S-A[i] 体积大小的背包。
        f[i][j] = true;
      }
    }
  }

  for(int i = m; i >= 0; i--) {
    for(int j = A.length; j >= 0; j--) {
      if(f[j][i]) {
        return i;
      }
    }
  }

  return 0;
}
```

#### 4.9.2 背包装最大价值问题

①题目：

```java
Given n items with size A[i] and value V[i], and a backpack with size m. What's the maximum value can you put into the backpack?
Note: You cann't divide item into small pieces and the total size of items you choose should smaller or equal to m.
Example: Given 4 items with size [2, 3, 5, 7] and value [1, 5, 2, 4], and a backpack with size 10. The maximum value is 9.
```

两个数组，一个表示体积，另一个表示价值，给定一个容积为 m 的背包， 求背包装入物品的最大价值。

②算法思路：

首先定义状态`K(i,w)`为前`i`个物品放入`size`为`w`的背包中所获得的最大价值，则相应的状态转移方程为：`K(i,w)=max{K(i-1,w), K(i-1,w-wi)+vi}`

③算法实现：

```java
    /**
     * 背包最大价值问题
     *
     *dpij   1 2 3 4 5 6 7 8 9 10   V
     *     0 0 0 0 0 0 0 0 0 0 0   //前0个item,在给定背包份数可以装得下的情形下能取到的最大价值
     *   2 0 0 1 1 1 1 1 1 1 1 1    1
     *   3 0 0 1 5 5 6 6 6 6 6 6    5
     *   5 0 0 1 5 5 6 6 6 7 7 8    2
     *   7 0 0 1 5 5 6 6 6 7 7 9    4
     *
     *   int[] V = {1, 5, 2, 4};
     * @param m An integer m denotes the size of a backpack
     * @param A Given n items with size A[i]
     * @param V These n items with value V[i]
     * @return The maximum value
     */
    public static int backPackMaxValue(int m, int[]A, int[] V) {
        if(A == null || A.length == 0 || V == null || V.length == 0) {
            return 0;
        }

        int[][] dp = new int[A.length + 1][m + 1];
        for (int i = 0; i <= A.length; i++) {
            for (int j = 0; j <= m; j++) {
                if(i == 0 || j == 0) {
                    dp[i][j] = 0;
                }else if(A[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - A[i - 1]] + V[i - 1]);
                }
            }
        }
        return dp[A.length][m];
    }
```

