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

