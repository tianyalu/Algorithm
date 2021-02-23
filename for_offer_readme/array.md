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

### 1.5 顺时针打印矩阵

`foroffer/array/PrintMatrixInCircle`，参考：[17](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/17.md)

①题目：

输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。

②算法思路：

把打印一圈分为4步，第一步从左到右打印一行，第二步从上到下打印一列，第三步从右到左打印一行，第四步从下到上打印一列。每一步我们根据起始坐标和终止坐标用一个循环就能打印出一行或者一列。

不过需要注意的是，最后一圈有可能退化成只有一行、只有一列，甚至只有一个数字，因此打印这样的一圈就不再需要四步。

因此我们要仔细分析打印时每一步的前提条件，第一步总是需要的，因为打印一圈至少有一步。如果只有一行，那么就不用第二步了，也就是需要第二步的前提条件是终止行号大于起始行号。需要第三步打印的前提条件是圈内至少有两行两列，也就是说除了要求终止行号大于起始行号之外，还要求终止列号大于起始列号。同理，需要打印第四步的前提条件是至少有三行两列，因此要求终止行号比起始行号至少大2，同时终止列号大于起始列号。

③算法实现：

```java
    public static void printMatrixClockWisely(int[][] numbers) {
        //输入的参数不能为空
        if(numbers == null) {
            return;
        }

        //记录一圈（环）的开始位置的行
        int x = 0;
        //记录一圈（环）的开始位置的列
        int y = 0;
        //对每一圈（环）进行处理：行号最大是 (numbers.length - 1)/2 ; 列号最大是(numbers[0].length - 1)/2
        while (x * 2 < numbers.length && y * 2 < numbers[0].length) {
            printMatrixInCircle(numbers, x, y);
            //指向下一个要处理的环的第一个位置
            x++;
            y++;
        }
    }

    private static void printMatrixInCircle(int[][] numbers, int x, int y) {
        //数组的行数
        int rows = numbers.length;
        //数组的列数
        int cols = numbers[0].length;

        //输出环的上面一行，包括最终的那个数字
        for (int i = y; i <= cols - y - 1; i++){
            System.out.print(numbers[x][i] + " ");
        }
        System.out.println();

        // 环的高度至少为2才会输出右边的一列，
        // rows-x-1 表示的是环最下的那一行的行号
        if(rows - x - 1 > x) {
            //因为右边那一列的最上面的那个已经被输出了，所以行号从 x+1 开始，输出包括右边那列的最下面的那个
            for (int i = x + 1; i <= rows - x - 1; i++) {
                System.out.print(numbers[i][cols - y - 1] + " ");
            }
            System.out.println();
        }

        // 环的高度至少为2并且环的宽度至少为2才会输出下边的一行,
        // cols-1-y 表示的是环最右边那一列的列号
        if(rows - x - 1 > x && cols - 1 - y > y) {
            //因为环的右下角的位置已经输出了，所以列号从 cols-y-2 开始
            for (int i = cols - y - 2; i >= y; i--) {
                System.out.print(numbers[rows - x - 1][i] + " ");
            }
            System.out.println();
        }

        // 环的宽度至少为2并且环的高度至少为3才会输出最左边的一列
        // rows-x-1 表示的是环最下的那一行的行号
        if(rows - 1 - x > x + 1 && cols - 1 - y > y) {
            //因为环的最左边那一列的第一个和最后一个已经输出了
            for (int i = rows - 1 - x - 1; i >= x + 1; i--) {
                System.out.print(numbers[i][y] + " ");
            }
            System.out.println();
        }
    }
```

### 1.6 包含`min`函数的栈

`foroffer/array/MinStack`，参考：[18](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/18.md)

①题目：

定义栈的数据结构，请在该类型中实现一个能够得到栈的最小数的`min`函数。在该栈中，调用`min`、`push`及`pop`的时间复杂度都是`O(1)`。

②算法思路：

把每次的最小元素（之前的最小元素和新压入栈的元素两者的较小值）都保存起来放入另外一个辅助栈里。如果每次都把最小元素压入辅助栈，那么就能保证辅助栈的栈顶一直都是最小元素。当最小元素从数据栈内被弹出之后，同时弹出辅助栈的栈顶元素，此时辅助栈的新栈顶元素就是下一个最小值。

③算法实现：

```java
public class MinStack {
    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>(); //辅助栈：栈顶永远保持stack中当前的最小元素

    public void push(int data) {
        stack.push(data); //直接往栈中添加数据
        //在辅助栈中需要做判断
        if(minStack.size() == 0 || data < minStack.peek()) {
            minStack.push(data);
        }else {
            minStack.add(minStack.peek()); //核心代码，peek方法返回的是栈顶的元素（不出栈）
        }
    }

    public int pop() throws Exception {
        if(stack.size() == 0) {
            throw new Exception("栈中为空");
        }

        int data = stack.pop();
        minStack.pop(); //核心代码
        return data;
    }

    public int min() throws Exception {
        if(minStack.size() == 0) {
            throw new Exception("栈空了");
        }
        return minStack.peek();
    }
}
```

