# 算法示例

[TOC]

# 一、算法示例

## 1. [数组相关算法](https://github.com/tianyalu/Algorithm/blob/master/readme/array.md)

数组是较为简单的数据结构，它**占据一块连续的内存**，并按照顺序存储数据。数组需要事先知道容量大小，然后根据大小分配存储空间，所以数组的**空间利用率不高**。数组有**很好的查找效率**，能在`O(1)`内找到元素，所以我们可以基于数实现简单的`hash`表，提高查找效率。

## 2. [字符串相关算法](https://github.com/tianyalu/Algorithm/blob/master/readme/string.md)

## 3. [链表相关算法](https://github.com/tianyalu/Algorithm/blob/master/readme/linklist.md)

## 4. [动态规划](https://github.com/tianyalu/Algorithm/blob/master/readme/dynamicprograming.md)

### 4.1 简介

动态规划的本质，是对问题**状态的定义**和**状态转移方程**的定义：

> `dynamic programming is a method for solving a complex problem by breaking it down into a collection of simpler subproblems.`

动态规划是通过**拆分问题**、**定义问题状态**和**状态之间的关系**，使得问题能够一递推（或者说分治）的方式去解决。以下介绍，大多是在说递推的求解方法，但**如何拆分问题**，才是动态规划的核心。而**拆分问题**，靠的是**状态的定义**和**状态转移方程的定义**。

### 4.2 状态的定义

首先说不要被下面的属性式吓到，这里只涉及到了函数相关的知识：

> 给定一个数列，长度为`N`，求这个数列的最长上升（递增）子数列（`LIS`）的长度，以1 7 2 8 3 4 为例，这个数列的最长递增子数列为 1 2 3 4，长度为4；次长的长度为3，包括1 7 8、1 2 3 等。

要解决这个问题，我们首先要**定义这个问题**和这个问题的子问题。可能有人会问：题目都已经在这了，我们还需要定义这个问题吗？需要，原因是这个问题在字面上看，找不出子问题，而没有子问题，这个题目就没办法解决。

> 给定一个数列，长度为`N`，设 F<sub>k</sub> 为：以数列中第`k`项结尾的最长递增子序列长度，求 F<sub>1</sub> ... F<sub>N</sub> 中的最大值。

显然这个新问题与原问题等价，二对于 F<sub>k</sub> 来讲， F<sub>1</sub> ... F<sub>k-1</sub> 都是 F<sub>k</sub> 的子问题：因为以第`k`项结尾的最长递增子序列（下称`LIS`），包含着以第 1 ... k-1 中某项结尾的`LIS`。

上述新问题 F<sub>k</sub> 也可以叫做状态，定义中的“ F<sub>k</sub> 为数列中第`k`项结尾的`LIS`的长度”，就叫做对状态的定义。之所以把 F<sub>k</sub> 叫做“状态”而不是“问题”，一是因为避免与原问题中的“问题”混淆，二是因为这个新问题是数学化定义的。

对状态的定义只有一种吗？当然不是：

> 给定一个数列，长度为`N`，设 `$F_{i, k}$` 为：在前 `i` 项中的长度为`k`的最长递增子序列中最后一位的最小值，1 <= k <= N，若在前`i`项中，不存在长度为`k`的最长递增子序列，则 `$F_{i, k}$` 为正无穷，求最大的`x`，使得`$F_{i, k}$`不为正无穷。

这个新定义与原问题的等价性也不难证明，请读者体会一下。上述的`$F_{i, k}$` 就是状态，定义中的 “`$F_{i, k}$`为：在前`i`项中，长度为`k`的最长递增子序列中，最后一位的最小值” 就是对状态的定义。

### 4.3 状态转移方程

上述状态定义好之后，状态和状态之间的关系式，就叫做**状态转移方程**。

> 设 F<sub>k</sub> 为：以数列中第`k`项结尾的最长递增子序列的长度。

设`A`为题中数列，状态转移方程为：
$$
F_{k+1} = \begin{cases} F_k, \quad max({F_{[k]}}) > A_{(k+1)}\\ F_k+1, \quad max({F_{[k]}}) < A_{(k+1)} \end{cases}
$$
其中 F<sub>k</sub> 为下标从 0~k 集合中的最长递增子序列，A<sub>k</sub> 表示集合中的第`k`项。

可以看出，这里的状态转移方程，就是定义了问题和子问题之间的关系。

可以看出，状态转移方程就是带有条件的递推式。

## 5. [贪心算法](https://github.com/tianyalu/Algorithm/blob/master/readme/greedy.md)

贪心法，又称**贪心算法**、**贪婪算法**、或称**贪婪法**，是一种在每一步选择中都采取在当前状态下最好或最优（即最有利）的选择，从而希望导致结果是最好或最优的算法。

贪心算法在有最优子结构的问题中尤为有效，最优子结构的意思是局部最优解能决定全局最优解。简单地说，问题能够分解成子问题来解决，子问题的最优解能传递到最终问题的最优解。

贪心算法与动态规划的不同在于它对每个子问题的解决方案都做出选择，不能回退。动态规划则会保存以前的运算结果，并根据以前的结果对当前进行选择，有回退功能。

贪心法可以解决一些最优化问题，如：求图中的最小生成树、求哈夫曼编码……对应其它问题，贪心算法一般不能得到我们所要求的答案。一旦一个问题可以通过贪心法来解决，那么贪心法一般是解决这个问题的最好办法。由于贪心法的高效性以及其所求得的答案接近最优结果，贪心法也可以用作辅助算法或者直接解决一些要求结果不特别精确的问题。

## 6. 排序类算法

### 6.1 快速排序

`sort/QuickSort`



## 7. 搜索查找类算法

### 7.1 二分查找

前提：有序。

`search/BinarySearch`








## 8. 其它

### 8.1 跳台阶

一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。

`other/JumpFloor`

### 8.2 逻辑运算符`&& ||`

优先级： `！> && > ||`     从左向右计算

`other/LogicOperator`

### 8.3 洗牌算法

#### 8.3.1 算法思想

从未处理的数据中随机取出一个数字，然后把该数据放在数组的尾部，即数组尾部存放的是已处理过的数据。

#### 8.3.2 `proof`

对于`arr[i]`,洗牌后在第`n-1`个位置的概率是`1/n`（第一次交换的随机数为`i`）；

在`n-2`个位置概率是`[(n-1)/n] * [1/(n-1)] = 1/n`，（第一次交换的随机数不为i，第二次为arr[i]所在的位置,注意，若i=n-1，第一交换arr[n-1]会被换到一个随机的位置））；

在第n-k个位置的概率是`[(n-1)/n] * [(n-2)/(n-1)] *...* [(n-k+1)/(n-k+2)] *[1/(n-k+1)] = 1/n`（第一个随机数不要为`i`，第二次不为`arr[i]`所在的位置(随着交换有可能会变)……第`n-k`次为`arr[i]`所在的位置）。

#### 8.3.3 算法实现

`other/shuffleAlgorithm`

```java
    public static void shuffle(int[] arr) {
        if(arr == null || arr.length == 0) {
            System.out.println("参数非法");
            return;
        }

        //Random random = new Random(System.currentTimeMillis());
      	Random random = new Random(); //也是随机的
        for (int i = arr.length - 1; i >= 0; i--) {
            //int randomIndex = Math.abs(random.nextInt()) % (i + 1); //(-i-1, i+1)
            int randomIndex = random.nextInt(i + 1); //[0, i+1)
            int temp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = temp;
        }
    }
```

### 8.4 蓄水池抽样算法

 从n个数中随机取m个数（`n>m`）。

#### 8.4.1 算法思想

 源数组为`a[]`，大小为`n`，“水池”即存放取出的数组`b[]`，大小为`m`，若要做到随机，则每个数据被选中的概率为`m/n`。

操作如下：

* ①对数组`a[]`进行遍历，设置j为遍历时当前元素的下标，将`a[]`数组的前`m`个数据依次存入数组`b[]`中，此操作`a[]`数组中的前 `m`个数被加入`b[]`数组的概率为`100% `；                                       
* ②当`i=m-1`时，此时`b[]`数组的元素已满，若后面的元素要加入`b[]`数组，则要与池中的元素替换，替换的规则如下：在`0-i`（`i`为当前元素下标，此时`i>=m`）的范围内随机出一个数字`r`,`r<m`则将i对应的元素`a[i]`值赋予水池中的`b[r]`,水池中r对应的数据被替换掉；如果`r>=m`，则`i++`向前移动，也就表示池中的数据没有被替换 ；
* ③` i`每向前移动一步，则重复步骤②，直到数组`a[]`被完全遍历。    

#### 8.4.2 概率分析

为分析简单，以下的i从1开始                    

* ①对于第i个数（`i<=m`）,在第`i`步之前，被选中的概率为`1`；当走到第`m+1`步时，**被`m+1`个元素替换的概率 = 第`m+1`个元素被选中的概率 * `i`被选中替换的概率**，即`m/(m+1) * 1/m = 1/(m+1)`。则不被`m+1`个元素替换的概率为`1 - 1/(m+1) = m/(m+1)`.  依次类推，不被`m+2`个元素替换的概率为`1 - m/(m+2) * 1/m = (m+1)/(m+2)`,则到第`n`步时，**第`i`个数让保留的概率 =  被选中的概率 * 不被替换的概率**，即：`1 * m/(m+1) * m+1/(m+2) * (m+2)/(m+3) * ... * (n-1)/n = m/n`. 
* ②对于第`j`个（`j>m`），我们知道，在第`j`步被选中的概率为`m/j`(选中了就会被放到池子中，但要保留的话就要保证该数不会被后面的步骤替换掉)。不被`j+1`个元素替换的概率为` 1 - m/(j+1) * 1/m = j/(j+1)`；则运行到第`n`步时，**被保留的概率 = 被选中的概率 * 不被替换的概率**，即：`m/j * j/(j+1) * ... * (n-1)/n = m/n`.    
*  所以对于其中的每个元素，被保留的概率都为m/n.        

#### 8.4.3 算法实现

`other/ReservoirSampling`:

```java
    /**
     * 蓄水池抽样算法
     * @param k 未知大小的数据源
     * @param m 蓄水池大小（随机抽样的个数）
     * @return
     */
    public static int[] reservoirSampling(int k[], int m) {
        int[] pool = new int[m];
        if(k.length <= m) {
            return new int[0];
        }

        Random random = new Random();
        for (int i = 0; i < k.length; i++) {
            if(i < m) {
                pool[i] = k[i]; //将前m个数放入数组（池）中
            }else { //从 0-i 中随机出一个数
                int r = random.nextInt(i + 1);
                if(r < m) {
                    pool[r] = k[i]; //如果随机出的r<水池大小，则进行替换
                }
            }
        }

        return pool;
    }
```

# 二、剑指`offer`

## 1. [数组相关算法](https://github.com/tianyalu/Algorithm/blob/master/for_offer_readme/array.md)

## 2. [字符串相关算法](https://github.com/tianyalu/Algorithm/blob/master/for_offer_readme/string.md)

## 3. [链表相关算法](https://github.com/tianyalu/Algorithm/blob/master/for_offer_readme/linklist.md)

## 4. [树相关算法](https://github.com/tianyalu/Algorithm/blob/master/for_offer_readme/tree.md)

## 5. [动态规划](https://github.com/tianyalu/Algorithm/blob/master/for_offer_readme/dynamicprograming.md)

## 7. [其它](https://github.com/tianyalu/Algorithm/blob/master/for_offer_readme/other.md)