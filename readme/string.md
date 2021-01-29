## 二、字符串相关

### 2.1 字符串的简单四则运算

`string/StrArithmetic`，参考：[https://blog.csdn.net/qq_36838191/article/details/81353415](https://blog.csdn.net/qq_36838191/article/details/81353415)

① 应用场景：

在计算器中输入一大串四则运算表达式，如果按下=号，计算出最后正确的结果。

②实现思路：

* **没有括号表达式的实现**：   将表达式拆分分别有序放入容器中（运算符放在符号容器中，数字放在数字容器<`List`>中），用List比较合适，因为要做有序操作：

  	①取运算符（从容器中移除）②取对应索引处的数字（从容器中移除）③计算并将计算结果放回（当前索引位置）

* **有括号表达式的实现**：找到最内侧的括号表达式，按照无括号的方法进行计算，将计算结果替换该括号表达式的位置，此时得到新的表达式，再继续递归调用当前方法。

### 2.2 字符串恢复`IP`地址

`string/RestoreIpAddress`,

参考 [https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/String/ip.md](https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/String/ip.md)

①题目：

```java
Given a string containing only digits,restore it by returning all possible valid IP address combinations.
For example: Given "25525511135",
return ["255.255.11.135", "255.255.111.35"].(Order doesnot mater)
```

给一个由数字组成的字符串，求出其可能恢复为的所有`IP`地址。

注意：中间`IP`位置不能以0开始，`0.01.01.1`非法，应该是`0.0.101.1`或者`0.0.10.11`。

②算法思路：

**方法一**：直接三重循环暴力求解；

**方法二**：深度搜索，回溯。

③算法实现：

**暴力求解法**：

```java
    /**
     * 暴力破解法
     * @param s 原字符串
     * @return
     */
    public static ArrayList<String> restoreIPAddress1(String s){
        ArrayList<String> res = new ArrayList<>();
        int len = s.length();
        for (int i = 1; i < 4 && i < len - 2; i++) { //第一个分隔小数点的范围
            for (int j = i + 1; j < i + 4 && j < len - 1; j++) { //第二个分隔小数点的范围
                for (int k = j + 1; k < j + 4 && k < len; k++) { //第三个分隔小数点的范围
                    String s1 = s.substring(0, i);
                    String s2 = s.substring(i, j);
                    String s3 = s.substring(j, k);
                    String s4 = s.substring(k, len);
                    if(isValid(s1) && isValid(s2) && isValid(s3) && isValid(s4)) {
                        res.add(s1 + "." + s2 + "." + s3 + "." + s4);
                    }
                }
            }
        }
        return res;
    }

    /**
     * 判断是否是合法的ip地址分段
     * @param s 分段的IP字符串
     * @return
     */
    public static boolean isValid(String s) {
        if(s.length() > 3 || s.length() == 0 || (s.charAt(0) == '0' && s.length() > 1) || Integer.parseInt(s) > 255) {
            return  false;
        }
        return true;
    }
```

**搜索回溯法**：

```java
    public static ArrayList<String> restoreIPAddress2(String s) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();

        if(s.length() < 4 || s.length() > 12) {
            return result;
        }

        helper(result, list, s, 0);
        return result;
    }

    private static void helper(ArrayList<String> result, ArrayList<String> list, String s, int start) {
        if(list.size() == 4) { //递归结束条件
            if(start != s.length()) { //这里要等全部字符串遍历完才能返回，否则会出现仅包含部分字符串s的结果
                return;
            }

            StringBuffer sb = new StringBuffer();
            for (String tmp : list) {
                sb.append(tmp);
                sb.append(".");
            }
            sb.deleteCharAt(sb.length() - 1);
            result.add(sb.toString());
            return;
        }

        for (int i = start; i < s.length() && i < start + 3; i++) {
            String tmp = s.substring(start, i + 1);
            if(isValid2(tmp)) {
                list.add(tmp);
                helper(result, list, s, i + 1);
                list.remove(list.size() - 1);  //回溯
            }
        }
    }

    /**
     * 判断是否是合法的ip地址分段
     * @param s 分段的IP字符串
     * @return
     */
    private static boolean isValid2(String s) {
        if(s.charAt(0) == '0') {
            return s.equals("0"); //to eliminate cases like "00", "10"
        }
        int digit  = Integer.parseInt(s);
        return digit >= 0 && digit <= 255;
    }
```

### 2.3 字符串翻转

`string/RotateString`

①题目：

```java
Given a string and an offset, rotate string by offset.(rotate from left to right)
Example:
Given "abcdefg", offset=0 =>"abcdefg"; offset=1 =>"gabcdef"; offset=2 =>"fgabcde"; offset=3 =>"efgabcd".
Challenge: Rotate in-place with O(1) extra memory.
```

给定一个字符串和一个偏移量，根据偏移量旋转字符串（从左向右旋转）。

②解题思路：

常见的翻转法应用题，仔细观察规律可知翻转的分隔点在从数组末尾起`offset`位置，先翻转前半部分，随后翻转后半部分，最后整体翻转。

③算法实现：

```java
    public static char[] rotateString(char[] A, int offset) {
        if(A == null || A.length == 0) {
            return A;
        }

        int len = A.length;
        offset %= len;
        reverse(A, 0, len - offset - 1);
        reverse(A, len - offset, len - 1);
        reverse(A, 0, len - 1);

        return A;
    }

    private static void reverse(char[] str, int start, int end) {
        while (start < end) {
            char temp = str[start];
            str[start] = str[end];
            str[end] = temp;
            start++;
            end--;
        }
    }
```

### 2.4 判断字符串是否是回文

`string/JudgePalindrome`

①题目：

```java
Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases. For example, "A man, a plan, a canal: Panama" is a palindrome. "race a car" is a palindrome.
Note:
	Have you consider that the string might be empty? This is a good question to ask during an interview. For the purpose of this problem, we define empty string as valid palindrome.
```

判断一个字符串是否为回文串。

②解题思路：

字符串的回文判断问题，由于字符串可随机访问，故逐个比较首尾字符是否相等最为便利，即常见的“两根指针”技法。两步走：a. 找到最左边和最右边的第一个合法字符串（字母或者字符）b. 一致转换为小写进行比较。

③算法实现：

```java
public static boolean isPalindrome(String s) {
  if(s == null || s.isEmpty()) {
    return true;
  }
  int left = 0;
  int right = s.length() - 1;
  while (left < right) {
    if(!Character.isAlphabetic(s.charAt(left))){
      left++;
      continue;
    }
    if(!Character.isAlphabetic(s.charAt(right))) {
      right--;
      continue;
    }
    if(Character.toLowerCase(s.charAt(left)) == Character.toLowerCase(s.charAt(right))) {
      left++;
      right--;
    }else {
      return false;
    }
  }
  return true;
}
```

### 2.5 返回最后一个单词的长度

 `string/LengthOfLastWord`

①题目：

```java
Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word in the string. If the last word does not exist, return 0.
Note: A word is defined as a character sequence consists of non-space characters only.
For example, Given s = "Hello World", return s.
```

给定一个字符串，包含大小写字母、空格' '，请返回其最后一个单词的长度；如果不存在最后一个单词，请返回0.

②算法思路：

关键点在于确定最后一个字符串之前的空格，此外还需要考虑末尾空格这一特殊情况，从最后往前扫码。

③算法实现：

```java
    public static int getLengthOfLastWord(String s) {
        if(s == null || s.isEmpty()) {
            return 0;
        }

        int len = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if(' ' == s.charAt(i)) {
                if(len > 0) {
                    return len;
                }
            }else {
                len++;
            }
        }
        return len;
    }
```

