## 树相关算法

[TOC]

### 4.1 根据前序和中序遍历结果重建该二叉树

`foroffer/tree/RecreateBinaryTree`，参考：[04](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/04.md)

①题目：

输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字，例如：前序遍历`{1, 2, 4, 7, 3, 5, 6, 8}`和中序遍历`{4, 7, 2, 1, 5, 3, 8, 6}`，重建二叉树并输出它的头结点。

②算法思路：

由前序遍历的第一个节点可知根节点，根据根节点，可以将中序遍历划分成左右子树，在前序遍历中找出对应的左右子树，其第一个节点便是根节点的 左右子节点。安装上述方式递归便可重建二叉树。

③算法实现：

```java
    /**
     * 根据前序遍历和中序遍历的结果重建该二叉树
     * @param preOrder 前序遍历
     * @param inOrder 中序遍历
     * @return
     */
    public static BinaryTreeNode construct(int[] preOrder, int[] inOrder) {
        //输入的合法性判断，两个数组都不能为空，并且都有数据，而且数据长度相同
        if(preOrder == null || inOrder == null || preOrder.length != inOrder.length || inOrder.length < 1) {
            return null;
        }
        return construct(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1);
    }

    /**
     * 根据前序遍历和中序遍历的结果重建该二叉树
     * @param preOrder 前序遍历
     * @param ps       前序遍历的开始位置
     * @param pe       前序遍历的结束位置
     * @param inOrder  中序遍历
     * @param is       中序遍历的开始位置
     * @param ie       中序遍历的结束位置
     * @return 树的根节点
     */
    public static BinaryTreeNode construct(int[] preOrder, int ps, int pe, int[] inOrder, int is, int ie) {
        //开始位置大于结束位置，说明已经没有需要处理的元素了
        if(ps > pe) {
            return null;
        }
        //取前序遍历的第一个数字，就是当前的根节点
        int value = preOrder[ps];
        int index = is;
        //在中序遍历的数组中找根节点的位置
        while(index <= ie && inOrder[index] != value) {
            index++;
        }
        //如果在整个中序遍历的数组中没有找到，说明输入的参数是不合法的，抛出异常
        if(index > ie) {
            throw new RuntimeException("Invalid input");
        }

        //创建当前的根节点，并且为节点赋值
        BinaryTreeNode node = new BinaryTreeNode(value);

        //递归构建当前根节点的左子树，左子树的元素个数：index-is 个
        //左子树对应的前序遍历的位置在[ps+1, ps+index-is]  <-- ps+1 + index-is - 1
        //左子树对应的中序遍历的位置在[is, index-1]
        node.left = construct(preOrder, ps+1, ps+index-is, inOrder, is, index-1);
        //递归构建当前根节点的右子树，右子树的元素个数：ie-index 个
        //右子树对应的前序遍历的位置在[ps+index-is+1, pe]
        //右子树对应的中序遍历的位置在[index+1, ie]
        node.right = construct(preOrder, ps+index-is+1, pe, inOrder, index+1, ie);
        //返回创建的根节点
        return node;
    }
```

### 4.2 树的子结构

`foroffer/tree/SubBinaryTree`, 参考：[15](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/15.md)

①题目：

输入两棵二叉树`A`和`B`，判断`B`是不是`A`的子结构。

②算法思路：

要查找树`A`中是否存在和数`B`结构一样的子树，我们可以分成两步：第一步在树`A`中找到和`B`的根节点的值一样的节点`R`，第二步再判断树`A`中以`R`为根节点的子树是不是包含和树`B`一样的结构。

③算法实现：

```java
    /**
     * 输入两棵二叉树A和B，判断B是不是A的子结构
     * @param root1 数A的根结点
     * @param root2 数B的根结点
     * @return 树B是否是树A的子结构
     */
    public static boolean hasSubTree(BinaryTreeNode root1, BinaryTreeNode root2) {
        //只要两个对象是同一个就返回true
        if(root1 == root2) {
            return true;
        }
        //只要数B的根节点为空就返回true
        if(root2 == null) {
            return true;
        }
        //树B的根结点不为空，如果树A的根结点为空就返回false
        if(root1 == null) {
            return false;
        }

        boolean result = false;
        //如果结点的值相等就调用匹配方法
        if(root1.value == root2.value) {
            result = match(root1, root2);
        }

        if(result) {  //如果匹配就直接返回结果
            return true;
        }else {  //如果不匹配就找树A的左子结点和右子结点进行判断
            return match(root1.left, root2) || match(root1.right, root2);
        }
    }

    /**
     * 判断以root1为根结点和以root2为根结点的两棵树是否完全相同
     * @param root1 
     * @param root2
     * @return
     */
    public static boolean match(BinaryTreeNode root1, BinaryTreeNode root2) {
        //只要两个对象是同一个就返回true
        if(root1 == root2) {
            return true;
        }
        //只要数B的根节点为空就返回true
        if(root2 == null) {
            return true;
        }
        //树B的根结点不为空，如果树A的根结点为空就返回false
        if(root1 == null) {
            return false;
        }

        //如果两个结点的值相等，则分别判断其左子结点和右子结点
        if(root1.value == root2.value) {
            return match(root1.left, root2.left) && match(root1.right, root2.right);
        }

        //结点值不相等，返回false
        return false;
    }
```

### 4.3 二叉树的镜像

`foroffer/tree/BinaryTreeMirror`, 参考：[16](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/16.md)

①题目：

请完成一个函数，输入一个二叉树，该函数输出它的镜像。

②算法思路：

先前序遍历这棵树的每个结点，如果遍历到的结点有子结点，就交换它的两个子结点；当交换完所有非叶子结点的左右子结点之后，就得到了树的镜像。

③算法实现：

```java
    /**
     * 输出输入二叉树的镜像
     * @param node 二叉树的根结点
     */
    public static void mirror(BinaryTreeNode node) {
        //如果当前的结点不为空则进程操作
        if(node != null) {
            //交换结点的左右两棵子树
            BinaryTreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            //对结点的左右两个子树进行递归处理
            mirror(node.left);
            mirror(node.right);
        }
    }
```

### 4.4 从上往下打印二叉树

`foroffer/tree/TraversalFromTopToBottom`, 参考：[20](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/20.md)

①题目：

从上往下打印出二叉树的每个结点，同一层的结点按照从左向右的顺序打印。

②算法思路：

这道题实质是考察树的遍历算法。从上到下打印二叉树的规律：每次打印一个节点的时候，如果该结点有子结点，则把该结点的子结点放到一个队列的末尾。接下来到队列的头部取出最早进入队列的结点，重复前面的打印操作，直至队列中所有的结点都被打印出来为止。

③算法实现：

```java
    /**
     * 从上往下打印出二叉树的每个结点，同一层的结点按照从左往右的顺序打印
     * 例如下面的二叉树：
     *        8
     *       ↙ ↘
     *      6   10
     *     ↙ ↘  ↙ ↘
     *    5   7 9  11
     * 则依次打印出8、6、10、5、7、9、11
     * @param root 树的根节点
     */
    public static void printFromTopToBottom(BinaryTreeNode root) {
        //当结点非空时才进行操作
        if(root != null) {
            //用于存放还未遍历的元素
            Queue<BinaryTreeNode> list = new LinkedList<>();
            //将根节点入队
            list.add(root);
            //用于记录当前处理的结点
            BinaryTreeNode curNode;

            //队列非空则进行处理
            while(!list.isEmpty()) {
                //删除队首元素
                curNode = list.remove();
                //输出队首元素的值
                System.out.print(curNode.value + " ");
                //如果左子结点不为空，则左子结点入队
                if(curNode.left != null) {
                    list.add(curNode.left);
                }
                //如果右子结点不为空，则有子结点入队
                if(curNode.right != null) {
                    list.add(curNode.right);
                }
            }
        }
        System.out.println();
    }
```

### 4.5 二叉搜索树的后序遍历序列

`foroffer/tree/BSTSequenceVerify`, 参考：[21](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/21.md)

①题目：

输入一个整数数组，判断该数组是不是某二叉搜索时的后序遍历的结果。如果是则返回`true`，否则返回`false`。假设输入的数组任意两个数字都互不相同。

②算法思路：

在后序遍历得到的序列中，最后一个数字是树的根节点的值。数组中前面的数字可以分为两部分：第一部分是左子树结点的值，它们都比根结点的值小；第二部分是右子树结点的值，它们都比根结点的值大。

③算法实现：

```java
    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索时的后序遍历的结果
     * @param sequence 某二叉搜索树后序遍历的结果
     * @return
     */
    public static boolean verifySequenceOfBST(int[] sequence) {
        //输入的数组不能为空，并且有数据
        if(sequence == null || sequence.length == 0) {
            return false;
        }
        //有数据，就调用辅助方法
        return verifySequenceOfBST(sequence, 0, sequence.length - 1);
    }

    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索时的后序遍历的结果
     * @param sequence 某二叉搜索树后序遍历的结果
     * @param start 处理的开始位置
     * @param end 处理的结束位置
     * @return
     */
    public static boolean verifySequenceOfBST(int[] sequence, int start, int end) {
        //如果对应要处理的数据只有一个或者已经没有数据要处理（start>end）就返回true
        if(start > end) {
            return true;
        }
        //从左向右找第一个不小于根节点（sequence[end]）的元素位置
        int index = start;
        while (index < end && sequence[index] < sequence[end]) {
            index++;
        }
        //执行到此处时[start, index - 1]的元素都是小于根节点 sequence[end] 的
        //[start, index - 1] 可以看做是根节点的左子树

        //right用于记录第一个大于根节点的元素的位置
        int right = index;
        //接下来要保证[index, end - 1]的所有元素都是大于根结点的值
        //因为[index, end - 1]是根结点的右子树
        //从第一个不小于根结点的元素开始，找到第一个不大于根结点的元素
        while(index < end && sequence[index] > sequence[end]) {
            index++;
        }
        //如果[index, end - 1]中有小于等于根结点的元素
        //不符合二叉搜索树的定义，返回false
        if(index != end) {
            return false;
        }

        //执行到此处说明到目前为止还是合法的
        //[start, index - 1]为根结点左子树的位置
        //[index, end - 1]为根结点右子树的位置
        index = right;
        return verifySequenceOfBST(sequence, start, index - 1) && verifySequenceOfBST(sequence, index, end - 1);
    }
```

### 4.6 二叉树中和为某一值的路径

`foroffer/tree/FindSumPath`, 参考：[22](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/22.md)

①题目：

输入一棵二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。从树的根节点开始往下一直到叶子结点所经过的结点形成一条路径。

②算法思路：

由于路径是从根结点触发到叶结点，也就是说路径总是以根结点为起始点，因此我们首先需要遍历根结点。在树的前序、中序和后序三种遍历方式中，只有前序遍历是首先访问根结点的。

当用前序遍历的方式访问到某一结点时，我们把该结点添加到路径上，并累加该结点的值，如果该结点为叶结点并且路径中结点的值的和刚好等于输入的整数，则当前的路径符号要求，我们就把它打印出来。如果当前结点不是叶结点，则继续访问它的子结点，当前结点访问结束后，递归函数自动回到它的父节点。因此我们在函数退出之前要在路径上删除当前结点并减去当前结点的值，以确保返回父结点时路径刚好是从根结点到父结点的路径。

不难看出保存路径的数据结构实际上是一个枝，因为路径要与递归调用状态一致，二递归调用的本质就是一个压栈和出栈的过程。

③算法实现：

```java
/**
 * 输入一棵二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径
 * @param root 树的根结点
 * @param expectedSum 要求的路径和
 */
public static ArrayList<ArrayList<Integer>> findPath(BinaryTreeNode root, int expectedSum) {
  //创建一个链表，用于存放根结点到当前处理结点的所经过的结点
  ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
  //如果根结点不为空，就调用辅助处理方法
  if(root != null) {
    ArrayList<Integer> path = new ArrayList<>();
    findPath(paths, path, root, expectedSum);
  }
  return paths;
}

private static void findPath(ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> path, BinaryTreeNode root, int expectedSum) {
  path.add(root.value);
  if(root.left == null && root.right == null) {
    if(expectedSum == root.value) {
      paths.add(path);
    }
    return;
  }
  ArrayList<Integer> path2 = new ArrayList<>();
  path2.addAll(path);
  if(root.left != null) {
    findPath(paths, path, root.left, expectedSum - root.value);
  }
  if(root.right != null) {
    findPath(paths, path2, root.right, expectedSum - root.value);
  }
}
```

