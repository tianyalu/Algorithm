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

