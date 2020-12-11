## 数组相关

[TOC]

### 1. 分治数组（大于哨兵的放右边，小于放左边）

 ①题目:

```java
Given an nums of integers and an int k, partition the array(i.e move the elements in "numbs") such that:
   * All elements < k are moved tho the left
   * All elements >= k are moved to the right
   * Return the partitioning index, i.e the first index i nums[i] >= k.
Notice
   you shuod do really partition in array nums instead of just counting the numbers of integers smaller than k.
   if all elements in nums are smaller than k, then return nums.length
```

②算法思路：

根据给定的`k`, 也就是类似于`Quick Sort`中的`pivot`，将`array`从两头进行缩进，时间复杂度`O(n)`。

③算法实现：

```java
    /**
     * 数组分治pivot
     * @param nums The integer array you should partition
     * @param k As description
     * @return The index after partition
     */
    public static int partitionArray(int[] nums, int k) {
        int pl = 0;
        int pr = nums.length - 1;
        while (pl <= pr) {
            while (pl <= pr && nums[pl] < k) { //从左向右找到第一个大于k的索引
                pl++;
            }
            while (pl <= pr && nums[pr] >= k) { //从右向左找到第一个小于等于k的索引
                pr--;
            }
            if(pl <= pr) { //如果左索引小于等于右索引，交换两个数并继续
                swap(pl, pr, nums);
                pl++;
                pr--;
            }
        }
        return pl;
    }
```

### 2. 寻找和为0的子数组

①题目：

```java
Given an integer array,find a subarray where the sum of numbers is zero.Your code should return the index of the first number and the index of the last number.
Example:
Given [-3, 1, 2, -3, 4],return [0, 2] or [1, 3]
```

②算法思路：

记录每一个位置的`sum`，存入`HashMap`中，如果某一个`sum`已经出现过，那么说明中间的`subarray`的`sum`为0，时间复杂度为`O(n)`，空间复杂度为`O(n)`.


| arrayIndex        | -1   | 0    | 1    | 2    | 3    | 4    |
| ----------------- | ---- | ---- | ---- | ---- | ---- | ---- |
| array[arrayIndex] | ×    | -3   | 1    | 2    | -3   | 4    |
| mapSum            | (0   | -3   | -2   | 0]   | -3   | 1    |

| arrayIndex        | -1   | 0    | 1    | 2    | 3    | 4    |
| ----------------- | ---- | ---- | ---- | ---- | ---- | ---- |
| array[arrayIndex] | ×    | -2   | 1    | 2    | -3   | 4    |
| arraySum          | 0    | (-2  | -1   | 1    | -2]  | 2    |

③算法实现：

```java
    public static List<Integer> subArraySum(Integer[] arr) {
        List<Integer> ansIndexList = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>(); //<和, arr中的索引>
        map.put(0, -1);
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if(map.containsKey(sum)) {
                ansIndexList.add(map.get(sum) + 1); // 左开右闭区间：( ]
                ansIndexList.add(i);
                return ansIndexList;
            }
            map.put(sum, i);
        }
        return ansIndexList;
    }
```

