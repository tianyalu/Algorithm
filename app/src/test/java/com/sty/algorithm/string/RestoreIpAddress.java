package com.sty.algorithm.string;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串恢复`IP`地址
 * 题目：
 *   Given a string containing only digits,restore it by returning all possible valid IP address combinations.
 *   For example: Given "25525511135",
 *   return ["255.255.11.135", "255.255.111.35"].(Order doesnot mater)
 *   给一个由数字组成的字符串，求出其可能恢复为的所有`IP`地址。
 *   注意：中间`IP`位置不能以0开始，`0.01.01.1`非法，应该是`0.0.101.1`或者`0.0.10.11`。
 *
 * 算法思路：
 *   方法一：直接三重循环暴力求解；
 *   方法二：深度搜索，回溯。
 *
 * 参考：https://github.com/LRH1993/android_interview/blob/master/algorithm/LeetCode/String/ip.md
 * @Author: tian
 * @UpdateDate: 2020/12/14 9:34 AM
 */
public class RestoreIpAddress {

    public static void main(String[] args) {
        //String s = "25525511135";
        String s = "001011";
        //printArr(restoreIPAddress1(s));
        printArr(restoreIPAddress2(s));
    }

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

    private static void printArr(List<String> res) {
        if(res == null || res.isEmpty()) {
            System.out.println("没有找到符合要求的数据");
            return;
        }
        for (String re : res) {
            System.out.println(re);
        }
    }


    //------------------------------------------------- 方法二 --------------------------------------
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
}
