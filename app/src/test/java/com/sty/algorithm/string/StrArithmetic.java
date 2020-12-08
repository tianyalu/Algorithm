package com.sty.algorithm.string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 用JAVA实现字符串的简单四则运算
 * 例如：(12*3/-2)*(3+5)/2 = -72
 * 应用场景：在计算器中输入一大串四则运算表达式，如果按下=号，计算出最后正确的结果
 *
 * 实现思路：
 * 一、没有括号表达式的实现
 *      将表达式拆分分别有序放入容器中（运算符放在符号容器中，数字放在数字容器<List>中），用List比较合适，因为要做有序操作：
 *  ①取运算符（从容器中移除）②取对应索引处的数字（从容器中移除）③计算并将计算结果放回（当前索引位置）
 * 二、有括号表达式的实现
 *      找到最内侧的括号表达式，按照无括号的方法进行计算，将计算结果替换该括号表达式的位置，此时得到新的表达式，再继续递归
 *  调用当前方法。
 * 参考：https://blog.csdn.net/qq_36838191/article/details/81353415
 * @Author: tian
 * @UpdateDate: 2020/12/8 3:54 PM
 */
public class StrArithmetic {
    public static void main(String[] args) {
        String operation = "-12*3/-2*3+5/2-5";
        String operation2 = "-12*4/(-2.5*4)+5/2.0-5";
        System.out.println(calcWithoutBracket(operation));
        System.out.println(-12*3/-2*3+5/2-5);
        System.out.println("----------------------");
        System.out.println(calcWithBracket(operation2));
        System.out.println(-12*4/(-2.5*4)+5/2.0-5);
    }

    /**
     * 不带括号的算术表达式运算
     * @param operation 算术表达式
     * @return 算术结果
     */
    public static String calcWithoutBracket(String operation) {
        //1.将运算表达式分离
        //1.1得到所有参与运算的运算符
        List<Character> operatorList = getOperator(operation);
        //1.2得到所有参与运算的数字
        List<Double> numberList = getNumber(operation);

        //2.遍历运算容器，完成乘除运算
        for (int i = 0; i < operatorList.size(); i++) {
            Character c = operatorList.get(i);
            if(c == '*' || c == '/') {
                //取得运算符--》把存储的运算符从符号容器中拿出来
                operatorList.remove(i);
                //取得运算符数字--》从数字容器中获得对应索引的字符
                Double numL = numberList.remove(i); //拿到运算符左侧数字
                Double numR = numberList.remove(i); //拿到运算符右侧数字
                //将运算结果放回--》将运算后的数放在数字容器索引处
                if(c == '*') {
                    numberList.add(i, numL * numR); //将运算后的数字添加到i的位置，当前i位置的数向后移动
                }else {
                    numberList.add(i, numL / numR); //将运算后的数字添加到i的位置，当前i位置的数向后移动
                }
                i--; //运算符容器的指针回调原来的位置，防止跳过下一个运算符
            }
        }

        //遍历运算容器，完成加减运算，当运算容器为空时，运算结束
        while (!operatorList.isEmpty()) {
            //获取运算符
            Character operator = operatorList.remove(0);
            //获取左右运算数字
            Double numberL = numberList.remove(0);
            Double numberR = numberList.remove(0);
            if(operator == '-') {
                numberL = numberL - numberR;
            }else if(operator == '+') {
                numberL = numberL + numberR;
            }
            //将运算结果放回到数字容器中
            numberList.add(0, numberL);
        }
        return numberList.get(0).toString();
    }

    /**
     * 带括号的算术表达式计算
     * @param operation 算术表达式
     * @return 运算结果
     */
    public static String calcWithBracket(String operation) {
        //从表达式中查找左右括号
        int leftIndex = operation.lastIndexOf('(');
        if(leftIndex == -1) { //没有左括号，调用calcWithoutBracket直接运算
            return calcWithoutBracket(operation);
        }else { //有括号
            //获取该括号右侧的第一个右括号
            int rightIndex = operation.indexOf(')', leftIndex);
            if(rightIndex == -1) {
                throw new RuntimeException("表达式语法错误");
            }else {
                //将两个括号之间的表达式截取
                String exp = operation.substring(leftIndex + 1, rightIndex);
                //将表达式计算
                String result = calcWithoutBracket(exp);
                //将计算结果替换左右括号包含的表达式，将表达式递归调用
                operation = operation.substring(0, leftIndex) + result + operation.substring(rightIndex + 1);
                return calcWithBracket(operation);
            }
        }
    }

    /**
     * 从表达式获得所有参与运算的数字
     * @param operation 运算表达式
     * @return 数字的list集合
     */
    private static List<Double> getNumber(String operation) {
        operation = changeMinus(operation);
        //将字符串按照运算符切割，得到字符串数组
        Pattern pattern = Pattern.compile("\\+|-|\\*|/");
        String[] split = pattern.split(operation);

        //遍历数组，判断每个元素，将特殊符号转换成负数符号，并转换成double放在list集合中
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String single = split[i];
            if(single.charAt(0) == '@') {
                single = "-" + single.substring(1);
            }
            list.add(Double.parseDouble(single));
        }
        return list;
    }

    /**
     * 得到表达式中所有的运算符
     * @param operation 运算表达式
     * @return 符号的List集合
     */
    private static List<Character> getOperator(String operation) {
        //将表达式中的负号替换为@符号(不替换减号)
        operation = changeMinus(operation);
        //将字符串按照正则表达式分组
        String regex = "@[0-9]|[0-9]";
        Pattern pattern = Pattern.compile(regex);
        String[] split = pattern.split(operation);

        //将数组元素放在集合中
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String temp = split[i].trim();
            if("+".equals(temp) || "-".equals(temp) || "*".equals(temp) || "/".equals(temp)) {
                list.add(temp.charAt(0));
            }
        }
        return list;
    }

    /**
     * 将表达式的负数符号'-'用@符号代替（注意：不替换运算符号减号）
     * @param operation 运算表达式
     * @return 替换后的表达式
     */
    private static String changeMinus(String operation) {
        //将表达式中的负数符号使用特殊符号替换，得到没有负数的字符串（注意：不替换运算符号减号）
        for (int i = 0; i < operation.length(); i++) {
            char c = operation.charAt(i);
            if(c == '-') {
                //判断第一个字符是否是负数
                if(i == 0) {
                    operation = "@" + operation.substring(1);
                }else {
                    //判断前一个字符是否是+-*/中的一个
                    char cprev = operation.charAt(i - 1);
                    if(cprev == '+' || cprev == '-' || cprev == '*' || cprev == '/') {
                        operation = operation.substring(0, i) + '@' + operation.substring(i + 1);
                    }
                }
            }
        }
        return operation;
    }
}
