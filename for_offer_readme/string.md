## 字符串相关算法

[TOC]

### 2.1 替换空格

`foroffer/string/ReplaceBlank`, 参考：[02](https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/02.md)

①题目：

请实现一个函数，把字符串中的每个空格替换成“%20”，例如“We are happy.”，则输出“We%20are%20happy.”。

②算法思路：

先判断字符串中空格的数量，根据数量判断该字符串有没有足够的空间替换成“%20”。

如果有足够空间，计算出需要的空间。根据最终需要的总空间，维护一个指针在最后，从后往前，遇到非空的就把该值挪到指针指向的位置，然后指针向前一位；遇到“ ”，则指针前移，依次替换为“02%”。

③算法实现：

```java
    /**
     * 字符串中空格替换成“%20”
     * @param str 要转换的字符数组（长度可能比实际内容要长）
     * @param usedLength 字符数组中已经使用的长度（实际有用字符的长度）
     * @return 转换后的字符长度，-1表示处理失败
     */
    public static int replaceBlank(char[] str, int usedLength) {
        if(str == null || str.length < usedLength) {
            return -1;
        }

        //统计字符数组中的空白字符数
        int whiteCount = 0;
        for (int i = 0; i < usedLength; i++) {
            if(str[i] == ' ') {
                whiteCount++;
            }
        }

        //计算转换后的字符长度是多少
        int targetLength = whiteCount * 2 + usedLength;
        int tmp = targetLength; //保存长度结果用于返回
        if(targetLength > str.length) { //如果转换后的长度大于数组的最大长度，直接返回失败
            return -1;
        }

        //如果没有空白字符就不用处理
        if(whiteCount == 0) {
            return usedLength;
        }

        usedLength--; //从后往前，第一个开始处理的字符
        targetLength--; //处理后的字符放置的位置

        //字符中有空白字符，一直处理到所有的空白字符处理完
        while(usedLength >= 0 && usedLength < targetLength) {
            //如果当前字符是空白字符，进行“%20”替换
            if(str[usedLength] == ' ') {
                str[targetLength--] = '0';
                str[targetLength--] = '2';
                str[targetLength--] = '%';
            }else { //否则移动字符
                str[targetLength--] = str[usedLength];
            }
            usedLength--;
        }

        return tmp;
    }
```



 