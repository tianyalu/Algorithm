# 算法示例

[TOC]

## 1. [数组相关算法](https://github.com/tianyalu/Algorithm/blob/master/array.md)

数组是较为简单的数据结构，它**占据一块连续的内存**，并按照顺序存储数据。数组需要事先知道容量大小，然后根据大小分配存储空间，所以数组的**空间利用率不高**。数组有**很好的查找效率**，能在`O(1)`内找到元素，所以我们可以基于数实现简单的`hash`表，提高查找效率。

## 2. [字符串相关算法](https://github.com/tianyalu/Algorithm/blob/master/string.md)

## 一、排序类算法

### 1.1 快速排序

`sort/QuickSort`



## 二、搜索查找类算法

### 2.1 二分查找

前提：有序。

`search/BinarySearch`



## 三、链表相关算法

### 3.1 链表翻转

迭代翻转、递归翻转，翻转从位置m到n的链表，k个一组翻转链表。

`linklist/ReverseLinkedList` 参考：[https://www.jianshu.com/p/a29834955ffb](https://www.jianshu.com/p/a29834955ffb)




## 五、其它

### 5.1 跳台阶

一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。

`other/JumpFloor`

### 5.2 逻辑运算符`&& ||`

优先级： `！> && > ||`     从左向右计算

`other/LogicOperator`

### 5.3 洗牌算法

#### 5.3.1 算法思想

从未处理的数据中随机取出一个数字，然后把该数据放在数组的尾部，即数组尾部存放的是已处理过的数据。

#### 5.3.2 `proof`

对于`arr[i]`,洗牌后在第`n-1`个位置的概率是`1/n`（第一次交换的随机数为`i`）；

在`n-2`个位置概率是`[(n-1)/n] * [1/(n-1)] = 1/n`，（第一次交换的随机数不为i，第二次为arr[i]所在的位置,注意，若i=n-1，第一交换arr[n-1]会被换到一个随机的位置））；

在第n-k个位置的概率是`[(n-1)/n] * [(n-2)/(n-1)] *...* [(n-k+1)/(n-k+2)] *[1/(n-k+1)] = 1/n`（第一个随机数不要为`i`，第二次不为`arr[i]`所在的位置(随着交换有可能会变)……第`n-k`次为`arr[i]`所在的位置）。

#### 5.3.3 算法实现

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

### 5.4 蓄水池抽样算法

 从n个数中随机取m个数（`n>m`）。

#### 5.4.1 算法思想

 源数组为`a[]`，大小为`n`，“水池”即存放取出的数组`b[]`，大小为`m`，若要做到随机，则每个数据被选中的概率为`m/n`。

操作如下：

* ①对数组`a[]`进行遍历，设置j为遍历时当前元素的下标，将`a[]`数组的前`m`个数据依次存入数组`b[]`中，此操作`a[]`数组中的前 `m`个数被加入`b[]`数组的概率为`100% `；                                       
* ②当`i=m-1`时，此时`b[]`数组的元素已满，若后面的元素要加入`b[]`数组，则要与池中的元素替换，替换的规则如下：在`0-i`（`i`为当前元素下标，此时`i>=m`）的范围内随机出一个数字`r`,`r<m`则将i对应的元素`a[i]`值赋予水池中的`b[r]`,水池中r对应的数据被替换掉；如果`r>=m`，则`i++`向前移动，也就表示池中的数据没有被替换 ；
* ③` i`每向前移动一步，则重复步骤②，直到数组`a[]`被完全遍历。    

#### 5.4.2 概率分析

为分析简单，以下的i从1开始                    

* ①对于第i个数（`i<=m`）,在第`i`步之前，被选中的概率为`1`；当走到第`m+1`步时，**被`m+1`个元素替换的概率 = 第`m+1`个元素被选中的概率 * `i`被选中替换的概率**，即`m/(m+1) * 1/m = 1/(m+1)`。则不被`m+1`个元素替换的概率为`1 - 1/(m+1) = m/(m+1)`.  依次类推，不被`m+2`个元素替换的概率为`1 - m/(m+2) * 1/m = (m+1)/(m+2)`,则到第`n`步时，**第`i`个数让保留的概率 =  被选中的概率 * 不被替换的概率**，即：`1 * m/(m+1) * m+1/(m+2) * (m+2)/(m+3) * ... * (n-1)/n = m/n`. 
* ②对于第`j`个（`j>m`），我们知道，在第`j`步被选中的概率为`m/j`(选中了就会被放到池子中，但要保留的话就要保证该数不会被后面的步骤替换掉)。不被`j+1`个元素替换的概率为` 1 - m/(j+1) * 1/m = j/(j+1)`；则运行到第`n`步时，**被保留的概率 = 被选中的概率 * 不被替换的概率**，即：`m/j * j/(j+1) * ... * (n-1)/n = m/n`.    
*  所以对于其中的每个元素，被保留的概率都为m/n.        

#### 5.4.3 算法实现

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

