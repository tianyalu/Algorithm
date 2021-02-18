## 数组相关算法

[TOC]

### 1.1 二维数组中的查找

`foroffer/array/SearchIn2DArray`，参考：[01](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/01.md)

①题目：

在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序，请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

②算法思路：

首先选取数组中右上角的数字，如果该数字等于要查找的数字，查找过程结束；如果该数字大于要查找的数字，剔除这个数字所在的列，如果该数字小于要查找的数字，剔除这个数字所在的行。也就是说如果要查找的数字不在数组右上角，则每一次都在数组的查找范围中剔除行或者一列，这样每一步都可以缩小查找的范围，直到找到要查找的数字或者查找范围为空。

③算法实现：

```java
    public static boolean find(int[][] matrix, int number) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int rows = matrix.length; //数组的行数
        int cols = matrix[0].length; //数组的列数

        int row = 0; //起始开始的行号
        int col = cols - 1; //起始开始的列号

        //要查找的位置确保在数组之内
        while(row >= 0 && row < rows && col >= 0 && col < cols) {
            if(matrix[row][col] == number) { //如果找到了就直接退出
                return true;
            }else if(matrix[row][col] > number) { //如果找到的数比要找的数大，说明要找的数在当前数的左边
                col--; //列数减一，代表向左移动
            }else { //如果找到的数比要找的数小，说明要找的数在当前数的下边
                row++; //行数加一，代表向下移动
            }
        }
        return false;
    }
```

### 1.2 用两个栈实现队列

`foroffer/array/StackQueue`，参考：[05](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/05.md)

①题目：

用两个栈实现一个队列，队列的声明如下，请实现它的两个函数`appendTail`和`deleteHead`，分别完成在队列尾部插入结点和在队列头部删除结点的功能。

②解题思路：

栈1用于存储元素，栈2用于弹出元素，负负得正。说得通俗一点，现在把数据1、2、3分别入栈1，然后从栈1中出来（3、2、1）放入到栈2中，那么从栈2中出来的数据（1、2、3）就符合队列的规律了，即负负得正。

③算法实现：

```java
    public static class MStackList<T> {
        //插入栈，只用于插入的数据
        private Stack<T> stack1 = new Stack<>();
        //弹出栈，只用于弹出数据
        private Stack<T> stack2 = new Stack<>();

        //添加操作，在队列尾部插入结点
        public void appendTail(T t) {
            stack1.add(t);
        }

        //删除操作，在队列头部删除结点
        public T deleteHead() {
            //先判断弹出栈是否为空，如果为空就将插入栈的所有数据弹出栈并压入弹出栈中
            if(stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.add(stack1.pop());
                }
            }

            //如果弹出栈中还是没有数据就抛出异常
            if(stack2.isEmpty()) {
                throw new RuntimeException("No more element");
            }

            //返回弹出栈的栈顶元素，对应的就是队首元素
            return stack2.pop();
        }
    }
```

### 1.3 找出排序旋转数组中的最小值

`foroffer/array/MinReverseArray`，参考：[06](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/06.md)

①题目

把一个数组最开始的若干个元素搬到数组的末尾，我们称之为旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如`{3, 4, 5, 1, 2}`为`{1, 2, 3, 4, 5}`的一个旋转，该数组的最小值为1.

②算法思路：

* 和二分查找法一样，我们用两个指针分别指向数组的第一个元素和最后一个元素；

* 接着我们可以找到数组的中间元素：

  如果该中间元素位于前面的递增子数组，那么它应该大于或者等于第一个指针指向的元素，此时数组中最小的元素应该位于该中间元素的后面，我们可以把第一个指针指向该中间元素，这样可以缩小寻找的范围；如果中间元素位于后面的递增子数组，那么它应该小于或者等于第二个指针指向的元素，此时该数组中最小的元素应该位于该中间元素的前面；

* 接下来我们再用更新后的两个指针，重新做新一轮的查找。

③算法实现：

```java
    public static int findMin(int[] numbers) {
        //判断输入是否合法
        if(numbers == null || numbers.length == 0) {
            throw new RuntimeException("Invalid input.");
        }

        //开始处理的第一个位置
        int left = 0;
        //开始处理的最后一个位置
        int right = numbers.length - 1;
        //设置初始值
        int mid = left;

        //确保left在前一个排好序的部分，right在排好序的后一个部分
        while(numbers[left] >= numbers[right]) {
            // 当处理范围只有两个数据时，返回后一个结果，
            // 因为numbers[left] >= numbers[right] 总是成立，后一个结果对应的是最小的值
            if(right - left == 1) {
                return numbers[right];
            }

            //取中间的位置
            mid = left + (right - left) / 2;

            //如果三个数都相等，则需要进行顺序处理从头到尾找最小的值
            if(numbers[mid] == numbers[left] && numbers[mid] == numbers[right]) {
                return minInOrder(numbers, left, right);
            }

            // 如果中间位置对应的值在前一个排好序的部分，将left设置为新的处理位置
            if(numbers[mid] >= numbers[left]) {
                left = mid;
            }
            //如果中间位置对应的值在后一个排好序的部分，将right设置为新的处理位置
            else if(numbers[mid] <= numbers[right]) {
                right = mid;
            }
        }
        //返回最终的处理结果
        return numbers[mid];
    }

    /**
     * 找数组中的最小值
     * @param numbers 数组
     * @param left    数组的起始位置
     * @param right   数组的结束位置
     * @return 找到的最小的数
     */
    private static int minInOrder(int[] numbers, int left, int right) {
        int result = numbers[left];
        for (int i = left + 1; i <= right; i++) {
            if(result > numbers[i])  {
                result = numbers[i];
            }
        }
        return result;
    }
```

### 1.4 整数数组中奇偶前后排序问题

`foroffer/array/ReOrderOddEven`，参考：[11](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/11.md)

①题目：

输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组后半部分。

②算法思路：

这个题目要求把奇数放在数组的前半部分，偶数放在数组的后半部分，因此所有的奇数应该位于偶数的前面；也就是说我们在扫描这个数组的时候，如果发现有偶数出现在奇数的前面，我们可以交换他们的顺序，交换之后就符号要求了。

因此我们可以维护两个指针，第一个指针初始化时指向数组的第一个数字，它只向后移动；第二个指针初始化时指向数组的最后一个数字，它只向前移动。在两个指针相遇之前，第一个指针总是位于第二个指针的前面。如果第一个指针指向的数字是偶数，并且第二个指针指向的数字是奇数，我们就交换这两个数字。

③算法实现：

```java
/**
 * 输入一个整数数组，实现一个函数调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组后半部分
 * @param arr 输入的数组
 */
public static void reOrderOddEven(int[] arr) {
  //对于输入的数组为空，或者长度小于2的直接返回
  if(arr == null || arr.length < 2) {
    return;
  }

  //从左向右记录偶数的位置
  int start = 0;
  //从右向左记录奇数的位置
  int end = arr.length - 1;
  //开始调整奇数和偶数的位置
  while(start < end) {
    //找偶数
    while (start < end && arr[start] % 2 != 0) {
      start++;
    }
    //找奇数
    while (start < end && arr[end] % 2 == 0) {
      end--;
    }
    //找到后就将计算和偶数交换位置，对于start == end这种情况，交换不会产生什么影响，所以将if判断省去了
    int tmp = arr[start];
    arr[start] = arr[end];
    arr[end] = tmp;
  }
}
```

