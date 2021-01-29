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

### 3. 数组表示的数字加一

①题目：

```java
Given a non-negative number represented as an array of digits, plus one to the number.
The digits ar stored such that the most significant digit is at the head of the list.
Example:
	Given [1,2,3] which represents 123, return [1,2,4].
  Given [9,9,9] which represents 999, return [1,0,0,0].
```

给一个包含非负整数的数组，其中每个值代表该位数的值，对这个数加1.

②算法思路：

> 1. 数组的最后一个数是个位数，所以从后面开始读，个位数+1后，如果有进位，存储进位值，没有直接存储；
> 2. 处理十位，如果个位数有进位，十位数+1，再判断十位数有没有进位；
> 3. 重复上面的动作直到没有进位为止。

③算法实现

```java
    public static int[] plusOne(int[] digits) {
        int carries = 1;
        for (int i = digits.length - 1; i >= 0 && carries > 0; i--) {
            int sum = digits[i] + carries;
            digits[i] = sum % 10;
            carries = sum / 10;
        }
        if(carries == 0) {
            return digits;
        }

        int[] rst = new int[digits.length + 1];
        rst[0] = 1;
        for (int i = 1; i < rst.length; i++) {
            rst[i] = digits[i - 1];
        }
        return rst;
    }
```

### 4. 判断一个数字是否为回文数

①题目：

```java
Determine whether an integer is a palindrome. Do this without extra space.
```

给定一个数字，要求判断这个数字是否为回文数字。比如121就是回文数字，122就不是回文数字。

②算法思路：

题目要求只能用`O(1)`的空间，所以不能考虑把它转化为字符串然后`reverse`比较的方法。

基本思路是每次去比较第一位和最后一位，如果不相同则返回`false`，否则继续直到位数为0。

需要注意的点：**负数不是回文数字**，**0是回文数字**。

③算法实现：

```java
    public static boolean isPalindrome(int x) {
        if(x < 0) {
            return false;
        }
        int div = 1;
        while (div <= x/10) {
            div *= 10;
        }
        while (x > 0) {
            if(x/div != x%10) {
                return false;
            }
            x = (x % div) / 10; // %:去掉最高位  /:去掉最低位
            div /= 100; //去掉的最高位和最低位，所以要/100
        }
        return true;
    }
```

### 5. 在数组中找出和为特定目标数字的两个数

①题目：

```java
Given an array of integers, return indices of the two numbers such that they add up to a specific target.
You may assume that each input would have exactly one solution.
Example:
Given nums = [2, 7, 11, 15], target = 9, Because nums[0] + nums[1] = 2 + 7 = 9, return [0, 1].
```

给定一个整型数组，找出能相加起来等于一个特定目标数字的两个数。

②算法思路：

用`HashMap`，`HashMap`是内部存储方式为哈希表的`map`结构。遍历数组，其中`key`存放目标值减去当前值，`value`存放对应索引，如果遍历过程中发现`map`中存在与当前值相等的`key`，则返回结果。

③算法实现：

```java
    public static int[] findTwoSum(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {
            if(map.get(numbers[i]) != null) {
                int[] result = {map.get(numbers[i]), i};
                return result;
            }
            map.put(target - numbers[i], i);
        }

        int[] result = {};
        return result;
    }
```

